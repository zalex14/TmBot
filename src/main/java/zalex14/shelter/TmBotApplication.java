package zalex14.shelter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TmBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(TmBotApplication.class, args);
        System.out.println("Pet shelter project incl. Telegram support");
    }
}
