package com.dany.models;

import com.dany.database.DataStore;
import com.dany.helpers.ApiResponse;

public class Patient extends User {

    @Override
    public ApiResponse<User> register() throws Exception {
        validatePassword();
        encryptPassword();
        DataStore.addUser(this);
        User user = DataStore.findUser(getEmail());
        return new ApiResponse<>("registered a patient successfully", user);
    }

    private void validatePassword() throws Exception {
        String password = getPassword();
        if (password.length() < 4 || password.length() > 6) {
            throw new Exception("Password must have a length of 4-6 characters");
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
    }
}
