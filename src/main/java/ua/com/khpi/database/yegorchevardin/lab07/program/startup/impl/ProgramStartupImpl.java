package ua.com.khpi.database.yegorchevardin.lab07.program.startup.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ua.com.khpi.database.yegorchevardin.lab07.program.handlers.MenuOptionResolver;
import ua.com.khpi.database.yegorchevardin.lab07.program.pages.Page;
import ua.com.khpi.database.yegorchevardin.lab07.program.startup.ProgramStartup;
import ua.com.khpi.database.yegorchevardin.lab07.service.services.ProgramStartupService;

import java.util.List;
import java.util.Map;

@Component
public class ProgramStartupImpl implements ProgramStartup {
    private static final String MENU_ITEMS =
            """
            0 - Exit the program;
            1 - Go to Permissions;
            2 - Go to Roles;
            3 - Go to Categories;
            4 - Go to Doctors;
            5 - Go to Users;
            """;
    private static final List<Integer> options = List.of(0, 1, 2, 3, 4, 5);
    private static final String LOGO =
            """
                      _   _                 _ _        _\s
                     | | | | ___  ___ _ __ (_) |_ __ _| |
                     | |_| |/ _ \\/ __| '_ \\| | __/ _` | |
                     |  _  | (_) \\__ \\ |_) | | || (_| | |
                     |_| |_|\\___/|___/ .__/|_|\\__\\__,_|_|
                                     |_|                 \
                    """;
    private final ProgramStartupService programStartupService;
    private final Map<String, Page> pages;
    private final MenuOptionResolver menuOptionResolver;

    @Autowired
    public ProgramStartupImpl(
            MenuOptionResolver menuOptionResolver,
            ProgramStartupService programStartupService,
            ApplicationContext applicationContext) {
        this.menuOptionResolver = menuOptionResolver;
        this.programStartupService = programStartupService;
        pages = applicationContext.getBeansOfType(Page.class);
    }

    @Override
    public void start() {
        programStartupService.refreshDatabase();
        run();
    }

    private void run() {
        System.out.println(LOGO);
        menu();
    }

    private void menu() {
        System.out.println(MENU_ITEMS);
        Integer option = menuOptionResolver.resolve(options);
        Page pageToProceed = doMenuAction(option);
        try {
            pageToProceed.proceed();
        } catch (NullPointerException e) {
            System.out.println("Error in the program occurred!");
            System.exit(0);
        }
        menu();
    }

    private Page doMenuAction(Integer option) {
        Page page = null;
        switch (option) {
            case 0 -> System.exit(0);
            case 1 -> page = pages.get("permissionPage");
            case 2 -> page = pages.get("rolesPage");
        }
        return page;
    }
}
