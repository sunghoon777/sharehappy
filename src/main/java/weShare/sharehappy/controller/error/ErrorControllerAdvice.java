package weShare.sharehappy.controller.error;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import weShare.sharehappy.Exception.NoExistingDonationPostCategory;
import weShare.sharehappy.Exception.NoMoreDonationPostException;
import weShare.sharehappy.dto.error.SimpleErrorResponse;
import weShare.sharehappy.service.MessageInfoProvider;


@Slf4j
@ControllerAdvice
@AllArgsConstructor
public class ErrorControllerAdvice {

    private MessageInfoProvider messageInfoProvider;

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> messageNotReadExHandle(HttpMessageNotReadableException exception){
        String message = messageInfoProvider.getMessage(exception.getClass().getSimpleName());
        return new ResponseEntity<>(new SimpleErrorResponse(message),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoExistingDonationPostCategory.class)
    public ResponseEntity<SimpleErrorResponse> noCategoryExHandle(NoExistingDonationPostCategory exception){
        String message = messageInfoProvider.getMessage(exception.getClass().getSimpleName());
        return new ResponseEntity<>(new SimpleErrorResponse(message), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoMoreDonationPostException.class)
    public ResponseEntity<SimpleErrorResponse> noMorePostExHandle(NoMoreDonationPostException exception){
        String message = messageInfoProvider.getMessage(exception.getClass().getSimpleName());
        return new ResponseEntity<>(new SimpleErrorResponse(message), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<SimpleErrorResponse> dataAccessExHandle(DataAccessException exception){
        String message = messageInfoProvider.getMessage(exception.getClass().getSimpleName());
        return new ResponseEntity<>(new SimpleErrorResponse(message),HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
