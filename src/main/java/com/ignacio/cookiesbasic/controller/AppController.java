package com.ignacio.cookiesbasic.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class AppController {

    ModelAndView mAV = new ModelAndView();
    private String user = "ignacio";
    private String pass = "1234";

    @GetMapping("/")
    public ModelAndView index(HttpServletRequest request) {
        // Check cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user")) {
                    mAV.addObject("user", cookie.getValue());
                    mAV.setViewName("main");
                    return mAV;
                }
            }
        }
        // If cookie is not set, redirect to login
        mAV.setViewName("redirect:/login");
        return mAV;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        mAV.setViewName("login");
        return mAV;
    }

    @PostMapping("/login")
    public ModelAndView login(
            @RequestParam String username,
            @RequestParam String password,
            HttpServletResponse response) {

        System.out.println("username: " + username);
        System.out.println("password: " + password);

        if (username.equals(user) && password.equals(pass)) {
            mAV.setViewName("redirect:/");

            // Set cookie
            Cookie cookie = new Cookie("user", username);
            response.addCookie(cookie);

        } else {
            mAV.setViewName("redirect:/login");
        }

        return mAV;
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletResponse response) {
        mAV.setViewName("redirect:/login");

        // Delete cookie
        Cookie cookie = new Cookie("user", "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return mAV;
    }

}
