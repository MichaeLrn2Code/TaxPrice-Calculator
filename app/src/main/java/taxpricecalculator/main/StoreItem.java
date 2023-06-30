package taxpricecalculator.main;

import androidx.annotation.NonNull;

public class StoreItem {
    private double price;

    public StoreItem(double price){
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%.2f ", price);
    }
}
