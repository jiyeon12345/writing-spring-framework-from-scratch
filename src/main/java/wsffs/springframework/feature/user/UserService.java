package wsffs.springframework.feature.user;

import wsffs.springframework.annotation.Component;

@Component
public class UserService {
    public User findById(long id) {
        return new User(
                "Chiho Won",
                "a@b.com"
        );
    }
}