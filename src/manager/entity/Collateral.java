package manager.entity;

public class Collateral {
    private int type;
    private String name;
    private int price;


    public Collateral(int type, String name, int price) {
        this.type = type;
        this.name = name;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
