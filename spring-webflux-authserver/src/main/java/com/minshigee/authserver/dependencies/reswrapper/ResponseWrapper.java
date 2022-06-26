package com.minshigee.authserver.dependencies.reswrapper;

import com.minshigee.authserver.dependencies.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseWrapper<T> {

    @Setter
    protected Integer status = HttpStatus.OK.value();
    @Setter
    private Boolean state = true;
    @Setter
    protected String message = "request is successed";
    @Setter
    protected T res;

    public ResponseWrapper(T obj){
        this.res = obj;
    }

    public ResponseWrapper(T obj, HttpStatus status) {
        this.res = obj;
        this.status = status.value();
    }

    public ResponseWrapper(T obj, ErrorCode code) {
        this.res = obj;
        this.state = true;
        this.message = code.build().getReason();
        this.status = code.build().getStatus().value();
    }

    public ResponseWrapper(ErrorCode code) {
        this.res = null;
        this.state = false;
        this.message = code.build().getReason();
        this.status = code.build().getStatus().value();
    }

}