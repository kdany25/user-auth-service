package com.dany.models;

import java.util.UUID;

import javax.naming.AuthenticationException;

import com.dany.database.DataStore;
import com.dany.enums.Gender;
import com.dany.enums.UserRole;
import com.dany.helpers.ApiResponse;
import com.dany.helpers.GenerateJwt;
import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public abstract class User {
    private final String id = UUID.randomUUID().toString();
    @NonNull
    private String email;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private String userName;
    @Expose(serialize = false)
    private String password;
    private Gender gender;
    @NonNull
    private String phone;
    private Integer age;
    private UserRole role;

    public abstract ApiResponse<User> register() throws Exception;

    public abstract void createUser(User user);

    public void encryptPassword() {
        StringBuilder encryptedPasswordBuilder = new StringBuilder();
        for (int i = this.password.length() - 1; i >= 0; i--) {
            encryptedPasswordBuilder.append(this.password.charAt(i));
        }
        String encryptedPassword = "**" + encryptedPasswordBuilder.toString() + "</>*" + this.role + "**";
        this.password = encryptedPassword;
    }

    public String decryptPassword() {
        String encryptedPassword = this.password.substring(2, this.password.length() - 2).split("</>*")[0];
        StringBuilder decryptedPasswordBuilder = new StringBuilder();
        for (int i = encryptedPassword.length() - 1; i >= 0; i--) {
            decryptedPasswordBuilder.append(encryptedPassword.charAt(i));
        }
        return decryptedPasswordBuilder.toString();
    }

    public ApiResponse<String> login(String email, String password) throws Exception {
        User user = DataStore.findUser(email);
        if (user == null) {
            throw new AuthenticationException("Invalid credentials");
        }
        if (!password.equals(user.decryptPassword())) {
            throw new AuthenticationException("Invalid credentials");
        }
        return new ApiResponse<>("You have logged in successfully", GenerateJwt.generateJwt(user));
    }
}
