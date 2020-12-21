package com.resttcar.dh.tools;

import android.content.Context;

import com.resttcar.dh.app.DiningCarAPP;


/**
 * 具体实现sp 
 * Created by James on 2016/10/30.
 */  
public class PreferenceUtils extends BasePreference {  
    private static PreferenceUtils preferenceUtils;

    /** 
     * 需要增加key就在这里新建 
     */  
    //用户名的key  
    private String USER_NAME = "user_name";

    //用户token的key
    private String USER_TOKEN = "user_token";

    //用户头像的key
    private String USER_HEADIMG = "user_headimg";

    //用户ID的key
    private String USER_ID = "user_id";

    //是否登录的key
    private String LOGIN_STATUS = "login_status";

    //登录后获取的的group_id
    private String GROUP_ID = "GROUP_ID";

    //记住账号
    private String REMEMBERACCOUNT="remember_account";

    //记住密码
    private String REMEMBERPASSWORD="remember_password";





    public String getRemeberPassword() {
        return getString(REMEMBERPASSWORD);
    }

    public void setRemeberPassword(String password){
        setString(REMEMBERPASSWORD,password);
    }

    public String getRemeberAccount() {
        return getString(REMEMBERACCOUNT);
    }

    public void setRemeberAccount(String account){
        setString(REMEMBERACCOUNT,account);
    }


    public void loginOut(){
        userLoginOut();
    }
  
    public void setLoginStatus(boolean isFirst) {
        setBoolean(LOGIN_STATUS,isFirst);
    }

    public boolean getLoginStatus() {
        return getBoolean(LOGIN_STATUS);
    }

    public void setUserID(String id){
        setString(USER_ID,id);
    }

    public String getUserId(){
        return getString(USER_ID);
    }

    public void setGroupId(String id){
        setString(GROUP_ID,id);
    }

    public String getGroupId(){
        return getString(GROUP_ID);
    }

    public String getUserToken() {
        return getString(USER_TOKEN);


    }
    public void setUserToken(String token){
        setString(USER_TOKEN,token);
    }


    public void setUserHeadimg(String userHeadimg){
        setString(USER_HEADIMG,userHeadimg);
    }

    public String getUserHeadimg() {
        return getString(USER_HEADIMG);
    }

    public void setUserName(String name) {
        setString(USER_NAME, name);
    }  
  
    public String getUserName() {
        return getString(USER_NAME);  
    }



    private PreferenceUtils(Context context) {
        super(context);
    }
    /**
     * 这里我通过自定义的Application来获取Context对象，所以在获取preferenceUtils时不需要传入Context。
     * @return
     */
    public synchronized static PreferenceUtils getInstance() {
        if (null == preferenceUtils) {
            preferenceUtils = new PreferenceUtils(DiningCarAPP.getApplication());
        }
        return preferenceUtils;
    }
}