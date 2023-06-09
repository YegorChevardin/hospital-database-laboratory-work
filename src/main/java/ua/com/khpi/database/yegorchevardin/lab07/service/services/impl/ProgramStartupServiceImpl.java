package ua.com.khpi.database.yegorchevardin.lab07.service.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.com.khpi.database.yegorchevardin.lab07.database.constants.DatabaseConstants;
import ua.com.khpi.database.yegorchevardin.lab07.database.dao.DatabasePostConfig;
import ua.com.khpi.database.yegorchevardin.lab07.service.exceptions.ConnectionException;
import ua.com.khpi.database.yegorchevardin.lab07.service.services.ProgramStartupService;

import java.io.IOException;
import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class ProgramStartupServiceImpl implements ProgramStartupService {
    private final DatabasePostConfig databasePostConfig;

    @Override
    public void refreshDatabase() {
        executeDump(DatabaseConstants.DUMP_FILE_NAME.getValue());
    }

    @Override
    public void insertionDumpExecute() {
        executeDump(DatabaseConstants.INSERTION_DUMP_FILE_NAME.getValue());
    }

    private void executeDump(String dumpName) {
        try {
            databasePostConfig.config(dumpName);
        } catch (SQLException | IOException e) {
            throw new ConnectionException(e.getMessage(), e);
        }
    }
}
