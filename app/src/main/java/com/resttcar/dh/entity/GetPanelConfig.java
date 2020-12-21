package com.resttcar.dh.entity;

import java.util.List;

/**
 * Created by James on 2020/5/15.
 */
public class GetPanelConfig {


        private CarBean car;
        private List<CarSendTypesBean> car_send_types;

        public CarBean getCar() {
            return car;
        }

        public void setCar(CarBean car) {
            this.car = car;
        }

        public List<CarSendTypesBean> getCar_send_types() {
            return car_send_types;
        }

        public void setCar_send_types(List<CarSendTypesBean> car_send_types) {
            this.car_send_types = car_send_types;
        }

        public static class CarBean {
            /**
             * car_id : 1
             * status : 1
             * send_status : 2
             * send_type_id : 1
             * send_range : 1.00
             * start_money : 1.00
             * car_name : KFC_餐车01
             * car_avatar : /uploads/20200214/163317ab5bde50b2a83e02ddd23a0ea3.jpg
             */

            private int car_id;
            private int status;
            private int send_status;
            private int send_type_id;
            private String send_range;
            private String start_money;
            private String car_name;
            private String car_avatar;

            public int getCar_id() {
                return car_id;
            }

            public void setCar_id(int car_id) {
                this.car_id = car_id;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getSend_status() {
                return send_status;
            }

            public void setSend_status(int send_status) {
                this.send_status = send_status;
            }

            public int getSend_type_id() {
                return send_type_id;
            }

            public void setSend_type_id(int send_type_id) {
                this.send_type_id = send_type_id;
            }

            public String getSend_range() {
                return send_range;
            }

            public void setSend_range(String send_range) {
                this.send_range = send_range;
            }

            public String getStart_money() {
                return start_money;
            }

            public void setStart_money(String start_money) {
                this.start_money = start_money;
            }

            public String getCar_name() {
                return car_name;
            }

            public void setCar_name(String car_name) {
                this.car_name = car_name;
            }

            public String getCar_avatar() {
                return car_avatar;
            }

            public void setCar_avatar(String car_avatar) {
                this.car_avatar = car_avatar;
            }
        }

        public static class CarSendTypesBean {
            /**
             * send_type_id : 1
             * type_name : 商家自送
             */

            private int send_type_id;
            private String type_name;

            public int getSend_type_id() {
                return send_type_id;
            }

            public void setSend_type_id(int send_type_id) {
                this.send_type_id = send_type_id;
            }

            public String getType_name() {
                return type_name;
            }

            public void setType_name(String type_name) {
                this.type_name = type_name;
            }
        }

}
