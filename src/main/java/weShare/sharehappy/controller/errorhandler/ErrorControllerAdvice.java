package weShare.sharehappy.controller.errorhandler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import weShare.sharehappy.Exception.*;
import weShare.sharehappy.dto.error.SimpleErrorResponse;
import weShare.sharehappy.service.message.MessageInfoProvider;


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

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<SimpleErrorResponse> dataAccessExHandle(DataAccessException exception){
        String message = messageInfoProvider.getMessage(exception.getClass().getSimpleName());
        return new ResponseEntity<>(new SimpleErrorResponse(message),HttpStatus.INTERNAL_SERVER_ERROR);
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

    @ExceptionHandler(NoExistingUserException.class)
    public ResponseEntity<Object> noExistingUserExHandle(NoExistingUserException exception){
        String message = messageInfoProvider.getMessage(exception.getClass().getSimpleName());
        return new ResponseEntity<>(new SimpleErrorResponse(message),HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> maxUploadSizeExceededtExHandle(MaxUploadSizeExceededException exception){
        String message = messageInfoProvider.getMessage(exception.getClass().getSimpleName());
        return new ResponseEntity<>(new SimpleErrorResponse(message),HttpStatus.PAYLOAD_TOO_LARGE);
    }

    @ExceptionHandler(FileStoreException.class)
    public ResponseEntity<Object> fileStoreExHandle(FileStoreException exception){
        String message = messageInfoProvider.getMessage(exception.getClass().getSimpleName());
        return new ResponseEntity<>(new SimpleErrorResponse(message),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FileDeleteException.class)
    public ResponseEntity<Object> fileDeleteExHandle(FileDeleteException exception){
        String message = messageInfoProvider.getMessage(exception.getClass().getSimpleName());
        return new ResponseEntity<>(new SimpleErrorResponse(message),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AwsS3StoreFilesException.class)
    public ResponseEntity<Object> awsS3StoreFilesExHandle(AwsS3StoreFilesException exception){
        String message = messageInfoProvider.getMessage(exception.getClass().getSimpleName());
        return new ResponseEntity<>(new SimpleErrorResponse(message),HttpStatus.PAYLOAD_TOO_LARGE);
    }
}
