package com.resttcar.dh.entity;

/**
 * Created by James on 2020/5/29.
 */
public class CWInfo {


        private CarBean car;

        public CarBean getCar() {
            return car;
        }

        public void setCar(CarBean car) {
            this.car = car;
        }

        public static class CarBean {
            /**
             * money : 114392.23
             * manage_fee : 1200.00
             * tixian_cycle : 1
             * manage_fee_date : 13
             * bank_account : 6222600240001446624
             * tixian_money : 113192.23
             * tixian_percent : 3.00
             */

            private String money;
            private String manage_fee;
            private int tixian_cycle;
            private int manage_fee_date;
            private String bank_account;
            private double tixian_money;
            private String tixian_percent;

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getManage_fee() {
                return manage_fee;
            }

            public void setManage_fee(String manage_fee) {
                this.manage_fee = manage_fee;
            }

            public int getTixian_cycle() {
                return tixian_cycle;
            }

            public void setTixian_cycle(int tixian_cycle) {
                this.tixian_cycle = tixian_cycle;
            }

            public int getManage_fee_date() {
                return manage_fee_date;
            }

            public void setManage_fee_date(int manage_fee_date) {
                this.manage_fee_date = manage_fee_date;
            }

            public String getBank_account() {
                return bank_account;
            }

            public void setBank_account(String bank_account) {
                this.bank_account = bank_account;
            }

            public double getTixian_money() {
                return tixian_money;
            }

            public void setTixian_money(double tixian_money) {
                this.tixian_money = tixian_money;
            }

            public String getTixian_percent() {
                return tixian_percent;
            }

            public void setTixian_percent(String tixian_percent) {
                this.tixian_percent = tixian_percent;
            }
        }
    }

