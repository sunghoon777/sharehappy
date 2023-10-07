package weShare.sharehappy.Exception.user;

import weShare.sharehappy.entity.User;

public class ExistingUserException extends RuntimeException{

    private String existingUserEmail;

    public ExistingUserException(String existingUserEmail) {
        this.existingUserEmail = existingUserEmail;
    }

    public String getExistingUserEmail() {
        return existingUserEmail;
    }
}
