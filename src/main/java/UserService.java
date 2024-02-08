import java.util.List;

public class UserService {
    private final UserDao userDao = new UserDao();

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

    public void addUser(User user) {
        userDao.addUser(user);
    }

    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }
}
