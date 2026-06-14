package com.project.back_end.controllers;

public class PrescriptionController {
    

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.back_end.models.Prescription;
import com.project.back_end.services.PrescriptionService;

@RestController
@RequestMapping("/api/prescriptions")
@CrossOrigin(origins = "*")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @PostMapping
    public ResponseEntity<Prescription> addPrescription(
            @RequestBody Prescription prescription) {

        Prescription savedPrescription =
                prescriptionService.addPrescription(prescription);

        return ResponseEntity.ok(savedPrescription);
    }

    @GetMapping
    public ResponseEntity<List<Prescription>> getAllPrescriptions() {
        return ResponseEntity.ok(
                prescriptionService.getAllPrescriptions());
    }

    @GetMapping("/{prescriptionId}")
    public ResponseEntity<?> getPrescriptionById(
            @PathVariable Long prescriptionId) {

        Optional<Prescription> prescription =
                prescriptionService.getPrescriptionById(prescriptionId);

        if (prescription.isPresent()) {
            return ResponseEntity.ok(prescription.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{prescriptionId}")
    public ResponseEntity<String> updatePrescription(
            @PathVariable Long prescriptionId,
            @RequestBody Prescription prescription) {

        String response =
                prescriptionService.updatePrescription(
                        prescriptionId,
                        prescription);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{prescriptionId}")
    public ResponseEntity<String> deletePrescription(
            @PathVariable Long prescriptionId) {

        String response =
                prescriptionService.deletePrescription(prescriptionId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<List<Prescription>> getPrescriptionsByAppointment(
            @PathVariable Long appointmentId) {

        return ResponseEntity.ok(
                prescriptionService.getPrescriptionsByAppointment(
                        appointmentId));
    }
}// 1. Set Up the Controller Class:
//    - Annotate the class with `@RestController` to define it as a REST API controller.
//    - Use `@RequestMapping("${api.path}prescription")` to set the base path for all prescription-related endpoints.
//    - This controller manages creating and retrieving prescriptions tied to appointments.


// 2. Autowire Dependencies:
//    - Inject `PrescriptionService` to handle logic related to saving and fetching prescriptions.
//    - Inject the shared `Service` class for token validation and role-based access control.
//    - Inject `AppointmentService` to update appointment status after a prescription is issued.


// 3. Define the `savePrescription` Method:
//    - Handles HTTP POST requests to save a new prescription for a given appointment.
//    - Accepts a validated `Prescription` object in the request body and a doctor’s token as a path variable.
//    - Validates the token for the `"doctor"` role.
//    - If the token is valid, updates the status of the corresponding appointment to reflect that a prescription has been added.
//    - Delegates the saving logic to `PrescriptionService` and returns a response indicating success or failure.


// 4. Define the `getPrescription` Method:
//    - Handles HTTP GET requests to retrieve a prescription by its associated appointment ID.
//    - Accepts the appointment ID and a doctor’s token as path variables.
//    - Validates the token for the `"doctor"` role using the shared service.
//    - If the token is valid, fetches the prescription using the `PrescriptionService`.
//    - Returns the prescription details or an appropriate error message if validation fails.


}
