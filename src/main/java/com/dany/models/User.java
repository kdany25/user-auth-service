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
import org.mindrot.jbcrypt.BCrypt;

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
    @NonNull
    private String phone;
    @Expose(serialize = false)
    private String password;
    @NonNull
    private Gender gender;
    private Integer age;
    private UserRole role;

    public abstract ApiResponse<User> register() throws Exception;

    public abstract void createUser(User user);

    public void encryptPassword() {
        String hashedPassword = BCrypt.hashpw(this.password, BCrypt.gensalt());
        this.password = hashedPassword;
    }

    public boolean checkPassword(String inputPassword) {
        return BCrypt.checkpw(inputPassword, this.password);
    }

    public ApiResponse<String> login(String usernameOrEmailOrPhone, String password) throws Exception {
        User user = null;
        String errorMessage = "Invalid credentials";
        if (isUsername(usernameOrEmailOrPhone)) {
            user = DataStore.getUserByUsername(usernameOrEmailOrPhone);
            errorMessage = "Invalid username or password";
        } else if (isEmail(usernameOrEmailOrPhone)) {
            user = DataStore.getUserByEmail(usernameOrEmailOrPhone);
            errorMessage = "Invalid email or password";
        } else if (isPhone(usernameOrEmailOrPhone)) {
            user = DataStore.getUserByPhone(usernameOrEmailOrPhone);
            errorMessage = "Invalid phone number or password";
        }

        if (user == null) {
            throw new AuthenticationException(errorMessage);
        }

        if (!user.checkPassword(password)) {
            throw new AuthenticationException(errorMessage);
        }

        return new ApiResponse<>("You have logged in successfully", GenerateJwt.generateJwt(user));
    }

    private boolean isUsername(String value) {
        if (value.matches("^[a-zA-Z]\\w*$")) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isEmail(String value) {
        if (value.matches("^\\S+@\\S+\\.\\S+$")) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isPhone(String value) {
        if (value.matches("^\\d{10}$")) {
            return true;
        } else {
            return false;
        }
    }

}
