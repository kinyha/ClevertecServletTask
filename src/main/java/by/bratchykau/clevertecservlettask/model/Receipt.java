package by.bratchykau.clevertecservlettask.model;

import java.util.List;

public class Receipt {
    private int id;
    private List<Product> products;
    private int totalAmount;
    private DiscountCard discountCard;

    public Receipt(List<Product> products, DiscountCard discountCard) {
        this.products = products;
        this.discountCard = discountCard;
        this.totalAmount = findTotalAmount();
    }

    public Receipt() {

    }

    public List<Product> getProducts() {
        return products;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public DiscountCard getDiscountCard() {
        return discountCard;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setDiscountCard(DiscountCard discountCard) {
        this.discountCard = discountCard;
    }

    public int findTotalAmount() {
        int amount = 0;
        for (Product product : products) {
            int price = product.getPrice() * product.getQuantity();
            if (product.getQuantity() > 5) {
                price = (int) (price * 0.9);
            }
            amount += price;
        }
        if (discountCard != null) {
            amount = (amount * (100 - discountCard.getDiscountPercentage())) / 100;
        }
        return amount;
    }


    @Override
    public String toString() {
        return "Receipt{" +
                "id=" + id +
                ", products=" + products +
                ", totalAmount=" + totalAmount +
                ", discountCard=" + discountCard +
                '}';
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
