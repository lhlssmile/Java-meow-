package lhl.hana.xk.review_multi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("lhl.hana.xk.review_multi.mapper")
public class ReviewMultiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReviewMultiApplication.class, args);
	}

}
