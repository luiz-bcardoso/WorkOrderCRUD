package com.ufncc.workorder.Controller;

import com.ufncc.workorder.Model.Order;
import com.ufncc.workorder.Model.User;
import com.ufncc.workorder.Repository.OrderRepository;

import com.ufncc.workorder.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/list")
    public String listOrders(@ModelAttribute Order order, Model model){
        // Get all users from repo and save it on a list.
        List<Order> oderList = (List<Order>) orderRepo.findAll();
        model.addAttribute("orders", oderList);

        return "order/orderRead";
    }

    @GetMapping("/create")
    public String registerUser(Model model){

        //Gets current auth username
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        System.out.println("Logged username: "+name);

        //Uses current username to locate on repository and get User to constructor
        Optional<User> optUser = userRepo.findByUsernameOrEmail(name,name);
        User user = optUser.get();
        System.out.println(user);

        //Gets current year-month-day as date for order's constructor.
        LocalDate currentDate = LocalDate.now();
        System.out.println("LocalDate: "+currentDate);

        //Order order = new Order();
        model.addAttribute("order", new Order(user, currentDate));

        // Send to the order's opening form.
        return "/order/orderCreate";
    }
    @PostMapping("/save")
    public String saveOrder(@ModelAttribute Order order, Model model){

        // Saves a new user on it's repository
        System.out.println("New order opened: "+order.toString());
        orderRepo.save(order);

        // Gets all orders plus the new one form the repository
        List<Order> oderList = (List<Order>) orderRepo.findAll();
        model.addAttribute("orders", oderList);

        // Redirects user to a list of orders.
        return "order/orderRead";
    }

    @GetMapping("/update/{id}")
    public String getUpdateOrderData(Model model, @PathVariable Long id){

        // SELECT * FROM {table} WHERE ID = {id}
        Order getOrder = orderRepo.findById(Math.toIntExact(id)).get();
        System.out.println("Fetched order: "+getOrder);

        // Get getOrder as order
        model.addAttribute("order", getOrder);

        // Redirects user to orderUpdate.html's form.
        return "order/orderUpdate";
    }

    @PostMapping("/update")
    public String setUpdateOrderData(@ModelAttribute Order newOrder, Model model){

        // UPDATE {table} SET {novaPessoa} WHERE ID {novaPessoa.getId()}
        newOrder.setSolution("");
        orderRepo.save(newOrder);
        System.out.println("Order saved: "+newOrder);

        // Update order's list.
        List<Order> orderList = (List<Order>) orderRepo.findAll();
        model.addAttribute("orders", orderList);

        // Redirects user to userRead.html
        return "order/orderRead";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrder(Model model, @PathVariable Long id){

        // DELETE FROM {table} WHERE ID = {id}
        orderRepo.deleteById(Math.toIntExact(id));

        // Get all users from repo and save it on a list.
        List<Order> oderList = (List<Order>) orderRepo.findAll();
        model.addAttribute("orders", oderList);

        // Redirects user to userRead.html
        return "order/orderRead";
    }


}
