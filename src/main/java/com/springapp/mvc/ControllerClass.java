package com.springapp.mvc;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by bhawna on 12/01/17.
 */
@Controller
@RequestMapping("/item")
public class ControllerClass extends BaseController {
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @ResponseBody
    public String AddItem(@CookieValue(value = "token", required = false) String cookie, @RequestParam("name") String name,
                          @RequestParam("about") String about, @RequestParam("price") int price) {
        String json = null;
        UserQuery d = new UserQuery();
        if (d.checkIfUsernameExist(cookie)) {
            Integer p = price;
            int user_id = d.getId(cookie);
            d.addItem(name, about, p);
            return new Gson().toJson(new Item(name, p, about, user_id));
        } else {

        }
        return json;
    }

    @RequestMapping(value = "/search/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String searchItem(@PathVariable("id") String id, @CookieValue(value = "token", required = false) String cookie) {
        UserQuery d = new UserQuery();
        ReturnObject object = new ReturnObject();
        ArrayList<Item> a;
        Gson gson = new Gson();
        a = d.searchItem(id);
        object.data = a;
        object.access = true;
        object.message = "error not found";
        return gson.toJson(object);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String deleteItem(@PathVariable("id") String name, @CookieValue(value = "token", required = false) String cookie) {
        UserQuery d = new UserQuery();
        if (d.checkIfUsernameExist(cookie)) {
            int user_id = d.getId(cookie);
            int id = d.getItemId(user_id, name);
            try {
                if (id != -1) {
                    if (d.deleteItem(id)) {
                        return new Gson().toJson(new Response("Item deleted", ""));
                    } else {
                        return new Gson().toJson(new Response("Item not deleted", "Server error"));
                    }
                } else {
                    return new Gson().toJson(new Response("Item not deleted", "Item doesn't exists"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return new Gson().toJson(new Response("Item not deleted", "Item doesn't exists"));
    }
}



