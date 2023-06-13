package com.blogapp.exception;

import com.blogapp.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
        @ExceptionHandler(ResourcesNotFoundException.class)
        public ResponseEntity<ErrorDetails> handelResourcesNotFoundException(ResourcesNotFoundException rnfException, WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), rnfException.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(BlogAPIException.class)
        public ResponseEntity<ErrorDetails> handelBlogAPIException(BlogAPIException baException, WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), baException.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handelGlobalException( Exception exception, WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
}