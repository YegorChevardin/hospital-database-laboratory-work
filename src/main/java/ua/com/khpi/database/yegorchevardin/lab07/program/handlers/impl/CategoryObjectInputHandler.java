package ua.com.khpi.database.yegorchevardin.lab07.program.handlers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.com.khpi.database.yegorchevardin.lab07.database.entity.Category;
import ua.com.khpi.database.yegorchevardin.lab07.program.handlers.ObjectInputHandler;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class CategoryObjectInputHandler implements ObjectInputHandler<Category> {
    private final Scanner scanner;

    @Override
    public Category getObjectFromInput() {
        Category category = new Category();
        System.out.println("Please, type name for category: ");
        String name = scanner.nextLine();

        category.setName(name);
        return category;
    }
}
