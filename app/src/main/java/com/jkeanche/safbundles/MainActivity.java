package com.jkeanche.safbundles;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.hover.sdk.api.Hover;
import com.jkeanche.safbundles.bundles.DailyBundlesBuyer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.shashank.sony.fancytoastlib.FancyToast;

public class MainActivity extends AppCompatActivity implements  RadioGroup.OnCheckedChangeListener {

    private DailyBundlesBuyer dailyBundleBuyer;
    private Button btnBuy;



    private boolean isOnce; //buy once
    private boolean isAirtime; //buy using aitime
    private RadioGroup bundlesRadioGroup,paymentRadioGroup,renewRadioGroup;


    private boolean isPaymentModeSelected;
    private boolean isAutoRenewStatusSelected;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);







        isOnce = false;  // buy once not selected
        isAirtime = false; //buy autorenew not selected

        isPaymentModeSelected= false;
        isAutoRenewStatusSelected = false;





        Hover.initialize(this);
        dailyBundleBuyer = new DailyBundlesBuyer(this);
        //testAirtel();

        FancyToast.makeText(MainActivity.this,"Select Prefered Daily Data Bundle",FancyToast.LENGTH_LONG, FancyToast.INFO,true).show();

        initRadioGroups();
//        initBundleUIMenu();
        //button click event
        btnBuy = findViewById(R.id.btnBuy);
        btnBuy.setVisibility(View.GONE);

        btnBuy.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //get the radioed buttons and buy bundles

                if(btnBuy.getText().toString().equalsIgnoreCase(getString(R.string.next))){
                    if(dailyBundleBuyer.getSelectedBundle() == null){

                        FancyToast.makeText(MainActivity.this,"Select Daily Bundles to buy!",FancyToast.LENGTH_LONG, FancyToast.INFO,true).show();
                    }
                    else{
                        if(!isAutoRenewStatusSelected) {
                            FancyToast.makeText(MainActivity.this,"Select Buy Once or Auto Renew",FancyToast.LENGTH_LONG, FancyToast.WARNING,true).show();
                            disPlayLayout(R.id.data_renew, View.VISIBLE);
                            disPlayLayout(R.id.data_plans, View.GONE);
                            disPlayLayout(R.id.payment_option, View.GONE);
                        }
                        else{
                            if(!isPaymentModeSelected) {
                                FancyToast.makeText(MainActivity.this,"Select your Payment Mode",FancyToast.LENGTH_LONG, FancyToast.WARNING,true).show();
                                disPlayLayout(R.id.payment_option, View.VISIBLE);
                                disPlayLayout(R.id.data_renew, View.GONE);
                                disPlayLayout(R.id.data_renew, View.GONE);
                            }
                            else{
                                FancyToast.makeText(MainActivity.this,"Please Select Payment Mode",FancyToast.LENGTH_LONG, FancyToast.WARNING,true).show();
                            }
                        }


                    }
                }
                else{
                    if(btnBuy.getText().toString().equalsIgnoreCase(getString(R.string.buy))) {
                        int amount=dailyBundleBuyer.getSelectedBundle().getBundleAmount();
                        int value=dailyBundleBuyer.getSelectedBundle().getBundleValue();
                        String autoStatus = isOnce ? "once" : "with autorenewal option";
                        String paymentMOde = isAirtime ? "by Airtime" : "via mpesa";

                        FancyToast.makeText(MainActivity.this," You are Now buying "+amount
                                +" MB @Ksh "+value+" "+autoStatus+" "+paymentMOde,FancyToast.LENGTH_LONG, FancyToast.INFO,true).show();

                        //Now initiate a Buy data Request
                        try {
                            requestBuyBundle();
                        } catch (Exception e) {
                            FancyToast.makeText(MainActivity.this,e.getMessage(),FancyToast.LENGTH_LONG, FancyToast.ERROR,true).show();

                        }
                    }
                    else{
                        FancyToast.makeText(MainActivity.this,"Payment Mode Not Selected!",FancyToast.LENGTH_LONG, FancyToast.WARNING,true).show();

                    }

                }


            }
        });

    }

    private void initRadioGroups() {
        bundlesRadioGroup = findViewById(R.id.bundlesRadioGroup);
        renewRadioGroup = findViewById(R.id.renewRadioGroup);
        paymentRadioGroup = findViewById(R.id.paymentRadioGroup);

        initOnCheckedChangeListeners();
    }

    private void initOnCheckedChangeListeners() {
        bundlesRadioGroup.setOnCheckedChangeListener(this);
        renewRadioGroup.setOnCheckedChangeListener(this);
        paymentRadioGroup.setOnCheckedChangeListener(this);
    }




    private void disPlayLayout(int layoutId, int visibility){
        findViewById(layoutId).setVisibility(visibility);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            String[] sessionTextArr = data.getStringArrayExtra("ussd_messages");
            String uuid = data.getStringExtra("uuid");
            FancyToast.makeText(MainActivity.this,"Request Accepted:" + data.getDataString(),FancyToast.LENGTH_LONG, FancyToast.SUCCESS,true).show();
        } else if (requestCode == 0 && resultCode == Activity.RESULT_CANCELED) {
            FancyToast.makeText(MainActivity.this,"Error: " + data.getStringExtra("error"),FancyToast.LENGTH_LONG, FancyToast.ERROR,true).show();
        }
    }


    public void handleBundleSelection(int viewId) {

            switch(viewId){
                case R.id.radio500Mb:{
                    dailyBundleBuyer.setSelectedBundle(0);
                }break;
                case R.id.radio150Mb:{
                    dailyBundleBuyer.setSelectedBundle(1);

                }break;
                case R.id.radio50Mb:{
                    dailyBundleBuyer.setSelectedBundle(2);

                }break;
                case R.id.radio15Mb:{
                    dailyBundleBuyer.setSelectedBundle(3);

                }break;
                case R.id.radio7Mb:{
                    dailyBundleBuyer.setSelectedBundle(4);

                }break;


            }


    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        int radioGroupId= radioGroup.getId();
        int buttonId=radioGroup.getCheckedRadioButtonId();
        switch(radioGroupId){
            case R.id.bundlesRadioGroup:{
                handleBundleSelection(buttonId);
                btnBuy.setVisibility(View.VISIBLE);
            }break;
            case R.id.renewRadioGroup:{
                handleAutoRenewalSelection(buttonId);

            }break;
            case R.id.paymentRadioGroup:{
                handlePaymentSelection(buttonId);
                btnBuy.setText(getString(R.string.buy));

            }
            
            break;
            default:
                throw new IllegalStateException("Unexpected value: " + radioGroupId);
        }

    }

    private void handlePaymentSelection(int viewId) {
        switch(viewId){

            case R.id.radioAirtime:{
                isAirtime=true;

            }break;
            case R.id.radioMpesa:{
                isAirtime=false;

            }
        }
        isPaymentModeSelected = true;
    }

    private void handleAutoRenewalSelection(int viewId) {
        switch(viewId){

            case R.id.radioOnce:{
                isOnce=true;

            }break;
            case R.id.radioAutoRenew:{
                isOnce=false;

            }

            break;

            default:
                throw new IllegalStateException("Unexpected value: " + viewId);
        }
        isAutoRenewStatusSelected = true;
    }

    private void requestBuyBundle(){
        try {
            dailyBundleBuyer.buyBundle(isOnce,isAirtime);
        } catch (Exception e) {
            TextView errorTxt;
            errorTxt = findViewById(R.id.errorTxt);
            errorTxt.setText("Oops, there's an error: Check Internet Connection & Allow this app to have the required permissions in settings");
            FancyToast.makeText(MainActivity.this,"Error:"+e.getStackTrace().toString(),FancyToast.LENGTH_LONG, FancyToast.ERROR,true).show();
        }
    }

    private void testAirtel(){
        dailyBundleBuyer.testAirtel();

    }
}
