package com.resttcar.dh.entity;

import java.util.List;

/**
 * Created by James on 2020/5/26.
 */
public class BrandData {

        private List<GoodsBean> goods;
        private List<GoodsClassesBean> goods_classes;

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public List<GoodsClassesBean> getGoods_classes() {
            return goods_classes;
        }

        public void setGoods_classes(List<GoodsClassesBean> goods_classes) {
            this.goods_classes = goods_classes;
        }

        public static class GoodsBean {
            /**
             * goods_id : 12
             * class_id : 9
             * title : 牛山手数888
             * price : 100.00
             * image : http://dh.tigerisa.com/uploads/20200318/305f08120ddbe5bfa01a1f00902bc4a7.jpg
             * class_name : 饮品
             * sub_id : 14
             */

            private int goods_id;
            private int class_id;
            private String title;
            private String price;
            private String image;
            private String class_name;
            private int sub_id;

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public int getClass_id() {
                return class_id;
            }

            public void setClass_id(int class_id) {
                this.class_id = class_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getClass_name() {
                return class_name;
            }

            public void setClass_name(String class_name) {
                this.class_name = class_name;
            }

            public int getSub_id() {
                return sub_id;
            }

            public void setSub_id(int sub_id) {
                this.sub_id = sub_id;
            }
        }

        public static class GoodsClassesBean {
            /**
             * class_id : 9
             * name : 饮品
             */

            private int class_id;
            private String name;

            public int getClass_id() {
                return class_id;
            }

            public void setClass_id(int class_id) {
                this.class_id = class_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

    }
}
