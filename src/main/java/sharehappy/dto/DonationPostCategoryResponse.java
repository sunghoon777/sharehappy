package sharehappy.dto;

public class DonationPostCategoryResponse {

    private String name;
    private String iconUrl;

    public DonationPostCategoryResponse() {
    }

    public DonationPostCategoryResponse(String name, String iconUrl) {
        this.name = name;
        this.iconUrl = iconUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getName() {
        return name;
    }

    public String getIconUrl() {
        return iconUrl;
    }
}
