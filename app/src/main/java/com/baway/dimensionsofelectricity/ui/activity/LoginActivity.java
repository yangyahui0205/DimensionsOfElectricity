package com.baway.dimensionsofelectricity.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baway.dimensionsofelectricity.R;
import com.baway.dimensionsofelectricity.data.beans.LoginBean;
import com.baway.dimensionsofelectricity.di.contract.ILoginContract;
import com.baway.dimensionsofelectricity.di.presenter.LoginPresenter;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<ILoginContract.ILoginView, LoginPresenter<ILoginContract.ILoginView>> implements ILoginContract.ILoginView {


    @BindView(R.id.et_userPhone)
    EditText etUserPhone;
    @BindView(R.id.et_userPwd)
    EditText etUserPwd;
    @BindView(R.id.iv_login_eye)
    ImageView ivLoginEye;
    @BindView(R.id.cb_rememberPwd)
    CheckBox cbRememberPwd;
    @BindView(R.id.tv_quickRegister)
    TextView tvQuickRegister;
    @BindView(R.id.btn_login)
    Button btnLogin;
    private SharedPreferences sharedPreferences;

    @Override
    public void showLoginData(String message) {
        Gson gson = new Gson();
        LoginBean loginBean = gson.fromJson(message, LoginBean.class);
        Toast.makeText(this, loginBean.getMessage(), Toast.LENGTH_SHORT).show();

        SharedPreferences user = getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor edit = user.edit();

        //判断是否登录成功
        if ("0000".equals(loginBean.getStatus())) {
            int userId = loginBean.getResult().getUserId();
            String sessionId = loginBean.getResult().getSessionId();
            //跳转展示页面
            edit.putInt("userId", userId);
            edit.putString("sessionId", sessionId);
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }
        edit.commit();
    }

    @Override
    protected LoginPresenter<ILoginContract.ILoginView> createPresenter() {
        return new LoginPresenter<>();
    }

    @Override
    protected void initData() {
        sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
        boolean isCheck = sharedPreferences.getBoolean("isCheck", false);
        cbRememberPwd.setChecked(isCheck);
        if (isCheck) {
            etUserPhone.setText(sharedPreferences.getString("phone", ""));
            etUserPwd.setText(sharedPreferences.getString("pwd", ""));
        }
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_login;
    }

    @OnClick({R.id.iv_login_eye, R.id.cb_rememberPwd, R.id.tv_quickRegister, R.id.btn_login})
    public void onViewClicked(View view) {
        String phone = etUserPhone.getText().toString().trim();
        String pwd = etUserPwd.getText().toString().trim();
        switch (view.getId()) {
            case R.id.iv_login_eye:
                break;
            case R.id.cb_rememberPwd:
                //记住密码
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putBoolean("isCheck", true).putString("phone", phone).putString("pwd", pwd).commit();
                break;
            case R.id.tv_quickRegister:
                //跳转注册页面
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.btn_login:
                //登录
                presenter.requestLoginData(phone, pwd);
                break;
        }
    }
}
