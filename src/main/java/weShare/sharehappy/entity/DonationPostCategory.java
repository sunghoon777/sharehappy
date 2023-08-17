package weShare.sharehappy.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
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

}
