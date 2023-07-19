package com.hanium.emoji_pot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class EmojiPotApplication {
	public static void main(String[] args) {
		SpringApplication.run(EmojiPotApplication.class, args);
	}

}
