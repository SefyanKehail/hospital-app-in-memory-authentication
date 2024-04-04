package hospital_app;


import hospital_app.entities.Patient;
import hospital_app.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.stream.Stream;


@SpringBootApplication
public class HospitalApplication {
    public static void main(String[] args) {
        SpringApplication.run(HospitalApplication.class, args);
    }

//    @Bean
    CommandLineRunner start(PatientRepository patientRepository){
        return args -> {
            Stream.of("Karim", "Ahmed", "Imane", "Nizar", "Nisrine", "Houda", "Jamal")
                    .forEach( name -> {
                        Patient patient = Patient.builder()
                                .nom(name)
                                .dateNaissance(LocalDate.now())
                                .malade(Math.random() > 0.5)
                                .score((int) (Math.random() * 100))
                                .build();
                        patientRepository.save(patient);
                    });
        };
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
