package com.argonautb04.sirio.rest;

import com.argonautb04.sirio.scheduled.MailScheduler;
import com.argonautb04.sirio.services.EmployeeRestService;
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
        employeeRestService.setPassword("administrator", "administrator123");
        employeeRestService.setPassword("supervisor", "supervisor123");
        employeeRestService.setPassword("manajer", "manajer123");
        employeeRestService.setPassword("leadlead", "leadlead123");
        employeeRestService.setPassword("qaofficer", "qaofficer123");
        employeeRestService.setPassword("branchmanager", "branchmanager123");
        employeeRestService.setPassword("superofficer", "superofficer123");
    }
}
