package wsffs.springframework.feature.user;

public class User {
    private final String name;
    private final String email;

    public User (String name, String email) {
        this.email = email;
        this.name = name;
    }

    @Override
    public String toString() {
        return "reflectionExample.User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
