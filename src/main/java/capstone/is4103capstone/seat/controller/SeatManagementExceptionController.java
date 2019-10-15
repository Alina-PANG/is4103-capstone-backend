package capstone.is4103capstone.seat.controller;

import capstone.is4103capstone.seat.model.CustomErrorRes;
import capstone.is4103capstone.util.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class SeatManagementExceptionController {

    @ExceptionHandler(value = SeatAllocationException.class)
    public ResponseEntity handleSeatAllocationException(SeatAllocationException ex) {
        CustomErrorRes error = new CustomErrorRes();
        error.setTimestamp(LocalDateTime.now());
        error.setError(ex.getMessage());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = SeatMapCreationException.class)
    public ResponseEntity handleSeatMapCreationException(SeatMapCreationException ex) {
        CustomErrorRes error = new CustomErrorRes();
        error.setTimestamp(LocalDateTime.now());
        error.setError(ex.getMessage());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = SeatMapUpdateException.class)
    public ResponseEntity handleSeatMapUpdateException(SeatMapUpdateException ex) {
        CustomErrorRes error = new CustomErrorRes();
        error.setTimestamp(LocalDateTime.now());
        error.setError(ex.getMessage());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = SeatMapNotFoundException.class)
    public ResponseEntity handleSeatMapNotFoundException(SeatMapNotFoundException ex) {
        CustomErrorRes error = new CustomErrorRes();
        error.setTimestamp(LocalDateTime.now());
        error.setError(ex.getMessage());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = SeatNotFoundException.class)
    public ResponseEntity handleSeatNotFoundException(SeatNotFoundException ex) {
        CustomErrorRes error = new CustomErrorRes();
        error.setTimestamp(LocalDateTime.now());
        error.setError(ex.getMessage());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = EmployeeNotFoundException.class)
    public ResponseEntity handleEmployeeNotFoundException(EmployeeNotFoundException ex) {
        CustomErrorRes error = new CustomErrorRes();
        error.setTimestamp(LocalDateTime.now());
        error.setError(ex.getMessage());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = TeamNotFoundException.class)
    public ResponseEntity handleTeamNotFoundException(TeamNotFoundException ex) {
        CustomErrorRes error = new CustomErrorRes();
        error.setTimestamp(LocalDateTime.now());
        error.setError(ex.getMessage());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = CompanyFunctionNotFoundException.class)
    public ResponseEntity handleCompanyFunctionNotFoundException(CompanyFunctionNotFoundException ex) {
        CustomErrorRes error = new CustomErrorRes();
        error.setTimestamp(LocalDateTime.now());
        error.setError(ex.getMessage());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = EntityModelConversionException.class)
    public ResponseEntity handleEntityModelConversionException(EntityModelConversionException ex) {
        CustomErrorRes error = new CustomErrorRes();
        error.setTimestamp(LocalDateTime.now());
        error.setError(ex.getMessage());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
