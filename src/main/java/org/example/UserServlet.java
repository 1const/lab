package org.example;

import org.example.entity.User;
import org.example.repository.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("v1/users")
public class UserServlet extends HttpServlet {
    private final UserRepository userRepository;

    public UserServlet(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        List<User> usersByName = userRepository.findByName(name);

        req.setAttribute("usersByName", usersByName);
    }

}
