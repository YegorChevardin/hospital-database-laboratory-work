package ua.com.khpi.database.yegorchevardin.lab07.program.handlers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.com.khpi.database.yegorchevardin.lab07.database.entity.Role;
import ua.com.khpi.database.yegorchevardin.lab07.program.handlers.ObjectInputHandler;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class RoleObjectInputHandler implements ObjectInputHandler<Role> {
    private final Scanner scanner;

    @Override
    public Role getObjectFromInput() {
        Role role = new Role();

        System.out.println("Please, type name for role: ");
        String name = scanner.nextLine();

        role.setName(name);
        return role;
    }
}
