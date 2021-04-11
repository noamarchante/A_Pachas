package esei.tfg.apachas.repository;

import esei.tfg.apachas.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository("RItem")
public interface RItem extends CrudRepository<Item, Long>, PagingAndSortingRepository<Item, Long> {

    Item findByItemId(long itemId);

    @Override
    Page<Item> findAll(Pageable pageable);
}
