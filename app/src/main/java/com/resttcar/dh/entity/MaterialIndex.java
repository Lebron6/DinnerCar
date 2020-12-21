package com.resttcar.dh.entity;

import java.util.List;

/**
 * Created by James on 2020/5/28.
 */
public class MaterialIndex {

        private List<MaterialTypesBean> material_types;

        public List<MaterialTypesBean> getMaterial_types() {
            return material_types;
        }

        public void setMaterial_types(List<MaterialTypesBean> material_types) {
            this.material_types = material_types;
        }

        public static class MaterialTypesBean {
            /**
             * type_id : 1
             * type_name : 底料
             * list : [{"material_id":1,"type_id":1,"name":"番茄酱","image":"http://dh.resttcar.com/uploads/20200325/e96cbafbb8120147da5bc2926dcda1f7.jpg","price":"0.01","amount":1000,"unit":"瓶","last_amount":78,"is_discount":0,"full":"0.00","discount":"0.00"}]
             */

            private int type_id;
            private String type_name;
            private List<ListBean> list;

            public int getType_id() {
                return type_id;
            }

            public void setType_id(int type_id) {
                this.type_id = type_id;
            }

            public String getType_name() {
                return type_name;
            }

            public void setType_name(String type_name) {
                this.type_name = type_name;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * material_id : 1
                 * type_id : 1
                 * name : 番茄酱
                 * image : http://dh.resttcar.com/uploads/20200325/e96cbafbb8120147da5bc2926dcda1f7.jpg
                 * price : 0.01
                 * amount : 1000
                 * unit : 瓶
                 * last_amount : 78
                 * is_discount : 0
                 * full : 0.00
                 * discount : 0.00
                 */

                private int material_id;
                private int type_id;
                private String name;
                private String image;
                private String price;
                private int amount;
                private String unit;
                private int last_amount;
                private int is_discount;
                private String full;
                private String discount;

                public int getMaterial_id() {
                    return material_id;
                }

                public void setMaterial_id(int material_id) {
                    this.material_id = material_id;
                }

                public int getType_id() {
                    return type_id;
                }

                public void setType_id(int type_id) {
                    this.type_id = type_id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public int getAmount() {
                    return amount;
                }

                public void setAmount(int amount) {
                    this.amount = amount;
                }

                public String getUnit() {
                    return unit;
                }

                public void setUnit(String unit) {
                    this.unit = unit;
                }

                public int getLast_amount() {
                    return last_amount;
                }

                public void setLast_amount(int last_amount) {
                    this.last_amount = last_amount;
                }

                public int getIs_discount() {
                    return is_discount;
                }

                public void setIs_discount(int is_discount) {
                    this.is_discount = is_discount;
                }

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
            }
        }

}
