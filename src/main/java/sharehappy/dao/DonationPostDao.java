package sharehappy.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import sharehappy.dto.DonationPostListResponse;

import javax.sql.DataSource;

@Repository
public class DonationPostDao {

    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public DonationPostDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public long countDonationPost(){
        return jdbcTemplate.queryForObject("SELECT count(*) FROM donationpost ",Long.class);
    }

    public long selectDonationPost(){

    }


}
