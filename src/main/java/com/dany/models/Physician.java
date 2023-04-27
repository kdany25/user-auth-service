package com.dany.models;

import com.dany.database.DataStore;
import com.dany.helpers.ApiResponse;
import com.dany.enums.Gender;
import com.dany.enums.UserRole;

public class Physician extends User {
    @Override
    public ApiResponse<User> register() throws Exception {
        validateUserDetails();
        encryptPassword();
        DataStore.addUser(this);
        User user = DataStore.getUserByEmail(getEmail());
        return new ApiResponse<>("registered a physian successfully", user);
    }

    private void validateUserDetails() throws Exception {
        validateUserName();
        validatePhone();
        validatePassword();
        validateGender();
        validateRole();
    }

    private void validateUserName() throws Exception {
        if (getUserName() == null || getUserName().isEmpty()) {
            throw new Exception("Username must be provided");
        }
    }

    private void validatePhone() throws Exception {
        if (getPhone() == null || getPhone().isEmpty()) {
            throw new Exception("Phone number must be provided");
        }
    }

    private void validatePassword() throws Exception {
        String password = getPassword();
        if (password.length() < 7 || password.length() > 8) {
            throw new Exception("Password must have a length of 7-8 characters");
        }
    }

    private void validateGender() throws Exception {
        Gender gender = getGender();
        if (gender != Gender.MALE && gender != Gender.FEMALE) {
            throw new Exception("Gender must be either MALE or FEMALE");
        }
    }

    private void validateRole() throws Exception {
        UserRole role = getRole();
        if (role != UserRole.PATIENT && role != UserRole.PHYSICIAN && role != UserRole.PHARMACIST) {
            throw new Exception("Role must be either PATIENT, PHYSICIAN or PHARMACIST");
        }
    }

    @Override
    public void createUser(User user) {
        setFirstName(user.getFirstName());
        setLastName(user.getLastName());
        setAge(user.getAge());
        setRole(user.getRole());
        setEmail(user.getEmail());
        setPassword(user.getPassword());
        setGender(user.getGender());
        setUserName(user.getUserName());
        setPhone(user.getPhone());
    }
}
