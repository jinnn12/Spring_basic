package com.beyond.basic.b2_board.Common;

import com.beyond.basic.b2_board.DTO.CommonErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

// controller어노테이션이 붙어 있는 클래스의 모든 예외를 모니터링 하여 예외를 인터셉팅
@ControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> illegalException(IllegalArgumentException e){ // IllegalArgsExcep 주입
        return new ResponseEntity<>(new CommonErrorDto(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST); // 400
//        return new ResponseEntity<>(new CommonErrorDto(HttpStatus.BAD_REQUEST.value(), "잘못된 입력값입니다.") <body>, HttpStatus.BAD_REQUEST<header>); // 400
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> NoSuchException(NoSuchElementException e){
        return new ResponseEntity<>(new CommonErrorDto(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND); // 404
    }

}
