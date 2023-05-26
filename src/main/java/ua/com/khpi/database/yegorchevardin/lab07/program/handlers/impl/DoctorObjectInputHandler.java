package ua.com.khpi.database.yegorchevardin.lab07.program.handlers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.com.khpi.database.yegorchevardin.lab07.database.constants.DoctorTypes;
import ua.com.khpi.database.yegorchevardin.lab07.database.entity.Category;
import ua.com.khpi.database.yegorchevardin.lab07.database.entity.Doctor;
import ua.com.khpi.database.yegorchevardin.lab07.program.handlers.MenuOptionResolver;
import ua.com.khpi.database.yegorchevardin.lab07.program.handlers.ObjectInputHandler;

@Component
@RequiredArgsConstructor
public class DoctorObjectInputHandler implements ObjectInputHandler<Doctor> {
    private final MenuOptionResolver menuOptionResolver;
    private final ObjectInputHandler<Category> objectInputHandler;

    @Override
    public Doctor getObjectFromInput() {
        Doctor doctor = new Doctor();

        System.out.println("Please type name for doctor: ");
        doctor.setName(menuOptionResolver.resolveLine());
        System.out.println("Please type second name for doctor: ");
        doctor.setSecondName(menuOptionResolver.resolveLine());
        System.out.println("Please, type here email for the doctor: ");
        doctor.setEmail(menuOptionResolver.resolveLine());
        System.out.println("Please type work experience for doctor: ");
        doctor.setWorkExperience(menuOptionResolver.resolve());
        System.out.println("Please type here type of doctor (doctor or nurse): ");
        doctor.setType(resolveDoctorType());
        System.out.println("Please type here category for doctor: ");
        doctor.setCategory(objectInputHandler.getObjectFromInput());

        return doctor;
    }

    private DoctorTypes resolveDoctorType() {
        String type = menuOptionResolver.resolveLine();
        try {
            return DoctorTypes.getTypeByName(type);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.out.println("Please, try again: ");
            return resolveDoctorType();
        }
    }
}
