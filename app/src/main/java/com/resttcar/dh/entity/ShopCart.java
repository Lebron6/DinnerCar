package com.resttcar.dh.entity;

import android.util.Log;

import com.resttcar.dh.tools.ArithUtil;
import com.resttcar.dh.tools.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by cheng on 16-11-12.
 */
public class ShopCart {
    private int shoppingAccount;//商品总数
    private double shoppingTotalPrice;//商品总价钱

    public double getShoppingDiscountTotalPrice() {
       double realPrice=0;
        for (int i = 0; i <allDishs.size() ; i++) {
            realPrice = ArithUtil.add(realPrice, allDishs.get(i).getTypeDishPrice())  ;
        }
        return realPrice;
    }

    public void setShoppingDiscountTotalPrice(double shoppingDiscountTotalPrice) {
        this.shoppingDiscountTotalPrice = shoppingDiscountTotalPrice;
    }

    private double shoppingDiscountTotalPrice;//商品总价钱的折扣价
    private Map<Dish, Integer> shoppingSingle;//单个物品的总价价钱
    private List<Dish> allDishs;        //购物车单项商品，如果有重复，数量加一

    public List<Dish> getAllDishs() {
        return allDishs;
    }

    public void setShoppingAccount(int shoppingAccount) {
        this.shoppingAccount = shoppingAccount;
    }

    public void setShoppingTotalPrice(double shoppingTotalPrice) {
        this.shoppingTotalPrice = shoppingTotalPrice;
    }

    public Map<Dish, Integer> getShoppingSingleMap() {
        return shoppingSingle;
    }

    public void setAllDishs(List<Dish> allDishs) {
        if (this.allDishs == null) {
            allDishs = new ArrayList<>();
        }
        this.allDishs = allDishs;
    }

    public ShopCart() {
        this.shoppingAccount = 0;
        this.shoppingTotalPrice = 0;
        this.shoppingDiscountTotalPrice = 0;
        this.allDishs = new ArrayList<>();
        this.shoppingSingle = new HashMap<>();
    }

    public int getShoppingAccount() {
        return shoppingAccount;
    }

    public double getShoppingTotalPrice() {
        return shoppingTotalPrice;
    }


    public boolean addShoppingSingle(Dish dish) {
        testAddIsHave(dish);
        shoppingTotalPrice = ArithUtil.add(shoppingTotalPrice, dish.getDishPrice());//正确的加法算法
        shoppingAccount++;
        return true;
    }

    public void testAddIsHave(Dish dish) {
        if (allDishs.contains(dish) == true) {
            for (int i = 0; i < allDishs.size(); i++) {
                Dish dish1 = allDishs.get(i);
                if (dish1.getGoodId() == dish.getGoodId() && dish1.getSelectSpec().equals(dish.getSelectSpec()) && dish1.getMaterial_id() == dish.getMaterial_id()) {
                    allDishs.get(i).setNum(allDishs.get(i).getNum() + 1);
                }
            }
        } else {
            dish.setNum(1);
            allDishs.add(dish);
        }

    }

    public boolean subShoppingSingle(Dish dish) {
        testSubIsHave(dish);
        shoppingTotalPrice = ArithUtil.sub(shoppingTotalPrice, dish.getDishPrice());//正确的减法算法
        shoppingAccount--;
        return true;
    }

    public void testSubIsHave(Dish dish) {
        List<Dish> removeDishs = new ArrayList<>();
        for (int i = 0; i < allDishs.size(); i++) {
            if (dish.getGoodId() == allDishs.get(i).getGoodId() && dish.getSelectSpec().equals(allDishs.get(i).getSelectSpec()) && dish.getMaterial_id() == allDishs.get(i).getMaterial_id()) {
                if (allDishs.get(i).getNum() == 1) {
                    removeDishs.add(dish);
                    allDishs.removeAll(removeDishs);
                } else {
                    allDishs.get(i).setNum(allDishs.get(i).getNum() - 1);
                }

            }
        }

    }


    public int getDishAccount() {
        return allDishs.size();
    }

    public int getDishNumByDish(Dish dish) {
        int num = 0;
        for (int i = 0; i < allDishs.size(); i++) {
            if (allDishs.get(i).getGoodId() == dish.getGoodId()) {
                num += allDishs.get(i).getNum();
            }
        }
        return num;
    }

    public int getDishNumByDish1(Dish dish) {
        int num = 0;
        for (int i = 0; i < allDishs.size(); i++) {
            if (allDishs.get(i).getMaterial_id() == dish.getMaterial_id()) {
                num += allDishs.get(i).getNum();
            }
        }
        return num;
    }

    public void clear() {
        this.shoppingAccount = 0;
        this.shoppingTotalPrice = 0;
        this.shoppingDiscountTotalPrice = 0;
        this.allDishs.clear();
    }
}
