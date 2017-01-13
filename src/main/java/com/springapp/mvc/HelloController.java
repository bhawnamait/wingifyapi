package com.springapp.mvc;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;

@Controller

@RequestMapping("/user")
public class HelloController extends BaseController {

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public String index(@CookieValue(value = "token", required = false) String cookie) throws SQLException {

        ArrayList<String> err = new ArrayList<String>();
        Gson gson = new Gson();
        ReturnObject object = new ReturnObject();

        ArrayList<Item> a = null;
        System.out.print(cookie);
        if (cookie != null) {
            //  cookies[i].setValue("null");
            UserQuery d = new UserQuery();

            a = d.View();

        }
        object.data = a;
        object.access = true;
        String json = gson.toJson(object);
        return json;
    }

    @RequestMapping(value = "/logout")
    @ResponseBody
    public String loggedOut(HttpServletResponse response) {


        Cookie cookie = new Cookie("token", "");
        cookie.setPath("/");
        cookie.setMaxAge(1000000000);
        response.addCookie(cookie);

        return new Gson().toJson(new Response("Successfully Logged out", null));
    }
}