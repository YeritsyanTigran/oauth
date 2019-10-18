package com.TigranCorporations.oauth2.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class RedirectToIndexController {
    @GetMapping
    public String redirectToIndex(){
        return "redirect:/home";
    }
}
