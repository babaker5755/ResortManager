package sample;

public enum FoodItem {
    Beverage(1.25), Entree(3.25), Snack(0.75), Meal(5.00);
    private Double price;

    FoodItem(Double price) {
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }
}
