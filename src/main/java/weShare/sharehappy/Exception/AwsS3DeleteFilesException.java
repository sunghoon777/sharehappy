package weShare.sharehappy.Exception;

import java.util.Set;

public class AwsS3DeleteFilesException extends RuntimeException {

    private Set<String> notDeletedFileKeys;
    public AwsS3DeleteFilesException(Set<String> keySet) {
        notDeletedFileKeys = keySet;
    }

    public Set<String> getNotDeletedFileKeys() {
        return notDeletedFileKeys;
    }
}
