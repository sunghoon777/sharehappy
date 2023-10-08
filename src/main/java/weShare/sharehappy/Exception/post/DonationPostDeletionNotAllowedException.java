package weShare.sharehappy.Exception.post;

import weShare.sharehappy.constant.PostDeleteCriteria;

public class DonationPostDeletionNotAllowedException extends RuntimeException{

    PostDeleteCriteria postDeleteCriteria;

    public DonationPostDeletionNotAllowedException(PostDeleteCriteria postDeleteCriteria){
        this.postDeleteCriteria = postDeleteCriteria;
    }

    public PostDeleteCriteria getPostDeleteCriteria() {
        return postDeleteCriteria;
    }
}
