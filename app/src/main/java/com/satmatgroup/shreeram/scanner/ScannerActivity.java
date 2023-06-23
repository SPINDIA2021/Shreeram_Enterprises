package com.satmatgroup.shreeram.scanner;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.satmatgroup.shreeram.R;
import com.satmatgroup.shreeram.network.PanCardFormResonse;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ScannerActivity extends AppCompatActivity {

    SurfaceView surfaceView;
    TextView txtBarcodeValue;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    Button btnAction;
    String intentData = "";
    boolean isEmail = false;
    boolean dailogShow=false;
    EditText edName, edUPI;


    String finalUpi = "", name = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        initView();
    }

    private void initView() {
        txtBarcodeValue = findViewById(R.id.txtBarcodeValue);
        surfaceView = findViewById(R.id.surfaceView);
        btnAction = findViewById(R.id.btnAction);
        edName=findViewById(R.id.etName);
        edUPI=findViewById(R.id.etUPI);

        btnAction.setVisibility(View.GONE);


        edName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count>0)
                {
                    btnAction.setVisibility(View.VISIBLE);
                }else {
                    btnAction.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        edUPI.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count>0)
                {
                    btnAction.setVisibility(View.VISIBLE);
                }else {
                    btnAction.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (edUPI.getText().toString().isEmpty())
                    {
                        Toast.makeText(ScannerActivity.this, "Please enter UPI ID", Toast.LENGTH_SHORT).show();
                    }else  if (edName.getText().toString().isEmpty())
                    {
                        Toast.makeText(ScannerActivity.this, "Please enter name", Toast.LENGTH_SHORT).show();
                    }else {
                        name=edName.getText().toString();
                        finalUpi=edUPI.getText().toString();

                        if (!dailogShow)
                        {
                            showAmountDailog();
                            dailogShow=true;
                        }
                    }

            }
        });
    }

    private void initialiseDetectorsAndSources() {

        Toast.makeText(getApplicationContext(), "Barcode scanner started", Toast.LENGTH_SHORT).show();
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(ScannerActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(ScannerActivity.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
             //   Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    txtBarcodeValue.post(new Runnable() {
                        @Override
                        public void run() {

                            if (barcodes.valueAt(0).email != null) {
                                txtBarcodeValue.removeCallbacks(null);
                                intentData = barcodes.valueAt(0).email.address;
                                txtBarcodeValue.setText(intentData);
                                isEmail = true;
                                btnAction.setText("ADD CONTENT TO THE MAIL");

                            } else {
                                isEmail = false;
                                btnAction.setText("LAUNCH URL");
                                intentData = barcodes.valueAt(0).displayValue;
                                String[] dataArray = intentData.split("&");
                                /*String upiid=dataArray[0];
                                String[]upiArray=upiid.split("=");
                                String finalUpi=upiArray[1];

                                String name=dataArray[1];*/

                                for (int i = 0; i < dataArray.length; i++) {
                                    if (dataArray[i].startsWith("pn=")) {
                                        name = dataArray[i].replace("pn=", "");
                                    }
                                    if (dataArray[i].startsWith("upi://pay?pa=")) {
                                        finalUpi = dataArray[i].replace("upi://pay?pa=", "");
                                    }
                                }



                               // txtBarcodeValue.setText(intentData + "\n\n" + finalUpi + "\n\n" + name);


                            }
                            if (!dailogShow)
                            {
                                showAmountDailog();
                                dailogShow=true;
                            }

                        }

                    });

                }
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        cameraSource.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialiseDetectorsAndSources();
    }


    String orderId = generateString();
    private void callService(String amount) {
        showLoading();
        System.setProperty("http.keepAlive", "false");
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.readTimeout(5, TimeUnit.MINUTES).
                connectTimeout(5, TimeUnit.MINUTES).
                writeTimeout(5, TimeUnit.MINUTES).
                retryOnConnectionFailure(true).addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request.Builder builder = original.newBuilder();

                        //Request request = chain.request().newBuilder().addHeader("parameter", "value").build();
                        builder.header("Content-Type", "application/json");
                        builder.addHeader("Accept", "application/json");
                    /*    builder.addHeader("CompName", "glob");
                        builder.addHeader("AuthPswd", "5AA37B2E90AF6EA983D2C68B330809BE");


                        try {
                            if (!TextUtils.isEmpty(Preferences.getString(Preferences.AUTH_TOKEN))) {
                                builder.addHeader("Authorization", Preferences.getString(Preferences.AUTH_TOKEN));
                            }
                        }catch(Exception e){}*/
                        Request request = builder.method(original.method(), original.body())
                                .build();

                        return chain.proceed(request);

                    }
                });

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SacnnerIAPI.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

       /* Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();*/

        //creating the retrofit api service
        SacnnerIAPI apiService = retrofit.create(SacnnerIAPI.class);

        orderId = generateString();

        String mobileNo = getIntent().getStringExtra("mobile");
        String cus_id = getIntent().getStringExtra("cus_id");

        //   RequestBody orderId1 = createPartFromString(orderId);
        RequestBody vpa1 = createPartFromString(finalUpi);
        RequestBody name1 = createPartFromString(name);
        RequestBody amount1 = createPartFromString(amount);
        RequestBody mon_no1 = createPartFromString(mobileNo);
        RequestBody cus_id1 = createPartFromString(cus_id);
        // RequestBody member_id1 = createPartFromString("MEW10002");
        //    RequestBody password1 = createPartFromString("78319");



        //Call<ScannerResponse> call = apiService.saveScan(orderId1,vpa1,name1,amount1,mon_no1,member_id1,password1);
        Call<PanCardFormResonse> call = apiService.saveScan(amount1,vpa1,name1,mon_no1,cus_id1);


        //making the call to generate checksum
        call.enqueue(new Callback<PanCardFormResonse>() {
            @Override
            public void onResponse(Call<PanCardFormResonse> call, Response<PanCardFormResonse> response) {
                dismissLoading();

                Toast.makeText(ScannerActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                onBackPressed();

                //once we get the checksum we will initiailize the payment.
                //the method is taking the checksum we got and the paytm object as the parameter

            }

            @Override
            public void onFailure(Call<PanCardFormResonse> call, Throwable t) {
                dismissLoading();

                callServiceFalse(amount);
                //Toast.makeText(ScannerActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


/*
    private void callService(String amount) {
    showLoading();
        System.setProperty("http.keepAlive", "false");
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.readTimeout(5, TimeUnit.MINUTES).
                connectTimeout(5, TimeUnit.MINUTES).
                writeTimeout(5, TimeUnit.MINUTES).
                retryOnConnectionFailure(true).addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request.Builder builder = original.newBuilder();

                        //Request request = chain.request().newBuilder().addHeader("parameter", "value").build();
                        builder.header("Content-Type", "application/json");
                        builder.addHeader("Accept", "application/json");
                    *//*    builder.addHeader("CompName", "glob");
                        builder.addHeader("AuthPswd", "5AA37B2E90AF6EA983D2C68B330809BE");


                        try {
                            if (!TextUtils.isEmpty(Preferences.getString(Preferences.AUTH_TOKEN))) {
                                builder.addHeader("Authorization", Preferences.getString(Preferences.AUTH_TOKEN));
                            }
                        }catch(Exception e){}*//*
                        Request request = builder.method(original.method(), original.body())
                                .build();

                        return chain.proceed(request);

                    }
                });

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SacnnerIAPI.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

       *//* Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();*//*

        //creating the retrofit api service
        SacnnerIAPI apiService = retrofit.create(SacnnerIAPI.class);

        String orderId = generateString();

        String mobileNo = getIntent().getStringExtra("mobile");

        RequestBody vpa1 = createPartFromString(finalUpi);
        RequestBody name1 = createPartFromString(name);
        RequestBody amount1 = createPartFromString(amount);
        RequestBody mon_no1 = createPartFromString(mobileNo);




        Call<ScannerResponse> call = apiService.saveScan(vpa1,name1,amount1,mon_no1);

        //making the call to generate checksum
        call.enqueue(new Callback<ScannerResponse>() {
            @Override
            public void onResponse(Call<ScannerResponse> call, Response<ScannerResponse> response) {
                dismissLoading();

                Toast.makeText(ScannerActivity.this, response.body().getStatus(), Toast.LENGTH_SHORT).show();


                //once we get the checksum we will initiailize the payment.
                //the method is taking the checksum we got and the paytm object as the parameter

            }

            @Override
            public void onFailure(Call<ScannerResponse> call, Throwable t) {
                dismissLoading();

                callServiceFalse(amount);
                //Toast.makeText(ScannerActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

}*/

    private String generateString() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replace("-", "");
    }

    private Dialog mDialog;
    private void showLoading() {
        try {
            ((AppCompatActivity) this).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mDialog = null;
                    if (mDialog == null) {
                        mDialog = new Dialog(ScannerActivity.this);
                        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        mDialog.setContentView(R.layout.progress_dialog);
                        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        mDialog.setCanceledOnTouchOutside(false);
                        mDialog.setCancelable(false);
                        mDialog.show();
                    }
                }
            });

        } catch (Exception e) {
            ((AppCompatActivity) this).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mDialog = null;
                    if (mDialog == null) {
                        mDialog = new Dialog(ScannerActivity.this);
                        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        mDialog.setContentView(R.layout.progress_dialog);
                        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        mDialog.setCanceledOnTouchOutside(false);
                        mDialog.setCancelable(false);
                        mDialog.show();
                    }
                }
            });
        }
    }


    public void dismissLoading() {
        try {
            ((AppCompatActivity) this).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mDialog != null) {
                        mDialog.cancel();
                        mDialog.dismiss();
                    }
                }
            });

        } catch (Exception e) {
            ((AppCompatActivity) this).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mDialog != null) {
                        mDialog.cancel();
                        mDialog.dismiss();
                    }
                }
            });
            e.printStackTrace();
        }
    }


    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, descriptionString);
    }


    BottomSheetDialog dialogAmount;
    private void showAmountDailog() {
        dialogAmount = new BottomSheetDialog(this, R.style.AppBottomSheetDialogTheme);
        dialogAmount.setContentView(R.layout.bottom_amount);

        EditText editAmount = dialogAmount.findViewById(R.id.etAmountScan);
        CardView btnSend=dialogAmount.findViewById(R.id.btnAction);
        ImageView imgBack=dialogAmount.findViewById(R.id.img_back);
        TextView tvName=dialogAmount.findViewById(R.id.tvName);
        TextView tvUpiId=dialogAmount.findViewById(R.id.tvUpiId);

        String username="";
        if (name.contains("%20")) {

            username=name.replace("%20"," ");
        }else {
            username=name;
        }

        tvName.setText(username);
        tvUpiId.setText(finalUpi);


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editAmount.getText().toString().isEmpty()) {

                    editAmount.requestFocus();
                    editAmount.setError("Enter Amount");
                    return;
                } else {
                    callService(editAmount.getText().toString());
                    editAmount.setText("0");
                }
                dialogAmount.dismiss();
                dailogShow=false;
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAmount.dismiss();
                dailogShow=false;
            }
        });


        dialogAmount.setTitle("");
        dialogAmount.show();
    }


    private void callServiceFalse(String amount) {
        showLoading();
        System.setProperty("http.keepAlive", "false");
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.readTimeout(5, TimeUnit.MINUTES).
                connectTimeout(5, TimeUnit.MINUTES).
                writeTimeout(5, TimeUnit.MINUTES).
                retryOnConnectionFailure(true).addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request.Builder builder = original.newBuilder();

                        //Request request = chain.request().newBuilder().addHeader("parameter", "value").build();
                        builder.header("Content-Type", "application/json");
                        builder.addHeader("Accept", "application/json");
                    /*    builder.addHeader("CompName", "glob");
                        builder.addHeader("AuthPswd", "5AA37B2E90AF6EA983D2C68B330809BE");


                        try {
                            if (!TextUtils.isEmpty(Preferences.getString(Preferences.AUTH_TOKEN))) {
                                builder.addHeader("Authorization", Preferences.getString(Preferences.AUTH_TOKEN));
                            }
                        }catch(Exception e){}*/
                        Request request = builder.method(original.method(), original.body())
                                .build();

                        return chain.proceed(request);

                    }
                });

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SacnnerIAPI.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

       /* Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();*/

        //creating the retrofit api service
        SacnnerIAPI apiService = retrofit.create(SacnnerIAPI.class);

        orderId = generateString();

        String mobileNo = getIntent().getStringExtra("mobile");
        String cus_id = getIntent().getStringExtra("cus_id");


        RequestBody vpa1 = createPartFromString(finalUpi);
        RequestBody name1 = createPartFromString(name);
        RequestBody amount1 = createPartFromString(amount);
        RequestBody mon_no1 = createPartFromString(mobileNo);
        RequestBody cus_id1 = createPartFromString(cus_id);




        Call<ScannerFalseResponse> call = apiService.saveFalseScan(amount1,vpa1,name1,mon_no1,cus_id1);

        //making the call to generate checksum
        call.enqueue(new Callback<ScannerFalseResponse>() {
            @Override
            public void onResponse(Call<ScannerFalseResponse> call, Response<ScannerFalseResponse> response) {
                dismissLoading();

                Toast.makeText(ScannerActivity.this, response.body().getStatus(), Toast.LENGTH_SHORT).show();


                //once we get the checksum we will initiailize the payment.
                //the method is taking the checksum we got and the paytm object as the parameter

            }

            @Override
            public void onFailure(Call<ScannerFalseResponse> call, Throwable t) {
                dismissLoading();


                Toast.makeText(ScannerActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }



/*

    private void callServiceFalse(String amount) {
        showLoading();
        System.setProperty("http.keepAlive", "false");
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.readTimeout(5, TimeUnit.MINUTES).
                connectTimeout(5, TimeUnit.MINUTES).
                writeTimeout(5, TimeUnit.MINUTES).
                retryOnConnectionFailure(true).addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request.Builder builder = original.newBuilder();

                        //Request request = chain.request().newBuilder().addHeader("parameter", "value").build();
                        builder.header("Content-Type", "application/json");
                        builder.addHeader("Accept", "application/json");
                    *//*    builder.addHeader("CompName", "glob");
                        builder.addHeader("AuthPswd", "5AA37B2E90AF6EA983D2C68B330809BE");


                        try {
                            if (!TextUtils.isEmpty(Preferences.getString(Preferences.AUTH_TOKEN))) {
                                builder.addHeader("Authorization", Preferences.getString(Preferences.AUTH_TOKEN));
                            }
                        }catch(Exception e){}*//*
                        Request request = builder.method(original.method(), original.body())
                                .build();

                        return chain.proceed(request);

                    }
                });

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SacnnerIAPI.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

       *//* Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();*//*

        //creating the retrofit api service
        SacnnerIAPI apiService = retrofit.create(SacnnerIAPI.class);

        String orderId = generateString();

        String mobileNo = getIntent().getStringExtra("mobile");


        RequestBody vpa1 = createPartFromString(finalUpi);
        RequestBody name1 = createPartFromString(name);
        RequestBody amount1 = createPartFromString(amount);
        RequestBody mon_no1 = createPartFromString(mobileNo);




        Call<ScannerFalseResponse> call = apiService.saveFalseScan(vpa1,name1,amount1,mon_no1);

        //making the call to generate checksum
        call.enqueue(new Callback<ScannerFalseResponse>() {
            @Override
            public void onResponse(Call<ScannerFalseResponse> call, Response<ScannerFalseResponse> response) {
                dismissLoading();

                Toast.makeText(ScannerActivity.this, response.body().getStatus(), Toast.LENGTH_SHORT).show();


                //once we get the checksum we will initiailize the payment.
                //the method is taking the checksum we got and the paytm object as the parameter

            }

            @Override
            public void onFailure(Call<ScannerFalseResponse> call, Throwable t) {
                dismissLoading();


                Toast.makeText(ScannerActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }*/
}
