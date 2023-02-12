package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class InitController {

    @RequestMapping("/admin")
    public ModelAndView init() {
        ModelAndView model = new ModelAndView("index");
        return model;


    }

    @RequestMapping(value = "/" , method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView model = new ModelAndView("index");
        return model;

    }

}
