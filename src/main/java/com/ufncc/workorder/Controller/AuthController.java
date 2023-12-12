package com.ufncc.workorder.Controller;

import com.ufncc.workorder.Model.User;
import com.ufncc.workorder.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/welcome")
    public String greeting() {
        return "welcome";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String registerUser(Model model){

        //User user = new User();
        model.addAttribute("usr", new User());

        // Send to the user's register form.
        return "/user/userCreate";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute User user, Model model){

        if(userRepo.existsByUsername(user.getUsername())){
            // TODO: bad request for existing username on database
            System.out.println("ERR: Username already exists on repository/database");
        }

        else if(userRepo.existsByEmail(user.getEmail())){
            // TODO: bad request for existing email on database
            System.out.println("ERR: Email already exists on repository/database");
        }

        else{
            // Saves a new user on it's repository
            System.out.println("New user registered: "+user.toString());

            // Encrypts password with BCrypto and saves it onto the object
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            userRepo.save(user);
        }

        // Return to the login form.
        return "login";
    }

}
