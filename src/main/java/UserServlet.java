import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users/*")
public class UserServlet extends HttpServlet {
    private final UserService userService = new UserService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.getWriter().println(objectMapper.writeValueAsString(userService.getAllUsers()));
        } else {
            try {
                int id = Integer.parseInt(pathInfo.substring(1));
                User user = userService.getUserById(id);
                if (user != null) {
                    resp.getWriter().println(objectMapper.writeValueAsString(user));
                } else {
                    resp.getWriter().println("User not found");
                }
            } catch (NumberFormatException e) {
                resp.getWriter().println("Invalid user ID");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            User newUser = objectMapper.readValue(req.getInputStream(), User.class);
            newUser.setId(generateId());
            userService.addUser(newUser);
            resp.getWriter().println("User added: " + objectMapper.writeValueAsString(newUser));
        } catch (JsonProcessingException e) {
            resp.getWriter().println("Invalid JSON format");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo != null && !pathInfo.equals("/")) {
            try {
                int id = Integer.parseInt(pathInfo.substring(1));
                User existingUser = userService.getUserById(id);

                if (existingUser != null) {
                    User updatedUser = objectMapper.readValue(req.getInputStream(), User.class);

                    existingUser.setName(updatedUser.getName());
                    existingUser.setLastName(updatedUser.getLastName());
                    existingUser.setAge(updatedUser.getAge());

                    userService.updateUser(existingUser);
                    resp.getWriter().println("User updated: " + objectMapper.writeValueAsString(existingUser));
                } else {
                    resp.getWriter().println("User not found");
                }
            } catch (JsonProcessingException e) {
                resp.getWriter().println("Invalid JSON format");
            } catch (NumberFormatException e) {
                resp.getWriter().println("Invalid user ID");
            }
        } else {
            resp.getWriter().println("Invalid request");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo != null && !pathInfo.equals("/")) {
            try {
                int id = Integer.parseInt(pathInfo.substring(1));
                User user = userService.getUserById(id);
                if (user != null) {
                    userService.deleteUser(id);
                    resp.getWriter().println("User deleted: " + objectMapper.writeValueAsString(user));
                } else {
                    resp.getWriter().println("User not found");
                }
            } catch (NumberFormatException e) {
                resp.getWriter().println("Invalid user ID");
            }
        } else {
            resp.getWriter().println("Invalid request");
        }
    }

    private int generateId() {
        return userService.getAllUsers().stream()
                .map(User::getId)
                .max(Integer::compareTo)
                .orElse(0) + 1;
    }
}
