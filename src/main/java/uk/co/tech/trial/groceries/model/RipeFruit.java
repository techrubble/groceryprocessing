package uk.co.tech.trial.groceries.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Ripe Fruit implementation of Grocery Items
 *
 * Created by sclowes
 */
public class RipeFruit implements GroceryItem {

    private String title;
    private String description;
    private long detailsSize;
    private double unitPrice;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDetailsSize(long detailsSize) {
        this.detailsSize = detailsSize;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSize() {
        return detailsSize + "kb";
    }

    @Override
    @JsonProperty("unit_price")
    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }


}
