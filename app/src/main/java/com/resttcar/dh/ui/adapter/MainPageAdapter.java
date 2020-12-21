package com.resttcar.dh.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import com.resttcar.dh.ui.fragment.CashierFragment;
import com.resttcar.dh.ui.fragment.MineFragment;
import com.resttcar.dh.ui.fragment.OrderFragment;


public class MainPageAdapter extends FragmentPagerAdapter {


    private OrderFragment orderFragment;
    private CashierFragment cashierFragment;
    private MineFragment mineFragment;

    public MainPageAdapter(android.support.v4.app.FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0){
            if(orderFragment==null){
                orderFragment = new OrderFragment();
                return orderFragment;
            }else{
                return orderFragment;
            }
        }else if (position==1){
            if(cashierFragment==null){
                cashierFragment = new CashierFragment();
                return cashierFragment;
            }else{
                return cashierFragment;
            }
        }else if(position==2){
            if(mineFragment==null){
                mineFragment = new MineFragment();
                return mineFragment;
            }else{
                return mineFragment;
            }
        }else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }


}
