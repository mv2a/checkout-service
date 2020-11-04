package net.leaotech.promotions;

import java.util.List;
import net.leaotech.domain.Item;

public interface Promotional {

    /**
     * @return Integer representing the order, to enforce ordering. The lower the number,
     * the higher priority it is when applying the promotion.
     */
    Integer getPriorityOrder();

    /**
     * sets the items/basket to be worked on this promotion
     * //TODO make item a constructor parameter instead
     */
    void setItems(List<Item> items);

    /**
     * @return whether this promotion is applicable
     */
    boolean isApplicable();

    /**
     *
     * @return the new total after the promotion is applied
     */
    Double retrieveNewTotal();

    /**
     * Runs the promotion specific rule
     * @return the list of items after processing
     */
    List<Item> process();
}
