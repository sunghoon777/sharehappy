package sharehappy.entity;

public class DonationPostCategory {

    private String name;
    private Integer categoryOrder;
    private String iconUrl;

    public DonationPostCategory() {
    }

    public DonationPostCategory(String name, Integer categoryOrder,String iconUrl) {
        this.name = name;
        this.categoryOrder = categoryOrder;
        this.iconUrl = iconUrl;
    }

    public String getName() {
        return name;
    }

    public Integer getCategoryOrder() {
        return categoryOrder;
    }

    public String getIconUrl() {
        return iconUrl;
    }
}
