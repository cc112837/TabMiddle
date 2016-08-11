package com.cc.tabmiddle;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

public class MainActivity extends FragmentActivity {
    private LinearLayout tab;
    private FrameLayout container;
    private RadioGroup rg;
    private Fragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment fragment = new NewsFragment();

        ChangeFragmentHelper helper = new ChangeFragmentHelper();
        helper.setTargetFragment(fragment);
        helper.setIsClearStackBack(true);

        changeFragment(helper);

        initView();

    }

    private void changeFragment(ChangeFragmentHelper helper) {
        //获取需要跳转的Fragment
        Fragment targetFragment = helper.getTargetFragment();
        //获取是否清空回退栈
        boolean clearStackBack = helper.isClearStackBack();
        //获取是否加入回退栈
        String targetFragmentTag = helper.getTargetFragmentTag();
        //获取Bundle
        Bundle bundle = helper.getBundle();
        //是否给Fragment传值
        if (bundle != null) {
            targetFragment.setArguments(bundle);
        }

        FragmentManager manager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = manager.beginTransaction();

        if (targetFragment != null) {
            fragmentTransaction.replace(R.id.container, targetFragment);
        }

        //是否添加到回退栈
        if (targetFragmentTag != null) {
            fragmentTransaction.addToBackStack(targetFragmentTag);
        }

        //是否清空回退栈
        if (clearStackBack) {
            manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        fragmentTransaction.commit();
    }

    private void initView() {
        container=(FrameLayout) findViewById(R.id.container);
        tab=(LinearLayout) findViewById(R.id.main_tab);
        rg=(RadioGroup) findViewById(R.id.main_tabBar);
        rg.check(R.id.main_home);
        fragment=null;
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.main_news:
                        fragment=new NewsFragment();
                        break;
                    case R.id.main_home:
                        fragment=new HomeFragment();
                        break;
                    case R.id.main_my:
                        fragment=new MyFragment();
                        break;
                }
                ChangeFragmentHelper helper = new ChangeFragmentHelper();
                helper.setTargetFragment(fragment);
                helper.setIsClearStackBack(true);
                changeFragment(helper);
            }
        });

    }
}
