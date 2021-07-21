package esei.tfg.apachas.repository;

import esei.tfg.apachas.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository("RUser")
public interface RUser extends CrudRepository<User, Long>, PagingAndSortingRepository<User, Long> {

    User findByUserId(long userId);
    User findByUserLogin (String userLogin);
    Long countByRolesAndUserLoginIsNot(String roles, String authLogin);
    Long countByRolesAndUserLoginContainingAndUserLoginIsNot(String roles,String userLogin, String authLogin);
    Page<User> findUsersByUserLoginContainingAndUserLoginIsNotAndRolesEqualsOrderByUserLoginAsc (String userLogin, String authLogin, String roles, Pageable pageable);
    @Override
    Page<User> findAll(Pageable pageable);
    Page<User> findUsersByRolesEqualsAndUserLoginIsNotOrderByUserLoginAsc(String roles, String userLogin, Pageable pageable);
}
