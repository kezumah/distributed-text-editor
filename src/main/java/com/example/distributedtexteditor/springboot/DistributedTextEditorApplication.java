package com.example.distributedtexteditor.springboot;

import com.example.distributedtexteditor.model.User;
import com.example.distributedtexteditor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.example.distributedtexteditor.repository")
public class DistributedTextEditorApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DistributedTextEditorApplication.class, args);
	}

	@Autowired
	private UserRepository userRepository;

	@Override
	public void run(String... args) throws Exception {
		this.userRepository.save(new User("Di", "Zhang", "dizhang@gmail.com"));
		this.userRepository.save(new User("Di1", "Zhang1", "dizhang1@gmail.com"));
		this.userRepository.save(new User("Di2", "Zhang2", "dizhang2@gmail.com"));
	}
}