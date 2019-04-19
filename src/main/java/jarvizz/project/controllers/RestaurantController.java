package jarvizz.project.controllers;

import jarvizz.project.models.Food;
import jarvizz.project.models.Type;
import jarvizz.project.models.User;
import jarvizz.project.sevices.FoodService;
import jarvizz.project.sevices.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class RestaurantController {

    FoodService foodService;
    UserService userService;

    @GetMapping("/restaurant")
    public List<Food> restaurant() {
        return foodService.findAll();
    }

    @GetMapping("/restaurant/product-category/{category}")
    public List<Food> category(@PathVariable("category") String category) {
        Type type = Type.valueOf(category.toUpperCase());
        System.out.println(type);
        return foodService.findAllByType(type);
    }

    @PostMapping("/addFood")
    public void add(@RequestHeader("item") String item) {
        System.out.println(item);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user =userService.findByName(name);
        Food food = foodService.findById(Integer.parseInt(item));
        String description = food.getDescription();
        System.out.println(description);
        food.setUser(user);
        foodService.save(food);
    }
}