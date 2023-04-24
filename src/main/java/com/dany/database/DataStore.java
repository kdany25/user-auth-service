package com.dany.database;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.dany.models.User;

import lombok.Getter;

@Getter
public class DataStore {
    private static Map<String, User> users = new LinkedHashMap<>();

    public static void addUser(User user) {
        if (users.get(user.getEmail()) != null) {
            throw new RuntimeException("User already Exits");
        }
        
        users.put(user.getEmail(), user);
    }

    public static User findUser(String email) {
        return users.get(email);
    }

    public static List<User> getUsers() {
        return new ArrayList<User>(users.values());
    }

}
