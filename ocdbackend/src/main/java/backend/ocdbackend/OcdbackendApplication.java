package backend.ocdbackend;

import backend.ocdbackend.model.ApplicationUser;
import backend.ocdbackend.model.Name;
import backend.ocdbackend.model.Role;
import backend.ocdbackend.repository.RoleRepository;
import backend.ocdbackend.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class OcdbackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(OcdbackendApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			// Ensure admin role exists
			Role adminRole = roleRepository.findByAuthority("ADMIN")
					.orElseGet(() -> roleRepository.save(new Role("ADMIN")));

			// Create and save the admin user
			Set<ObjectId> authorities = new HashSet<>();
			authorities.add(adminRole.getId());

			ApplicationUser admin = new ApplicationUser(
					"vaibhav20547@iiitd.ac.in", // email
					passwordEncoder.encode("vaibhavj@2023"), // encoded password
					authorities, // authorities
					new Name("Admin", "User"), // name
					null, // patient_number, adjust as necessary
					null, // dob, adjust as necessary
					null, // day_of_enrollment, adjust as necessary
					"Male", // gender, adjust as necessary
					"High School", // education, adjust as necessary
					"Engineer", // occupation, adjust as necessary
					null, // therapist_id, adjust as necessary
					null // profile_image, adjust as necessary
			);

			userRepository.save(admin);

			// Optionally, create and save other users as needed
		};
	}
}
