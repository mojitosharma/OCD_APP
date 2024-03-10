package backend.ocdbackend.controller;

import backend.ocdbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.ConditionalOnGraphQlSchema;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@RestController
//@RequestMapping("/users")
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//    @PostMapping("/create")
//    public User createUser(@RequestBody User user) {
//        System.out.println("here");
//        return userService.save(user);
//    }
//
//}

@RestController
@RequestMapping("/user")
@CrossOrigin("*")

public class UserController {

    @GetMapping("/")
    public String helloUserController(){
        return "User access level";
    }

}
