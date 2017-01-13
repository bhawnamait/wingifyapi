package com.springapp.mvc;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by bhawna on 12/01/17.
 */
public class Interceptor extends HandlerInterceptorAdapter {


    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        if (request.getRequestURI().startsWith(SysProperties.getInstance().getProperty("BY_PASS_URL"))) {
            return true;
        }

        Cookie[] c = request.getCookies();
        UserQuery userQuery = new UserQuery();
        if (c != null) {
            for (Cookie cookie : c) {
                if (cookie.getName().equals("token")) {
                    String value = cookie.getValue();
                    if (userQuery.checkIfUsernameExist(value)) {
                        int user_id = userQuery.getId(value);
                        if (user_id == -1) {

                        } else {
                            return true;
                        }
                    }
                }
            }
        }

        try {
            response.sendRedirect(SysProperties.getInstance().getProperty("R_URL"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}



