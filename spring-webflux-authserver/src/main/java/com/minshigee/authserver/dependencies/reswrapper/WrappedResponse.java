package com.minshigee.authserver.dependencies.reswrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
public class WrappedResponse<T> {

    protected final Integer status;
    private final Boolean state;
    protected final String message;
    @JsonProperty("res")
    protected T body;

    public WrappedResponse(T body, Boolean state, HttpStatus status) {
        this.state = state;
        this.status = status.value();
        this.body = body;
        this.message  = "request is successed";
    }

    public WrappedResponse(T body, Boolean state, HttpStatus status, String msg) {
        this.state = state;
        this.status = status.value();
        this.body = body;
        this.message  = msg;
    }
}