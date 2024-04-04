package hospital_app.web;

import hospital_app.entities.Patient;
import hospital_app.repositories.PatientRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;

    @GetMapping("/user/index")
    public String index(
            Model model,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "4") int size,
            @RequestParam(name = "keyword", defaultValue = "") String keyword
    ) {

        Page<Patient> pagePatients;

        int patientsCountInPage;

        pagePatients = patientRepository.findPatientsByNomContainingIgnoreCase(keyword, PageRequest.of(page, size));
        patientsCountInPage = pagePatients.getContent().size();
        pagePatients = patientsCountInPage > 0 && page > 0
                ? patientRepository.findPatientsByNomContainingIgnoreCase(keyword, PageRequest.of(page, size))
                : patientRepository.findPatientsByNomContainingIgnoreCase(
                keyword, PageRequest.of(page > 0 ? page - 1 : page, size));

        model.addAttribute("patients", pagePatients.getContent());
        model.addAttribute("pages", new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage", patientsCountInPage > 0 ? page : page > 0 ? page - 1 : page);
        model.addAttribute("keyword", keyword);
        model.addAttribute("size", size);
        return "patients";
    }

    @GetMapping("/admin/delete")
    public String delete(
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "4") int size,
            @RequestParam(name = "keyword", defaultValue = "") String keyword
    ) {

        patientRepository.deleteById(id);
        return "redirect:/user/index?page=" + page + "&size=" + size + "&keyword=" + keyword;
    }

    @GetMapping("/admin/addPatient")
    public String addPatient(Model model) {
        model.addAttribute("patient", new Patient());
        return "addPatient";
    }

    @PostMapping("/admin/save")
    public String save(
            @Valid Patient patient,
            BindingResult bindingResult,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "4") int size,
            @RequestParam(name = "keyword", defaultValue = "") String keyword
    ) {
        if (bindingResult.hasErrors()) {
            return "addPatient";
        }

        patientRepository.save(patient);
        return "redirect:/user/index?page=" + page + "&size=" + size + "&keyword=" + keyword;
    }

    @GetMapping("/admin/editPatient")
    public String addPatient(
            Model model,
            Long id,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "4") int size,
            @RequestParam(name = "keyword", defaultValue = "") String keyword
    ) {
        Patient patient = patientRepository.findById(id).orElse(null);
        if (patient == null) throw new RuntimeException("Patient with id= '" + id + "' doesn't exist");
        model.addAttribute("patient", patient);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        model.addAttribute("size", size);
        return "editPatient";
    }

    @GetMapping("/")
    public String home(){
        return "redirect:/user/index";
    }
}
