package cn.bestsort;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-08-24 10:39
 */

@EnableJpaAuditing
@SpringBootApplication(scanBasePackages = {"cn.bestsort",
    "org.springframework.boot.autoconfigure.data.redis"})
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
