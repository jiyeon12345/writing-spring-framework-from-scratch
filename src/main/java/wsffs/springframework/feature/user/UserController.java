package wsffs.springframework.feature.user;

import wsffs.springframework.annotation.Component;
import wsffs.springframework.annotation.CustomRequestMapping;

@Component
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public User findById(long id) {
        return userService.findById(id);
    }

    @CustomRequestMapping("/hello")
    public String getHello() {
        return "hello";
    }
}