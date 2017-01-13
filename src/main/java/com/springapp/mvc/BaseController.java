package com.springapp.mvc;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

public class BaseController {
    @ExceptionHandler
    public
    @ResponseBody
    String defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {

        return new Gson().toJson(new Response("", "error"));
    }
}