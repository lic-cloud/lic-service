package cn.bestsort;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-08-24 10:39
 */
@EnableJpaAuditing
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        disableWarning(); //禁用警告
        SpringApplication.run(Main.class, args);
    }
    public static void disableWarning() {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            Unsafe u = (Unsafe) theUnsafe.get(null);

            Class cls = Class.forName("jdk.internal.module.IllegalAccessLogger");
            Field logger = cls.getDeclaredField("logger");
            u.putObjectVolatile(cls, u.staticFieldOffset(logger), null);
        } catch (Exception e) {
            // ignore
        }
    }
}