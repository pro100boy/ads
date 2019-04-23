package com.loopme.ads.http;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class SelfDocController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public RedirectView selfDoc() {
        return new RedirectView("/swagger-ui.html");
    }
}