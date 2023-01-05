package at.noah.employee.controller;

import at.noah.employee.exceptions.EmployeeNotFoundException;
import org.hibernate.sql.ordering.antlr.OrderingSpecification;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpServerErrorException;

import javax.annotation.Priority;
import java.sql.SQLException;
import java.time.DateTimeException;

@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler({SQLException.class, DataAccessException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> databaseError() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database Error");
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleEmployeeNotFund() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find Employee with given id");
    }

    @ExceptionHandler(DateTimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleDateTimeException(DateTimeException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
