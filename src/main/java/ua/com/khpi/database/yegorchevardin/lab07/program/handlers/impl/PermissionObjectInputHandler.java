package ua.com.khpi.database.yegorchevardin.lab07.program.handlers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.com.khpi.database.yegorchevardin.lab07.database.entity.Permission;
import ua.com.khpi.database.yegorchevardin.lab07.program.handlers.ObjectInputHandler;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class PermissionObjectInputHandler implements ObjectInputHandler<Permission> {
    private final Scanner scanner;

    @Override
    public Permission getObjectFromInput() {
        Permission permission = new Permission();

        System.out.println("Please, type name for permission: ");
        String name = scanner.nextLine();
        System.out.println("Please, type description for permission: ");
        String description = scanner.nextLine();

        permission.setName(name);
        permission.setDescription(description);
        return permission;
    }
}
