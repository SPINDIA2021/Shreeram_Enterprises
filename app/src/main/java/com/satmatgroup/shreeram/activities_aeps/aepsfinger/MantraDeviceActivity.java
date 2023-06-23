package com.satmatgroup.shreeram.activities_aeps.aepsfinger;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;

import com.satmatgroup.shreeram.NewMainActivity;
import com.satmatgroup.shreeram.R;
import com.satmatgroup.shreeram.activities_aeps.BalanceCheckResponseActivity;
import com.satmatgroup.shreeram.activities_aeps.CashDepositActivity;
import com.satmatgroup.shreeram.activities_aeps.CashWithdrawalSuccessActivity;
import com.satmatgroup.shreeram.activities_aeps.MiniStatementModel;
import com.satmatgroup.shreeram.activities_aeps.MinistatementActivity;
import com.satmatgroup.shreeram.activities_aeps.aepsfinger.global.Verhoeff;
import com.satmatgroup.shreeram.activities_aeps.aepsfinger.maskedittext.MaskedEditText;
import com.satmatgroup.shreeram.activities_aeps.aepsfinger.model.DeviceInfo;
import com.satmatgroup.shreeram.activities_aeps.aepsfinger.model.Opts;
import com.satmatgroup.shreeram.activities_aeps.aepsfinger.model.PidData;
import com.satmatgroup.shreeram.activities_aeps.aepsfinger.model.PidOptions;
import com.satmatgroup.shreeram.activities_aeps.aepsfinger.model.uid.AuthReq;
import com.satmatgroup.shreeram.activities_aeps.aepsfinger.model.uid.AuthRes;
import com.satmatgroup.shreeram.activities_aeps.aepsfinger.model.uid.Meta;
import com.satmatgroup.shreeram.activities_aeps.aepsfinger.model.uid.Uses;
import com.satmatgroup.shreeram.activities_aeps.aepsfinger.signer.XMLSigner;
import com.satmatgroup.shreeram.network.Preferences;
import com.satmatgroup.shreeram.network_calls.AppApiCalls;
import com.satmatgroup.shreeram.utils.AppCommonMethods;
import com.satmatgroup.shreeram.utils.AppConstants;
import com.satmatgroup.shreeram.utils.AppPrefs;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MantraDeviceActivity extends AppCompatActivity implements AppApiCalls.OnAPICallCompleteListener, TextToSpeech.OnInitListener {
    public static final String AEPS_TRANSACTION = "AEPS_TRANSACTION";
    public static final String EKYC = "E_KYC";

    @BindView(R.id.spinnerTotalFingerCount)
    Spinner spinnerTotalFingerCount;
    @BindView(R.id.linearFingerCount)
    LinearLayout linearFingerCount;
    @BindView(R.id.spinnerTotalFingerType)
    Spinner spinnerTotalFingerType;
    @BindView(R.id.spinnerTotalFingerFormat)
    Spinner spinnerTotalFingerFormat;
    @BindView(R.id.linearFingerFormat)
    LinearLayout linearFingerFormat;
    @BindView(R.id.edtxTimeOut)
    EditText edtxTimeOut;
    @BindView(R.id.edtxPidVer)
    EditText edtxPidVer;
    @BindView(R.id.linearTimeoutPidVer)
    LinearLayout linearTimeoutPidVer;
    @BindView(R.id.txtSelectPosition)
    TextView txtSelectPosition;
    @BindView(R.id.chbxUnknown)
    CheckBox chbxUnknown;
    @BindView(R.id.chbxLeftIndex)
    CheckBox chbxLeftIndex;
    @BindView(R.id.chbxLeftMiddle)
    CheckBox chbxLeftMiddle;
    @BindView(R.id.chbxLeftRing)
    CheckBox chbxLeftRing;
    @BindView(R.id.chbxLeftSmall)
    CheckBox chbxLeftSmall;
    @BindView(R.id.chbxLeftThumb)
    CheckBox chbxLeftThumb;
    @BindView(R.id.chbxRightIndex)
    CheckBox chbxRightIndex;
    @BindView(R.id.chbxRightMiddle)
    CheckBox chbxRightMiddle;
    @BindView(R.id.chbxRightRing)
    CheckBox chbxRightRing;
    @BindView(R.id.chbxRightSmall)
    CheckBox chbxRightSmall;
    @BindView(R.id.chbxRightThumb)
    CheckBox chbxRightThumb;
    @BindView(R.id.linearSelectPosition)
    LinearLayout linearSelectPosition;
    @BindView(R.id.edtxAdharNo)
    MaskedEditText edtxAdharNo;
    @BindView(R.id.linearAdharNo)
    LinearLayout linearAdharNo;
    @BindView(R.id.btnDeviceInfo)
    Button btnDeviceInfo;
    @BindView(R.id.btnCapture)
    Button btnCapture;
    @BindView(R.id.btnAuthRequest)
    Button btnAuthRequest;
    @BindView(R.id.btnReset)
    Button btnReset;
    @BindView(R.id.txtDataLabel)
    TextView txtDataLabel;
    @BindView(R.id.txtOutput)
    TextView txtOutput;
    @BindView(R.id.activity_main)
    LinearLayout activityMain;
    @BindView(R.id.spinnerEnv)
    Spinner spinnerEnv;


    private int fingerCount = 0;
    private PidData pidData = null;
    private Serializer serializer = null;
    private ArrayList<String> positions;

    private String latitude;
    private String longitude;

    private String cus_id;
    private String aadhar_no;
    private String nationalBankIdenticationNumber;
    private String mobile_no;
    private String transactionType;
    private String sendAmount;
    private String bankName;
    private String flag;
    private String requestremarks;
    private String aadharnumberkyc;
    private String pannumberkyc;
    private String kyccusid;
    String pidOptionDummy;
    private RelativeLayout progress_bar;

    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantra_device);
        ButterKnife.bind(this);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        positions = new ArrayList<>();
        serializer = new Persister();
        //Toolbar
        Toolbar toolbar = findViewById(R.id.custToolbar);
        ImageView ivBackBtn = toolbar.findViewById(R.id.ivBackBtn);
        ivBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        tts = new TextToSpeech(this, this);


        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            flag = bundle.getString("flag");
            if (flag.equals("ekyc")) {
                requestremarks = bundle.getString("requestremarks");
                aadharnumberkyc = bundle.getString("aadharnumberkyc");
                pannumberkyc = bundle.getString("pannumberkyc");
                kyccusid = bundle.getString("kyccusid");
            } else {
                cus_id = bundle.getString("cus_id");
                aadhar_no = bundle.getString("aadhar_no");
                nationalBankIdenticationNumber = bundle.getString("nationalBankIdenticationNumber");
                mobile_no = bundle.getString("mobile_no");
                transactionType = bundle.getString("transactionType");
                sendAmount = bundle.getString("sendAmount");
                bankName = bundle.getString("bankName");
                latitude = bundle.getString("latitude");
                longitude = bundle.getString("longitude");

            }
        }

        edtxAdharNo.setText("");


        progress_bar = findViewById(R.id.progress_bar);
       /* aepsTransaction(cus_id, "<PidData>\n" +
                        "   <Data type=\"X\">MjAyMS0wMi0wMVQxMjoxNToyNi56wzenqHLg8GYnIc5iHQuTa+P5ufU93s+e2HFm5j5XQDIYdvIQ43dTqvb+G2O5tbk9z3TrR5BLt5VCjNLeEqE615n6jmSSee5AhK4xqQVq2oAI2nT7b0fqFBxgnhvD8Tvv4/VRgcrODRF6QOy0ryzwwixzrs7ZfkzPrnR8XJNmCj8J6wVKSb2aWJa8G/EckAAifb9fxnT/kh4d9ZfvxYq0nl40jNNki1eLvt9OjGvRwgeseBgxTvTOD8/1TrR5Z9LeTY4INIlLCNVOl6ty5dDfhyzfUHdRfGh2Sdy+HIjG9kfgn1ipsmE+J+fu4R0xCGj35YPVnkCShu+aY8KYI6D9CXS1bBmmO4ytwIyAy9UhovPqi5jErLN7alCeMootKIwkG60bbqUq0b79YZcCXFbUBDXAaI45UXy2TL6FsnI2l/9CUpbb6P5F14l6ncP0d19zb7e1gAA51HliGNiH6GjP/ez1x3+mHYTvFthfckYJOzeKlpyK9uwh9oV7KaMP5+BcH67wezHGypZWQ0T6tbWutDdq4vOxRm3SPM7SeGGxWlVl1ufGZfuhTibl5D2tqZAjwUcvpXUi9EMJLezKM/rZySI6hc6msLFhClmeUxvYVdI8w9JCM2MQW+pzo40gHKmUXgVwE7JpMhQmoU15/UznqE5JtdhsRgClSEbnPdk7KW3LMCaIm5u8LtPwpHjqy6b8R+THQ+BeFjx9Gv4lJcTYKyHfBatKBZ/LFzZBqzZ0V1wzi/DEziyqMMFDyITLX1tZDLictjwO6jCXTqFTh8H5n4g5CjrAAihq/PRsMlCF1t6Iyul5wGOXpk7zSAambsgA8O3Qu3P3ShYyUWnGQdKFtO3tGIaeS11CVDzeR7xwQxfn8cxgRT1jM0wQnkMV/429jKfu1vePLGNoMSeDk4cErf/RCLIsaRyjhV7V0dJUmYydzI6WZRPqJeCcnTsEWPatWcZ/2zLwjvzXB304K/u/LUiO3pFZ6SnYjgpOCKS67NWpQSdv5PsrM5d3yg==</Data>\n" +
                        "   <DeviceInfo dc=\"c39c09b6-02e1-416c-a308-6f0d87632cb7\" dpId=\"MANTRA.MSIPL\" mc=\"MIIEGjCCAwKgAwIBAgIGAXdH3uNvMA0GCSqGSIb3DQEBCwUAMIHqMSowKAYDVQQDEyFEUyBNYW50cmEgU29mdGVjaCBJbmRpYSBQdnQgTHRkIDcxQzBBBgNVBDMTOkIgMjAzIFNoYXBhdGggSGV4YSBvcHBvc2l0ZSBHdWphcmF0IEhpZ2ggQ291cnQgUyBHIEhpZ2h3YXkxEjAQBgNVBAkTCUFobWVkYWJhZDEQMA4GA1UECBMHR3VqYXJhdDEdMBsGA1UECxMUVGVjaG5pY2FsIERlcGFydG1lbnQxJTAjBgNVBAoTHE1hbnRyYSBTb2Z0ZWNoIEluZGlhIFB2dCBMdGQxCzAJBgNVBAYTAklOMB4XDTIxMDEyODA3MDcxNloXDTIxMDIyNzA3MjIwM1owgbAxJDAiBgkqhkiG9w0BCQEWFXN1cHBvcnRAbWFudHJhdGVjLmNvbTELMAkGA1UEBhMCSU4xEDAOBgNVBAgTB0dVSkFSQVQxEjAQBgNVBAcTCUFITUVEQUJBRDEOMAwGA1UEChMFTVNJUEwxHjAcBgNVBAsTFUJpb21ldHJpYyBNYW51ZmFjdHVyZTElMCMGA1UEAxMcTWFudHJhIFNvZnRlY2ggSW5kaWEgUHZ0IEx0ZDCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBANyiYcJuzMZfo+h9n4rjCkz1lMQyw3hPakiSspcJ8IauKnjEzUu0M/89LwP3Hs2kEIBEompipv8U+dydVXP0djYolTvNe80SVb98Tc/V1kraZqEnqISRtoF7KABqEbAWz419bK513jRXJDHsJe8vbxs9AHL4GNM/zQ04mDqVichNmdtSG6nDJ0VXhbdP4mMxw0fJH/IemiRhx5T0/qV968Y8Q+QVCAX6W6WkkpryZYtEQMqs1yzrAqND5OqZ04poyGMYpOAqgakK9pffHlm2Vjoqt2eZyJ31QU53skXCSWhBHWuAP4CL6Oi0Ds9lvuLKuPBWVYovp6039g+DHLRkOm0CAwEAATANBgkqhkiG9w0BAQsFAAOCAQEAiy1GNY/z+tgZ3UumEDPDTXeomkhffJgMLfeQMeptgA4BYe4NKKu33ddZk7MnTxlq6ruqePXwhHoeg6zmhTbU/eikcHrtVR0HgG1BA6Sp4ftlKhLpZ/XanMA0lRiLPX5Z4FDOcwLlyRISXwqNW7FIAZ8qjpNPGnKbL8qnuE+utNKJNZ1klTfUPcA5hxMQXjlEx6VVssw5FFEm7h3dI5bnC3DR/MN5tDWE6zHWotDjFjEEGC7RZ5kMdqGkt1GsB3ByLOtxbAzow4GkZ9YdFOjBIfhqDEggAg9WOk07/Knitrfo0jsRbVbfgklyH9jzRUfYnAnq3uSp5JV0BRkrEFL5yA==\" mi=\"MFS100\" rdsId=\"MANTRA.AND.001\" rdsVer=\"1.0.4\">\n" +
                        "      <additional_info>\n" +
                        "         <Param name=\"srno\" value=\"3170633\"/>\n" +
                        "         <Param name=\"sysid\" value=\"353573092631109\"/>\n" +
                        "         <Param name=\"ts\" value=\"2021-02-01T12:15:31+05:30\"/>\n" +
                        "      </additional_info>\n" +
                        "   </DeviceInfo>\n" +
                        "   <Hmac>ZkD2Tlt+URRcMDueQTok4z7QDhvie/k4JTixVPYWqBFnOwTaLm7RqbWrHa+fDAkB</Hmac>\n" +
                        "   <Resp errCode=\"0\" errInfo=\"Capture Success\" fCount=\"1\" fType=\"0\" iCount=\"0\" iType=\"0\" nmPoints=\"25\" pCount=\"0\" pType=\"0\" qScore=\"78\"/>\n" +
                        "   <Skey ci=\"20221021\">jI+hQad0FNwO3UnQ9usmHyjCFv8t2HzyoqQVYJW/vXbd18293/XS73jEHmKj6uEboUTy4rukURojNrETFaI0ICg7GMDbC9BioGUbiFXWUmJcDSkmILcdCByrJ1H+QGtkerYcToIzu2eQ5XhlBT3a4O6bvzrzZNRVjWNt2+DUmejY3sxLYsIf0sLR3dn121MOMlDXCSyO1Ad24q2+DQsbwE3vOI8DZB4SeM4UWeMMB/cmYyC98D0ZqFlswwZu93YUSbob9EiN1TBG85uaHj3PEbpiRL/YKjOlRDbS8OUs3AXkMs7Gh/zjaUYtV5CnFbOSXRNQopFEarQV680WPTr+rg==</Skey>\n" +
                        "</PidData>", aadhar_no, nationalBankIdenticationNumber,
                mobile_no, transactionType, sendAmount);*/


    }

    @OnClick({R.id.btnDeviceInfo, R.id.btnCapture, R.id.btnAuthRequest, R.id.btnReset})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnDeviceInfo:
                try {
                    Intent intent = new Intent();
                    intent.setAction("in.gov.uidai.rdservice.fp.INFO");
                    startActivityForResult(intent, 1);
                } catch (Exception e) {
                    Log.e("Error", e.toString());
                }
                break;
            case R.id.btnCapture:
                try {
                    try {
                        Intent intent = new Intent();
                        intent.setAction("in.gov.uidai.rdservice.fp.INFO");
                        startActivityForResult(intent, 1);
                    } catch (Exception e) {
                        Log.e("Error", e.toString());
                    }


                    String pidOption = getPIDOptions();
                    pidOptionDummy = getPIDOptions();

              //      Toast.makeText(this, "" + pidOption, Toast.LENGTH_SHORT).show();


                    /*String pidOption = "<PidOptions ver=\"2.0\">" +
                            "   <Opts env=\"S\" fCount=\"1\" fType=\"0\" format=\"0\" iCount=\"0\" iType=\"0\" otp=\"1234\" wadh=\"Hello\" pCount=\"0\" pType=\"0\" pidVer=\"2.0\" posh=\"UNKNOWN\" timeout=\"10000\"/>" +
                            "   <Demo lang=\"05\">" +
                            "   <Pi ms=\"P\" mv=\"Jigar Shekh\" name=\"Jigar\" lname=\"Shekh\" lmv=\"\" gender=\"M\" dob=\"\" dobt=\"V\" age=\"24\" phone=\"\" email=\"\"/>" +
                            "   <Pa ms=\"E\" co=\"\" house=\"\" street=\"\" lm=\"\" loc=\"\"" +
                            "  vtc=\"\" subdist=\"\" dist=\"\" state=\"\" country=\"\" pc=\"\" po=\"\"/>" +
                            "   <Pfa ms=\"E|P\" mv=\"\" av=\"\" lav=\"\" lmv=\"\"/>" +
                            "   </Demo>" +
                            "</PidOptions>";*/

                    /*String pidOption = "<PidOptions ver=\"2.0\">" +
                            "   <Opts env=\"S\" fCount=\"1\" fType=\"0\" format=\"0\" iCount=\"0\" iType=\"0\" pCount=\"0\" pType=\"0\" pidVer=\"2.0\" posh=\"UNKNOWN\" timeout=\"10000\"/>" +
                            "   <Demo lang=\"05\">" +
                            "   <Pi ms=\"P\" mv=\"100\" name=\"Mahesh\" gender=\"M\"/>" +
                            "   </Demo>" +
                            "</PidOptions>";*/
                    if (pidOption != null) {
                        Log.e("PidOptions", pidOption);
                        Intent intent2 = new Intent();
                        intent2.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
                        intent2.putExtra("PID_OPTIONS", pidOption);
                        startActivityForResult(intent2, 2);
                    }
                } catch (Exception e) {
                    Log.e("Error", e.toString());
                }
                break;
            case R.id.btnAuthRequest:
                String aadharNo = edtxAdharNo.getText().toString();
                if (aadharNo.contains("-")) {
                    aadharNo = aadharNo.replaceAll("-", "").trim();
                }
                if (aadharNo.length() != 12 || !Verhoeff.validateVerhoeff(aadharNo)) {
                    setText("Please enter valid aadhaar number.");
                } else if (pidData == null) {
                    setText("Please scan your finger.");
                } else if (!pidData._Resp.errCode.equals("0")) {
                    setText("Error: " + pidData._Resp.errInfo);
                } else {
                    new MantraDeviceActivity.AuthRequest(aadharNo, pidData).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
                break;
            case R.id.btnReset:
                txtOutput.setText("");
                onResetClicked();
                break;
        }
    }

    public void onCheckboxClicked(View view) {
        CheckBox cb = (CheckBox) view;
        boolean checked = cb.isChecked();
        if (checked) {
            int pos = spinnerTotalFingerCount.getSelectedItemPosition();
            if ((pos + 1) > fingerCount) {
                fingerCount++;
                positions.add(cb.getText().toString());
            } else {
                ((CheckBox) view).setChecked(false);
                Toast.makeText(this, "Please Select Total Finger Count Proper", Toast.LENGTH_LONG).show();
            }
        } else {
            fingerCount--;
            String val = cb.getText().toString();
            if (positions.contains(val)) {
                positions.remove(val);
            }
        }
    }

    @SuppressLint("SetTextI18n")
    public void onResetClicked() {
        fingerCount = 0;
        edtxTimeOut.setText("10000");
        edtxAdharNo.setText("");
        edtxPidVer.setText("2.0");
        spinnerTotalFingerCount.setSelection(0);
        spinnerTotalFingerType.setSelection(0);
        spinnerTotalFingerFormat.setSelection(0);
//        spinnerEnv.setSelection(0);
        chbxLeftIndex.setChecked(false);
        chbxLeftMiddle.setChecked(false);
        chbxLeftRing.setChecked(false);
        chbxLeftSmall.setChecked(false);
        chbxLeftThumb.setChecked(false);
        chbxRightIndex.setChecked(false);
        chbxRightMiddle.setChecked(false);
        chbxRightRing.setChecked(false);
        chbxRightSmall.setChecked(false);
        chbxRightThumb.setChecked(false);
        chbxUnknown.setChecked(false);
        pidData = null;
        positions.clear();
        positions = new ArrayList<>();
    }

    private void setText(final String message) {

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (message.contains("<PidData>")) {


                }
                txtOutput.setText(message);


            }
        });
    }

    private String getPIDOptions() {
        try {
            int fingerCount = spinnerTotalFingerCount.getSelectedItemPosition() + 1;
           // int fingerType = spinnerTotalFingerType.getSelectedItemPosition();
            int fingerType = 2;
            int fingerFormat = spinnerTotalFingerFormat.getSelectedItemPosition();
            String pidVer = edtxPidVer.getText().toString();
            String timeOut = edtxTimeOut.getText().toString();
            String posh = "UNKNOWN";
            if (positions.size() > 0) {
                posh = positions.toString().replace("[", "").replace("]", "").replaceAll("[\\s+]", "");
            }

            Opts opts = new Opts();
            opts.fCount = String.valueOf(fingerCount);
            opts.fType = String.valueOf(fingerType);
            opts.iCount = "0";
            opts.iType = "0";
            opts.pCount = "0";
            opts.pType = "0";
            opts.format = String.valueOf(fingerFormat);
            opts.pidVer = pidVer;
            opts.timeout = timeOut;
//            opts.otp = "123456";
//            opts.wadh = "Hello";
            if(flag.equals("ekyc")) {
                opts.wadh = "NA";
            }
            opts.posh = posh;
            opts.env = "P";
            //      Toast.makeText(this, "ENV : " + spinnerEnv.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

            PidOptions pidOptions = new PidOptions();
            pidOptions.ver = "1.0";
            pidOptions.Opts = opts;

            //Toast.makeText(this, ""+pidOptions.Opts, Toast.LENGTH_SHORT).show();

            Serializer serializer = new Persister();
            StringWriter writer = new StringWriter();
            serializer.write(pidOptions, writer);
            return writer.toString();
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        if (data != null) {
                            String result = data.getStringExtra("DEVICE_INFO");
                            String rdService = data.getStringExtra("RD_SERVICE_INFO");
                            String display = "";
                            if (rdService != null) {
                                display = "RD Service Info :\n" + rdService + "\n\n";
                            }
                            if (result != null) {
                                /*DeviceInfo info = serializer.read(DeviceInfo.class, result);
                                display = display + "Device Code: " + info.dc + "\n\n"
                                        + "Serial No: " + info.srno + "\n\n"
                                        + "dpId: " + info.dpId + "\n\n"
                                        + "MC: " + info.mc + "\n\n"
                                        + "MI: " + info.mi + "\n\n"
                                        + "rdsId: " + info.rdsId + "\n\n"
                                        + "rdsVer: " + info.rdsVer;*/
                                display += "Device Info :\n" + result;
                             //   Toast.makeText(this, "" + display, Toast.LENGTH_SHORT).show();
                                setText(display);
                            }
                        }
                    } catch (Exception e) {
                        Log.e("Error", "Error while deserialze device info", e);
                      //  Toast.makeText(this, "Error while deserialze device info: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case 2:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        if (data != null) {
                            String result = data.getStringExtra("PID_DATA");
                            if (result != null) {
                                pidData = serializer.read(PidData.class, result);
                                setText(result);

                                if (!result.contains("Resp errCode=\"720\"")) {
                                    //   dummPid(result, pidOptionDummy);

                                    //open(result);


                                    if (flag.equals("ekyc")) {
                                        eKyc(requestremarks,
                                                pannumberkyc,
                                                aadharnumberkyc,
                                                result,
                                                pidOptionDummy,
                                                kyccusid);
                                //        Toast.makeText(this, "KYC Flag " , Toast.LENGTH_SHORT).show();

                                    } else {


                                        Preferences.saveValue(Preferences.FINGERDATA,result);
                                        Preferences.saveValue(Preferences.aadhar_no,aadhar_no);
                                        Preferences.saveValue(Preferences.nationalBankIdenticationNumber,nationalBankIdenticationNumber);
                                        Preferences.saveValue(Preferences.mobile_no,mobile_no);
                                        Preferences.saveValue(Preferences.transactionType,transactionType);
                                        Preferences.saveValue(Preferences.sendAmount,sendAmount);
                                        Preferences.saveValue(Preferences.latitude,latitude);
                                        Preferences.saveValue(Preferences.longitude,longitude);

                                        aepsTransaction(cus_id, result, aadhar_no, nationalBankIdenticationNumber,
                                                mobile_no, transactionType, sendAmount, latitude,
                                                longitude);
                                    //    Toast.makeText(this, "DATATTTTTAAA: "+result , Toast.LENGTH_SHORT).show();
                                    }

                                   /* Bundle bundle = new Bundle();
                                    bundle.putString("pid", result);
                                    Intent intent = new Intent(MantraDeviceActivity.this, AepsTransactionActivity.class);
                                    intent.putExtras(bundle);
                                    startActivity(intent);*/

                                }
                            }
                        }
                    } catch (Exception e) {
                        Log.e("Error", "Error while deserialze pid data", e);
                    }
                }
                break;
        }
    }

    @Override
    public void onAPICallCompleteListner(@Nullable Object item, @Nullable String flag, @NotNull String result) throws JSONException {
        if (flag.equals(AEPS_TRANSACTION)) {
            Log.e("AEPS_TRANSACTION", result);
         //  Toast.makeText(this, "AEPS_TRANSACTION : "+result , Toast.LENGTH_SHORT).show();
            //result(result);
            JSONObject jsonObject = new JSONObject(result);
            String status = jsonObject.getString(AppConstants.STATUS);
            String aepsmessage = jsonObject.getString(AppConstants.MESSAGE);

            Log.e(AppConstants.STATUS, status);
            //Toast.makeText(this, "Result3 : "+status , Toast.LENGTH_SHORT).show();
            if (status.contains("true")) {
                progress_bar.setVisibility(View.GONE);
             //   Toast.makeText(this, "Result : "+status , Toast.LENGTH_SHORT).show();

                if (transactionType.equalsIgnoreCase("ministatement")) {

          //          Toast.makeText(this, "" + result, Toast.LENGTH_SHORT).show();
                    ArrayList<MiniStatementModel> miniStatementModelArrayList = new ArrayList<>();

                    JSONArray cast = jsonObject.getJSONArray("ministatement");
                    Toast.makeText(this, "cast : "+cast , Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < cast.length(); i++) {
                        JSONObject notifyObjJson = cast.getJSONObject(i);
                        String date = notifyObjJson.getString("date");

                        Log.e("date", date);
                        MiniStatementModel offersModel = new Gson()
                                .fromJson(notifyObjJson.toString(), MiniStatementModel.class);
                        miniStatementModelArrayList.add(offersModel);

                    }

                    Bundle newBundle = new Bundle();
                    newBundle.putParcelableArrayList("miniStatementModelArrayList",
                            miniStatementModelArrayList);
                    Intent intent = new Intent(this, MinistatementActivity.class);
                    intent.putExtras(newBundle);
                    startActivity(intent);

                } else {


                    JSONObject aepsResultObj = jsonObject.getJSONObject("result");
                    //  JSONObject aepsData = aepsResultObj.getJSONObject("data");
                    String terminalId = aepsResultObj.getString("terminalId");
                    String requestTransactionTime = aepsResultObj.getString("requestTransactionTime");
                    String transactionAmount = aepsResultObj.getString("transactionAmount");
                    String transactionStatus = aepsResultObj.getString("transactionStatus");
                    String balanceAmount = aepsResultObj.getString("balanceAmount");
                    String bankRRN = aepsResultObj.getString("bankRRN");
                    String transactionType = aepsResultObj.getString("transactionType");
                    String fpTransactionId = aepsResultObj.getString("fpTransactionId");
                    String merchantTransactionId = aepsResultObj.getString("merchantTransactionId");
                    String outletname = jsonObject.getString("outletname");
                    String outletmobile = jsonObject.getString("outletmobile");
                    String url = jsonObject.getString("url");

                 //   Toast.makeText(this, "transactionType : "+transactionType , Toast.LENGTH_SHORT).show();

                    // String retailerid = jsonObject.getString("cus_id");
                    if (transactionType.equalsIgnoreCase("BE")) {
                        tts.speak(aepsmessage, TextToSpeech.QUEUE_FLUSH, null,"");

                        Bundle beBunndle = new Bundle();
                        beBunndle.putString("aepsmessage", aepsmessage);
                        beBunndle.putString("terminalId", terminalId);
                        beBunndle.putString("requestTransactionTime", requestTransactionTime);
                        beBunndle.putString("transactionAmount", transactionAmount);
                        beBunndle.putString("transactionStatus", transactionStatus);
                        beBunndle.putString("balanceAmount", balanceAmount);
                        beBunndle.putString("bankRRN", bankRRN);
                        beBunndle.putString("transactionType", transactionType);
                        beBunndle.putString("fpTransactionId", fpTransactionId);
                        beBunndle.putString("merchantTransactionId", merchantTransactionId);
                        beBunndle.putString("outletname", outletname);
                        beBunndle.putString("outletmobile", outletmobile);
                        beBunndle.putString("url", url);
                        Intent intent = new Intent(this, BalanceCheckResponseActivity.class);
                        intent.putExtras(beBunndle);
                        startActivity(intent);
                        finish();

                    } else if (transactionType.equalsIgnoreCase("CW") ||
                            transactionType.equalsIgnoreCase("M")) {
                        tts.speak(aepsmessage, TextToSpeech.QUEUE_FLUSH, null,"");

                        Bundle cwBunndle = new Bundle();
                        cwBunndle.putString("aepsmessage", aepsmessage);
                        cwBunndle.putString("terminalId", terminalId);
                        cwBunndle.putString("requestTransactionTime", requestTransactionTime);
                        cwBunndle.putString("transactionAmount", transactionAmount);
                        cwBunndle.putString("transactionStatus", transactionStatus);
                        cwBunndle.putString("balanceAmount", balanceAmount);
                        cwBunndle.putString("bankRRN", bankRRN);
                        cwBunndle.putString("transactionType", transactionType);
                        cwBunndle.putString("fpTransactionId", fpTransactionId);
                        cwBunndle.putString("merchantTransactionId", merchantTransactionId);
                        cwBunndle.putString("outletname", outletname);
                        cwBunndle.putString("outletmobile", outletmobile);
                        cwBunndle.putString("url", url);
                        cwBunndle.putString("aadhar_no", aadhar_no);
                        cwBunndle.putString("bankName", bankName);
                       // cwBunndle.putString("retailerId", retailerid);
                        Intent intent = new Intent(this, CashWithdrawalSuccessActivity.class);
                        intent.putExtras(cwBunndle);
                        startActivity(intent);
                        finish();

                    } else if (transactionType.equalsIgnoreCase("cashdeposit")) {
                        tts.speak(aepsmessage, TextToSpeech.QUEUE_FLUSH, null,"");

                        Bundle cwBunndle = new Bundle();
                        cwBunndle.putString("aepsmessage", aepsmessage);
                        cwBunndle.putString("terminalId", terminalId);
                        cwBunndle.putString("requestTransactionTime", requestTransactionTime);
                        cwBunndle.putString("transactionAmount", transactionAmount);
                        cwBunndle.putString("transactionStatus", transactionStatus);
                        cwBunndle.putString("balanceAmount", balanceAmount);
                        cwBunndle.putString("bankRRN", bankRRN);
                        cwBunndle.putString("transactionType", transactionType);
                        cwBunndle.putString("fpTransactionId", fpTransactionId);
                        cwBunndle.putString("merchantTransactionId", merchantTransactionId);
                        cwBunndle.putString("outletname", outletname);
                        cwBunndle.putString("outletmobile", outletmobile);
                        cwBunndle.putString("url", url);
                        cwBunndle.putString("aadhar_no", aadhar_no);
                        cwBunndle.putString("bankName", bankName);
                      //  cwBunndle.putString("retailerId", retailerid);
                        Intent intent = new Intent(this, CashWithdrawalSuccessActivity.class);
                        intent.putExtras(cwBunndle);
                        startActivity(intent);
                        finish();

                    }
                }
            } else {
                tts.speak(aepsmessage, TextToSpeech.QUEUE_FLUSH, null,"");

                Toast.makeText(this, "Toast 3:"+aepsmessage, Toast.LENGTH_SHORT).show();
                progress_bar.setVisibility(View.GONE);

              //  Toast.makeText(this, "Result Toast 3: "+aepsmessage , Toast.LENGTH_SHORT).show();
//                toast("Beneficiary Adding Failed")
            }
        }


        if (flag.equals(EKYC)) {
            Log.e("EKYC", result);
            JSONObject jsonObject = new JSONObject(result);
            String status = jsonObject.getString(AppConstants.STATUS);
            String aepsmessage = jsonObject.getString(AppConstants.MESSAGE);
            Log.e(AppConstants.STATUS, status);
            if (status.contains("true")) {
                progress_bar.setVisibility(View.GONE);
                Toast.makeText(this,"Toast2 : "+ aepsmessage, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, NewMainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this,"Toast1 : "+ aepsmessage, Toast.LENGTH_SHORT).show();
                progress_bar.setVisibility(View.GONE);
            }
        }
    }


    private class AuthRequest extends AsyncTask<Void, Void, String> {

        private String uid;
        private PidData pidData;
        private ProgressDialog dialog;
        private int posFingerFormat = 2;

        private AuthRequest(String uid, PidData pidData) {
            this.uid = uid;
            this.pidData = pidData;
            dialog = new ProgressDialog(MantraDeviceActivity.this);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            posFingerFormat = 2;
            dialog.setMessage("Please wait...");
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                DeviceInfo info = pidData._DeviceInfo;

                Uses uses = new Uses();
                uses.pi = "n";
                uses.pa = "n";
                uses.pfa = "n";
                uses.bio = "y";
                if (posFingerFormat == 2) {
                    uses.bt = "FIR";
                } else {
                    uses.bt = "FMR";
                }
                uses.pin = "n";
                uses.otp = "n";

                Meta meta = new Meta();
                meta.udc = "MANT0";
                meta.rdsId = info.rdsId;
                meta.rdsVer = info.rdsVer;
                meta.dpId = info.dpId;
                meta.dc = info.dc;
                meta.mi = info.mi;
                meta.mc = info.mc;

                AuthReq authReq = new AuthReq();
                authReq.uid = uid;
                authReq.rc = "Y";
                authReq.tid = "registered";
                authReq.ac = "public";
                authReq.sa = "public";
                authReq.ver = "2.0";
                authReq.txn = generateTXN();
                authReq.lk = "MEaMX8fkRa6PqsqK6wGMrEXcXFl_oXHA-YuknI2uf0gKgZ80HaZgG3A"; //AUA
                authReq.skey = pidData._Skey;
                authReq.Hmac = pidData._Hmac;
                authReq.data = pidData._Data;
                authReq.meta = meta;
                authReq.uses = uses;

                StringWriter writer = new StringWriter();
                serializer.write(authReq, writer);
                String pass = "public";
                String reqXML = writer.toString();
                String signAuthXML = XMLSigner.generateSignXML(reqXML, getAssets().open("staging_signature_privateKey.p12"), pass);

                URL url = new URL(getAuthURL(uid));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(30000);
                conn.setConnectTimeout(30000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/xml");
                conn.setUseCaches(false);
                conn.setDefaultUseCaches(false);
                OutputStreamWriter writer2 = new OutputStreamWriter(conn.getOutputStream());
                writer2.write(signAuthXML);
                writer2.flush();
                conn.connect();

                StringBuilder sb = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String response;
                while ((response = reader.readLine()) != null) {
                    sb.append(response).append("\n");
                }
                response = sb.toString();

                AuthRes authRes = serializer.read(AuthRes.class, response);
                String res;
                if (authRes.err != null) {
                    if (authRes.err.equals("0")) {
                        res = "Authentication Success" + "\n"
                                + "Auth Response: " + authRes.ret.toUpperCase() + "\n"
                                + "TXN: " + authRes.txn + "\n"
                                + "";
                    } else {
                        res = "Error Code: " + authRes.err + "\n"
                                + "Auth Response: " + authRes.ret.toUpperCase() + "\n"
                                + "TXN: " + authRes.txn + "\n"
                                + "";
                    }
                } else {
                    res = "Authentication Success" + "\n"
                            + "Auth Response: " + authRes.ret.toUpperCase() + "\n"
                            + "TXN: " + authRes.txn + "\n"
                            + "";
                }
                return res;
            } catch (Exception e) {
                Log.e("Error", "Error while auth request", e);
                return "Error: " + e.toString();
            }
        }

        @Override
        protected void onPostExecute(String res) {
            super.onPostExecute(res);
            if (res != null) {
                setText(res);
            }
            onResetClicked();
            if (dialog.isShowing())
                dialog.dismiss();
        }
    }

    private String generateTXN() {
        try {
            Date tempDate = Calendar.getInstance().getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.ENGLISH);
            String dTTXN = formatter.format(tempDate);
//            return "UKC:public:" + dTTXN;
            return dTTXN;
        } catch (Exception e) {
            Log.e("generateTXN.Error", e.toString());
            return "";
        }
    }

    private String getAuthURL(String UID) {
        String url = "http://developer.uidai.gov.in/auth/";
//        String url = "http://developer.uidai.gov.in/uidauthserver/";
        url += "public/" + UID.charAt(0) + "/" + UID.charAt(1) + "/";
        url += "MG41KIrkk5moCkcO8w-2fc01-P7I5S-6X2-X7luVcDgZyOa2LXs3ELI"; //ASA
        return url;
    }


    private void aepsTransaction(String cus_id, String txtPidData, String adhaarNumber,
                                 String nationalBankIdenticationNumber, String mobileNumber,
                                 String type, String transactionAmount, String latitude,
                                 String longitude) {

        if (new AppCommonMethods(this).isNetworkAvailable()) {

            progress_bar.setVisibility(View.VISIBLE);

            AppApiCalls mAPIcall = new AppApiCalls(this, AEPS_TRANSACTION, this);
            mAPIcall.aepsTransaction(cus_id,
                    txtPidData,
                    adhaarNumber,
                    nationalBankIdenticationNumber,
                    mobileNumber,
                    type,
                    transactionAmount, latitude, longitude);
        } else {
            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show();
        }
    }

    private void dummPid(String txtPidData, String pidOptions) {
        if (new AppCommonMethods(this).isNetworkAvailable()) {

            progress_bar.setVisibility(View.VISIBLE);

            AppApiCalls mAPIcall = new AppApiCalls(this, AEPS_TRANSACTION, this);
            mAPIcall.dummyPid(
                    txtPidData, pidOptions
            );
        } else {
            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show();
        }
    }

    private void eKyc(String requestRemarks,
                      String userPan,
                      String aadhaarNumber,
                      String txtPidData,
                      String PidOptions,
                      String cus_id) {
        if (new AppCommonMethods(this).isNetworkAvailable()) {

            progress_bar.setVisibility(View.VISIBLE);

            AppApiCalls mAPIcall = new AppApiCalls(this, EKYC, this);
            mAPIcall.eKyc(requestRemarks,
                    userPan,
                    aadhaarNumber,
                    txtPidData,
                    PidOptions,
                    cus_id);
        } else {
            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show();
        }
    }

    public void result(String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("yes",
                (arg0, arg1) -> {

                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void open(String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("message");
        alertDialogBuilder.setPositiveButton("yes",
                (arg0, arg1) -> {
                    if(flag.equals("ekyc")) {
                        eKyc(requestremarks,
                                pannumberkyc,
                                aadharnumberkyc,
                                message,
                                pidOptionDummy,
                                kyccusid);
                    } else {
                        aepsTransaction(cus_id, message, aadhar_no, nationalBankIdenticationNumber,
                                mobile_no, transactionType, sendAmount, latitude, longitude);


                    }


                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language specified is not supported!");
            } else {
                //   tts!!.speak("Text to speech", TextToSpeech.QUEUE_FLUSH, null,"")

                Log.e("TTS","buttonSpeak!!.isEnabled = true");
                // buttonSpeak!!.isEnabled = true
            }
        }  else {
        Log.e("TTS", "Initilization Failed!");
    }
    }



}