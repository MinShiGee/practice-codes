package com.minshigee.authserver.dependencies.reswrapper;

import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.net.URI;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.function.Consumer;

public class ResponseFactory<T>{

    public static ResponseFactory.BodyBuilder status(HttpStatus status) {
        Assert.notNull(status, "HttpStatus must not be null");
        return new ResponseFactory.DefaultBuilder(status);
    }
    public static ResponseFactory.BodyBuilder ok() {
        return status(HttpStatus.OK);
    }
    public static <T> ResponseEntity<WrappedResponse<T>> ok(@Nullable T body) {
        return ok().body(body);
    }
    public static <T> ResponseEntity<WrappedResponse<T>> of(Optional<T> body) {
        Assert.notNull(body, "Body must not be null");
        return body.map(ResponseFactory::ok).orElseGet(() -> notFound().build());
    }
    public static ResponseFactory.BodyBuilder created(URI location) {
        return status(HttpStatus.CREATED).location(location);
    }
    public static ResponseFactory.BodyBuilder accepted() {
        return status(HttpStatus.ACCEPTED);
    }
    public static ResponseFactory.HeadersBuilder<?> noContent() {
        return status(HttpStatus.NO_CONTENT);
    }
    public static ResponseFactory.BodyBuilder badRequest() {
        return status(HttpStatus.BAD_REQUEST);
    }
    public static ResponseFactory.HeadersBuilder<?> notFound() {
        return status(HttpStatus.NOT_FOUND);
    }
    public static ResponseFactory.BodyBuilder unprocessableEntity() {
        return status(HttpStatus.UNPROCESSABLE_ENTITY);
    }
    public static ResponseFactory.BodyBuilder internalServerError() {
        return status(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public interface HeadersBuilder<B extends ResponseFactory.HeadersBuilder<B>> {
        B header(String headerName, String... headerValues);
        B headers(@Nullable HttpHeaders headers);
        B headers(Consumer<HttpHeaders> headersConsumer);
        B allow(HttpMethod... allowedMethods);
        B eTag(String etag);
        B lastModified(ZonedDateTime lastModified);
        B lastModified(Instant lastModified);
        B lastModified(long lastModified);
        B location(URI location);
        B cacheControl(CacheControl cacheControl);
        B varyBy(String... requestHeaders);
        <T> ResponseEntity<WrappedResponse<T>> build();
    }
    public interface BodyBuilder extends ResponseFactory.HeadersBuilder<ResponseFactory.BodyBuilder> {
        ResponseFactory.BodyBuilder contentLength(long contentLength);
        ResponseFactory.BodyBuilder contentType(MediaType contentType);
        <T> ResponseEntity<WrappedResponse<T>> body(@Nullable T body);
    }

    private static class DefaultBuilder implements ResponseFactory.BodyBuilder {

        private final Object statusCode;

        private final HttpHeaders headers = new HttpHeaders();

        public DefaultBuilder(Object statusCode) {
            this.statusCode = statusCode;
        }

        @Override
        public ResponseFactory.BodyBuilder header(String headerName, String... headerValues) {
            for (String headerValue : headerValues) {
                this.headers.add(headerName, headerValue);
            }
            return this;
        }

        @Override
        public ResponseFactory.BodyBuilder headers(@Nullable HttpHeaders headers) {
            if (headers != null) {
                this.headers.putAll(headers);
            }
            return this;
        }

        @Override
        public ResponseFactory.BodyBuilder headers(Consumer<HttpHeaders> headersConsumer) {
            headersConsumer.accept(this.headers);
            return this;
        }

        @Override
        public ResponseFactory.BodyBuilder allow(HttpMethod... allowedMethods) {
            this.headers.setAllow(new LinkedHashSet<>(Arrays.asList(allowedMethods)));
            return this;
        }

        @Override
        public ResponseFactory.BodyBuilder contentLength(long contentLength) {
            this.headers.setContentLength(contentLength);
            return this;
        }

        @Override
        public ResponseFactory.BodyBuilder contentType(MediaType contentType) {
            this.headers.setContentType(contentType);
            return this;
        }

        @Override
        public ResponseFactory.BodyBuilder eTag(String etag) {
            if (!etag.startsWith("\"") && !etag.startsWith("W/\"")) {
                etag = "\"" + etag;
            }
            if (!etag.endsWith("\"")) {
                etag = etag + "\"";
            }
            this.headers.setETag(etag);
            return this;
        }

        @Override
        public ResponseFactory.BodyBuilder lastModified(ZonedDateTime date) {
            this.headers.setLastModified(date);
            return this;
        }

        @Override
        public ResponseFactory.BodyBuilder lastModified(Instant date) {
            this.headers.setLastModified(date);
            return this;
        }

        @Override
        public ResponseFactory.BodyBuilder lastModified(long date) {
            this.headers.setLastModified(date);
            return this;
        }

        @Override
        public ResponseFactory.BodyBuilder location(URI location) {
            this.headers.setLocation(location);
            return this;
        }

        @Override
        public ResponseFactory.BodyBuilder cacheControl(CacheControl cacheControl) {
            this.headers.setCacheControl(cacheControl);
            return this;
        }

        @Override
        public ResponseFactory.BodyBuilder varyBy(String... requestHeaders) {
            this.headers.setVary(Arrays.asList(requestHeaders));
            return this;
        }

        @Override
        public <T> ResponseEntity<WrappedResponse<T>> build() {
            return body(null);
        }

        @Override
        public <T> ResponseEntity<WrappedResponse<T>> body(@Nullable T body) {
            return new ResponseEntity<>(
                    new WrappedResponse<T>(body, true, (HttpStatus)statusCode),
                    this.headers,
                    (HttpStatus) this.statusCode
            );
        }
    }
}

