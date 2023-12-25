package com.example.userservice.exception;

import com.example.userservice.vo.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Chuob Bunthoeurn
 */
@ControllerAdvice
@Slf4j
public class CustomControllerAdvice {

    @ExceptionHandler(ServerException.class) // exception handled
    public ResponseEntity<ResponseMessage<Object>> handleExceptions(ServerException e) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 500

        // converting the stack trace to String
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);

        log.info("Error logging " ,e);

        ResponseMessage<Object> response = new ResponseMessage<>().error(e.getCode(), e.getMessage()).response();

        return new ResponseEntity<>(
                response,
                status
        );
    }
}
