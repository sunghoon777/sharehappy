package weShare.sharehappy.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import weShare.sharehappy.dao.DonationPostCategoryRepository;
import weShare.sharehappy.entity.DonationPostCategory;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcDonationPostCategoryRepository implements DonationPostCategoryRepository {

    private final JdbcTemplate jdbcTemplate;
    private static final RowMapper<DonationPostCategory> rowMapper = new RowMapper<DonationPostCategory>() {
        @Override
        public DonationPostCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new DonationPostCategory(rs.getString("name"),rs.getInt("categoryorder"),rs.getString("iconurl"));
        }
    };

    @Autowired
    public JdbcDonationPostCategoryRepository(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //category 리스트 얻어오기
    public List<DonationPostCategory> selectAll(){
        return jdbcTemplate.query("SELECT * FROM donationpostcategory ORDER BY categoryorder",rowMapper);
    }

    //category 얻기
    public Optional<DonationPostCategory> selectCategory(String categoryName){
        List<DonationPostCategory> list = jdbcTemplate.query("SELECT * FROM donationpostcategory WHERE name = ?",rowMapper,categoryName);
        return Optional.ofNullable(list.isEmpty()?null:list.get(0));
    }

}
