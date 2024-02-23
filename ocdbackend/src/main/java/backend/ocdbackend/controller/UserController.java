package backend.ocdbackend.controller;

import backend.ocdbackend.model.User;
import backend.ocdbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.ConditionalOnGraphQlSchema;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        System.out.println("here");
        return userService.save(user);
    }


    @GetMapping("/test")
    public HttpEntity<String> createUser() {
        return new ResponseEntity<>("test", HttpStatus.OK);
    }

}
