package esei.tfg.apachas.configuration;

import esei.tfg.apachas.entity.User;
import esei.tfg.apachas.repository.RUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class DbInit implements CommandLineRunner {
    @Autowired
    private RUser rUser;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    @Override
    public void run(String... args) {

        this.rUser.deleteAll();

        User noa = new User(0,"noa","López Marchante","noamarchante",securityConfiguration.passwordEncoder().encode("abc123."),"noamarchante@gmail.com",new GregorianCalendar(1997,06,12).getTime(),"","USER","");
        User admin = new User(0,"admin","admin","admin",securityConfiguration.passwordEncoder().encode("abc123."),"admin@admin.com",new GregorianCalendar(1997,06,12).getTime(),"","ADMIN","");

        List<User> users = Arrays.asList(noa,admin);

        this.rUser.saveAll(users);
    }
}
