### error.getDefaultMessage() 가 어떻게 나오는걸까. getMessage 가 아니구.

- getDefaultMessage()은 org.springframework.validation.FieldError 클래스에서 상속 받은 메소드입니다. FieldError 객체는 스프링 MVC에서 발생하는 유효성 검사 오류를 나타내며, 이 오류는 BindingResult 객체를 통해 수집된다.
- BindingResult 객체는 validate() 메소드를 호출한 후에 해당 객체의 getFieldErrors() 메소드를 사용하여 수집된 FieldError 객체를 가져올 수 있다.
- FieldError 객체는 ObjectError 객체를 상속하며, ObjectError는 Throwable 객체를 상속한다.
- 따라서 getDefaultMessage()은 ObjectError에서 상속받은 메소드이며, getMessage()는 Throwable에서 상속받은 메소드이다.
- Error 클래스에서 field와 message 속성은 유효성 검사 오류의 필드 이름과 메시지를 나타낸다.
- fieldErrors 스트림에서 map() 메소드를 사용하여 Error 객체를 생성할 때, FieldError 객체에서 getField() 메소드로 필드 이름을 가져오고, getDefaultMessage() 메소드로 오류 메시지를 가져옵니다.
  - 따라서 error.getDefaultMessage()가 사용되는 것.

```java
@Getter
@Setter
public class Error {
    private String field;
    private String message;

    public Error(String field, String message) {
        this.field = field;
        this.message = message;
    }

}
```

```java
@Getter
@Setter
public class ErrorResponse {
    private String code;
    private String message;
    private List<Error> errors;

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorResponse(String code, String message, List<Error> errors) {
        this.code = code;
        this.message = message;
        this.errors = errors;
    }

}
```


```java
package com.example.mapsearch.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalControllerAdvice {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse("500", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        List<Error> errors = fieldErrors.stream()
                .map(error -> new Error(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());

        ErrorResponse errorResponse = new ErrorResponse("400", "Validation error", errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // ... other exception handlers

    @ModelAttribute("data")
    public Map<String, Object> addDataToModel() {
        Map<String, Object> data = new HashMap<>();
        data.put("timestamp", LocalDateTime.now());
        return data;
    }

}
```