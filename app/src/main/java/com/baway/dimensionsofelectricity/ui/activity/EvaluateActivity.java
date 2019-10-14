package com.baway.dimensionsofelectricity.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baway.dimensionsofelectricity.R;
import com.baway.dimensionsofelectricity.data.net.HttpUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.File;
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

public class EvaluateActivity extends AppCompatActivity {

    @BindView(R.id.iv_evaluate_detail_pic)
    ImageView ivEvaluateDetailPic;
    @BindView(R.id.tv_evaluate_detail_name)
    TextView tvEvaluateDetailName;
    @BindView(R.id.tv_evaluate_detail_price)
    TextView tvEvaluateDetailPrice;
    @BindView(R.id.et_evaluate_content)
    EditText etEvaluateContent;
    @BindView(R.id.iv_evaluate_detail_camera_one)
    ImageView ivEvaluateDetailCameraOne;
    @BindView(R.id.iv_evaluate_detail_camera_two)
    ImageView ivEvaluateDetailCameraTwo;
    @BindView(R.id.iv_evaluate_detail_camera_three)
    ImageView ivEvaluateDetailCameraThree;
    @BindView(R.id.iv_evaluate_detail_camera)
    ImageView ivEvaluateDetailCamera;
    @BindView(R.id.cb_sync_to_circles)
    CheckBox cbSyncToCircles;
    @BindView(R.id.btn_evaluate_published)
    Button btnEvaluatePublished;
    private int commodityId;
    private String orderId;
    private int userId;
    private String sessionId;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);
        ButterKnife.bind(this);
        SharedPreferences user = getSharedPreferences("user", MODE_PRIVATE);
        userId = user.getInt("userId", 4870);
        sessionId = user.getString("sessionId", "");

        Intent intent = getIntent();
        commodityId = intent.getIntExtra("commodityId", 0);
        orderId = intent.getStringExtra("orderId");
        String commodityName = intent.getStringExtra("commodityName");
        String commodityPic = intent.getStringExtra("commodityPic");
        String[] split = commodityPic.split(",");
        Glide.with(EvaluateActivity.this).load(split).into(ivEvaluateDetailPic);
        int commodityPrice = intent.getIntExtra("commodityPrice", 0);
        tvEvaluateDetailName.setText(commodityName);
        tvEvaluateDetailPrice.setText(commodityPrice + "");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && data != null) {
            Uri uri = data.getData();
            Glide.with(EvaluateActivity.this).load(uri).into(ivEvaluateDetailCameraOne);
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = managedQuery(uri, proj, null, null, null);
            int actual_image_column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String img_path = cursor.getString(actual_image_column_index);
            file = new File(img_path);

        }
    }

    @OnClick({R.id.iv_evaluate_detail_camera, R.id.cb_sync_to_circles, R.id.btn_evaluate_published})
    public void onViewClicked(View view) {
        final String content = etEvaluateContent.getText().toString();
        switch (view.getId()) {
            case R.id.iv_evaluate_detail_camera:
                //打开相机相册选择
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 100);
                break;
            case R.id.cb_sync_to_circles:
                //同步圈子
                break;
            case R.id.btn_evaluate_published:
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                //MutilpartBody声明请求体是多模块请求体
                RequestBody.create(MediaType.parse("multipart/form-data"),file);
                MultipartBody multipartBody = new MultipartBody
                        .Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("commodityId", String.valueOf(commodityId))
                        .addFormDataPart("orderId", orderId)
                        .addFormDataPart("content", content)
                        .addFormDataPart("image", file.getName(), requestBody)
                        .build();
                //获取MultipartBody中的所有部分
                List<MultipartBody.Part> parts = multipartBody.parts();

                HttpUtils
                        .getInstance()
                        .getApiService()
                        .addCommodityComment(userId, sessionId, parts)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<ResponseBody>() {
                            @Override
                            public void accept(ResponseBody responseBody) throws Exception {
                                if (cbSyncToCircles.isChecked()) {
                                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                                    //同步到圈子
                                    MultipartBody multipartBody = new MultipartBody
                                            .Builder()
                                            .setType(MultipartBody.FORM)
                                            .addFormDataPart("commodityId", String.valueOf(commodityId))
                                            .addFormDataPart("content", content)
                                            .addFormDataPart("image", file.getName(), requestBody)
                                            .build();
                                    //获取MultipartBody中的所有部分
                                    List<MultipartBody.Part> parts = multipartBody.parts();
                                    HttpUtils
                                            .getInstance()
                                            .getApiService()
                                            .releaseCircle(userId, sessionId, parts)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new Consumer<ResponseBody>() {
                                                @Override
                                                public void accept(ResponseBody responseBody) throws Exception {
                                                    String responseBodyString = responseBody.string();
                                                    Gson gson = new Gson();
                                                    SyncToCirclesBean syncToCirclesBean = gson.fromJson(responseBodyString, SyncToCirclesBean.class);
                                                    Toast.makeText(EvaluateActivity.this, syncToCirclesBean.getMessage(), Toast.LENGTH_SHORT).show();
                                                    if (syncToCirclesBean.getStatus().equals("0000")) {
                                                        finish();
                                                    }
                                                }
                                            });
                                }
                                String responseBodyString = responseBody.string();
                                Gson gson = new Gson();
                                EvaluateBean evaluateBean = gson.fromJson(responseBodyString, EvaluateBean.class);
                                Toast.makeText(EvaluateActivity.this, evaluateBean.getMessage(), Toast.LENGTH_SHORT).show();
                                if (evaluateBean.getStatus().equals("0000")) {
                                    finish();
                                }
                            }
                        });

                break;
        }
    }

    class SyncToCirclesBean {

        /**
         * message : 发布成功
         * status : 0000
         */

        private String message;
        private String status;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    class EvaluateBean {


        /**
         * message : 评论成功
         * status : 0000
         */

        private String message;
        private String status;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
