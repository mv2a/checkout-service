package net.leaotech.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Item {

    private String productCode;
    private String name;
    private Double price;

    public Item(String productCode, String name, Double price) {
        this.productCode = productCode;
        this.name = name;
        this.price = price;
    }
}
