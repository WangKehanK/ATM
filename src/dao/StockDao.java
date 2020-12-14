package dao;

import manager.entity.Stock;
import utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 单例，操作stock相关的数据操作
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
}
