package com.fzcoder.opensource.blog.utils.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class R implements Serializable {
    private static final long serialVersionUID = -6030654216451073486L;

    private static final String DEFAULT_MSG = "";

    @JsonProperty("status")
    private Integer code;
    @JsonProperty("message")
    private String msg;
    @JsonProperty("data")
    private Object data;

    private R(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    // 2xx
    public static R ok(Object data) {
        return ok(DEFAULT_MSG, data);
    }

    public static R ok(String msg, Object data) {
        return new R(HttpStatusCode.OK, msg, data);
    }

    // 4xx
    public static R badRequest(Object data) {
        return badRequest(DEFAULT_MSG, data);
    }

    public static R badRequest(String msg, Object data) {
        return new R(HttpStatusCode.BAD_REQUEST, msg, data);
    }

    public static R unauthorized(Object data) {
        return unauthorized(DEFAULT_MSG, data);
    }

    public static R unauthorized(String msg, Object data) {
        return new R(HttpStatusCode.UNAUTHORIZED, msg, data);
    }

    public static R paymentRequired(Object data) {
        return paymentRequired(DEFAULT_MSG, data);
    }

    public static R paymentRequired(String msg, Object data) {
        return new R(HttpStatusCode.PAYMENT_REQUIRED, msg, data);
    }

    public static R forbidden(Object data) {
        return forbidden(DEFAULT_MSG, data);
    }

    public static R forbidden(String msg, Object data) {
        return new R(HttpStatusCode.FORBIDDEN, msg, data);
    }

    public static R notFound(Object data) {
        return notFound(DEFAULT_MSG, data);
    }

    public static R notFound(String msg, Object data) {
        return new R(HttpStatusCode.NOT_FOUND, msg, data);
    }

    // 5xx
    public static R internalServerError(Object data) {
        return internalServerError(DEFAULT_MSG, data);
    }

    public static R internalServerError(String msg, Object data) {
        return new R(HttpStatusCode.INTERNAL_SERVER_ERROR, msg, data);
    }
}
