package weShare.sharehappy.Exception.file;

import lombok.Getter;

import java.util.List;

@Getter
public class AwsS3StoreFilesException extends RuntimeException{
    private List<String> fileNames;

    public AwsS3StoreFilesException(List<String> fileNames){
        this.fileNames = fileNames;
    }

}
