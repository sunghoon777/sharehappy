package sharehappy.dto;

public class DonationPostListResponse {

    private String postId;
    private String organization;
    private String title;
    private String imageUrl;
    private String targetAmount;
    private String currentAmount;

    public DonationPostListResponse() {
    }

    public DonationPostListResponse(String postId, String organization, String title, String imageUrl, String targetAmount, String currentAmount) {
        this.postId = postId;
        this.organization = organization;
        this.title = title;
        this.imageUrl = imageUrl;
        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(String targetAmount) {
        this.targetAmount = targetAmount;
    }

    public String getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(String currentAmount) {
        this.currentAmount = currentAmount;
    }
}
