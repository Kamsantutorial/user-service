package com.example.userservice;

import com.example.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
@Slf4j
class DemoApplicationTests {

	@Autowired
	private UserService userService;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;


	@Test
	void create() throws Exception {
		log.info("{}", bCryptPasswordEncoder.encode("admin@123"));
		log.info("{}", bCryptPasswordEncoder.encode("123"));
		log.info("{}", bCryptPasswordEncoder.matches("123", "$2a$10$ySBaY4qUaZ2c8w/BEufnnOaTFlGbZDGnEXs3vG71m96iPbusboixC"));
		log.info("{}", bCryptPasswordEncoder.matches("admin@123", "$2a$10$kW8ksfcZ9.MyQ0KbUT1TIusml4kghSNZgGeDqUGkOyeZuwPhsoDqe"));
//		UserDTO userDTO = new UserDTO();
//		userDTO.setUsername("username");
//		userDTO.setPassword("password");
//		this.userService.create(userDTO);
	}
}
