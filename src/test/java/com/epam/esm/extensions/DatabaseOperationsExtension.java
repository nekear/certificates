package com.epam.esm.extensions;

import com.epam.esm.utils.DatabaseManager;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class DatabaseOperationsExtension implements BeforeEachCallback {
    @Override
    public void beforeEach(ExtensionContext extensionContext) {
        ApplicationContext context = SpringExtension.getApplicationContext(extensionContext);

        DatabaseManager databaseManager = context.getBean(DatabaseManager.class);

        databaseManager.destroy();
        databaseManager.setup();
    }

}
