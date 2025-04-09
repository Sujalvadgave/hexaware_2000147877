package com.hexaware.dao;

import com.hexaware.entity.Appointment;
import com.hexaware.exception.PatientNumberNotFoundException;

import java.util.List;

public interface IHospitalService {
    
    // Get appointment by ID
    Appointment getAppointmentById(int appointmentId) throws Exception;
    
    // Get appointments for a specific patient
    List<Appointment> getAppointmentsForPatient(int patientId) throws PatientNumberNotFoundException;
    
    // Get appointments for a specific doctor
    List<Appointment> getAppointmentsForDoctor(int doctorId) throws Exception;
    
    // Schedule a new appointment
    boolean scheduleAppointment(Appointment appointment) throws Exception;
    
    // Update an existing appointment
    boolean updateAppointment(Appointment appointment) throws Exception;
    
    // Cancel an appointment
    boolean cancelAppointment(int appointmentId) throws Exception;
}
