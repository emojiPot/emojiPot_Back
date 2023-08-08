package com.hanium.emoji_pot.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class Response<T> {

    private String resultCode;

    private T result;

    public static <T> Response<T> error(T result){
        return new Response<>("ERROR", result);
    }

    public static <T> Response<T> success(T result){
        return new Response<>("SUCCESS", result);
    }
}
