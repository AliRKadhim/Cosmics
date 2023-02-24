package database.migration;

import database.PgDatabaseConfig;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;

public class FlywayRunner {
    private final PgDatabaseConfig dbConfig;

    public FlywayRunner(PgDatabaseConfig dbConfig) {
        this.dbConfig = dbConfig;
    }

    public void migrate() throws FlywayException {
        Flyway flyway = Flyway.configure()
                .dataSource(dbConfig.getJdbcUrl(), dbConfig.adminUsername(), dbConfig.adminPassword())
                .schemas(dbConfig.schema())
                .createSchemas(true)
                .connectRetries(10)
                .connectRetriesInterval(5)
                .load();
        flyway.migrate();
    }
}