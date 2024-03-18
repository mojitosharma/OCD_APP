package backend.ocdbackend.controller;

import backend.ocdbackend.model.PasswordChangeDTO;
import backend.ocdbackend.service.AuthenticationService;
import backend.ocdbackend.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
//   }
//
//}

@RestController
@RequestMapping("/user")
@CrossOrigin("*")

public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/")
    public String helloUserController(){
        return "User access level";
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeDTO passwordChangeDto, @RequestHeader("Authorization") String token) {
        return userService.changePassword(passwordChangeDto, token);
    }
}
