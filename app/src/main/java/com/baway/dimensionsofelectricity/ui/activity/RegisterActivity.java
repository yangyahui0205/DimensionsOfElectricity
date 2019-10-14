package com.baway.dimensionsofelectricity.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baway.dimensionsofelectricity.R;
import com.baway.dimensionsofelectricity.data.beans.RegisterBean;
import com.baway.dimensionsofelectricity.di.contract.IRegisterContract;
import com.baway.dimensionsofelectricity.di.presenter.RegisterPresenter;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity<IRegisterContract.IRegisterView, RegisterPresenter<IRegisterContract.IRegisterView>> implements IRegisterContract.IRegisterView {


    @BindView(R.id.et_userPhone_register)
    EditText etUserPhoneRegister;
    @BindView(R.id.et_verification_code)
    EditText etVerificationCode;
    @BindView(R.id.et_userPwd_register)
    EditText etUserPwdRegister;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.iv_login_eye)
    ImageView ivLoginEye;
    @BindView(R.id.tv_login_immediately)
    TextView tvLoginImmediately;
    @BindView(R.id.btn_register)
    Button btnRegister;

    @Override
    public void showRegisterData(String message) {
        Gson gson = new Gson();
        RegisterBean registerBean = gson.fromJson(message, RegisterBean.class);
        Toast.makeText(this, registerBean.getMessage(), Toast.LENGTH_SHORT).show();
        if ("0000".equals(registerBean.getStatus())) {
            //跳转登录页面
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        }
    }

    @Override
    protected RegisterPresenter<IRegisterContract.IRegisterView> createPresenter() {
        return new RegisterPresenter<>();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_register;
    }

    @OnClick({R.id.tv_get_code, R.id.iv_login_eye, R.id.tv_login_immediately, R.id.btn_register})
    public void onViewClicked(View view) {
        String phone = etUserPhoneRegister.getText().toString().trim();
        String verificationCode = etVerificationCode.getText().toString().trim();
        String pwd = etUserPwdRegister.getText().toString().trim();
        switch (view.getId()) {
            case R.id.tv_get_code:
                break;
            case R.id.iv_login_eye:
                break;
            case R.id.tv_login_immediately:
                //跳转登录页面
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
                break;
            case R.id.btn_register:
                //立即注册
                presenter.requestRegisterData(phone, pwd);
                break;
        }
    }
}
