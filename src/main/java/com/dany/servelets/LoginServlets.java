package com.dany.servelets;

import java.io.IOException;
import javax.naming.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dany.helpers.ApiResponse;
import com.dany.helpers.Json;
import com.dany.helpers.Response;
import com.dany.models.Patient;
import com.dany.models.User;

@WebServlet("/login")
public class LoginServlets extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        User user = new Json().parseBodyJson(req, Patient.class);
        try {
            ApiResponse<String> result = user.login(user.getEmail(), user.getPassword());
            Response.send(res, result, HttpServletResponse.SC_OK);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            Response.send(res, new ApiResponse<>(e.getMessage(), null), HttpServletResponse.SC_UNAUTHORIZED);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
