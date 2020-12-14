package manager.entity;

import java.util.Objects;

public class Stock {
    private String stockId;
    private String stockName;
    private int price;


    public Stock(String stockId, String stockName, int price) {
        this.stockId = stockId;
        this.stockName = stockName;
        this.price = price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getStockId() {
        return stockId;
    }

    public String getStockName() {
        return stockName;
    }


    public int getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return Objects.equals(stockId, stock.stockId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stockId);
    }
}
