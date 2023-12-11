package com.ufncc.workorder.Controller;

import com.ufncc.workorder.Model.Order;

import com.ufncc.workorder.Repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping(path = "/solution")
public class SolutionController {

    @Autowired
    private OrderRepository orderRepo;

    @GetMapping("/list")
    public String listOrders(@ModelAttribute Order order, Model model){
        // Get all users from repo and save it on a list.
        List<Order> oderList = (List<Order>) orderRepo.findAll();
        model.addAttribute("orders", oderList);

        return "solution/solutionRead";
    }

    @GetMapping("/update/{id}")
    public String getUpdateOrderData(Model model, @PathVariable Long id){

        // SELECT * FROM {table} WHERE ID = {id}
        Order getOrder = orderRepo.findById(Math.toIntExact(id)).get();
        System.out.println("Fetched order: "+getOrder);

        // Get getOrder as order
        model.addAttribute("order", getOrder);

        // Redirects user to solutionUpdate.html's form.
        return "solution/solutionUpdate";
    }

    @PostMapping("/update")
    public String setUpdateOrderData(@ModelAttribute Order newOrder, Model model){

        // UPDATE {order} SET {newOrder} WHERE ID {newOrder.getId()}
        orderRepo.save(newOrder);
        System.out.println("Order solution saved: "+newOrder);

        // Update order's list.
        List<Order> orderList = (List<Order>) orderRepo.findAll();
        model.addAttribute("orders", orderList);

        // Redirects user to solutionRead.html
        return "solution/solutionRead";
    }

}
