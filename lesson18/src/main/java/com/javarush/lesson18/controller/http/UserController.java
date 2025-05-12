package com.javarush.lesson18.controller.http;

import com.javarush.lesson18.entity.Role;
import com.javarush.lesson18.dto.UserTo;
import com.javarush.lesson18.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
@SessionAttributes({"currentUser"})
public class UserController {

    private final UserService userService;

    @GetMapping()
    public ModelAndView showAllUsers(ModelAndView view) {
        view.addObject("users", userService.findAll());
        view.setViewName("userpage");
        view.addObject("roles", Role.values());
        return view;
    }


    @GetMapping("/{id}")
    public ModelAndView showOneUserAndUsers(ModelAndView view, @PathVariable("id") Long id) {
        Optional<UserTo> optionalUser = userService.get(id);
        if (optionalUser.isPresent()) {
            view.addObject("user", optionalUser.get());
            view.addObject("users", userService.findAll());
        }
        view.setViewName("userpage");
        view.addObject("roles", Role.values());
        return view;
    }

    @PostMapping()
    public String processNewUserOrLogin(UserTo userTo,
                                        HttpSession session,
                                        @RequestParam(required = false, name = "createUser") String createUser) {
        if (Objects.nonNull(createUser)) {
            userTo = userService.save(userTo);
            return "redirect:/users/%d".formatted(userTo.getId());
        } else {
            log.info(" user {} login ", userTo);
            session.setAttribute("currentUser", userTo);
            return "redirect:/users";
        }
    }


    @PostMapping("/{id}")
    public String updateOrDeleteUser(UserTo userTo,
                                     @RequestParam(required = false, name = "deleteUser") String deleteUser) {
        if (Objects.nonNull(deleteUser)) {
            userService.delete(userTo);
            return "redirect:/users";
        } else {
            userService.save(userTo);
            return "redirect:/users/%d".formatted(userTo.getId());
        }
    }
}
