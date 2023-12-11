package com.ufncc.workorder.Controller;

import com.ufncc.workorder.Model.User;
import com.ufncc.workorder.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    // User listing on an HTML Table, Role: 'ROLE_ADMIN'
    @GetMapping("/list")
    public String listUser(@ModelAttribute User usr, Model model){
        // Get all users from repo and save it on a list.
        List<User> userList = (List<User>) userRepo.findAll();
        model.addAttribute("users", userList);

        return "user/userRead";
    }

    @GetMapping("/update/{id}")
    public String getUpdateUserData(Model model, @PathVariable Long id){

        // SELECT * FROM {table} WHERE ID = {id}
        User getUser = userRepo.findById(Math.toIntExact(id)).get();

        // Get user as user
        model.addAttribute("user", getUser);

        // Redirects user to userUpdate.html's form.
        return "user/userUpdate";
    }

    @PostMapping("/update")
    public String setUpdateUserData(@ModelAttribute User newUser, Model model){

        // Encrypts password with BCrypto and saves it onto the object
        newUser.setPassword(new BCryptPasswordEncoder().encode(newUser.getPassword()));

        // UPDATE {table} SET {novaPessoa} WHERE ID {novaPessoa.getId()}
        userRepo.save(newUser);

        // Update user's list.
        List<User> userList = (List<User>) userRepo.findAll();
        model.addAttribute("users", userList);

        // Redirects user to userRead.html
        return "user/userRead";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(Model model, @PathVariable Long id){

        // DELETE FROM {table} WHERE ID = {id}
        userRepo.deleteById(Math.toIntExact(id));

        // Get all users from repo and save it on a list.
        List<User> userList = (List<User>) userRepo.findAll();
        model.addAttribute("users", userList);

        // Redirects user to userRead.html
        return "user/userRead";
    }
}
