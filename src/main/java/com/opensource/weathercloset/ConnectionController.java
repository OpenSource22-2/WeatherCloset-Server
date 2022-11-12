package com.opensource.weathercloset;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConnectionController {

    @GetMapping("test")
    public String test() {
        return "SUCCESS";
    }

}
