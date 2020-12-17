package dao;

import manager.entity.Stock;
import utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * The stock Dao pattern we use to read the database, operating stock-related data operations
 */
public class StockDao {
    private List<Stock> stockList;

    private static StockDao stockDao = new StockDao();

    public StockDao() {
        stockList = FileUtils.readStockList();
    }

    public static StockDao getInstance() {
        return stockDao;
    }

    public boolean updateStock(String stockId, int price) {
        Stock stock = getStockById(stockId);
        if(stock == null){
            return false;
        }

        stock.setPrice(price);

        FileUtils.saveStockList(stockList);
        return true;
    }

    public Stock getStockById(String stockId) {
        for(Stock stock : stockList){
            if(stock.getStockId().equals(stockId)){
                return stock;
            }
        }
        return null;
    }

    public List<Stock> getStockList() {
        return stockList;
    }

    public void addStock(String stockId, String stockName, int price) {
        Stock stock = new Stock(stockId, stockName, price);
        stockList.add(stock);

        FileUtils.saveStockList(stockList);
    }

    public void delStock(String stockId) {
        Stock stock = getStockById(stockId);

        if(stock != null){
            stockList.remove(stock);

            FileUtils.saveStockList(stockList);
        }
    }
}
