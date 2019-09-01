package capstone.is4103capstone;

import capstone.is4103capstone.demoModule.service.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class Is4103CapstoneApplication {
    public static void main(String[] args) {
    	SpringApplication.run(Is4103CapstoneApplication.class, args);
    }
}
