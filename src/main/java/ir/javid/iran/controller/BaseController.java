package ir.javid.iran.controller;

import ir.javid.iran.dtos.RegisterNewDriver;
import ir.javid.iran.model.Driver;
import ir.javid.iran.service.DriverService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * author: Mr.javidmolaei
 */

@Controller
public class BaseController {

    private final DriverService driverService;

    @Autowired
    public BaseController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping({"/",""})
    public String index() {
        return "welcome/index";
    }

    @GetMapping("/welcome")
    public String greeting() {
        return "welcome/index";
    }

    @GetMapping("/dashboard")
    public String dashboardLogin() {
        return "dashboard/index";
    }

    @GetMapping("/login")
    public String login(){
        return "authentication/login";
    }

    @GetMapping("/success")
    public String successNotif(){
        return "notif/success";
    }

    @GetMapping("/unsuccess")
    public String unsuccessNotif(){
        return "notif/unsuccess";
    }

    @GetMapping("/successcode")
    public String successCode(){
        return "notif/successCode";
    }

    @RequestMapping(value = "/panel", method = RequestMethod.GET)
    public String daashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.printf("auth : " + auth.getAuthorities());
        model.addAttribute("username", auth.getName());

//        if (auth.getAuthorities().toArray()[0].toString().equals("SUPER_ADMIN")) {
//            model.addAttribute("role", "مدیر ارشد سیستم");
//            return "dashboard/admin";
//        }
//        if (auth.getAuthorities().toArray()[0].toString().equals("DRIVER")) {
//            model.addAttribute("role", "راننده حودرو");
//            return "dashboard/driver/main";
//        }

        if (auth.getAuthorities().toArray()[0].toString().equals("MARKETER")) {
            model.addAttribute("role", "کارشتاس بازاریابی");
            return "dashboard/marketer/main";
        }
//        if (auth.getAuthorities().toArray()[0].toString().equals("TRANSPORTATION_INTERNAL")) {
//            model.addAttribute("role", "حمل و نقل");
//            return "dashboard/transporter/main";
//        }
//        if (auth.getAuthorities().toArray()[0].toString().equals("PRODUCT_OWNER")) {
//            model.addAttribute("role", "مالک محصول");
//            return "dashboard/productowner/main";
//        }

        return "redirect:/welcome";
    }
}
