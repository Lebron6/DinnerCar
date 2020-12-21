package com.resttcar.dh.entity;

import java.util.List;

/**
 * Created by James on 2020/5/18.
 */
public class DeskIndex {



        private int hang_num;
        private List<GoodsClassesBean> goods_classes;

        public int getHang_num() {
            return hang_num;
        }

        public void setHang_num(int hang_num) {
            this.hang_num = hang_num;
        }

        public List<GoodsClassesBean> getGoods_classes() {
            return goods_classes;
        }

        public void setGoods_classes(List<GoodsClassesBean> goods_classes) {
            this.goods_classes = goods_classes;
        }

        public static class GoodsClassesBean {
            /**
             * class_id : 3
             * class_name : 饮品
             * sort : 1
             * list : [{"goods_id":1,"class_id":3,"title":"卡布奇诺","price":"100.00","image":"http://dh.tigerisa.com//uploads/20200228/163317ab5bde50b2a83e02ddd23a0ea3.jpg","content":"卡布奇诺","spec":[{"name":"手数","value":"11,112"},{"name":"嘻嘻嘻","value":"柔柔弱弱,阿迪发动"}],"last_amount":9965,"sell_amount":3,"class_name":"饮品"},{"goods_id":11,"class_id":3,"title":"牛山手数","price":"111.00","image":"/uploads/20200317/a7a6d97d917b3a5cf5c5ed30986c0e36.jpg","content":"<p><img src=\"http://dh.tigerisa.com/assets/plugins/umeditor/php/upload/20200317/15844142716798.jpg\"/><\/p>","spec":[],"last_amount":109,"sell_amount":0,"class_name":"饮品"}]
             */

            private int class_id;
            private String class_name;
            private int sort;
            private List<ListBean> list;

            public int getClass_id() {
                return class_id;
            }

            public void setClass_id(int class_id) {
                this.class_id = class_id;
            }

            public String getClass_name() {
                return class_name;
            }

            public void setClass_name(String class_name) {
                this.class_name = class_name;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * goods_id : 1
                 * class_id : 3
                 * title : 卡布奇诺
                 * price : 100.00
                 * image : http://dh.tigerisa.com//uploads/20200228/163317ab5bde50b2a83e02ddd23a0ea3.jpg
                 * content : 卡布奇诺
                 * spec : [{"name":"手数","value":"11,112"},{"name":"嘻嘻嘻","value":"柔柔弱弱,阿迪发动"}]
                 * last_amount : 9965
                 * sell_amount : 3
                 * class_name : 饮品
                 */

                private int goods_id;
                private int class_id;
                private String title;
                private String price;
                private String image;
                private String content;
                private int last_amount;
                private int sell_amount;
                private String class_name;
                private List<SpecBean> spec;

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

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public int getLast_amount() {
                    return last_amount;
                }

                public void setLast_amount(int last_amount) {
                    this.last_amount = last_amount;
                }

                public int getSell_amount() {
                    return sell_amount;
                }

                public void setSell_amount(int sell_amount) {
                    this.sell_amount = sell_amount;
                }

                public String getClass_name() {
                    return class_name;
                }

                public void setClass_name(String class_name) {
                    this.class_name = class_name;
                }

                public List<SpecBean> getSpec() {
                    return spec;
                }

                public void setSpec(List<SpecBean> spec) {
                    this.spec = spec;
                }

                public static class SpecBean {
                    /**
                     * name : 手数
                     * value : 11,112
                     */

                    private String name;
                    private String value;

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getValue() {
                        return value;
                    }

                    public void setValue(String value) {
                        this.value = value;
                    }


                }
            }
        }

}
