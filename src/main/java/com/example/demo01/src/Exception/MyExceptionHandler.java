package com.example.demo01.src.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class MyExceptionHandler {

   /* @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handle(RuntimeException exception){
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("RuntimeException:"+exception.getMessage());
    }*/
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handle(IllegalArgumentException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("IllegalArgumentException:"+exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public  ResponseEntity<Map<String,String>> handle(MethodArgumentNotValidException exception){
        Map<String,String> errorMap=new HashMap<>();
        exception.getBindingResult().getFieldErrors()
                .forEach(error->errorMap.put(error.getField(),error.getDefaultMessage()));
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
    }
}
