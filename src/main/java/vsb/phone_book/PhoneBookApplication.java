package vsb.phone_book;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import vsb.phone_book.model.User;
import vsb.phone_book.service.UserServiceImpl;

@SpringBootApplication
public class PhoneBookApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhoneBookApplication.class, args);
	}

}
