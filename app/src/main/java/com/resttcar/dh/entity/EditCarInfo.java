package com.resttcar.dh.entity;

import java.util.List;

/**
 * Created by James on 2020/6/2.
 */
public class EditCarInfo {

        private CarBean car;
        private List<BrandsBean> brands;
        private List<RunTypesBean> run_types;

        public CarBean getCar() {
            return car;
        }

        public void setCar(CarBean car) {
            this.car = car;
        }

        public List<BrandsBean> getBrands() {
            return brands;
        }

        public void setBrands(List<BrandsBean> brands) {
            this.brands = brands;
        }

        public List<RunTypesBean> getRun_types() {
            return run_types;
        }

        public void setRun_types(List<RunTypesBean> run_types) {
            this.run_types = run_types;
        }

        public static class CarBean {
            /**
             * car_id : 1
             * brand_id : 1
             * run_type_id : 1
             * car_name : KFC_餐车01
             * car_avatar : /uploads/20200214/163317ab5bde50b2a83e02ddd23a0ea3.jpg
             * car_owner : 张勇
             * car_no : 苏E-123456
             * telephone : 18626151561
             * province : 北京市
             * city : 北京市市辖区
             * district : 东城区
             * address : 苏州石路8号1
             * bank : 苏州建设银行
             * bank_account : 6222600240001446624
             * jointime : 1581609600
             */

            private int car_id;
            private int brand_id;
            private int run_type_id;
            private String car_name;
            private String car_avatar;
            private String car_owner;
            private String car_no;
            private String telephone;
            private String province;
            private String city;
            private String district;
            private String address;
            private String bank;
            private String bank_account;
            private int jointime;

            public int getCar_id() {
                return car_id;
            }

            public void setCar_id(int car_id) {
                this.car_id = car_id;
            }

            public int getBrand_id() {
                return brand_id;
            }

            public void setBrand_id(int brand_id) {
                this.brand_id = brand_id;
            }

            public int getRun_type_id() {
                return run_type_id;
            }

            public void setRun_type_id(int run_type_id) {
                this.run_type_id = run_type_id;
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

            public String getCar_owner() {
                return car_owner;
            }

            public void setCar_owner(String car_owner) {
                this.car_owner = car_owner;
            }

            public String getCar_no() {
                return car_no;
            }

            public void setCar_no(String car_no) {
                this.car_no = car_no;
            }

            public String getTelephone() {
                return telephone;
            }

            public void setTelephone(String telephone) {
                this.telephone = telephone;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getBank() {
                return bank;
            }

            public void setBank(String bank) {
                this.bank = bank;
            }

            public String getBank_account() {
                return bank_account;
            }

            public void setBank_account(String bank_account) {
                this.bank_account = bank_account;
            }

            public int getJointime() {
                return jointime;
            }

            public void setJointime(int jointime) {
                this.jointime = jointime;
            }
        }

        public static class BrandsBean {
            /**
             * brand_id : 1
             * brand_name : 肯德基
             */

            private int brand_id;
            private String brand_name;

            public int getBrand_id() {
                return brand_id;
            }

            public void setBrand_id(int brand_id) {
                this.brand_id = brand_id;
            }

            public String getBrand_name() {
                return brand_name;
            }

            public void setBrand_name(String brand_name) {
                this.brand_name = brand_name;
            }
        }

        public static class RunTypesBean {
            /**
             * run_type_id : 1
             * run_type_name : 加盟
             */

            private int run_type_id;
            private String run_type_name;

            public int getRun_type_id() {
                return run_type_id;
            }

            public void setRun_type_id(int run_type_id) {
                this.run_type_id = run_type_id;
            }

            public String getRun_type_name() {
                return run_type_name;
            }

            public void setRun_type_name(String run_type_name) {
                this.run_type_name = run_type_name;
            }
        }

}
