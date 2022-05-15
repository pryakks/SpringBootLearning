package com.coupan.service.app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class CouponServiceApplicationTests {

	@Test
	void passwordEncoders() {
		//implementation of PasswordEncoder interface
		// BCryptPasswordEncoder
		// noAuthPasswordEncoder
		//Pbkdf2PasswordEncoder = Password Based Key Derivation Function 2
		// SCryptPasswordEncoder = uses Scrypt hashing function (Bouncy Castle dependency to be added)

		System.out.println(new BCryptPasswordEncoder().encode("9999"));
		//$2a$10$aO8WtjHbU.ztHae0BBosnuAmnoRdryydxIFXM4q0eBcF7uXwKZuq.

	}
}

