package com.hexaware.main;

import com.hexaware.dao.HospitalServiceImpl;
import com.hexaware.dao.IHospitalService;
import com.hexaware.entity.Appointment;
import com.hexaware.exception.PatientNumberNotFoundException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MainModule {
    private static final Scanner scanner = new Scanner(System.in);
    private static final IHospitalService hospitalService = new HospitalServiceImpl();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static void main(String[] args) throws PatientNumberNotFoundException {
        boolean exit = false;

        System.out.println("===============================================");
        System.out.println(" HOSPITAL MANAGEMENT SYSTEM");
        System.out.println("===============================================");

        while (!exit) {
            System.out.println("\nSelect an option:");
            System.out.println("1. View Appointment by ID");
            System.out.println("2. View Patient's Appointments");
            System.out.println("3. View Doctor's Appointments");
            System.out.println("4. Schedule New Appointment");
            System.out.println("5. Update Appointment");
            System.out.println("6. Cancel Appointment");
            System.out.println("0. Exit");
            System.out.print("\nEnter your choice: ");

            int choice = getIntInput();

            try {
                switch (choice) {
                    case 1:
                        viewAppointmentById();
                        break;
                    case 2:
                        viewPatientAppointments();
                        break;
                    case 3:
                        viewDoctorAppointments();
                        break;
                    case 4:
                        scheduleAppointment();
                        break;
                    case 5:
                        updateAppointment();
                        break;
                    case 6:
                        cancelAppointment();
                        break;
                    case 0:
                        exit = true;
                        System.out.println("Thank you for using Hospital Management System!");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private static void viewAppointmentById() {
        try {
            System.out.print("Enter Appointment ID: ");
            int appointmentId = getIntInput();

            Appointment appointment = hospitalService.getAppointmentById(appointmentId);
            System.out.println("\nAppointment Details:");
            System.out.println(appointment);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewPatientAppointments() {
        try {
            System.out.print("Enter Patient ID: ");
            int patientId = getIntInput();

            List<Appointment> appointments = hospitalService.getAppointmentsForPatient(patientId);

            if (appointments.isEmpty()) {
                System.out.println("No appointments found for this patient.");
            } else {
                System.out.println("\nPatient's Appointments:");
                for (Appointment appointment : appointments) {
                    System.out.println(appointment);
                }
            }

        } catch (PatientNumberNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewDoctorAppointments() {
        try {
            System.out.print("Enter Doctor ID: ");
            int doctorId = getIntInput();

            List<Appointment> appointments = hospitalService.getAppointmentsForDoctor(doctorId);

            if (appointments.isEmpty()) {
                System.out.println("No appointments found for this doctor.");
            } else {
                System.out.println("\nDoctor's Appointments:");
                for (Appointment appointment : appointments) {
                    System.out.println(appointment);
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void scheduleAppointment() {
        try {
            Appointment appointment = new Appointment();

            System.out.print("Enter Patient ID: ");
            appointment.setPatientId(getIntInput());

            System.out.print("Enter Doctor ID: ");
            appointment.setDoctorId(getIntInput());

            System.out.print("Enter Appointment Date and Time (yyyy-MM-dd HH:mm): ");
            String dateTimeStr = scanner.next();
            Date appointmentDate = dateFormat.parse(dateTimeStr);
            appointment.setAppointmentDate(appointmentDate);

            // Consume the newline
            scanner.nextLine();

            System.out.print("Enter Description: ");
            String description = scanner.nextLine();
            appointment.setDescription(description);

            boolean success = hospitalService.scheduleAppointment(appointment);

            if (success) {
                System.out.println("Appointment scheduled successfully!");
            } else {
                System.out.println("Failed to schedule appointment.");
            }

        } catch (ParseException e) {
            System.out.println("Error: Invalid date format. Please use yyyy-MM-dd HH:mm");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void updateAppointment() {
        try {
            System.out.print("Enter Appointment ID to update: ");
            int appointmentId = getIntInput();

            // First, retrieve the existing appointment
            Appointment appointment;
            try {
                appointment = hospitalService.getAppointmentById(appointmentId);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                return;
            }

            System.out.println("Current Appointment Details:");
            System.out.println(appointment);
            System.out.println("\nEnter new details (press Enter to keep current value):");

            // Update patient ID
            System.out.print("Patient ID [" + appointment.getPatientId() + "]: ");
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                appointment.setPatientId(Integer.parseInt(input));
            }

            // Update doctor ID
            System.out.print("Doctor ID [" + appointment.getDoctorId() + "]: ");
            input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                appointment.setDoctorId(Integer.parseInt(input));
            }

            // Update appointment date
            System.out.print("Appointment Date and Time [" + dateFormat.format(appointment.getAppointmentDate()) + "] (yyyy-MM-dd HH:mm): ");
            input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                try {
                    Date appointmentDate = dateFormat.parse(input);
                    appointment.setAppointmentDate(appointmentDate);
                } catch (ParseException e) {
                    System.out.println("Invalid date format. Keeping current value.");
                }
            }

            // Update description
            System.out.print("Description [" + appointment.getDescription() + "]: ");
            input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                appointment.setDescription(input);
            }

            boolean success = hospitalService.updateAppointment(appointment);

            if (success) {
                System.out.println("Appointment updated successfully!");
            } else {
                System.out.println("Failed to update appointment.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void cancelAppointment() {
        try {
            System.out.print("Enter Appointment ID to cancel: ");
            int appointmentId = getIntInput();

            boolean success = hospitalService.cancelAppointment(appointmentId);

            if (success) {
                System.out.println("Appointment cancelled successfully!");
            } else {
                System.out.println("Failed to cancel appointment.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
}