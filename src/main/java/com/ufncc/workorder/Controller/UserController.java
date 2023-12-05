package com.ufncc.workorder.Controller;

import com.ufncc.workorder.Model.User;
import com.ufncc.workorder.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping(path = "/user")
public class UserController {

    // Marking attribute for User's CRUD.
    @Autowired
    private UserRepository userRepo;

    // HTML from for User registration, Role: All
    @GetMapping("/sign-up")
    public String createUser(Model model){
        // Instance of a new User.
        model.addAttribute("user", new User());

        // Send user to userCreate.html form.
        return "/user/userCreate";
    }

    @PostMapping("/sign-up/success")
    public String saveUser(@ModelAttribute User usr, Model model){
        // Prints out the new user and saves on its repository.
        System.out.println(usr.toString());
        userRepo.save(usr);

        // Get all users from repo and save it on a list.
        List<User> userList = (List<User>) userRepo.findAll();
        model.addAttribute("user", userList);

        // Redirects user for the login form.
        return "sign-in";
    }

    // User listing on an HTML Table, Role: 'TECNICO'
    @GetMapping("/user/list")
    public String listUser(@ModelAttribute User usr, Model model){
        // Get all users from repo and save it on a list.
        List<User> userList = (List<User>) userRepo.findAll();
        model.addAttribute("users", userList);

        return "/user/userRead";
    }






}
