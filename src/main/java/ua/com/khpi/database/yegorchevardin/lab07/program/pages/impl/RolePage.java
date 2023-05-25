package ua.com.khpi.database.yegorchevardin.lab07.program.pages.impl;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.khpi.database.yegorchevardin.lab07.database.entity.Role;
import ua.com.khpi.database.yegorchevardin.lab07.program.handlers.MenuOptionResolver;
import ua.com.khpi.database.yegorchevardin.lab07.program.handlers.ObjectInputHandler;
import ua.com.khpi.database.yegorchevardin.lab07.program.pages.AbstractPage;
import ua.com.khpi.database.yegorchevardin.lab07.service.services.RoleService;

import java.util.List;

@Component("rolePage")
public class RolePage extends AbstractPage {
    private static final String MENU_ITEMS =
            """
            0 - Exit the program;
            1 - Show all role;
            2 - Show all role as a table;
            3 - Show role by id;
            4 - Insert role;
            5 - Delete role by id;
            6 - Go back;
            """;
    private static final List<Integer> options = List.of(0, 1, 2, 3, 4, 5, 6);
    private final MenuOptionResolver menuOptionResolver;
    private final ObjectInputHandler<Role> objectInputHandler;
    private final RoleService roleService;

    @Autowired
    public RolePage(
            Gson gson,
            MenuOptionResolver menuOptionResolver,
            ObjectInputHandler<Role> objectInputHandler,
            RoleService roleService) {
        super(gson);
        this.menuOptionResolver = menuOptionResolver;
        this.objectInputHandler = objectInputHandler;
        this.roleService = roleService;
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
                System.out.println(gson.toJson(roleService.getAll()));
                break;
            case 2:
                System.out.println(roleService.findAllAsTable().print());
                break;
            case 3:
                System.out.println(
                        gson.toJson(
                                roleService.getById(
                                        Long.valueOf(menuOptionResolver.resolve())
                                )
                        )
                );
                break;
            case 4:
                System.out.println(gson.toJson(
                        roleService.insert(objectInputHandler.getObjectFromInput())
                ));
                break;
            case 5:
                roleService.removeById(
                        Long.valueOf(menuOptionResolver.resolve())
                );
                System.out.println("Successfully deleted!");
                break;
            case 6:
                return;
        }
        execute();
    }
}
