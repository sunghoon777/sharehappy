package sharehappy.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DBConfig {

    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String USER = "sunghoon";
    private static final String PASSWORD = "qkqxld12!@";
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String SCHEMAS = "sharehappy";
    private static final String OPTION = "?characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true";

    //커낵션풀 빈객체
    @Bean(destroyMethod = "close")
    public DataSource dataSource(){
        DataSource dataSource = new DataSource();
        dataSource.setDriverClassName(DRIVER);
        dataSource.setUrl(URL+SCHEMAS+OPTION);
        dataSource.setUsername(USER);
        dataSource.setPassword(PASSWORD);
        //초기 커넥션풀 사이즈는 30개
        dataSource.setInitialSize(30);
        //최대 300개까지 풀에서 유지, 최대 300개까지 가져올 수 있다.
        dataSource.setMaxActive(300);
        dataSource.setMaxIdle(300);
        //커낵션을 가져올 때 최대 40초까지만 기다린다.
        dataSource.setMaxWait(40);
        //유효성 검사 쿼리 설정
        dataSource.setValidationQuery("SELECT 1");
        //커낵션을 가져올 때 유효성 검사 실행
        dataSource.setTestOnBorrow(true);
        //유휴 상태일 때 커낵션이 유효한지 검사함.
        dataSource.setTestWhileIdle(true);
        //유휴 시간이 5분 지나면 이 커넥션을 커넥션풀에서 제거, DBMS 에서도 커넥션이 한동한 쿼리가 일어나지 않으면 DBMS 자체에서 커넥션을 끊는 설정을 할 수 있다.
        dataSource.setMinEvictableIdleTimeMillis(1000*60*5);
        //10초마다 유휴 상태인 커넥션들을 검사한다. 이 값을 1초 이하로 설정하면 안된다.
        dataSource.setTimeBetweenEvictionRunsMillis(1000*10);
        return dataSource;
    }

    //플랫폼 트랜잭션 매니저 빈객체
    @Bean
    public PlatformTransactionManager transactionManager(){
        return new DataSourceTransactionManager(dataSource());
    }

}
