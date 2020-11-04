package net.leaotech.checkout;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.leaotech.domain.Item;
import net.leaotech.promotions.Promotional;

public class CheckoutImpl implements Checkout {

    private List<Item> items = new ArrayList<>();
    private List<Promotional> promotions;
    private Double dTotal = 0.0;

    public CheckoutImpl(List<Promotional> promotions) {
        this.promotions = promotions;
    }

    @Override
    public void scan(Item item) {
        items.add(item);

        //could leave this to the end instead of invoking for each item
        //leaving as is for now, assuming the client interface would require items to be scanned after each addition to the basket
        promotions.stream().sorted(Comparator.comparing(Promotional::getPriorityOrder)).forEach(promotion -> {
            promotion.setItems(items);
            if (promotion.isApplicable()) {
                items = promotion.process(); //maybe move the processing outside this block?
                dTotal = promotion.retrieveNewTotal();
            }
        });
        //TODO there are a lot of room for improvements on the above logic such as a supplier to avoid multiple iterations
    }

    @Override
    public void scan(List<Item> items) {
        if (items != null) {
            items.forEach(this::scan);
        }
    }

    @Override
    public Double total() {
        //round the double to 2 decimal places
        return Math.round(dTotal * 100.0) / 100.0;
    }
}
