package ua.com.khpi.database.yegorchevardin.lab07.program.pages.impl;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.khpi.database.yegorchevardin.lab07.database.entity.Permission;
import ua.com.khpi.database.yegorchevardin.lab07.program.handlers.MenuOptionResolver;
import ua.com.khpi.database.yegorchevardin.lab07.program.handlers.ObjectInputHandler;
import ua.com.khpi.database.yegorchevardin.lab07.program.pages.AbstractPage;
import ua.com.khpi.database.yegorchevardin.lab07.service.services.PermissionService;
import java.util.List;

@Component("permissionPage")
public class PermissionPage extends AbstractPage {
    private static final String MENU_ITEMS =
            """
            0 - Exit the program;
            1 - Show all permission;
            2 - Show all permissions as a table;
            3 - Show permission by id;
            4 - Insert permission;
            5 - Delete permission by id;
            6 - Go back;
            """;
    private static final List<Integer> options = List.of(0, 1, 2, 3, 4, 5, 6);

    private final MenuOptionResolver menuOptionResolver;
    private final PermissionService permissionService;
    private final ObjectInputHandler<Permission> objectInputHandler;

    @Autowired
    public PermissionPage(
            Gson gson,
            MenuOptionResolver menuOptionResolver,
            PermissionService permissionService,
            ObjectInputHandler<Permission> objectInputHandler
    ) {
        super(gson);
        this.menuOptionResolver = menuOptionResolver;
        this.permissionService = permissionService;
        this.objectInputHandler = objectInputHandler;
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
                System.out.println(gson.toJson(permissionService.getAll()));
                break;
            case 2:
                System.out.println(permissionService.findAllAsTable().print());
                break;
            case 3:
                System.out.println(
                        gson.toJson(
                                permissionService.getById(
                                        Long.valueOf(menuOptionResolver.resolve())
                                )
                        )
                );
                break;
            case 4:
                System.out.println(gson.toJson(
                        permissionService.insert(objectInputHandler.getObjectFromInput())
                ));
                break;
            case 5:
                permissionService.getById(
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
