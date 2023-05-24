package ua.com.khpi.database.yegorchevardin.lab07.database.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import ua.com.khpi.database.yegorchevardin.lab07.database.constants.DatabaseConstants;
import ua.com.khpi.database.yegorchevardin.lab07.database.dao.DatabasePostConfig;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Component
@RequiredArgsConstructor
public final class DatabasePostConfigImpl implements DatabasePostConfig {
    private final DataSource dataSource;
    private final ResourceLoader resourceLoader;

    @Override
    public void config() throws SQLException, IOException {
        Resource dumpFile = resourceLoader.getResource(
                DatabaseConstants.DUMP_FILE_NAME.getValue()
        );

        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(dumpFile.getInputStream()));
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement()
        ) {
            StringBuilder query = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty() || line.startsWith("--")) {
                    continue;
                }
                query.append(line);
                if (line.endsWith(";")) {
                    String sql = query.toString();
                    statement.execute(sql);
                    query.setLength(0);
                }
            }
        }
    }
}
