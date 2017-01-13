package com.springapp.mvc;

class Response {
    String message;
    String error;

    public Response(String message, String error) {
        this.message = message;
        this.error = error;
    }
}