package com.theironyard.controllers;

import com.theironyard.entities.Bbq;
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
        Boolean editVis = (Boolean) session.getAttribute("editvis");
        Iterable<Bbq> bbqList = bbqs.findAll();
        Boolean userPost;


        //credit to dan voss for assistance in making this code work
        //sets visibility boolean for post ownership re: edit and delete buttons
        for (Bbq b: bbqList) {
            if (username!= null && username.equals(b.getUser().getName())) {
                userPost = true;
            } else{
                userPost = false;
            }

            model.addAttribute("userpost", userPost);
        }

        model.addAttribute("bbqs", bbqList);
        model.addAttribute("username", username);
        model.addAttribute("now", LocalDateTime.now());
        model.addAttribute("editvis", editVis);

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

    @RequestMapping(path="/create-bbq", method = RequestMethod.POST)
    public String createBbq (HttpSession session, String name, String startTime, String cookTime) {
        String username = (String) session.getAttribute("username");
        User user = users.findByName(username);
        Bbq bbq = new Bbq(name, Double.valueOf(cookTime), LocalDateTime.parse(startTime), user);
        bbqs.save(bbq);
        return "redirect:/";
    }

    @RequestMapping(path="/delete-bbq", method = RequestMethod.POST)
    public String deleteBbq(int deleteid) {
        bbqs.delete(deleteid);
        return "redirect:/";
    }

    @RequestMapping(path="/edit-bbq", method = RequestMethod.POST)
    public String editBbq(HttpSession session, String name, String startTime, String cookTime) {
        String username = (String) session.getAttribute("username");
        boolean editVis = false;
        session.setAttribute("editvis", editVis);
        User user = users.findByName(username);
        Integer editId = (Integer)(session.getAttribute("editid"));
        Bbq bbq = new Bbq(editId, name, Double.valueOf(cookTime), LocalDateTime.parse(startTime), user);
        bbqs.save(bbq);

        return "redirect:/";
    }

    @RequestMapping(path="/edit-button", method = RequestMethod.POST)
    public String editButton (HttpSession session, Integer editid) {
        String username = (String) session.getAttribute("username");
        User user = users.findByName(username);
        boolean editVis = true;
        session.setAttribute("editvis", editVis);
        session.setAttribute("editid", editid);
        return "redirect:/";
    }

}
