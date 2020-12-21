package com.resttcar.dh.entity;

import android.util.Log;

import com.resttcar.dh.tools.ArithUtil;
import com.resttcar.dh.tools.Utils;

import java.util.List;

/**
 * Created by cheng on 16-11-10.
 */
public class Dish {

    private String dishName;
    private double dishPrice;
    private String dishPic;
    private List<SpecValuesBean> valuesBeans;
    private int dishAmount;
    private int goodId;
    private int typeId;
    private int material_id;
    private int is_discount;
    private double typeDishPrice;

    public double getTypeDishPrice() {
        typeDishPrice = dishPrice * num;

        if (is_discount == 1) {
            if (typeDishPrice >= Double.valueOf(full)) {
                return ArithUtil.sub(typeDishPrice, Double.parseDouble(Utils.stringToKeep2Point(discount))) ;
            } else {
                return dishPrice * num;
            }
        }else{
            return typeDishPrice;
        }

    }

    public void setTypeDishPrice(double typeDishPrice) {
        this.typeDishPrice = typeDishPrice;
    }

    private boolean already_calculation = false;

    public boolean isAlready_calculation() {
        return already_calculation;
    }

    public void setAlready_calculation(boolean already_calculation) {
        this.already_calculation = already_calculation;
    }

    public int getIs_discount() {
        return is_discount;
    }

    public void setIs_discount(int is_discount) {
        this.is_discount = is_discount;
    }

    private String full;
    private String discount;

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public int getMaterial_id() {
        return material_id;
    }

    public void setMaterial_id(int material_id) {
        this.material_id = material_id;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    private int sell_Amount;
    private int status;//餐车菜单菜品上架状态
    private int classId;
    private String classType;
    private String goodsContent;

    public String getGoodsContent() {
        return goodsContent;
    }

    public void setGoodsContent(String goodsContent) {
        this.goodsContent = goodsContent;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSell_Amount() {
        return sell_Amount;
    }

    public void setSell_Amount(int sell_Amount) {
        this.sell_Amount = sell_Amount;
    }

    private String selectSpec = "";
    private int num = 0;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "dishName='" + dishName + '\'' +
                ", dishPrice=" + dishPrice +
                ", dishPic='" + dishPic + '\'' +
                ", dishAmount=" + dishAmount +
                ", goodId=" + goodId +
                ", selectSpec='" + selectSpec + '\'' +
                ", num=" + num +
                '}';
    }

    public int getGoodId() {
        return goodId;
    }

    public void setGoodId(int goodId) {
        this.goodId = goodId;
    }

    public String getSelectSpec() {
        return selectSpec;
    }

    public void setSelectSpec(String selectSpec) {
        this.selectSpec = selectSpec;
    }


    public List<SpecValuesBean> getValuesBeans() {
        return valuesBeans;
    }

    public void setValuesBeans(List<SpecValuesBean> valuesBeans) {
        this.valuesBeans = valuesBeans;
    }


    public String getDishPic() {
        return dishPic;
    }

    public void setDishPic(String dishPic) {
        this.dishPic = dishPic;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public double getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(double dishPrice) {
        this.dishPrice = dishPrice;
    }

    public int getDishAmount() {
        return dishAmount;
    }

    public void setDishAmount(int dishAmount) {
        this.dishAmount = dishAmount;
    }


    public int hashCode() {
        int code = this.dishName.hashCode() + (int) this.dishPrice + this.selectSpec.hashCode() + this.dishAmount;
        return code;
    }

    public static class SpecValuesBean {

        private String valueName;
        private String selectValue;

        public String getSelectValue() {
            return selectValue;
        }

        public void setSelectValue(String selectValue) {
            this.selectValue = selectValue;
        }

        private ValueBean valueBean;

        public ValueBean getValueBean() {
            return valueBean;
        }

        public void setValueBean(ValueBean valueBean) {
            this.valueBean = valueBean;
        }

        public String getValueName() {
            return valueName;
        }

        public void setValueName(String valueName) {
            this.valueName = valueName;
        }

        public static class ValueBean {
            private List<Tag> tags;

            public List<Tag> getTags() {
                return tags;
            }

            public void setTags(List<Tag> tags) {
                this.tags = tags;
            }

            public static class Tag {
                private String va;
                private int TAG = 0;

                public String getVa() {
                    return va;
                }

                public void setVa(String va) {
                    this.va = va;
                }

                public int getTAG() {
                    return TAG;
                }

                public void setTAG(int TAG) {
                    this.TAG = TAG;
                }
            }

            @Override
            public String toString() {
                return "ValueBean{" +
                        "tags=" + tags +
                        '}';
            }
        }
    }

    @Override
    public boolean equals(Object obj) {


        return obj instanceof Dish &&
                this.selectSpec.equals(((Dish) obj).selectSpec) &&
                this.goodId == (((Dish) obj).goodId) &&
                this.material_id == (((Dish) obj).material_id);
    }


}
