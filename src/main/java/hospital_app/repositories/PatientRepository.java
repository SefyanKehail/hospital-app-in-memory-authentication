package hospital_app.repositories;

import hospital_app.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findPatientByNomIgnoreCase(String nom);
    Page<Patient> findPatientsByNomContainingIgnoreCase(String nom, Pageable pageable);
}
