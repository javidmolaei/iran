package ir.javid.iran.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mashape.unirest.http.exceptions.UnirestException;
import ir.javid.iran.SmsPanel.MySmsService;
import ir.javid.iran.dtos.MarketerDriverDto;
import ir.javid.iran.dtos.MarketerProductOwnerDto;
import ir.javid.iran.email.EmailService;
import ir.javid.iran.helper.UploadFile.FileService;
import ir.javid.iran.model.*;
import ir.javid.iran.service.*;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.*;
import java.util.regex.Pattern;

@Controller
@RequestMapping(name = "/")
public class RegisterController {

    org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder BCryptPasswordEncoder = new BCryptPasswordEncoder();
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
    private final StateService stateService;

    @Autowired
    public RegisterController(UserService userService, RoleService roleService, DriverService driverService, MarketerService marketerService, ProductOwnerService productOwnerService, TransportationService transportationService, TempService tempService, MySmsService mySmsService, EmailService emailService, FileService fileService, StateService stateService) {
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

    @RequestMapping(value = "/register/driver", method = RequestMethod.GET)
    public String registerDriver(Model model, Principal principal) {
        try {
            model.addAttribute("username", principal.getName());
            model.addAttribute("role", "بازاریاب");
            model.addAttribute("driver", new MarketerDriverDto());
            model.addAttribute("driverPicture", new Object());
            model.addAttribute("states", stateService.getAllStates());

            return "dashboard/marketer/registerDriver";
        } catch (Exception e) {
            return "notif/unsuccess";
        }
    }

    @RequestMapping(value = "/register/driver", method = RequestMethod.POST)
    public String registerDriver(@ModelAttribute(name = "user") MarketerDriverDto marketerDriverDto,
                                 BindingResult bindingResult) throws IOException, InvocationTargetException, IllegalAccessException, UnirestException {
        try {
            if (bindingResult.hasErrors())
                return "notif/unsuccess";
            if (!Pattern.matches("^[0-9]{0,20}$", marketerDriverDto.getPhoneNumber()))
                throw new RuntimeException("invalid input");

            marketerDriverDto.setUsername(marketerDriverDto.getPhoneNumber());
            marketerDriverDto.setProvider(Provider.LOCAL);
            marketerDriverDto.setPassword(BCryptPasswordEncoder.encode(marketerDriverDto.getPassword()));

            User registerUser = userService.RegisterUser(this.convertMarketerDriverToUser(marketerDriverDto));

            Driver registerDriver = this.convertMarketerDriverToDriver(marketerDriverDto);
            registerDriver.setUser(registerUser);
            registerDriver.setAdminConfirmation(false);

            String tempCode = tempService.saveTempCode(registerUser);
            String message = "کاربر گرامی، کد ورود شما به ایران لج " + tempCode + " می باشد.";
            List<String> recipient = new ArrayList<>();
            recipient.add(registerUser.getPhoneNumber());
//            mySmsService.sendSms(new SmsDto(recipient, message));

            if (driverService.saveDriver(registerDriver)) return "notif/success";
            else return "notif/userRegExistsError";

        } catch (Exception e) {
            return "notif/unsuccess";
        }

    }

    private User convertMarketerDriverToUser(MarketerDriverDto marketerDriverDto) {
        User newDriverUser = new User();
        newDriverUser.setUsername(marketerDriverDto.getUsername());
        newDriverUser.setName(marketerDriverDto.getName());
        newDriverUser.setFamily(marketerDriverDto.getFamily());
        newDriverUser.setFatherName(marketerDriverDto.getFatherName());
        newDriverUser.setPhoneNumber(marketerDriverDto.getPhoneNumber());
        newDriverUser.setPostalCode(marketerDriverDto.getPostalCode());
        newDriverUser.setNationalCode(marketerDriverDto.getNationalCode());
        newDriverUser.setAddress(marketerDriverDto.getAddress());
        newDriverUser.setEmail(marketerDriverDto.getEmail());
        newDriverUser.setPassword(marketerDriverDto.getPassword());
        newDriverUser.setGender(marketerDriverDto.getGender());
        newDriverUser.setProvider(marketerDriverDto.getProvider());
//        newDriverUser.setRoles(new HashSet<>(5));
        return newDriverUser;
    }

    private Driver convertMarketerDriverToDriver(MarketerDriverDto marketerDriverDto) {
        Driver newDriver = new Driver();

        newDriver.setLicenseNumber(marketerDriverDto.getLicenseNumber());
        newDriver.setTransitPlate(marketerDriverDto.getTransitPlate());
        newDriver.setActivityType(marketerDriverDto.getActivityType());
        newDriver.setTruckInformation(marketerDriverDto.getTruckInformation());
        newDriver.setDriverPicture(marketerDriverDto.getDriverPicture());
        newDriver.setDriverPictureFile(marketerDriverDto.getDriverPictureFile());
        newDriver.setDriverPictureString(marketerDriverDto.getDriverPictureString());
        newDriver.setNationalCardPicture(marketerDriverDto.getNationalCardPicture());
        newDriver.setNationalCardPictureFile(marketerDriverDto.getNationalCardPictureFile());
        newDriver.setNationalCardPictureString(marketerDriverDto.getNationalCardPictureString());
        newDriver.setLicenseCardPicture(marketerDriverDto.getLicenseCardPicture());
        newDriver.setLicenseCardPictureFile(marketerDriverDto.getLicenseCardPictureFile());
        newDriver.setLicenseCardPictureString(marketerDriverDto.getLicenseCardPictureString());
        newDriver.setCarCartPicture(marketerDriverDto.getCarCartPicture());
        newDriver.setCarCartPictureFile(marketerDriverDto.getCarCartPictureFile());
        newDriver.setCarCartPictureString(marketerDriverDto.getCarCartPictureString());
        newDriver.setTruckType(marketerDriverDto.getTruckType());
        newDriver.setTruckUsage(marketerDriverDto.getTruckUsage());
        newDriver.setTruckModel(marketerDriverDto.getTruckModel());
        newDriver.setPlateNumber(marketerDriverDto.getPlateNumber());
        newDriver.setSmartCardNumber(marketerDriverDto.getSmartCardNumber());
        newDriver.setCommitmentConfirmation(marketerDriverDto.getCommitmentConfirmation());
        newDriver.setPlacesActivity(marketerDriverDto.getPlacesActivity());
        newDriver.setFavoritePlace(marketerDriverDto.getFavoritePlace());
        newDriver.setAdminConfirmation(marketerDriverDto.getAdminConfirmation());
        newDriver.setState(marketerDriverDto.getState());
        newDriver.setUser(marketerDriverDto.getUser());

        return newDriver;
    }

    //###############################################################################

    @RequestMapping(value = "/register/productowner", method = RequestMethod.GET)
    public String registerProductOwner(Model model, Principal principal) {
        try {
            model.addAttribute("username", principal.getName());
            model.addAttribute("role", "کارشناس بازاریابی");
//            model.addAttribute("driverPicture", new Object());
            model.addAttribute("productowner", new MarketerProductOwnerDto());
            model.addAttribute("states", stateService.getAllStates());
            return "dashboard/marketer/registerProductOwner";
        } catch (Exception e) {
            return "notif/unsuccess";
        }
    }

    @RequestMapping(value = "/register/productowner", method = RequestMethod.POST)
    public String registerProductOwner(@ModelAttribute(name = "productowner") MarketerProductOwnerDto marketerProductOwnerDto, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors())
                return "notif/unsuccess";
            if (!Pattern.matches("^[0-9]{0,20}$", marketerProductOwnerDto.getPhoneNumber()))
                throw new RuntimeException("invalid input");

            marketerProductOwnerDto.setUsername(marketerProductOwnerDto.getPhoneNumber());
            marketerProductOwnerDto.setProvider(Provider.LOCAL);
            marketerProductOwnerDto.setPassword(BCryptPasswordEncoder.encode(marketerProductOwnerDto.getPassword()));

            if ((userService.findByPhone(marketerProductOwnerDto.getPhoneNumber())) != null)
                return "notif/userRegExistsError";
            User registerUser = userService.RegisterUser(this.convertMarketerProductOwnerToUser(marketerProductOwnerDto));

            ProductOwner registerProductOwner = this.convertMarketerProductOwnerToProductOwner(marketerProductOwnerDto);
            registerProductOwner.setUser(registerUser);
            registerProductOwner.setAdminConfirmation(false);

            String tempCode = tempService.saveTempCode(registerUser);
            String message = "کاربر گرامی، کد ورود شما به ایران لج " + tempCode + " می باشد.";
            List<String> recipient = new ArrayList<>();
            recipient.add(registerUser.getPhoneNumber());
//            mySmsService.sendSms(new SmsDto(recipient, message));

            if (productOwnerService.saveProductOwner(registerProductOwner)) return "notif/success";
            else return "notif/userRegExistsError";
        } catch (Exception e) {
            e.fillInStackTrace();
            return "notif/unsuccess";
        }

    }

    private User convertMarketerProductOwnerToUser(MarketerProductOwnerDto productOwnerDto) {
        User newDriverUser = new User();
        newDriverUser.setUsername(productOwnerDto.getUsername());
        newDriverUser.setName(productOwnerDto.getName());
        newDriverUser.setFamily(productOwnerDto.getFamily());
        newDriverUser.setFatherName(productOwnerDto.getFatherName());
        newDriverUser.setPhoneNumber(productOwnerDto.getPhoneNumber());
        newDriverUser.setPostalCode(productOwnerDto.getPostalCode());
        newDriverUser.setNationalCode(productOwnerDto.getNationalCode());
        newDriverUser.setAddress(productOwnerDto.getAddress());
        newDriverUser.setEmail(productOwnerDto.getEmail());
        newDriverUser.setPassword(productOwnerDto.getPassword());
        newDriverUser.setGender(productOwnerDto.getGender());
        newDriverUser.setProvider(productOwnerDto.getProvider());
//        newDriverUser.setRoles(new HashSet<>(5));
        return newDriverUser;
    }

    private ProductOwner convertMarketerProductOwnerToProductOwner(MarketerProductOwnerDto productOwnerDto) {
        ProductOwner productOwner = new ProductOwner();
        productOwner.setUser(productOwnerDto.getUser());
        productOwner.setAdminConfirmation(productOwnerDto.getAdminConfirmation());
        productOwner.setSignatureOwners(productOwnerDto.getSignatureOwners());
        productOwner.setState(productOwnerDto.getState());
        productOwner.setActivityLicensePicture(productOwnerDto.getActivityLicensePicture());
        productOwner.setActivityLicensePictureText(productOwnerDto.getActivityLicensePictureText());
        productOwner.setActivityLicensePictureFile(productOwnerDto.getActivityLicensePictureFile());
        productOwner.setCompanyLogoFile(productOwnerDto.getCompanyLogoFile());
        productOwner.setCompanyLogo(productOwnerDto.getCompanyLogo());
        productOwner.setCompanyLogoText(productOwnerDto.getCompanyLogoText());
        productOwner.setCeoName(productOwnerDto.getCeoName());
        productOwner.setEconomicCode(productOwnerDto.getEconomicCode());

        return productOwner;
    }

    //###############################################################################


}

