package dao;

import manager.entity.Collateral;
import utils.FileUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * LoanDao class
 */
public class LoanDao {


    public static final int DAY_RATE = 1;
    public static final int MONTH_RATE = 2;
    public static final int YEAR_RATE = 3;

    private List<Collateral> collateralList;

    private Map<Integer, Double> loanRateMap;

    private static LoanDao loanDao = new LoanDao();

    public LoanDao() {
        collateralList = FileUtils.readCollateralList();
        loanRateMap = getLoanRateMap();
    }


    public static LoanDao getInstance() {
        return loanDao;
    }

    public List<Collateral> getCollateralList() {
        return collateralList;

    }


    public Collateral getCollateral(int loanType) {
        for(Collateral collateral : collateralList){
            if(collateral.getType() == loanType){
                return collateral;
            }
        }
        return null;
    }

    public double getLoanRate(int loanRateType){
        return loanRateMap.get(loanRateType);
    }

    private Map<Integer, Double> getLoanRateMap() {
        Map<Integer, Double> map = new HashMap<>();
        map.put(DAY_RATE, 0.002);
        map.put(MONTH_RATE, 0.012);
        map.put(YEAR_RATE, 0.142);

        return map;
    }


}
