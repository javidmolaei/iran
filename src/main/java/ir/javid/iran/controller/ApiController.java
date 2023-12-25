package ir.javid.iran.controller;

import com.mashape.unirest.http.exceptions.UnirestException;
import ir.javid.iran.dtos.VerifyDto;
import ir.javid.iran.email.EmailDetails;
import ir.javid.iran.email.EmailService;
import ir.javid.iran.SmsPanel.MySmsService;
import ir.javid.iran.helper.UploadFile.FileInfo;
import ir.javid.iran.helper.UploadFile.FileService;
import ir.javid.iran.model.*;
import ir.javid.iran.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * author: Dr.mahyartolooie
 */

@Controller
@RequestMapping(name = "/")
public class ApiController {

    BCryptPasswordEncoder BCryptPasswordEncoder = new BCryptPasswordEncoder();
    private final UserService userService;
    private final RoleService roleService;
    private final DriverService driverService;
    private final MarketerService marketerService;
    private final ProductOwnerService productOwnerService;
    private final TransportationService transportationService;
    private final TempService tempService;
    private final MySmsService mySmsService;
    private final EmailService emailService;
    private final FileService fileService;
    private  final StateService stateService;

    @Autowired
    public ApiController(UserService userService, RoleService roleService,
                         DriverService driverService, MarketerService marketerService,
                         ProductOwnerService productOwnerService, TransportationService transportationService,
                         TempService tempService, MySmsService mySmsService,
                         EmailService emailService, FileService fileService, StateService stateService) {
        this.userService = userService;
        this.roleService = roleService;
        this.driverService = driverService;
        this.marketerService = marketerService;
        this.productOwnerService = productOwnerService;
        this.transportationService = transportationService;
        this.tempService = tempService;
        this.mySmsService = mySmsService;
        this.emailService = emailService;
        this.fileService = fileService;
        this.stateService = stateService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerPage(Model model) {
        try {
            model.addAttribute("user", new User());
            List<Role> persianRoles = new ArrayList<>();
            for (Role role : roleService.roles()) {
//                if (Objects.equals(role.getName(), "SUPER_ADMIN")) persianRoles.add(new Role(role.getId(), "مدیر ارشد"));
//                if (Objects.equals(role.getName(), "DRIVER")) persianRoles.add(new Role(role.getId(), "راننده"));
//                if (Objects.equals(role.getName(), "PRODUCT_OWNER")) persianRoles.add(new Role(role.getId(), "مالک محصول"));
                if (Objects.equals(role.getName(), "MARKETER")) persianRoles.add(new Role(role.getId(), "بازاریاب"));
            }
            model.addAttribute("rols", persianRoles);
            return "authentication/register";
        } catch (Exception e) {
            return "notif/unsuccess";
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@ModelAttribute(name = "user") User user,
                           BindingResult bindingResult) throws IOException, InvocationTargetException, IllegalAccessException, UnirestException {
        try {
            if (bindingResult.hasErrors())
                return "authentication/register";
            if (!Pattern.matches("^[0-9]{0,20}$", user.getPhoneNumber()))
                throw new RuntimeException("invalid input");
            user.setUsername(user.getPhoneNumber());
            user.setProvider(Provider.LOCAL);
            user.setPassword(BCryptPasswordEncoder.encode(user.getPassword()));
            User registerUser = userService.RegisterUser(user);
            String tempCode = tempService.saveTempCode(registerUser);
            String message = "کاربر گرامی، کد ورود شما به ایران لج " + tempCode + " می باشد.";
            List<String> recipient = new ArrayList<>();
            recipient.add(user.getPhoneNumber());
//            mySmsService.sendSms(new SmsDto(recipient, message));
            return "redirect:/login";
        } catch (Exception e) {
            return "notif/unsuccess";
        }

    }

    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public String twoStepVerification(Model model) {

        model.addAttribute("cod", new VerifyDto());
        return "authentication/two-step-verification";
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public String twoStepVerificationPost(@ModelAttribute(name = "cod") VerifyDto vrifyCode,
                                          BindingResult bindingResult) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String verify = vrifyCode.getValue1() + vrifyCode.getValue2() + vrifyCode.getValue3() + vrifyCode.getValue4();
        try {
            if (bindingResult.hasErrors())
                return "notif/unsuccess";
            User byPhone = userService.findByPhone(auth.getName());
            if (Objects.equals(verify, byPhone.getTempCode().getVerifyPhoneCode())) {
                byPhone.setVerifyPhone(true);
                return "notif/successCode";
            } else {
                return "notif/unsuccessCode";
            }
        } catch (Exception e) {
            return "notif/unsuccess";
        }
    }

    @RequestMapping(value = "/register/user/verifyNumber", method = RequestMethod.POST)
    public String verifyNumber(@ModelAttribute(name = "verify") String verify,
                               @ModelAttribute(name = "phoneNumber") String phoneNumber,
                               BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors())
                return "notif/unsuccess";
            User byPhone = userService.findByPhone(phoneNumber);
            if (Objects.equals(verify, byPhone.getTempCode().getVerifyPhoneCode())) {
                byPhone.setVerifyPhone(true);
                return "notif/successCode";
            } else {
                return "notif/unsuccessCode";
            }
        } catch (Exception e) {
            return "notif/unsuccess";
        }

    }

    @RequestMapping(value = "/register/user/email", method = RequestMethod.POST)
    public String email(@ModelAttribute(name = "userId") Long userId,
                        @ModelAttribute(name = "email") String email,
                        BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors())
                return "inja";
            if (!Pattern.matches("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", email))
                throw new RuntimeException("invalid input");
            User registerUser = userService.findById(userId);
            String tempCode = tempService.saveEmailCode(registerUser);
            String message = "کاربر گرامی، کد ورود شما به ایران لج " + tempCode + " می باشد.";
            EmailDetails details = new EmailDetails(email, message, "احراز هویت ایران لج");
            String sendSimpleMail = emailService.sendSimpleMail(details);
            if (Objects.equals(sendSimpleMail, "ok")) {
                return "bebaresh onja ke gham nist";
            } else {
                return "begaei pish amade";
            }
        } catch (Exception e) {
            return "yeja befrestesh khodet";
        }

    }

    @RequestMapping(value = "/register/user/verifyEmail", method = RequestMethod.POST)
    public String verifyEmail(@ModelAttribute(name = "verify") String verify,
                              @ModelAttribute(name = "email") String email,
                              BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors())
                return "inja";
            User byPhone = userService.findByEmail(email);
            if (Objects.equals(verify, byPhone.getTempCode().getVerifyEmailCode())) {
                byPhone.setVerifyEmail(true);
                return "bere onja k taeed shodas";
            } else {
                return "berin be yaro";
            }
        } catch (Exception e) {
            return "yeja befrestesh khodet";
        }

    }

//    @RequestMapping(value = "/register/driver", method = RequestMethod.GET)
//    public String driverget(Model model, Principal principal) {
//        try {
//            model.addAttribute("username", principal.getName());
//            model.addAttribute("role", "راننده");
//            model.addAttribute("driver", new Driver());
//            model.addAttribute("driverPicture", new Object());
//            model.addAttribute("states", stateService.getAllStates());
//
//            return "dashboard/driver/edit";
//        } catch (Exception e) {
//            return "notif/unsuccess";
//        }
//    }

//    @RequestMapping(value = "/register/driver", method = RequestMethod.POST)
//    public String driver(@ModelAttribute(name = "driver") Driver driver,
//                         BindingResult bindingResult
//    ) throws IOException {
//        try {
//            if (bindingResult.hasErrors())
//                return "notif/unsuccess";
//            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//            driver.setUser(userService.findByPhone(auth.getName()));
//            driver.setAdminConfirmation(false);
//            if (driverService.saveDriver(driver)) return "notif/success";
//            else return "notif/userRegExistsError";
//        } catch (Exception e) {
//            e.fillInStackTrace();
//            return "notif/unsuccess";
//        }
//
//    }

    //#############################################################
    @RequestMapping(value = "/register/marketer", method = RequestMethod.GET)
    public String marketerGet(Model model, Principal principal) {
        try {
            model.addAttribute("username", principal.getName());
            model.addAttribute("role", "بازاریاب");

            model.addAttribute("marketer", new Marketer());
            model.addAttribute("marketerPicture", new Object());
            model.addAttribute("states", this.stateService.getAllStates());
            return "dashboard/marketer/edit";
        } catch (Exception e) {
            return "notif/unsuccess";
        }
    }

    @RequestMapping(value = "/register/marketer", method = RequestMethod.POST)
    public String driver(@ModelAttribute(name = "marketer") Marketer marketer,
                         BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors())
                return "notif/unsuccess";
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            marketer.setUser(userService.findByPhone(auth.getName()));
            marketer.setAdminConfirmation(false);

            if (marketerService.saveMarketer(marketer)) return "notif/success";
            else return "redirect:/panel";
        } catch (Exception e) {
            e.fillInStackTrace();
            return "notif/unsuccess";
        }

    }

    //#############################################################
//    @RequestMapping(value = "/register/productowner", method = RequestMethod.GET)
//    public String productOwner(Model model ,Principal principal) {
//        try {
//            model.addAttribute("username", principal.getName());
//            model.addAttribute("role", "مالک محصول");
//
//            model.addAttribute("productowner", new ProductOwner());
//            model.addAttribute("states", stateService.getAllStates());
//            return "dashboard/productowner/edit";
//        } catch (Exception e) {
//            return "notif/unsuccess";
//        }
//    }
//    @RequestMapping(value = "/register/productowner", method = RequestMethod.POST)
//    public String productOwner(@ModelAttribute(name = "productowner") ProductOwner productOwner,
//                         BindingResult bindingResult) {
//        try {
//            if (bindingResult.hasErrors())
//                return "notif/unsuccess";
//            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//            productOwner.setUser(userService.findByPhone(auth.getName()));
//            productOwner.setAdminConfirmation(false);
//
//            if (productOwnerService.saveProductOwner(productOwner)) return "notif/success";
//            else return "redirect:/panel";
//        } catch (Exception e) {
//            e.fillInStackTrace();
//            return "notif/unsuccess";
//        }
//
//    }

    //#############################################################
    @RequestMapping(value = "/register/transporter", method = RequestMethod.GET)
    public String driver(Model model, Principal principal) {
        try {
            model.addAttribute("username", principal.getName());
            model.addAttribute("role", "مامور انتقال");

            model.addAttribute("transporter", new Transportation());
            return "dashboard/transporter/edit";
        } catch (Exception e) {
            return "notif/unsuccess";
        }
    }

    @RequestMapping(value = "/register/transporter", method = RequestMethod.POST)
    public String driver(@ModelAttribute(name = "transportation") Transportation transportation,
                         @ModelAttribute(name = "userId") Long userId,
                         @ModelAttribute(name = "activityLicensePicture") MultipartFile activityLicensePicture,
                         @ModelAttribute(name = "companyLogo") MultipartFile companyLogo,
                         BindingResult bindingResult) throws IOException {
        try {
            if (bindingResult.hasErrors())
                return "inja";
            transportation.setUser(userService.findById(userId));
            transportation.setAdminConfirmation(false);
            transportation.setActivityLicensePicture(multipartSave(activityLicensePicture));
            transportation.setCompanyLogo(multipartSave(companyLogo));
            transportationService.saveTransportation(transportation);
            return "onja";
        } catch (Exception e) {
            return "yeja befrestesh khodet";
        }
    }

    //#############################################################

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String pagepmUsers(Model model, @PageableDefault(size = 1000) Pageable pageable) {
        model.addAttribute("users", userService.findAll(pageable));
        return "/authentication/users";
    }

    public String multipartSave(MultipartFile multipartFile) throws IOException {
        FileInfo fileInfo = fileService.uploadSingleFile(multipartFile);
        return fileInfo.getFileLocation();
    }


}
