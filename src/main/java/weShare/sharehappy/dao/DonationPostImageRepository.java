package weShare.sharehappy.dao;

import weShare.sharehappy.entity.DonationPostImage;

import java.util.List;

public interface DonationPostImageRepository {
    List<DonationPostImage> save(List<DonationPostImage> donationPostImageList);
}
