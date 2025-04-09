package com.hexaware.dao;

import com.hexaware.entity.Appointment;
import com.hexaware.exception.PatientNumberNotFoundException;
import com.hexaware.util.DBConnUtil;
import com.hexaware.util.DBPropertyUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HospitalServiceImpl implements IHospitalService {

    private Connection getDBConnection() throws SQLException {
        String connectionString = DBPropertyUtil.getPropertyString("db.properties");
        return DBConnUtil.getConnection(connectionString);
    }

    @Override
    public Appointment getAppointmentById(int appointmentId) throws Exception {
        String query = "SELECT * FROM Appointment WHERE appointmentId = ?";
        
        try (Connection connection = getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setInt(1, appointmentId);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractAppointmentFromResultSet(resultSet);
                } else {
                    throw new Exception("Appointment with ID " + appointmentId + " not found");
                }
            }
        }
    }

    @Override
    public List<Appointment> getAppointmentsForPatient(int patientId) throws PatientNumberNotFoundException {
        String query = "SELECT * FROM Appointment WHERE patientId = ?";
        List<Appointment> appointments = new ArrayList<>();
        
        try (Connection connection = getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setInt(1, patientId);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                // Check if patient exists first
                if (!resultSet.isBeforeFirst()) {
                    // No data found for this patient ID
                    throw new PatientNumberNotFoundException("Patient with ID " + patientId + " not found");
                }
                
                while (resultSet.next()) {
                    appointments.add(extractAppointmentFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new PatientNumberNotFoundException("Error retrieving appointments: " + e.getMessage());
        }
        
        return appointments;
    }

    @Override
    public List<Appointment> getAppointmentsForDoctor(int doctorId) throws Exception {
        String query = "SELECT * FROM Appointment WHERE doctorId = ?";
        List<Appointment> appointments = new ArrayList<>();
        
        try (Connection connection = getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setInt(1, doctorId);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    appointments.add(extractAppointmentFromResultSet(resultSet));
                }
            }
        }
        
        return appointments;
    }

    @Override
    public boolean scheduleAppointment(Appointment appointment) throws Exception {
        String query = "INSERT INTO Appointment (patientId, doctorId, appointmentDate, description) VALUES (?, ?, ?, ?)";
        
        try (Connection connection = getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setInt(1, appointment.getPatientId());
            statement.setInt(2, appointment.getDoctorId());
            statement.setTimestamp(3, new Timestamp(appointment.getAppointmentDate().getTime()));
            statement.setString(4, appointment.getDescription());
            
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    @Override
    public boolean updateAppointment(Appointment appointment) throws Exception {
        String query = "UPDATE Appointment SET patientId=?, doctorId=?, appointmentDate=?, description=? WHERE appointmentId=?";
        
        try (Connection connection = getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setInt(1, appointment.getPatientId());
            statement.setInt(2, appointment.getDoctorId());
            statement.setTimestamp(3, new Timestamp(appointment.getAppointmentDate().getTime()));
            statement.setString(4, appointment.getDescription());
            statement.setInt(5, appointment.getAppointmentId());
            
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    @Override
    public boolean cancelAppointment(int appointmentId) throws Exception {
        String query = "DELETE FROM Appointment WHERE appointmentId=?";
        
        try (Connection connection = getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setInt(1, appointmentId);
            
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    // Helper method to extract Appointment from ResultSet
    private Appointment extractAppointmentFromResultSet(ResultSet rs) throws SQLException {
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(rs.getInt("appointmentId"));
        appointment.setPatientId(rs.getInt("patientId"));
        appointment.setDoctorId(rs.getInt("doctorId"));
        appointment.setAppointmentDate(rs.getTimestamp("appointmentDate"));
        appointment.setDescription(rs.getString("description"));
        return appointment;
    }
}