package wsffs.springframework.junit;


import wsffs.springframework.feature.user.User;
import wsffs.springframework.feature.user.UserService;

public class UserServiceTest {

    @Test
    public void randomName() {
        System.out.println("randomName");
        final UserService sut = new UserService();
        final User user = sut.findById(10);
        assertNotNull(user);
    }

    public void testFindByIdMethod() {
        System.out.println("testFindByIdMethod");
        final UserService sut = new UserService();
        final User user = sut.findById(10);
        assertNotNull(user);
    }

    public void assertNotNull(Object object) {
        if (object == null) {
            throw new RuntimeException("Object가 null입니다.");
        }
    }

    public void test2() {
        System.out.println("실행되선 안돼요.");
    }
}