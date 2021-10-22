package esei.tfg.apachas.configuration;

import esei.tfg.apachas.entity.User;
import esei.tfg.apachas.repository.RUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import java.io.*;
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
    public void run(String... args) throws IOException {
        this.rUser.deleteAll();
        User noa = new User(0, "noa", "López Marchante", "noamarchante", securityConfiguration.passwordEncoder().encode("abc123."), "noamarchante@gmail.com", new GregorianCalendar(1997, 06, 12).getTime(), null, "USER", "");

        User laura = new User(0, "laura", "Blanco Delgado", "lauraBlanco", securityConfiguration.passwordEncoder().encode("abc123."), "laurablanco@gmail.com", new GregorianCalendar(1996, 12, 30).getTime(), null, "USER", "");

        User juanma = new User(0, "Juan Manuel", "Fuentes Macía", "juanmafuentes", securityConfiguration.passwordEncoder().encode("abc123."), "juanmafuentes@gmail.com", new GregorianCalendar(1997, 10, 24).getTime(), null, "USER", "");

        User marcos = new User(0, "Marcos", "García Fernández", "marcosgarcia", securityConfiguration.passwordEncoder().encode("abc123."), "marcosgarcia@gmail.com", new GregorianCalendar(1998, 05, 10).getTime(), null, "USER", "");

        User jony = new User(0, "Jonatan Alberto", "Gomez de la Torre", "jonygomez", securityConfiguration.passwordEncoder().encode("abc123."), "jonygomez@gmail.com", new GregorianCalendar(1996, 07, 11).getTime(), null, "USER", "");

        User marcus = new User(0, "Marcos", "Fernández Criado", "marcoscriado", securityConfiguration.passwordEncoder().encode("abc123."), "marcoscriado@gmail.com", new GregorianCalendar(1997, 07, 02).getTime(), null, "USER", "");

        User millan = new User(0, "Millán", "Puga López", "millanpuga", securityConfiguration.passwordEncoder().encode("abc123."), "millanpuga@gmail.com", new GregorianCalendar(1997, 03, 05).getTime(), null, "USER", "");

        User cris = new User(0, "Cristina", "Ferreiro Collazos", "crisferreiro", securityConfiguration.passwordEncoder().encode("abc123."), "crisferreiro@gmail.com", new GregorianCalendar(1996, 12, 17).getTime(), null, "USER", "");

        User miguel = new User(0, "Miguel", "Ferreiro Diaz", "miguelferreiro", securityConfiguration.passwordEncoder().encode("abc123."), "miguelferreiro@gmail.com", new GregorianCalendar(1997, 12, 31).getTime(), null, "USER", "");

        User aaron = new User(0, "Aarón", "Iglesias Mosteiro", "aaroniglesias", securityConfiguration.passwordEncoder().encode("abc123."), "aaroniglesias@gmail.com", new GregorianCalendar(1999, 02, 13).getTime(), null, "USER", "");

        User brais = new User(0, "Brais", "Fontán", "braisfontan", securityConfiguration.passwordEncoder().encode("abc123."), "braisfontan@gmail.com", new GregorianCalendar(1998, 06, 23).getTime(), null, "USER", "");

        User admin = new User(0, "admin", "admin", "admin", securityConfiguration.passwordEncoder().encode("abc123."), "admin@admin.com", new GregorianCalendar(1997, 06, 12).getTime(), null, "ADMIN", "");

        List<User> users = Arrays.asList(noa, laura, juanma, marcos, jony, marcus, millan, cris, miguel, aaron, brais, admin);


        this.rUser.saveAll(users);
    }
}
