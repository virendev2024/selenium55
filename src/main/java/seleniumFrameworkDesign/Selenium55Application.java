package seleniumFrameworkDesign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Selenium55Application {

    public static void main(String[] args) {
        SpringApplication.run(Selenium55Application.class, args);
        String path = System.getProperty("user.dir");
        System.out.println("so the path is : "+path);

    }

}
