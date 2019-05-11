package br.com.alura.forum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@EnableSpringDataWebSupport
@SpringBootApplication
public class AluraForumApplication {

	public static void main(String[] args) {
		SpringApplication.run(AluraForumApplication.class, args);
	}
}
