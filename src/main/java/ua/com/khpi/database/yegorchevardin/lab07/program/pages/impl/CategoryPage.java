package ua.com.khpi.database.yegorchevardin.lab07.program.pages.impl;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import ua.com.khpi.database.yegorchevardin.lab07.database.entity.Category;
import ua.com.khpi.database.yegorchevardin.lab07.program.handlers.MenuOptionResolver;
import ua.com.khpi.database.yegorchevardin.lab07.program.handlers.ObjectInputHandler;
import ua.com.khpi.database.yegorchevardin.lab07.program.pages.AbstractPage;
import ua.com.khpi.database.yegorchevardin.lab07.service.services.CategoryService;

import java.util.List;

@Component("categoryPage")
public class CategoryPage extends AbstractPage {
    private static final String MENU_ITEMS =
            """
            0 - Exit the program;
            1 - Show all category;
            2 - Show all category as a table;
            3 - Show category by id;
            4 - Insert category;
            5 - Delete category by id;
            6 - Go back;
            """;
    private static final List<Integer> options = List.of(0, 1, 2, 3, 4, 5, 6);
    
    private final MenuOptionResolver menuOptionResolver;
    private final ObjectInputHandler<Category> objectInputHandler;
    private final CategoryService categoryService;
    
    public CategoryPage(
            Gson gson, 
            MenuOptionResolver menuOptionResolver, 
            ObjectInputHandler<Category> objectInputHandler, 
            CategoryService categoryService) {
        super(gson);
        this.menuOptionResolver = menuOptionResolver;
        this.objectInputHandler = objectInputHandler;
        this.categoryService = categoryService;
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
                System.out.println(gson.toJson(categoryService.getAll()));
                break;
            case 2:
                System.out.println(categoryService.findAllAsTable().print());
                break;
            case 3:
                System.out.println(
                        gson.toJson(
                                categoryService.getById(
                                        Long.valueOf(menuOptionResolver.resolve())
                                )
                        )
                );
                break;
            case 4:
                System.out.println(gson.toJson(
                        categoryService.insert(objectInputHandler.getObjectFromInput())
                ));
                break;
            case 5:
                categoryService.removeById(
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
