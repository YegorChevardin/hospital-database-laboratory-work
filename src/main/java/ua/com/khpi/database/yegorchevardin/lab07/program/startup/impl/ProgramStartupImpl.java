package ua.com.khpi.database.yegorchevardin.lab07.program.startup.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.com.khpi.database.yegorchevardin.lab07.program.startup.ProgramStartup;
import ua.com.khpi.database.yegorchevardin.lab07.service.services.ProgramStartupService;

@Component
@RequiredArgsConstructor
public class ProgramStartupImpl implements ProgramStartup {
    private final ProgramStartupService programStartupService;

    @Override
    public void start() {
        programStartupService.refreshDatabase();
    }
}
