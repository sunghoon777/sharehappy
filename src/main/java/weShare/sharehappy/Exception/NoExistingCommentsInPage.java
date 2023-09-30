package weShare.sharehappy.Exception;

import lombok.Getter;

@Getter
public class NoExistingCommentsInPage extends RuntimeException{

    private int page;

    public NoExistingCommentsInPage(int page){
        this.page = page;
    }

}
