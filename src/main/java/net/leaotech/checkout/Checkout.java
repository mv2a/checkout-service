package net.leaotech.checkout;

import java.util.List;
import net.leaotech.domain.Item;

public interface Checkout {

    /**
     * scans an Item, finds applicable promotion to apply
     */
    void scan(Item item);

    /**
     * scans a list of items, finds applicable promotions to apply for all items
     */
    void scan(List<Item> items);

    /**
     * @return the final total after all applicable promotions have been applied
     */
    Double total();
}
