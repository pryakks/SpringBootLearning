package com.product.service.app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductServiceApplicationTests {
	@Test
	void passwordEncoders() {
		//implementation of PasswordEncoder interface
		// BCryptPasswordEncoder
		// noAuthPasswordEncoder
		//Pbkdf2PasswordEncoder = Password Based Key Derivation Function 2
		// SCryptPasswordEncoder = uses Scrypt hashing function (Bouncy Castle dependency to be added)

		System.out.println(new BCryptPasswordEncoder().encode("password"));
		//$2a$10$aO8WtjHbU.ztHae0BBosnuAmnoRdryydxIFXM4q0eBcF7uXwKZuq.

		System.out.println(new Pbkdf2PasswordEncoder().encode("password"));
		//43c5d834864dce32e808eeb6afcd61a7cd28b0a8d43a7cdd011ed3bbd08063e6c48de7c863087056

		System.out.println(new SCryptPasswordEncoder().encode("password"));
//$e0801$7NeBJmWHwbi9//2Jhwc+xDXDT1yk2GgItNs4s3E5djqqJu6V60ik0Q+N0EAtC0to389J74krDVoBMp0m5qdJ3g==$F+xBJlrmk2pe7ev/IwHx4wYkJRj851L4ZZkAO4yjvYQ=

		Map<String, PasswordEncoder> encoders = new HashMap<>();
		encoders.put("bcrypt", new BCryptPasswordEncoder());
		encoders.put("scrypt", new SCryptPasswordEncoder());
		System.out.println(new DelegatingPasswordEncoder("bcrypt", encoders).encode("password"));
		//{bcrypt}$2a$10$2Leg./mhOr.DU4aU2mTO..3CgoO7xRSQx0opMR.PzUSkxLZf6Hf36

		// we give list of encoders to DelegatingPasswordEncoder and then at runtime we pass a key of the encoder in the map then it will
		// encode accordingly
		// we can pass key in body or header
	}
}

}
