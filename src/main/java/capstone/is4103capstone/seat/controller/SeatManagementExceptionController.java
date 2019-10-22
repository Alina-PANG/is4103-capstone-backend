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

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity handleGeneralException(Exception ex) {
        CustomErrorRes error = new CustomErrorRes();
        error.setTimestamp(LocalDateTime.now());
        ex.printStackTrace();
        error.setError("An internal server error occurred.");
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

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

    @ExceptionHandler(value = CreateSeatAllocationRequestException.class)
    public ResponseEntity handleCreateSeatAllocationRequestException(CreateSeatAllocationRequestException ex) {
        CustomErrorRes error = new CustomErrorRes();
        error.setTimestamp(LocalDateTime.now());
        error.setError(ex.getMessage());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = CreateSeatAllocationRequestRightException.class)
    public ResponseEntity handleCreateSeatAllocationRequestRightException(CreateSeatAllocationRequestRightException ex) {
        CustomErrorRes error = new CustomErrorRes();
        error.setTimestamp(LocalDateTime.now());
        error.setError(ex.getMessage());
        error.setStatus(HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = SeatRequestAdminMatchNotFoundException.class)
    public ResponseEntity handleSeatRequestAdminMatchNotFoundException(SeatRequestAdminMatchNotFoundException ex) {
        CustomErrorRes error = new CustomErrorRes();
        error.setTimestamp(LocalDateTime.now());
        error.setError(ex.getMessage());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = SeatAllocationRequestNotFoundException.class)
    public ResponseEntity handleSeatAllocationRequestNotFoundException(SeatAllocationRequestNotFoundException ex) {
        CustomErrorRes error = new CustomErrorRes();
        error.setTimestamp(LocalDateTime.now());
        error.setError(ex.getMessage());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = SeatAllocationRequestProcessingException.class)
    public ResponseEntity handleSeatAllocationRequestProcessingException(SeatAllocationRequestProcessingException ex) {
        CustomErrorRes error = new CustomErrorRes();
        error.setTimestamp(LocalDateTime.now());
        error.setError(ex.getMessage());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ApprovalForRequestNotFoundException.class)
    public ResponseEntity handleApprovalForRequestNotFoundException(ApprovalForRequestNotFoundException ex) {
        CustomErrorRes error = new CustomErrorRes();
        error.setTimestamp(LocalDateTime.now());
        error.setError(ex.getMessage());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
