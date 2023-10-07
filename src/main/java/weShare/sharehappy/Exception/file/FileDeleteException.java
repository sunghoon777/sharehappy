package weShare.sharehappy.Exception.file;

import lombok.Getter;

import java.util.List;

@Getter
public class FileDeleteException extends RuntimeException{

    private List<String> fileNames;
    public FileDeleteException(List<String> fileNames){
        this.fileNames = fileNames;
    }

}
