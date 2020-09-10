package cn.bestsort.model.vo;

import com.alibaba.fastjson.JSON;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
/**
 * @author bestsort
 * @version 1.0
 * @date 2020-08-24 16:57
 */
@JsonInclude(Include.NON_NULL)
@Getter
@AllArgsConstructor
public class ErrorResponse {
    private final Integer code;
    private final Object  type;
    private final Object  errors;
    private final Object  data;
    private final Object  message;


    public static ErrorResponse ok() {
        return ErrorResponse.builder()
            .ok()
            .build();
    }

    public static ResponseBuilder builder() {
        return new ResponseBuilder();
    }

    public String toJSONString() {
        return JSON.toJSONString(this);
    }

    public static class ResponseBuilder {
        private Integer code;
        private Object  type;
        private Object  errors;
        private Object  message;
        private Object  data;

        private ResponseBuilder() {
        }

        public ResponseBuilder code(final Integer code) {
            this.code = code;
            return this;
        }

        public ResponseBuilder message(final Object message) {
            this.message = message;
            return this;
        }

        public ResponseBuilder code(int status) {
            this.code = status;
            return this;
        }

        public ResponseBuilder ok() {
            this.code = 0;
            this.message = "success";
            return this;
        }

        public ResponseBuilder type(final Object type) {
            this.type = type;
            return this;
        }

        public ResponseBuilder errors(final Object errors) {
            this.errors = errors;
            return this;
        }

        public ResponseBuilder data(final Object message) {
            this.data = JSON.toJSONString(message);
            return this;
        }

        public ErrorResponse build() {
            return new ErrorResponse(this.code, this.type, this.errors, this.data, this.message);
        }
    }
}