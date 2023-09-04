package weShare.sharehappy.controller.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import weShare.sharehappy.Exception.NoExistingDonationPostCategory;
import weShare.sharehappy.Exception.NoMoreDonationPostException;
import weShare.sharehappy.dto.error.SimpleErrorResponse;


@Slf4j
@ControllerAdvice
public class ErrorControllerAdvice {

    @ExceptionHandler(NoExistingDonationPostCategory.class)
    public ResponseEntity<SimpleErrorResponse> noCategoryExHandle(){
        return new ResponseEntity<>(new SimpleErrorResponse("해당 카데고리를 찾을 수 없습니다"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoMoreDonationPostException.class)
    public ResponseEntity<SimpleErrorResponse> noMorePostExHandle(){
        return new ResponseEntity<>(new SimpleErrorResponse("더 이상 게시판 글이 존재하지 않습니다"), HttpStatus.NOT_FOUND);
    }


}
