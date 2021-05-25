package com.coldcoder.reddit.exception;

public class PostNotFoundException extends  RuntimeException{
    public PostNotFoundException(String message) {
        super(message);
    }
}
