package com.dany.helpers;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ApiResponse<T> {
    String message;
    T payload;
}
