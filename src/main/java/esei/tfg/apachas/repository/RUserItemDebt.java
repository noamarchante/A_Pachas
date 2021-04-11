package esei.tfg.apachas.repository;


import esei.tfg.apachas.entity.UserItemDebt;
import esei.tfg.apachas.entity.id.UserItemId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository("RUserItemDebt")
public interface RUserItemDebt extends CrudRepository<UserItemDebt, UserItemId>, PagingAndSortingRepository<UserItemDebt, UserItemId> {

    UserItemDebt findByUserItemId(UserItemId userItemId);

    @Override
    Page<UserItemDebt> findAll(Pageable pageable);
}
