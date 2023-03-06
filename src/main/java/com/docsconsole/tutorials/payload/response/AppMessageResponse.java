package com.docsconsole.tutorials.payload.response;

public class AppMessageResponse {
    private String message;

    public AppMessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
