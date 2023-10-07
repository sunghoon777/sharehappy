package weShare.sharehappy.Exception.comment;

import lombok.Getter;

@Getter
public class NoExistingCommentsInPage extends RuntimeException{

    private int page;

    public NoExistingCommentsInPage(int page){
        this.page = page;
    }

}
