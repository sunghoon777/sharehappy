package weShare.sharehappy.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import weShare.sharehappy.constant.SortingOrder;
import weShare.sharehappy.dao.DonationPostRepository;
import weShare.sharehappy.entity.DonationPost;
import weShare.sharehappy.entity.DonationPostCategory;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcDonationPostRepository implements DonationPostRepository {

    private final JdbcTemplate jdbcTemplate;



    @Autowired
    public JdbcDonationPostRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //post 총 개수
    public Long countDonationPost(){
        return jdbcTemplate.queryForObject("SELECT count(*) FROM donationpost ",Long.class);
    }

    //category에 따른 post 총 개수 얻기
    @Override
    public Long countDonationPost(String category) {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM donationpost WHERE category = ?",Long.class,category);
    }

    @Override
    public List<DonationPost> selectOrderByRegDate(String category, SortingOrder sortingOrder) {
        return null;
    }

    @Override
    public List<DonationPost> selectOrderByEndDate(String category, SortingOrder sortingOrder) {
        return null;
    }

    @Override
    public List<DonationPost> selectOrderByTargetAmount(String category, SortingOrder sortingOrder) {
        return null;
    }

    @Override
    public List<DonationPost> selectOrderByFundPercentageDESC(String category, SortingOrder sortingOrder) {
        return null;
    }


}
