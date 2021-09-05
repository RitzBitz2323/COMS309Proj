package com.developer.experiment1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
 /**
@Controller
public class HomeController {
    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }
}
*/

class Greeting {
 String greeting;
 Greeting(String g) {
   greeting = g;
 }
 public String getGreeting() {
   return greeting;
 }
}

@Controller
public class HomeController {
   @RequestMapping(value="/",produces = "application/json")
   @ResponseBody
   Object home() {
       return new Greeting("Hello World!");
   }
}