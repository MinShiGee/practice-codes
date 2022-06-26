package com.minshigee.authserver.dependencies.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> map = super.getErrorAttributes(request, options);
        map.remove("error");
        map.remove("path");
        map.remove("trace");
        map.put("state", false);

        Throwable throwable = getError(request);

        if (throwable instanceof GlobalException) {
            GlobalException ex = (GlobalException) getError(request);
            map.put("res", null);
            map.put("message", ex.getReason());
            map.put("status", ex.getStatus().value());
            return map;
        }

        map.put("res", null);
        map.put("message", throwable.getLocalizedMessage());
        map.put("status", 500);
        return map;
    }

}
