import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDao {
    private final Map<Integer, User> usersMap = new HashMap<>();
    private final int idCounter = 1;

    public List<User> getAllUsers() {
        return new ArrayList<>(usersMap.values());
    }

    public User getUserById(int id) {
        return usersMap.get(id);
    }

    public void addUser(User user) {
        user.setId(generateId());
        usersMap.put(user.getId(), user);
    }

    public void updateUser(User user) {
        usersMap.put(user.getId(), user);
    }

    public void deleteUser(int id) {
        usersMap.remove(id);
    }

    private int generateId() {
        return usersMap.keySet().stream()
                .max(Integer::compareTo)
                .map(id -> id + 1)
                .orElse(1);
    }
}
