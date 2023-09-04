package weShare.sharehappy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class SharehappyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SharehappyApplication.class, args);
	}

}
