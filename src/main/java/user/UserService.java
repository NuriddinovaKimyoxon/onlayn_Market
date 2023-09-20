package user;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserService {
    private final UserRepository userRepository = UserRepository.getInstance();
    private static final UserService userService = new UserService();

    public void add(User entity) {
        userRepository.add(entity);
    }

    public void delete(UUID id) {
        userRepository.delete(id);
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }

    public Optional<User> findById(UUID id) {

        return userRepository.findById(id);
    }

    public static UserService getInstance() {
        return userService;
    }

    public boolean isExist(String email) {
        for (User user : userService.getAll()) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkLogin(String email, String password) {
        for (User user : getAll()) {
            if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
}
