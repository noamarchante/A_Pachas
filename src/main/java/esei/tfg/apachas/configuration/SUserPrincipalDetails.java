package esei.tfg.apachas.configuration;

import esei.tfg.apachas.entity.User;
import esei.tfg.apachas.repository.RUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SUserPrincipalDetails implements UserDetailsService {

    @Autowired
    private RUser rUser;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = this.rUser.findByUserLogin(s);
        UserPrincipal userPrincipal = new UserPrincipal(user);

        return userPrincipal;
    }
}
