package ir.javid.iran.component;

import ir.javid.iran.service.CountryService;
import ir.javid.iran.service.RoleService;
import ir.javid.iran.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * author: Mr.javidmolaei
 */

@Component
public class StartupRunner implements CommandLineRunner {

    private final StateService stateService;
    private final CountryService countryService;
    private final RoleService roleService;

    @Autowired
    public StartupRunner(StateService stateService, CountryService countryService, RoleService roleService) {
        this.stateService = stateService;
        this.countryService = countryService;
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) throws Exception {
        stateService.initializeStates();
        countryService.initializeCountries();
        roleService.initializeStates();
    }

}
