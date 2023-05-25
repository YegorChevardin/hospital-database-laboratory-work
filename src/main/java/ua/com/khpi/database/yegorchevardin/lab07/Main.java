package ua.com.khpi.database.yegorchevardin.lab07;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.com.khpi.database.yegorchevardin.lab07.program.configuration.ProgramConfiguration;
import ua.com.khpi.database.yegorchevardin.lab07.program.startup.ProgramStartup;

/**
 * Point of entrance
 * @author yegorchevardin
 * @version 0.0.1
 */
public class Main {
    public static void main(String[] args) {
        boolean withDump;

        try {
            withDump = Boolean.getBoolean(args[0]);
        } catch (IndexOutOfBoundsException e) {
            withDump = false;
        }

        ApplicationContext context = new AnnotationConfigApplicationContext(ProgramConfiguration.class);
        ProgramStartup programStartup = context.getBean(ProgramStartup.class);
        programStartup.start(withDump);
    }
}