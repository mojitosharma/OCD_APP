package backend.ocdbackend.controller;

import org.springframework.web.bind.annotation.*;

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
