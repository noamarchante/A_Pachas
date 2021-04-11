package esei.tfg.apachas.repository;

import esei.tfg.apachas.entity.UserItemPay;
import esei.tfg.apachas.entity.id.UserItemId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository("RUserItemPay")
public interface RUserItemPay extends CrudRepository<UserItemPay, UserItemId>, PagingAndSortingRepository<UserItemPay, UserItemId> {

    UserItemPay findByUserItemId(UserItemId userItemId);

    @Override
    Page<UserItemPay> findAll(Pageable pageable);
}
