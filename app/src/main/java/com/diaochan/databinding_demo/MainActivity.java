package com.diaochan.databinding_demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.diaochan.databinding_demo.databinding.ActivityMainBinding;
import com.diaochan.databinding_demo.model.UserInfo;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "diaochan >>>";
    private UserInfo userInfo;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        //单向绑定第一种方式：
//        method01(binding);

        //单向绑定第二种方式：
//        method02(binding);

        //双向绑定
        method03(binding);
    }

    private void method03(ActivityMainBinding binding) {
        
        //View --> Model
        userInfo = new UserInfo();
        userInfo.username.set("diaochan");
        userInfo.password.set("89757");
        binding.setUserInfo(userInfo);

        /**
         * 将布局中修改为 @={userInfo.username} 
         * 当EditText发生变化时，10秒后再获取发现 会影响Model的改变
         */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Model --> View
                Log.e(TAG,userInfo.username.get() + "/" + userInfo.password.get());
            }
        },5000);
    }
    
    private void method02(ActivityMainBinding binding) {
        userInfo = new UserInfo();
        userInfo.username.set("diaochan");
        userInfo.password.set("89757");
        binding.setUserInfo(userInfo);

        //数据会刷新 （javabean使用可观察的属性ObservableField）
        /**
         *
         * public class UserInfo {
         *     public ObservableField<String> username = new ObservableField<>();
         *     public ObservableField<String> password = new ObservableField<>();
         * }
         */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                userInfo.username.set("kuangye");
                userInfo.password.set("001");
            }
        },2000);
    }

    private void method01(ActivityMainBinding binding) {
//        userInfo = new UserInfo();
//        userInfo.setUsername("diaochan");
//        userInfo.setPassword("89757");
//        binding.setUserInfo(userInfo);


        //数据不会刷新 （使用传统的JavaBean）
        /**
         * public class UserInfo {
         *     private String username;
         *     private String password;
         *
         *     public String getUsername() {
         *         return username;
         *     }
         *
         *     public UserInfo setUsername(String username) {
         *         this.username = username;
         *         return this;
         *     }
         *
         *     public String getPassword() {
         *         return password;
         *     }
         *
         *     public UserInfo setPassword(String password) {
         *         this.password = password;
         *         return this;
         *     }
         * }
         */


        //数据会刷新 （javabean继承BaseObservable）
        /**
         * public class UserInfo extends BaseObservable {
         *     private String username;
         *     private String password;
         *
         *     @Bindable
         *     public String getUsername() { 
         *         return username;
         *     }
         *
         *     public UserInfo setUsername(String username) {
         *         this.username = username;
         *         notifyPropertyChanged(com.diaochan.databinding_demo.BR.username);//通知
         *         return this;
         *     }
         *
         *     @Bindable
         *     public String getPassword() {
         *         return password;
         *     }
         *
         *     public UserInfo setPassword(String password) {
         *         this.password = password;
         *         notifyPropertyChanged(com.diaochan.databinding_demo.BR.password);
         *         return this;
         *     }
         * }
         */
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                userInfo.setUsername("kuangye");
//                userInfo.setPassword("001");
//            }
//        },2000);
    }
}
