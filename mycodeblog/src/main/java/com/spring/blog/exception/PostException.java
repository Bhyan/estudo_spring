package com.spring.blog.exception;

public class PostException extends Throwable {

    private static final long serialVersionUID = 1L;

    public PostException(String message) {
        super(message);
    }
}
