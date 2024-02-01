package com.vikky.app.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vikky.app.utils.ConstantUtils;


@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ProblemDetail> handleValidationExceptions(MethodArgumentNotValidException ex) {
		ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
		List<String> errorMessages = new ArrayList<String>();
		ex.getBindingResult().getAllErrors().forEach(error -> errorMessages.add(error.getDefaultMessage()));
		problemDetail.setProperty(ConstantUtils.MESSAGE, String.join(", ", errorMessages));
		problemDetail.setProperty(ConstantUtils.STATUS, ConstantUtils.ERROR_MESSAGE);
		problemDetail.setStatus(HttpStatus.BAD_REQUEST);
		problemDetail.setType(ConstantUtils.ERROR_MESSAGE_URL);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
	}

	@ExceptionHandler(RefreshTokenExpiredException.class)
	public ResponseEntity<ProblemDetail> handleRefreshTokenExpiredException(RefreshTokenExpiredException ex) {
		ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
		problemDetail.setProperty(ConstantUtils.MESSAGE, ex.getMessage());
		problemDetail.setProperty(ConstantUtils.STATUS, ConstantUtils.ERROR_MESSAGE);
		problemDetail.setStatus(HttpStatus.BAD_REQUEST);
		problemDetail.setType(ConstantUtils.ERROR_MESSAGE_URL);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
	}

	@ExceptionHandler(ResourceNotProcessedException.class)
	public ResponseEntity<ProblemDetail> handleResourceNotProcessedException(ResourceNotProcessedException ex) {
		ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		problemDetail.setProperty(ConstantUtils.MESSAGE, ex.getMessage());
		problemDetail.setProperty(ConstantUtils.STATUS, ConstantUtils.ERROR_MESSAGE);
		problemDetail.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		problemDetail.setType(ConstantUtils.ERROR_MESSAGE_URL);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetail);

	}

}
