package com.ArgonautB04.SIRIO.rest;

import com.ArgonautB04.SIRIO.scheduled.MailScheduler;
import com.ArgonautB04.SIRIO.services.EmployeeRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.TimeZone;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private final EmployeeRestService employeeRestService;
    Logger logger = LoggerFactory.getLogger(DatabaseLoader.class);

    public DatabaseLoader(EmployeeRestService employeeRestService) {
        this.employeeRestService = employeeRestService;
    }

    @Override
    public void run(String... args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Jakarta"));
        logger.info("Timezone set to Asia/Jakarta");

        resetEmployeesPasswords();

        MailScheduler.startService = true;
    }

    private void resetEmployeesPasswords() {
        employeeRestService.changePassword("administrator", "administrator123", "administrator123");
        employeeRestService.changePassword("supervisor", "supervisor123", "administrator123");
        employeeRestService.changePassword("manajer", "manajer123", "administrator123");
        employeeRestService.changePassword("leadlead", "leadlead123", "administrator123");
        employeeRestService.changePassword("qaofficer", "qaofficer123", "administrator123");
        employeeRestService.changePassword("branchmanager", "branchmanager123", "administrator123");
        employeeRestService.changePassword("superofficer", "superofficer123", "administrator123");
    }
}
