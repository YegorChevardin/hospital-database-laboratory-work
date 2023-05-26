package ua.com.khpi.database.yegorchevardin.lab07.program.pages.impl;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import ua.com.khpi.database.yegorchevardin.lab07.database.entity.User;
import ua.com.khpi.database.yegorchevardin.lab07.program.handlers.MenuOptionResolver;
import ua.com.khpi.database.yegorchevardin.lab07.program.handlers.ObjectInputHandler;
import ua.com.khpi.database.yegorchevardin.lab07.program.pages.AbstractPage;
import ua.com.khpi.database.yegorchevardin.lab07.service.services.UserService;

import java.util.List;

@Component("userPage")
public class UserPage extends AbstractPage {
    private static final String MENU_ITEMS =
            """
            0 - Exit the program;
            1 - Show all users;
            2 - Show all users by name;
            3 - Show user by id;
            4 - Insert user;
            5 - Delete user by id;
            6 - Find user by email;
            7 - Show users by phone number;
            8 - Update user;
            9 - Go back;
            """;
    private static final List<Integer> options = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

    private final MenuOptionResolver menuOptionResolver;
    private final ObjectInputHandler<User> objectInputHandler;
    private final UserService userService;
    
    public UserPage(Gson gson, MenuOptionResolver menuOptionResolver, ObjectInputHandler<User> objectInputHandler, UserService userService) {
        super(gson);
        this.menuOptionResolver = menuOptionResolver;
        this.objectInputHandler = objectInputHandler;
        this.userService = userService;
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
                System.out.println(gson.toJson(userService.getAll()));
                break;
            case 2:
                String name = menuOptionResolver.resolveLine();
                System.out.println(
                        gson.toJson(userService.findByName(name))
                );
                break;
            case 3:
                System.out.println(
                        gson.toJson(
                                userService.getById(
                                        Long.valueOf(menuOptionResolver.resolve())
                                )
                        )
                );
                break;
            case 4:
                System.out.println(gson.toJson(
                        userService.insert(objectInputHandler.getObjectFromInput())
                ));
                break;
            case 5:
                userService.removeById(
                        Long.valueOf(menuOptionResolver.resolve())
                );
                System.out.println("Successfully deleted!");
                break;
            case 6:
                String email = menuOptionResolver.resolveLine();
                System.out.println(
                        gson.toJson(
                                userService.findByEmail(email)
                        )
                );
                break;
            case 7:
                String categoryName = menuOptionResolver.resolveLine();
                System.out.println(
                        gson.toJson(
                                userService.findByPhoneNumber(categoryName)
                        )
                );
                break;
            case 8:
                System.out.println("Type here user id you want to update: ");
                int id = menuOptionResolver.resolve();
                User userToUpdate = objectInputHandler.getObjectFromInput();
                userToUpdate.setId((long) id);
                System.out.println(
                        gson.toJson(
                                userService.update(
                                        userToUpdate
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
