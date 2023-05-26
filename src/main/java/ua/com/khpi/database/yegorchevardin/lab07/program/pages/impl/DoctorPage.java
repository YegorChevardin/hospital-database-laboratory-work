package ua.com.khpi.database.yegorchevardin.lab07.program.pages.impl;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import ua.com.khpi.database.yegorchevardin.lab07.database.entity.Doctor;
import ua.com.khpi.database.yegorchevardin.lab07.program.handlers.MenuOptionResolver;
import ua.com.khpi.database.yegorchevardin.lab07.program.handlers.ObjectInputHandler;
import ua.com.khpi.database.yegorchevardin.lab07.program.pages.AbstractPage;
import ua.com.khpi.database.yegorchevardin.lab07.service.services.DoctorService;

import java.util.List;

@Component("doctorPage")
public class DoctorPage extends AbstractPage {
    private static final String MENU_ITEMS =
            """
            0 - Exit the program;
            1 - Show all doctors;
            2 - Show all doctors by name;
            3 - Show doctor by id;
            4 - Insert doctor;
            5 - Delete doctor by id;
            6 - Find doctor by email;
            7 - Show all doctors by specific category;
            8 - Update doctor;
            9 - Go back;
            """;
    private static final List<Integer> options = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
    
    private final MenuOptionResolver menuOptionResolver;
    private final ObjectInputHandler<Doctor> objectInputHandler;
    private final DoctorService doctorService;
    
    public DoctorPage(Gson gson, MenuOptionResolver menuOptionResolver, ObjectInputHandler<Doctor> objectInputHandler, DoctorService doctorService) {
        super(gson);
        this.menuOptionResolver = menuOptionResolver;
        this.objectInputHandler = objectInputHandler;
        this.doctorService = doctorService;
    }

    @Override
    protected void execute() {
        System.out.println(MENU_ITEMS);
        Integer option = menuOptionResolver.resolve(options);
        switch (option) {
            case 0:
                System.exit(0);
                break;
            case 1:
                System.out.println(gson.toJson(doctorService.getAll()));
                break;
            case 2:
                String name = menuOptionResolver.resolveLine();
                System.out.println(
                        gson.toJson(doctorService.findByName(name))
                );
                break;
            case 3:
                System.out.println(
                        gson.toJson(
                                doctorService.getById(
                                        Long.valueOf(menuOptionResolver.resolve())
                                )
                        )
                );
                break;
            case 4:
                System.out.println(gson.toJson(
                        doctorService.insert(objectInputHandler.getObjectFromInput())
                ));
                break;
            case 5:
                doctorService.removeById(
                        Long.valueOf(menuOptionResolver.resolve())
                );
                System.out.println("Successfully deleted!");
                break;
            case 6:
                String email = menuOptionResolver.resolveLine();
                System.out.println(
                        gson.toJson(
                                doctorService.findByEmail(email)
                        )
                );
                break;
            case 7:
                String categoryName = menuOptionResolver.resolveLine();
                System.out.println(
                        gson.toJson(
                                doctorService.findByCategory(categoryName)
                        )
                );
                break;
            case 8:
                System.out.println("Type here doctor id you want to update: ");
                int id = menuOptionResolver.resolve();
                Doctor doctorToUpdate = objectInputHandler.getObjectFromInput();
                doctorToUpdate.setId((long) id);
                System.out.println(
                        gson.toJson(
                                doctorService.update(
                                        doctorToUpdate
                                )
                        )
                );
                break;
            case 9:
                return;
        }
        execute();
    }
}
