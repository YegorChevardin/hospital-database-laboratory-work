package ua.com.khpi.database.yegorchevardin.lab07.program.handlers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.com.khpi.database.yegorchevardin.lab07.database.entity.User;
import ua.com.khpi.database.yegorchevardin.lab07.program.handlers.MenuOptionResolver;
import ua.com.khpi.database.yegorchevardin.lab07.program.handlers.ObjectInputHandler;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class UserObjectInputHandler implements ObjectInputHandler<User> {
    private final MenuOptionResolver menuOptionResolver;
    
    @Override
    public User getObjectFromInput() {
        User user = new User();

        System.out.println("Please type name for user: ");
        user.setName(menuOptionResolver.resolveLine());
        System.out.println("Please type second name for user: ");
        user.setSecondName(menuOptionResolver.resolveLine());
        System.out.println("Please, type here email for the user: ");
        user.setEmail(menuOptionResolver.resolveLine());
        System.out.println("Please type phone for user: ");
        user.setPhone(menuOptionResolver.resolveLine());
        System.out.println("Please type here password for user : ");
        user.setPassword(menuOptionResolver.resolveLine());

        return user;
    }
}
