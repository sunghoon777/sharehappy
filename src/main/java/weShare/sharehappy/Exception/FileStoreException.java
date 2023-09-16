package weShare.sharehappy.Exception;

import lombok.Getter;

@Getter
public class FileStoreException extends RuntimeException{

    private String fileName;

    public FileStoreException(String fileName) {
        this.fileName = fileName;
    }


}
