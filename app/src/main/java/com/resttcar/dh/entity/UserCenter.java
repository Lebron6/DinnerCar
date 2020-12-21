package com.resttcar.dh.entity;

/**
 * Created by James on 2020/6/2.
 */
public class UserCenter {

        /**
         * car : {"car_id":1,"car_name":"KFC_餐车1","car_avatar":"/uploads/20200527/05acc9a44cfc64a2678b6cd708b7e68d.jpg","status":2,"send_status":2}
         * statistics : {"num":2,"money":"0.02"}
         */

        private CarBean car;
        private StatisticsBean statistics;

        public CarBean getCar() {
            return car;
        }

        public void setCar(CarBean car) {
            this.car = car;
        }

        public StatisticsBean getStatistics() {
            return statistics;
        }

        public void setStatistics(StatisticsBean statistics) {
            this.statistics = statistics;
        }

        public static class CarBean {
            /**
             * car_id : 1
             * car_name : KFC_餐车1
             * car_avatar : /uploads/20200527/05acc9a44cfc64a2678b6cd708b7e68d.jpg
             * status : 2
             * send_status : 2
             */

            private int car_id;
            private String car_name;
            private String car_avatar;
            private int status;
            private int send_status;

            public int getCar_id() {
                return car_id;
            }

            public void setCar_id(int car_id) {
                this.car_id = car_id;
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
        }

        public static class StatisticsBean {
            /**
             * num : 2
             * money : 0.02
             */

            private int num;
            private String money;

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

    }
}
