package com.resttcar.dh.entity;

import java.util.List;

/**
 * Created by James on 2020/5/26.
 */
public class GoodIndex {

        private List<GoodsClassesBean> goods_classes;

        public List<GoodsClassesBean> getGoods_classes() {
            return goods_classes;
        }

        public void setGoods_classes(List<GoodsClassesBean> goods_classes) {
            this.goods_classes = goods_classes;
        }

        public static class GoodsClassesBean {
            /**
             * class_id : 1
             * class_name : 盖浇饭
             * sort : 1
             * list : [{"goods_id":2,"image":"/uploads/20200113/b47109edf204a155aff1292b1dfbcff5.jpg","class_id":1,"title":"牛肉盖浇饭","price":"0.01","last_amount":9958,"status":-1,"spec":"","content":"<p>牛肉盖浇饭只能看到牛肉看不到饭<\/p>","sell_amount":46,"class_name":"盖浇饭"},{"goods_id":3,"image":"/uploads/20200410/af114706b65719233e216e61612e5be8.jpg","class_id":1,"title":"特色盖浇饭1","price":"0.20","last_amount":978,"status":1,"spec":"[{\"name\":\"甜度\",\"value\":\"三分甜,七分甜\"}]","content":"<p>牛肉盖浇饭只能看到牛肉看不到饭<\/p>","sell_amount":0,"class_name":"盖浇饭"},{"goods_id":25,"image":"/uploads/20200316/bf31426b67af7c5a77fa3d03842c61ac.png","class_id":1,"title":"一号盖饭","price":"0.01","last_amount":982,"status":-1,"spec":"","content":"一号盖饭","sell_amount":0,"class_name":"盖浇饭"},{"goods_id":26,"image":"/uploads/20200316/bf31426b67af7c5a77fa3d03842c61ac.png","class_id":1,"title":"二号盖饭","price":"0.01","last_amount":968,"status":1,"spec":"","content":"二号盖饭","sell_amount":0,"class_name":"盖浇饭"},{"goods_id":27,"image":"/uploads/20200316/bf31426b67af7c5a77fa3d03842c61ac.png","class_id":1,"title":"三号盖饭","price":"0.01","last_amount":965,"status":1,"spec":"","content":"三号盖饭","sell_amount":0,"class_name":"盖浇饭"},{"goods_id":28,"image":"/uploads/20200316/bf31426b67af7c5a77fa3d03842c61ac.png","class_id":1,"title":"四号盖饭","price":"0.01","last_amount":996,"status":1,"spec":"","content":"四号盖饭","sell_amount":0,"class_name":"盖浇饭"},{"goods_id":29,"image":"/uploads/20200316/bf31426b67af7c5a77fa3d03842c61ac.png","class_id":1,"title":"五号盖饭","price":"0.01","last_amount":996,"status":1,"spec":"","content":"五号盖饭","sell_amount":0,"class_name":"盖浇饭"},{"goods_id":30,"image":"/uploads/20200316/bf31426b67af7c5a77fa3d03842c61ac.png","class_id":1,"title":"六号盖饭","price":"0.01","last_amount":999,"status":1,"spec":"","content":"六号盖饭","sell_amount":0,"class_name":"盖浇饭"},{"goods_id":31,"image":"/uploads/20200316/bf31426b67af7c5a77fa3d03842c61ac.png","class_id":1,"title":"七号盖饭","price":"0.00","last_amount":1000,"status":1,"spec":"","content":"七号盖饭","sell_amount":0,"class_name":"盖浇饭"},{"goods_id":32,"image":"/uploads/20200316/bf31426b67af7c5a77fa3d03842c61ac.png","class_id":1,"title":"八号盖饭","price":"0.00","last_amount":1111,"status":1,"spec":"","content":"八号盖饭","sell_amount":0,"class_name":"盖浇饭"},{"goods_id":42,"image":"/uploads/20200330/45bfa9366846e9fb07dea3c98a01dbb2.jpg","class_id":1,"title":"椒盐排条","price":"10.00","last_amount":1,"status":1,"spec":"","content":"<p>好吃的椒盐排伊奥<\/p>","sell_amount":0,"class_name":"盖浇饭"},{"goods_id":46,"image":"/uploads/20200410/0ec042129b7c2e0bec6d39cafa9d1229.jpg","class_id":1,"title":"水果","price":"0.03","last_amount":100,"status":1,"spec":"","content":"苹果","sell_amount":0,"class_name":"盖浇饭"},{"goods_id":59,"image":"/uploads/20200427/317bba7430434cf48c323674efe7966a.jpg","class_id":1,"title":"测试盖浇饭","price":"0.01","last_amount":200,"status":1,"spec":"","content":"然头像","sell_amount":0,"class_name":"盖浇饭"}]
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
                 * goods_id : 2
                 * image : /uploads/20200113/b47109edf204a155aff1292b1dfbcff5.jpg
                 * class_id : 1
                 * title : 牛肉盖浇饭
                 * price : 0.01
                 * last_amount : 9958
                 * status : -1
                 * spec :
                 * content : <p>牛肉盖浇饭只能看到牛肉看不到饭</p>
                 * sell_amount : 46
                 * class_name : 盖浇饭
                 */

                private int goods_id;
                private String image;
                private int class_id;
                private String title;
                private String price;
                private int last_amount;
                private int status;
                private String spec;
                private String content;
                private int sell_amount;
                private String class_name;

                public int getGoods_id() {
                    return goods_id;
                }

                public void setGoods_id(int goods_id) {
                    this.goods_id = goods_id;
                }

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
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

                public int getLast_amount() {
                    return last_amount;
                }

                public void setLast_amount(int last_amount) {
                    this.last_amount = last_amount;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public String getSpec() {
                    return spec;
                }

                public void setSpec(String spec) {
                    this.spec = spec;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
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
            }
        }

}
