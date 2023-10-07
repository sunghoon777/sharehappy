package weShare.sharehappy.Exception.file;

import lombok.Getter;

import java.util.List;

@Getter
public class FileStoreException extends RuntimeException{

    private List<String> fileNames;

    public FileStoreException(List<String> fileNames) {
        this.fileNames = fileNames;
    }


}
