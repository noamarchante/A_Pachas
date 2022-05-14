package esei.tfg.apachas.configuration;

import esei.tfg.apachas.entity.User;
import esei.tfg.apachas.repository.RUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import java.io.*;
import java.sql.Timestamp;
import java.util.Arrays;
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
        User noa = new User(0, "noa", "López Marchante", "noamarchante", securityConfiguration.passwordEncoder().encode("abc123."), "noamarchante@gmail.com", new Timestamp(12), null, "USER", "");

        User laura = new User(0, "laura", "Blanco Delgado", "lauraBlanco", securityConfiguration.passwordEncoder().encode("abc123."), "laurablanco@gmail.com",new Timestamp(12) , null, "USER", "");

        User juanma = new User(0, "Juan Manuel", "Fuentes Macía", "juanmafuentes", securityConfiguration.passwordEncoder().encode("abc123."), "juanmafuentes@gmail.com",new Timestamp(12) , null, "USER", "");

        User marcos = new User(0, "Marcos", "García Fernández", "marcosgarcia", securityConfiguration.passwordEncoder().encode("abc123."), "marcosgarcia@gmail.com",new Timestamp(12) , null, "USER", "");

        User jony = new User(0, "Jonatan Alberto", "Gomez de la Torre", "jonygomez", securityConfiguration.passwordEncoder().encode("abc123."), "jonygomez@gmail.com",new Timestamp(12) , null, "USER", "");

        User marcus = new User(0, "Marcos", "Fernández Criado", "marcoscriado", securityConfiguration.passwordEncoder().encode("abc123."), "marcoscriado@gmail.com",new Timestamp(12), null, "USER", "");

        User millan = new User(0, "Millán", "Puga López", "millanpuga", securityConfiguration.passwordEncoder().encode("abc123."), "millanpuga@gmail.com",new Timestamp(12) , null, "USER", "");

        User cris = new User(0, "Cristina", "Ferreiro Collazos", "crisferreiro", securityConfiguration.passwordEncoder().encode("abc123."), "crisferreiro@gmail.com", new Timestamp(12), null, "USER", "");

        User miguel = new User(0, "Miguel", "Ferreiro Diaz", "miguelferreiro", securityConfiguration.passwordEncoder().encode("abc123."), "miguelferreiro@gmail.com",new Timestamp(12) , null, "USER", "");

        User aaron = new User(0, "Aarón", "Iglesias Mosteiro", "aaroniglesias", securityConfiguration.passwordEncoder().encode("abc123."), "aaroniglesias@gmail.com", new Timestamp(12), null, "USER", "");

        User brais = new User(0, "Brais", "Fontán", "braisfontan", securityConfiguration.passwordEncoder().encode("abc123."), "braisfontan@gmail.com",new Timestamp(12) , null, "USER", "");

        User admin = new User(0, "admin", "admin", "admin", securityConfiguration.passwordEncoder().encode("abc123."), "admin@admin.com",new Timestamp(12) , null, "ADMIN", "");

        User otro = new User(0, "otro", "otro", "otro", securityConfiguration.passwordEncoder().encode("abc123."), "otro@otro.com",new Timestamp(12) , null, "USER", "");

        User otro2 = new User(0, "otro", "otro", "otro2", securityConfiguration.passwordEncoder().encode("abc123."), "otro2@otro.com",new Timestamp(12) , null, "USER", "");

        User otro3 = new User(0, "otro", "otro", "otro3", securityConfiguration.passwordEncoder().encode("abc123."), "otro3@otro.com",new Timestamp(12) , null, "USER", "");

        User otro4 = new User(0, "otro", "otro", "otro4", securityConfiguration.passwordEncoder().encode("abc123."), "otro4@otro.com",new Timestamp(12) , null, "USER", "");

        User otro5 = new User(0, "otro", "otro", "otro5", securityConfiguration.passwordEncoder().encode("abc123."), "otro5@otro.com",new Timestamp(12) , null, "USER", "");

        User otro6 = new User(0, "otro", "otro", "otro6", securityConfiguration.passwordEncoder().encode("abc123."), "otro6@otro.com",new Timestamp(12) , null, "USER", "");

        User otro7 = new User(0, "otro", "otro", "otro7", securityConfiguration.passwordEncoder().encode("abc123."), "otro7@otro.com",new Timestamp(12) , null, "USER", "");

        List<User> users = Arrays.asList(noa, laura, juanma, marcos, jony, marcus, millan, cris, miguel, aaron, brais, admin, otro, otro2, otro3, otro4, otro5, otro6, otro7);


        this.rUser.saveAll(users);
    }
}
