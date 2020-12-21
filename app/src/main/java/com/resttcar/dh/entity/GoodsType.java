package com.resttcar.dh.entity;

import java.util.List;

/**
 * Created by James on 2020/5/26.
 */
public class GoodsType {

        private List<ClassesBean> classes;

        public List<ClassesBean> getClasses() {
            return classes;
        }

        public void setClasses(List<ClassesBean> classes) {
            this.classes = classes;
        }

        public static class ClassesBean {
            /**
             * class_id : 1
             * class_name : 盖浇饭
             * sort : 1
             * num : 12
             */

            private int class_id;
            private String class_name;
            private int sort;
            private int num;

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

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }
        }
    }
