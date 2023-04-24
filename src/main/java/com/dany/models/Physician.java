package com.dany.models;

import com.dany.database.DataStore;
import com.dany.helpers.ApiResponse;

public class Physician extends User {
    @Override
    public ApiResponse<User> register() throws Exception {
        validatePassword();
        encryptPassword();
        DataStore.addUser(this);
        User user = DataStore.findUser(getEmail());
        return new ApiResponse<>("registered a physian successfully", user);
    }

    private void validatePassword() throws Exception {
        String password = getPassword();
        if (password.length() < 9 || password.length() > 10) {
            throw new Exception("Password must have a length of 9-10 characters");
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
