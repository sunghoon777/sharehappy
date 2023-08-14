package sharehappy.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class DonationPostListRequest {

    @NotNull(message = "group value is empty")
    @Positive(message = "group value is positive value")
    private Long group;
    @NotEmpty(message = "category value is empty")
    private String category;
    @NotEmpty(message = "sortCriteria value is empty")
    private String sortCriteria;

    public DonationPostListRequest() {
    }

    public DonationPostListRequest(Long group, String category, String sortCriteria) {
        this.group = group;
        this.category = category;
        this.sortCriteria = sortCriteria;
    }

    public Long getGroup() {
        return group;
    }

    public void setGroup(Long group) {
        this.group = group;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSortCriteria() {
        return sortCriteria;
    }

    public void setSortCriteria(String sortCriteria) {
        this.sortCriteria = sortCriteria;
    }
}
