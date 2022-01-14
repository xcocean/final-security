package top.lingkang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import top.lingkang.annotation.EnableFinalAnnotation;
import top.lingkang.annotation.FinalSecurity;

/**
 * @author lingkang
 * @date 2021/8/11 10:09
 * @description
 */

@FinalSecurity
@EnableFinalAnnotation
@EnableScheduling
@SpringBootApplication
public class FinalSecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(FinalSecurityApplication.class);
    }
}
