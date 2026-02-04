package detect_mortgage_system.D.M;

import detect_mortgage_system.D.M.Util.ShutdownManager;
import detect_mortgage_system.D.M.processing.ProcessingService;
import detect_mortgage_system.D.M.Service.ValidationService;
import jakarta.annotation.PreDestroy;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner sanityCheck() {
		return args -> System.out.println("🔥 SANITY CHECK: Runner executing");
	}

	@Bean
	CommandLineRunner startValidation(ValidationService validationService) {
		return args -> {
			System.out.println("🛡️ Starting Validation Thread");
			Thread t = new Thread(validationService);
			t.setDaemon(true);
			t.start();
		};
	}


	@Bean
	CommandLineRunner startProcessing(ProcessingService processingService) {
		return args -> {
			Thread processingThread = new Thread(processingService);
			processingThread.setName("processing-thread");
			processingThread.start();
		};
	}
	@PreDestroy
	public void onShutdown() {
		System.out.println("🛑 Application shutting down...");
		ShutdownManager.SHUTTING_DOWN.set(true);
	}
}
