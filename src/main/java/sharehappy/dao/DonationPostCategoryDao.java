package sharehappy.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import sharehappy.entity.DonationPostCategory;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DonationPostCategoryDao {

    private final JdbcTemplate jdbcTemplate;
    private static final RowMapper<DonationPostCategory> rowMapper = new RowMapper<DonationPostCategory>() {
        @Override
        public DonationPostCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new DonationPostCategory(rs.getString("name"),rs.getInt("categoryorder"),rs.getString("iconurl"));
        }
    };

    @Autowired
    public DonationPostCategoryDao(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //category 리스트 얻어오기
    public List<DonationPostCategory> selectAll(){
        return jdbcTemplate.query("SELECT * FROM donationpostcategory ORDER BY categoryorder",rowMapper);
    }

    //category 얻기
    public DonationPostCategory selectCategory(String categoryName){
        return jdbcTemplate.queryForObject("SELECT * FROM donationpostcategory WHERE name = ?",rowMapper,categoryName);
    }

}
