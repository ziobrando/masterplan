package it.avanscoperta.masterplan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class MasterplanApplication {

	public static void main(String[] args) {
		SpringApplication.run(MasterplanApplication.class, args);
	}

}
