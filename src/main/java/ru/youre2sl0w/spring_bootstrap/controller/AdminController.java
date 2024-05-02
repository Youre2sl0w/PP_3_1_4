package ru.youre2sl0w.spring_bootstrap.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.youre2sl0w.spring_bootstrap.service.RoleService;
import ru.youre2sl0w.spring_bootstrap.service.UserService;
import ru.youre2sl0w.spring_bootstrap.entity.User;
import ru.youre2sl0w.spring_bootstrap.entity.Role;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @GetMapping()
    public String adminPage(Model model, Principal principal) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("currentUser", userService.findByUsername(principal.getName()));
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("newUser", new User());
        return "admin-panel";
    }

    @GetMapping("/user-create")
    public String addUserForm(Model model, Principal principal) {
        model.addAttribute("currentUser", userService.findByUsername(principal.getName()));
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("user", new User());
        return "user-create";
    }

    @PostMapping()
    public String addUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,
                          @ModelAttribute("currentUser") User currentUser,
                          @ModelAttribute("roles") List<Role> roles) {
        if (bindingResult.hasErrors()) {
            return "redirect:/admin";
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/user-delete")
    public String deleteUser(@RequestParam(value = "id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @PatchMapping("/user-update")
    public String updateUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/admin";
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }
}