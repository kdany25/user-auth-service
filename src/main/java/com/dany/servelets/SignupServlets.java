package com.dany.servelets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dany.enums.UserRole;
import com.dany.helpers.ApiResponse;
import com.dany.helpers.Json;
import com.dany.helpers.Response;
import com.dany.models.Patient;
import com.dany.models.Pharmacist;
import com.dany.models.Physician;
import com.dany.models.User;

@WebServlet("/register")
public class SignupServlets extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        User user = new Json().parseBodyJson(req, Patient.class);

        try {
            ApiResponse<User> results = null;
            if (user.getRole() == UserRole.PATIENT) {
                Patient patient = new Patient();
                patient.createUser(user);
                results = patient.register();
            } else if (user.getRole() == UserRole.PHARMACIST) {
                Pharmacist pharmacist = new Pharmacist();
                pharmacist.createUser(user);
                results = pharmacist.register();
            } else if (user.getRole() == UserRole.PHYSICIAN) {
                Physician physician = new Physician();
                physician.createUser(user);
                results = physician.register();
            }

            Response.send(res, results, HttpServletResponse.SC_CREATED);

        } catch (Exception e) {
            e.printStackTrace();
            Response.send(res, new ApiResponse<>(e.getMessage(), null), HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}
