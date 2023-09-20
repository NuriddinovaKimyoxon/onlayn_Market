package user;

import common.BaseRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserRepository extends BaseRepository<UUID , User> {
    private static final UserRepository userRepository = new UserRepository();

    @Override
    protected String getFileName() {
        return "src/main/resources/users.txt";
    }

    public static UserRepository getInstance() {
        return userRepository;
    }
}
