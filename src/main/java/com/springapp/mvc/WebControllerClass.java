package com.springapp.mvc;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.security.auth.login.LoginException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by bhawna on 12/01/17.
 */
@Controller
@RequestMapping("/web")
public class WebControllerClass extends BaseController {


    @RequestMapping(value = "/register", method = RequestMethod.GET)
    @ResponseBody
    public String registeration(@RequestParam("username") String username,
                                @RequestParam("password") String password) {
        UserQuery d = new UserQuery();
        boolean success = d.newUser(username, password);
        return new Gson().toJson(new Response(success ? username + " is successfully registered now" : null, !success ? "Not allowed" : null));
    }

    @RequestMapping(value = "/test")
    @ResponseBody
    public String test(HttpServletRequest request) {
        return new Gson().toJson(new Response("hello", "No error ! not on this server"));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/login")
    @ResponseBody
    public String handleLogin(HttpServletResponse response,
                              @RequestParam("username") String username, @RequestParam("password") String password
    ) throws LoginException {
        UserQuery d = new UserQuery();
        if (d.isRegistered(username, password)) {
            String token = createCookie.randomString(10);
            Cookie cookie = new Cookie("token", token);
            cookie.setPath("/");
            cookie.setMaxAge(1000000000);
            response.addCookie(cookie);
            d.setToken(username, password, token);
            return new Gson().toJson(new Response(username + " is successfully logged in", null));
        } else {
            return new Gson().toJson(new Response("Username or password is incorrect", null));
        }
    }
}



