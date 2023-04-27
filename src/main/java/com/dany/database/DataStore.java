package com.dany.database;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.dany.models.User;

import lombok.Getter;

@Getter
public class DataStore {
    private static final Map<String, User> users = new LinkedHashMap<>();

    public static void addUser(User user) {
        String key;
        switch (user.getRole()) {
            case PATIENT:
                key = user.getUserName();
                break;
            case PHYSICIAN:
                key = user.getEmail();
                break;
            case PHARMACIST:
                key = user.getPhone();
                break;
            default:
                throw new IllegalArgumentException("Invalid user role");
        }
        if (users.containsKey(key)) {
            throw new RuntimeException("User already exists");
        }
        users.put(key, user);
    }

    public static User getUserByEmail(String email) {
        return users.get(email);
    }

    public static User getUserByPhone(String phone) {
        return users.get(phone);
    }

    public static User getUserByUsername(String username) {
        return users.get(username);
    }

    public static void removeUser(String email) {
        users.remove(email);
    }

    public static List<User> getUsers() {
        return new ArrayList<>(users.values());
    }
}
