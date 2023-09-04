package weShare.sharehappy.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import weShare.sharehappy.dto.postCategory.DonationPostCategoryInfo;
import javax.persistence.Entity;
import javax.persistence.Id;


@Getter
@NoArgsConstructor
@Entity
public class DonationPostCategory {

    @Id
    private String name;
    private String krName;
    private Integer categoryOrder;

    private String iconUrl;

    public DonationPostCategory(String name, String krName, Integer categoryOrder, String iconUrl) {
        this.name = name;
        this.krName = krName;
        this.categoryOrder = categoryOrder;
        this.iconUrl = iconUrl;
    }

    public DonationPostCategoryInfo changeToResponse(){
        return new DonationPostCategoryInfo(name,krName,iconUrl);
    }
}
