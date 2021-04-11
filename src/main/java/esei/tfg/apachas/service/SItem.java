package esei.tfg.apachas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import esei.tfg.apachas.converter.ConItem;
import esei.tfg.apachas.entity.Event;
import esei.tfg.apachas.entity.Item;
import esei.tfg.apachas.model.MItem;
import esei.tfg.apachas.repository.REvent;
import esei.tfg.apachas.repository.RItem;
import java.util.ArrayList;
import java.util.List;

@Service("SItem")
public class SItem {

    @Autowired
    @Qualifier("RItem")
    private RItem rItem;

    @Autowired
    @Qualifier("REvent")
    private REvent rEvent;

    @Autowired
    @Qualifier("ConItem")
    private ConItem conItem;

    public synchronized boolean insert(MItem mItem) {
        Item item = conItem.conMItem(mItem);
        Item existingItem = rItem.findByItemId(item.getItemId());
        Event existingEvent = rEvent.findByEventId(item.getEvent().getEventId());
        if (existingItem != null || existingEvent == null) {
            return false;
        } else {
            rItem.save(item);
            return true;
        }
    }

    public synchronized boolean update(MItem mItem) {
        Item item = conItem.conMItem(mItem);
        Item existingItem = rItem.findByItemId(item.getItemId());
        Event existingEvent = rEvent.findByEventId(item.getEvent().getEventId());
        if (existingItem != null || existingEvent != null) {
            rItem.save(item);
            return true;
        } else {
            return false;
        }
    }

    public synchronized boolean delete(long itemId) {
        Item existingItem = rItem.findByItemId(itemId);
        if (existingItem != null) {
            rItem.delete(existingItem);
            return true;
        } else {
            return false;
        }
    }

    public synchronized List<MItem> selectAll() {
        List<Item> itemList = new ArrayList<>();
        rItem.findAll().forEach(e -> itemList.add(e));
        return conItem.conItemList(itemList);
    }

    public synchronized List<MItem> selectPageable(Pageable pageable) {
        return conItem.conItemList(rItem.findAll(pageable).getContent());
    }

    public synchronized MItem selectItemById(long itemId) {
        Item item = rItem.findById(itemId).get();
        return conItem.conItem(item);
    }
}
