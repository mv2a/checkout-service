package net.leaotech.promotions;

import java.util.List;
import net.leaotech.domain.Item;

//If you spend over £60, then you get 10% off your purchase
public class OverSixtyDiscount implements Promotional {

    private double originalTotal = 0.0;
    private double finalTotal = 0.0;
    private static final Integer ORDERING_PRIORITY = 2;

    private List<Item> items;

    public List<Item> process() {
        finalTotal = ((100 - 10) * originalTotal) / 100;
        return items;
    }

    @Override
    public Integer getPriorityOrder() {
        return ORDERING_PRIORITY;
    }

    @Override
    public boolean isApplicable() {
        return originalTotal > 60;
    }

    public void setItems(List<Item> items) {
        this.items = items;
        setOriginalTotal(items.stream().mapToDouble(Item::getPrice).sum());
    }

    @Override
    public Double retrieveNewTotal() {
        return finalTotal;
    }

    private void setOriginalTotal(double originalTotal) {
        this.originalTotal = originalTotal;
    }
}
