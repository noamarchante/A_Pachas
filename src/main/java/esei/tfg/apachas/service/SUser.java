package esei.tfg.apachas.service;

import esei.tfg.apachas.converter.ConUser;
import esei.tfg.apachas.converter.ConUserGroupUser;
import esei.tfg.apachas.entity.User;
import esei.tfg.apachas.model.MUser;
import esei.tfg.apachas.repository.RUser;
import esei.tfg.apachas.repository.RUserGroup;
import esei.tfg.apachas.repository.RUserGroupUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service("SUser")
public class SUser implements UserDetailsService {

    @Autowired
    @Qualifier("RUser")
    private RUser rUser;

    @Autowired
    @Qualifier("ConUser")
    private ConUser conUser;

    @Autowired
    @Qualifier("RUserGroup")
    private RUserGroup rUserGroup;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    @Qualifier("RUserGroupUser")
    private RUserGroupUser rUserGroupUser;


    //JWT: ESTE MÉTODO BUSCAR UN USUARIO CON EL NOMBRE PASADO POR PARÁMETRO Y DEVUELVE UN OBJETO DE TIPO USERDETAILS CON LOGIN + CONTRASEÑA + LISTA DE PERMISOS VACÍA
    @Override
    public UserDetails loadUserByUsername(String userLogin) throws UsernameNotFoundException {
        User user = rUser.findByUserLogin(userLogin);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with userLogin: " + userLogin);
        }else {
            return new org.springframework.security.core.userdetails.User(user.getUserLogin(), user.getUserPassword(), getAuthorities(user));
        }
    }

    protected Collection<? extends GrantedAuthority> getAuthorities (User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        rUserGroupUser.findByUserGroupUserId_UserId(user.getUserId()).forEach(p -> {
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+rUserGroup.findByUserGroupId(p.getUserGroupUserId().getUserGroupId()).getUserGroupName());
            authorities.add(authority);
        });
        return authorities;
    }


    public synchronized boolean insert(MUser mUser) {
        User user = conUser.conMUser(mUser);
        User existingUser = rUser.findByUserId(user.getUserId());
        if (existingUser != null) {
            return false;
        } else {
            rUser.save(user);
            return true;
        }
    }

    public synchronized boolean update(MUser mUser) {
        User user = conUser.conMUser(mUser);
        User existingUser = rUser.findByUserId(user.getUserId());
        if (existingUser != null) {
            rUser.save(user);
            return true;
        } else {
            return false;
        }
    }

    public synchronized boolean delete(long userId) {
        User existingUser = rUser.findByUserId(userId);
        if (existingUser != null) {
            rUser.delete(existingUser);
            return true;
        } else {
            return false;
        }
    }

    public synchronized List<MUser> selectAll() {
        List<User> userList = new ArrayList<>();
        rUser.findAll().forEach(e -> userList.add(e));
        return conUser.conUserList(userList);
    }

    public synchronized List<MUser> selectPageable(String userLogin, Pageable pageable) {
        return conUser.conUserList(rUser.findUsersByRolesEqualsAndUserLoginIsNotOrderByUserLoginAsc("USER", userLogin,pageable).getContent());
    }

    public synchronized List<MUser> selectPageableByUserLogin(String userLogin, String authLogin, Pageable pageable) {
        return conUser.conUserList(rUser.findUsersByUserLoginContainingAndUserLoginIsNotAndRolesEqualsOrderByUserLoginAsc(userLogin, authLogin, "USER", pageable).getContent());
    }

    public synchronized MUser selectById(long userId) {
        User user = rUser.findById(userId).get();
        return conUser.conUser(user);
    }

    public synchronized MUser selectByUserLogin(String userLogin) {
        User user = rUser.findByUserLogin(userLogin);
        return conUser.conUser(user);
    }

    public synchronized Long countUsers(String authLogin){
        return rUser.countByRolesAndUserLoginIsNot("USER",authLogin);
    }

    public synchronized Long countSearchUsers(String userLogin, String authLogin){
        return rUser.countByRolesAndUserLoginContainingAndUserLoginIsNot("USER",userLogin,authLogin);
    }
}
