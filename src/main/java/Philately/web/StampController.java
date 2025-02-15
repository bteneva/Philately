package Philately.web;

import Philately.stamp.service.StampService;
import Philately.user.model.User;
import Philately.user.service.UserService;
import Philately.web.dto.NewStampRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping("/stamps")
public class StampController {

    private final UserService userService;
    private final StampService stampService;

    @Autowired
    public StampController(UserService userService, StampService stampService) {
        this.userService = userService;
        this.stampService = stampService;
    }

    @GetMapping("/new")
    public ModelAndView addNewStamp(HttpSession session) {

        UUID userId = (UUID) session.getAttribute("user_id");
        User user = userService.getById(userId);


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("add-stamp");
        modelAndView.addObject("user", user);
        modelAndView.addObject("newStampRequest", new NewStampRequest());

        return modelAndView;
    }

    @PostMapping("/new")
    public ModelAndView postNewStamp(@Valid NewStampRequest newStampRequest, BindingResult bindingResult, HttpSession session) {

        UUID userId = (UUID) session.getAttribute("user_id");
        User user = userService.getById(userId);

        if(bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("add-stamp");
            modelAndView.addObject("user", user);

            return modelAndView;
        }

        stampService.createStamp(newStampRequest, user);

        return new ModelAndView("redirect:/home");
    }

    @PostMapping("/{id}/wishlist")
    public String createWishListItemForStamp(@PathVariable UUID id, HttpSession session) {
        UUID userId = (UUID) session.getAttribute("user_id");
        User user = userService.getById(userId);

        stampService.createWished(id, user);

        return "redirect:/home";
    }

    @DeleteMapping("/{id}")
    public String deleteWishedStamp(@PathVariable UUID id){
        stampService.deleteWishedStampById(id);
        return "redirect:/home";
    }



}
