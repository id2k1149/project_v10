package org.id2k1149.project_v10.controllerWeb;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.id2k1149.project_v10.model.User;
import org.id2k1149.project_v10.service.SecurityService;
import org.id2k1149.project_v10.service.UserService;
import org.id2k1149.project_v10.validator.UserValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserWebController {
    private final UserService userService;
    private final SecurityService securityService;
    private final UserValidator userValidator;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm,
                               BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            log.error("registration errors");
            return "auth/registration";
        }

        userService.addUser(userForm);
        log.info("User {} was added to DB", userForm.getUsername());
        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());
        return "redirect:/vote";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null) {
            log.error("username or password is invalid");
            model.addAttribute("error", "Your username or password is invalid.");
        }

        if (logout != null) {
            log.info("success logout");
            model.addAttribute("message", "You have been logged out successfully.");
        }
        return "auth/login";
    }

    @GetMapping({"/", "/index", "/welcome"})
    public String welcome(Model model) {
        return "welcome";
    }
}