package com.resttcar.dh.entity;


import java.util.List;

/**
 * Created by James on 2020/5/18.
 */
public class ConfirmMaterialData {


    private String token;

  private String beizhu;
  private int pay_type;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public int getPay_type() {
        return pay_type;
    }

    public void setPay_type(int pay_type) {
        this.pay_type = pay_type;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    private List<Order> orders;

  public static class Order{
      private int material_id;
      private int amount;

      public int getMaterial_id() {
          return material_id;
      }

      public void setMaterial_id(int material_id) {
          this.material_id = material_id;
      }

      public int getAmount() {
          return amount;
      }

      public void setAmount(int amount) {
          this.amount = amount;
      }
  }

}
