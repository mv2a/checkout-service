package net.leaotech.promotions;

import java.util.List;
import java.util.stream.Collectors;
import net.leaotech.domain.Item;

//If you buy 2 or more travel card holders then the price drops to £8.50
public class CardHoldersDiscount implements Promotional {

    private List<Item> items;
    private static final Integer ORDERING_PRIORITY = 1;

    public List<Item> process() {
        getTravelCardHolderItems().forEach(item -> item.setPrice(8.50));
        return items;
    }

    @Override
    public Integer getPriorityOrder() {
        return ORDERING_PRIORITY;
    }


    @Override
    public boolean isApplicable() {
        return getTravelCardHolderItems().size() >= 2;
    }

    @Override
    public Double retrieveNewTotal() {
        return items.stream().mapToDouble(Item::getPrice).sum();
    }

    @Override
    public void setItems(List<Item> items) {
        this.items = items;
    }

    private List<Item> getTravelCardHolderItems() {
        return items.stream().filter(item -> item.getProductCode().equals("001")).collect(Collectors.toList());
    }
}
