package com.theironyard.controllers;

import com.theironyard.entities.User;
import com.theironyard.services.BbqRepository;
import com.theironyard.services.UserRepository;
import com.theironyard.utils.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

/**
 * Created by Ben on 6/23/16.
 */
@Controller
public class BBQTrackerController {
    @Autowired
    UserRepository users;
    @Autowired
    BbqRepository bbqs;



    @RequestMapping(path="/", method = RequestMethod.GET)
    public String home(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        model.addAttribute("bbqs", bbqs.findAll());
        model.addAttribute("username", username);
        model.addAttribute("now", LocalDateTime.now());
        return "home";
    }

    @RequestMapping(path="/login", method = RequestMethod.POST)
    public String login(HttpSession session, String username, String password) throws Exception {
        User user = users.findByName(username);
        if (user == null) {
            user = new User(username, PasswordStorage.createHash(password));
            users.save(user);
        }
        else if(!PasswordStorage.verifyPassword(password, user.getPassword())) {
            throw new Exception("Wrong Password!");
        }
        session.setAttribute("username", username);
        return "redirect:/";
    }

    @RequestMapping(path="/logout", method = RequestMethod.POST)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

}
