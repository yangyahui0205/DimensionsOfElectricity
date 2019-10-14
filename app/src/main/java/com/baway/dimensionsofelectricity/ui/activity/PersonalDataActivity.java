package com.baway.dimensionsofelectricity.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baway.dimensionsofelectricity.R;
import com.baway.dimensionsofelectricity.data.net.HttpUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class PersonalDataActivity extends AppCompatActivity {

    @BindView(R.id.iv_person_pic)
    ImageView ivPersonPic;
    @BindView(R.id.et_person_name)
    EditText etPersonName;
    @BindView(R.id.et_person_pwd)
    EditText etPersonPwd;
    @BindView(R.id.btn_person_save_personal_data)
    Button btnPersonSavePersonalData;
    private int userId;
    private String sessionId;
    private String old_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
        ButterKnife.bind(this);
        SharedPreferences user = getSharedPreferences("user", MODE_PRIVATE);
        userId = user.getInt("userId", 4870);
        sessionId = user.getString("sessionId", "");
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        old_pwd = intent.getStringExtra("pwd");
        String pic = intent.getStringExtra("pic");
        etPersonName.setText(name);
        etPersonPwd.setText(old_pwd);
        Glide.with(PersonalDataActivity.this).load(pic).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivPersonPic);
    }


    @OnClick({R.id.iv_person_pic, R.id.btn_person_save_personal_data})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_person_pic:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 100);
                break;
            case R.id.btn_person_save_personal_data:
                final String pwd = etPersonPwd.getText().toString();
                String name = etPersonName.getText().toString();
                HttpUtils
                        .getInstance()
                        .getApiService()
                        .modifyUserNick(userId, sessionId, name)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<ResponseBody>() {
                            @Override
                            public void accept(ResponseBody responseBody) throws Exception {
                                String responseBodyString = responseBody.string();
                                Toast.makeText(PersonalDataActivity.this, responseBodyString, Toast.LENGTH_SHORT).show();
                            }
                        });
                HttpUtils
                        .getInstance()
                        .getApiService()
                        .modifyUserPwd(userId, sessionId, old_pwd, pwd)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<ResponseBody>() {
                            @Override
                            public void accept(ResponseBody responseBody) throws Exception {
                                String responseBodyString = responseBody.string();
                                Toast.makeText(PersonalDataActivity.this, responseBodyString, Toast.LENGTH_SHORT).show();
                            }
                        });
                /*  //上传图片
        try {
            //打开资产目录文件下的图片，针对该图片进行上传
            InputStream inputStream = getAssets().open("qq.png");
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            //将Bitmap转换为File文件
            //File file = BitmapUtil.compressImage(bitmap);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //文件压缩
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            //创建文件
            File file = new File(Environment.getExternalStorageDirectory(), "qq.png");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
            //将文件明确成请求体部分
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            //MutilpartBody声明请求体是多模块请求体
            MultipartBody multipartBody = new MultipartBody
                    .Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("image", file.getName(), requestBody)
                    .build();
            //获取MultipartBody中的所有部分
            List<MultipartBody.Part> parts = multipartBody.parts();
            Call<ResponseBody> updateIconCall = apiService.updateIcon(6933, "15628255213586933", parts);
            //开启队列
            updateIconCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        Toast.makeText(MainActivity.this, response.body().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }*/
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && data != null) {
            try {
                Uri uri = data.getData();//得到Uri
                Glide.with(PersonalDataActivity.this).load(uri).into(ivPersonPic);
                Log.d("PersonalDataActivity", "uri:" + uri);

                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor cursor = managedQuery(uri, proj, null, null, null);
                cursor.moveToNext();
                String cursorString = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));

                File file = new File(cursorString);
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                //MutilpartBody声明请求体是多模块请求体
                MultipartBody multipartBody = new MultipartBody
                        .Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("image", file.getName(), requestBody)
                        .build();
                //获取MultipartBody中的所有部分
                List<MultipartBody.Part> parts = multipartBody.parts();
                HttpUtils
                        .getInstance()
                        .getApiService()
                        .modifyHeadPic(userId, sessionId, parts)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<ResponseBody>() {
                            @Override
                            public void accept(ResponseBody responseBody) throws Exception {
                                String responseBodyString = responseBody.string();
                                Toast.makeText(PersonalDataActivity.this, responseBodyString, Toast.LENGTH_SHORT).show();
                            }
                        });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
