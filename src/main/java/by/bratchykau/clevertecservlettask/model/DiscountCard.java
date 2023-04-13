package by.bratchykau.clevertecservlettask.model;



public class DiscountCard {

    private String number;


    private int discountPercentage;

    public DiscountCard() {

    }

    public DiscountCard(String number, int discountPercentage) {
        this.number = number;
        this.discountPercentage = discountPercentage;
    }

    public String getNumber() {
        return number;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}


