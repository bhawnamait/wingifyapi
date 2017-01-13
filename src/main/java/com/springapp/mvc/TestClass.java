package com.springapp.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by bhawna on 12/01/17.
 */
@Controller
@RequestMapping("/")
public class TestClass {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String AddItem() {
        return "ok";
    }
}



