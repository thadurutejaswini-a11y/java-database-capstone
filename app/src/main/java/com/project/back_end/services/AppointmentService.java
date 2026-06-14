package com.project.back_end.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.back_end.models.Appointment;
import com.project.back_end.repositories.AppointmentRepository;
import com.project.back_end.repositories.DoctorRepository;
import com.project.back_end.repositories.PatientRepository;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final TokenService tokenService;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public AppointmentService(
            AppointmentRepository appointmentRepository,
            TokenService tokenService,
            PatientRepository patientRepository,
            DoctorRepository doctorRepository) {

        this.appointmentRepository = appointmentRepository;
        this.tokenService = tokenService;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    @Transactional
    public int bookAppointment(Appointment appointment) {
        try {
            appointmentRepository.save(appointment);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    @Transactional
    public String updateAppointment(Long appointmentId,
                                    Long patientId,
                                    Appointment updatedAppointment) {

        Optional<Appointment> existingOpt =
                appointmentRepository.findById(appointmentId);

        if (existingOpt.isEmpty()) {
            return "Appointment not found";
        }

        Appointment existingAppointment = existingOpt.get();

        if (!existingAppointment.getPatient().getPatientId().equals(patientId)) {
            return "Unauthorized patient";
        }

        existingAppointment.setAppointmentDate(
                updatedAppointment.getAppointmentDate());
        existingAppointment.setAppointmentTime(
                updatedAppointment.getAppointmentTime());
        existingAppointment.setDoctor(
                updatedAppointment.getDoctor());

        appointmentRepository.save(existingAppointment);

        return "Appointment updated successfully";
    }

    @Transactional
    public String cancelAppointment(Long appointmentId, Long patientId) {

        Optional<Appointment> appointmentOpt =
                appointmentRepository.findById(appointmentId);

        if (appointmentOpt.isEmpty()) {
            return "Appointment not found";
        }

        Appointment appointment = appointmentOpt.get();

        if (!appointment.getPatient().getPatientId().equals(patientId)) {
            return "Unauthorized patient";
        }

        appointmentRepository.delete(appointment);

        return "Appointment cancelled successfully";
    }

    @Transactional(readOnly = true)
    public List<Appointment> getAppointments(
            Long doctorId,
            LocalDate appointmentDate,
            String patientName) {

        return appointmentRepository
                .findByDoctorDoctorIdAndAppointmentDate(
                        doctorId,
                        appointmentDate);
    }

    @Transactional
    public String changeStatus(Long appointmentId, String status) {

        Optional<Appointment> appointmentOpt =
                appointmentRepository.findById(appointmentId);

        if (appointmentOpt.isEmpty()) {
            return "Appointment not found";
        }

        Appointment appointment = appointmentOpt.get();
        appointment.setStatus(status);

        appointmentRepository.save(appointment);

        return "Status updated successfully";
    }
}
