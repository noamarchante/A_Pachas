package esei.tfg.apachas.service;

import esei.tfg.apachas.converter.ConUser;
import esei.tfg.apachas.entity.User;
import esei.tfg.apachas.model.MUser;
import esei.tfg.apachas.repository.RUser;
import esei.tfg.apachas.repository.RGroup;
import esei.tfg.apachas.repository.RGroupUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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
    @Qualifier("RGroup")
    private RGroup rGroup;

    @Autowired
    @Qualifier("RGroupUser")
    private RGroupUser rGroupUser;


    //JWT: ESTE MÉTODO BUSCAR UN USUARIO CON EL NOMBRE PASADO POR PARÁMETRO Y DEVUELVE UN OBJETO DE TIPO USERDETAILS CON LOGIN + CONTRASEÑA + LISTA DE PERMISOS VACÍA
    @Override
    public UserDetails loadUserByUsername(String userLogin) throws UsernameNotFoundException {
        User user = rUser.findByUserLoginAndUserActiveTrue(userLogin);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with userLogin: " + userLogin);
        }else {
            return new org.springframework.security.core.userdetails.User(user.getUserLogin(), user.getUserPassword(), getAuthorities(user));
        }
    }

    protected Collection<? extends GrantedAuthority> getAuthorities (User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        rGroupUser.findByGroupUserId_UserId(user.getUserId()).forEach(p -> {
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+ rGroup.findByGroupId(p.getGroupUserId().getGroupId()).getGroupName());
            authorities.add(authority);
        });
        return authorities;
    }

    public synchronized boolean insertUser(MUser mUser) {
        User user = conUser.conMUser(mUser);
        User existingUser = rUser.findByUserId(user.getUserId());
        if (existingUser != null)
        {
            return false;
        } else {
            user.setUserCreation(new Timestamp(System.currentTimeMillis()));
            user.setUserRemoval(null);
            user.setUserActive(true);
            rUser.save(user);
            return true;
        }
    }

    public synchronized Long countUsers(long authId){
        return rUser.countByRolesAndUserIdIsNotAndUserActiveTrue("USER",authId);
    }

    public synchronized Long countSearchUsers(String userLogin, long authId){
        return rUser.countByRolesAndUserLoginContainingAndUserIdIsNotAndUserActiveTrue("USER",userLogin,authId);
    }

    public synchronized List<MUser> selectPageableUsers(long authId, Pageable pageable) {
        return conUser.conUserList(rUser.findUsersByRolesEqualsAndUserIdIsNotAndUserActiveTrueOrderByUserLoginAsc("USER", authId,pageable).getContent());
    }

    public synchronized List<MUser> selectPageableSearchUsers(String userLogin, long authId, Pageable pageable) {
        return conUser.conUserList(rUser.findUsersByUserLoginContainingAndUserIdIsNotAndRolesEqualsAndUserActiveTrueOrderByUserLoginAsc(userLogin, authId, "USER", pageable).getContent());
    }

    public synchronized MUser selectUser(String userLogin) {
        User user = rUser.findByUserLoginAndUserActiveTrue(userLogin);
        return conUser.conUser(user);
    }

   /* public synchronized boolean update(MUser mUser) {
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
    }*/
}
