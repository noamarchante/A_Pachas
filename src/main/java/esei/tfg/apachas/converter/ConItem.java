package esei.tfg.apachas.converter;

import org.springframework.stereotype.Component;
import esei.tfg.apachas.entity.Event;
import esei.tfg.apachas.entity.Item;
import esei.tfg.apachas.model.MItem;
import java.util.ArrayList;
import java.util.List;

@Component("ConItem")
public class ConItem {
    public List<MItem> conItemList(List<Item> itemList) {
        List<MItem> mItemList = new ArrayList<>();
        for (Item item : itemList) {
            mItemList.add(new MItem(item));
        }
        return mItemList;
    }

    public MItem conItem(Item item) {
        return new MItem(item);
    }

    public Item conMItem(MItem mItem) {
        return new Item(mItem.getItemId(), new Event(mItem.getEventId()), mItem.getItemName(), mItem.getItemDescription(), mItem.getItemCost(), mItem.getItemQuantity(), mItem.getItemUrl(), mItem.getItemPhoto(), mItem.getItemPurchaseDate());
    }
}
