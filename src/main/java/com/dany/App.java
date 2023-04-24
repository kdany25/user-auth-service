package com.dany;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dany.helpers.ApiResponse;
import com.dany.helpers.Response;

@WebServlet(urlPatterns = "")
public class App extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Response.send(resp, new ApiResponse<>("Medical unit authentication", null),
                HttpServletResponse.SC_OK);
    }

}