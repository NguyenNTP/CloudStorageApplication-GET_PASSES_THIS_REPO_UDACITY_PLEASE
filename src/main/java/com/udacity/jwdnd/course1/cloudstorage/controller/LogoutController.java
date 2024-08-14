package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/logout")
public class LogoutController {

    @PostMapping()
    public String logout(HttpServletResponse response) {
        Cookie cookieRemove = new Cookie("JSESSIONID", "");
        cookieRemove.setMaxAge(0);
        response.addCookie(cookieRemove);
        return "redirect:/login?logout";
    }
}
