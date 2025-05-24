package sustech.cs304.AIDE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the AIDE server.
 */
@SpringBootApplication
public class AideServerSpringApplication {

    /**
     * Main method to run the AIDE server application.
     */
	public static void main(String[] args) {
		SpringApplication.run(AideServerSpringApplication.class, args);
	}

}
