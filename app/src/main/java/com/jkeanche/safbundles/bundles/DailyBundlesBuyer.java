package com.jkeanche.safbundles.bundles;


import android.content.Intent;
import android.widget.TextView;


import com.hover.sdk.api.HoverParameters;
import com.jkeanche.safbundles.MainActivity;
import com.jkeanche.safbundles.R;
import com.jkeanche.safbundles.hoveractions.HoverActions;

import java.util.ArrayList;
import java.util.List;

public class DailyBundlesBuyer {

    private MainActivity context;

    private DailyBundle selectedBundle = null; //the bundle that shall selected by user to be bought

    private  List<DailyBundle> supportedList = null; // list of supported daily bundles buy safaricom


    public DailyBundlesBuyer(MainActivity context) {
        this.context=context;
        initSupportedBundles();
    }


    public void setSelectedBundle(int bundleIndex){
        selectedBundle=supportedList.get(bundleIndex);
    }

    public DailyBundle getSelectedBundle() {
        return selectedBundle;
    }

    public void buyBundle(boolean isOnce, boolean isByAirtime) throws Exception{
      //call respective Hover Action

        if(selectedBundle == null){
            throw new Exception("Please Select the Daily Bundle you want");
        }
        else {

            int optionNumber = selectedBundle.getOptionNumber();


            switch (optionNumber) {
                case 1: { //500 mb selected
                    if (isOnce) { //once
                        if (isByAirtime) { //use airtime
                            buyByAirtimeOnce(500);
                        } else {
                            buyByMpesaOnce(500);
                        }
                    } else {//autorenew
                        if (isByAirtime) {
                            buyByAirtimeAutoRenew(500);
                        } else {
                            buyByMpesaAutoRenew(500);
                        }

                    }
                }break;

                case 2: {
                    if (isOnce) {
                        // buy 500mb by airtime once
                        if (isByAirtime) {//airtime
                            buyByAirtimeOnce(150);
                        } else {//mpesa
                            buyByMpesaOnce(150);
                        }
                    } else {//aurorenew
                        if (isByAirtime) {
                            buyByAirtimeAutoRenew(150);
                        } else {
                            buyByMpesaAutoRenew(150);
                        }

                    }
                }
                break;

                case 3: {
                    if (isOnce) {//once
                        if (isByAirtime) {//airtime
                            buyByAirtimeOnce(50);
                        } else {//mpesa
                            buyByMpesaOnce(50);
                        }
                    } else {
                        if (isByAirtime) {//airtime
                            buyByAirtimeAutoRenew(50);
                        } else {//mpesa
                            buyByMpesaAutoRenew(50);
                        }

                    }

                }break;
                case 4: {// buy 15mb
                    if (isOnce) {//// buy once
                        if (isByAirtime) {// by airtime
                            buyByAirtimeOnce(15);
                        } else {//by mpeaa
                            buyByMpesaOnce(15);
                        }
                    } else {
                        if (isByAirtime) {// by airtime
                            buyByAirtimeAutoRenew(15);
                        } else {//by mpesa
                            buyByMpesaAutoRenew(15);
                        }

                    }

                }break;
                case 5: { // buy 7mb
                    if (isOnce) {//buy once

                        if (isByAirtime) {//  by airtime
                            buyByAirtimeOnce(7);
                        } else { //  by mpesa
                            buyByMpesaOnce(7);
                        }
                    } else { //autorenew
                        if (isByAirtime) {//  by airtime
                            buyByAirtimeAutoRenew(7);
                        } else {//  by mpesa
                            buyByMpesaAutoRenew(7);
                        }

                    }


                }

                }
            }
        }



    //done once to define the daily bundles offered by Safaricom
    private  void initSupportedBundles(){
        supportedList=new ArrayList<>();
        supportedList.add(new DailyBundle(1,500,99));
        supportedList.add(new DailyBundle(2,150,50));
        supportedList.add(new DailyBundle(3,50,20));
        supportedList.add(new DailyBundle(4,15,10));
        supportedList.add(new DailyBundle(5,7,5));

    }


    private void buyByAirtimeOnce(int amount){
        //switch with 5 cases
        switch(amount){
            case 500:{
                initiateHoverActivity(HoverActions.ID_500MB_ONCE_BY_AIRTIME);
            }break;
            case 150:{
                initiateHoverActivity(HoverActions.ID_150MB_ONCE_BY_AIRTIME);

            }
            case 50:{
                initiateHoverActivity(HoverActions.ID_50MB_ONCE_BY_AIRTIME);

            }break;
            case 15:{
                initiateHoverActivity(HoverActions.ID_15MB_ONCE_BY_AIRTIME);

            }break;
            case 7:{
                initiateHoverActivity(HoverActions.ID_7MB_ONCE_BY_AIRTIME);

            }

        }
    }
    private void buyByAirtimeAutoRenew(int amount){
        switch(amount) {
            case 500: {
                initiateHoverActivity(HoverActions.ID_500MB_AUTORENEW_BY_AIRTIME);
            }break;
            case 150: {
                initiateHoverActivity(HoverActions.ID_150MB_AUTORENEW_BY_AIRTIME);
            }break;
            case 50: {
                initiateHoverActivity(HoverActions.ID_50MB_AUTORENEW_BY_AIRTIME);

            }break;
            case 15: {
                initiateHoverActivity(HoverActions.ID_15MB_AUTORENEW_BY_AIRTIME);

            }break;
            case 7: {
                initiateHoverActivity(HoverActions.ID_7MB_AUTORENEW_BY_AIRTIME);

            }
        }

    }

    private void buyByMpesaOnce(int amount){
        switch(amount) {
            case 500: {
                initiateHoverActivity(HoverActions.ID_500MB_ONCE_BY_MPESA);
            }break;
            case 150: {
                initiateHoverActivity(HoverActions.ID_150MB_ONCE_BY_MPESA);

            }break;
            case 50: {
                initiateHoverActivity(HoverActions.ID_50MB_ONCE_BY_MPESA);

            }break;
            case 15: {
                initiateHoverActivity(HoverActions.ID_15MB_ONCE_BY_MPESA);

            }break;
            case 7: {
                initiateHoverActivity(HoverActions.ID_7MB_ONCE_BY_MPESA);

            }
        }

    }
    private void buyByMpesaAutoRenew(int amount){
        switch(amount) {
            case 500: {
                initiateHoverActivity(HoverActions.ID_500MB_AUTORENEW_BY_MPESA);
            }break;
            case 150: {
                initiateHoverActivity(HoverActions.ID_150MB_AUTORENEW_BY_MPESA);

            }break;
            case 50: {
                initiateHoverActivity(HoverActions.ID_50MB_AUTORENEW_BY_MPESA);

            }break;
            case 15: {
                initiateHoverActivity(HoverActions.ID_15MB_AUTORENEW_BY_MPESA);

            }break;
            case 7: {
                initiateHoverActivity(HoverActions.ID_7MB_AUTORENEW_BY_MPESA);

            }
        }

    }

    //call the clast method for the selected options
    public void initiateHoverActivity(String hoverId){

        Intent i = new HoverParameters.Builder(context)
                .request(hoverId)
                 .buildIntent();
        context.startActivityForResult(i, 0);

    }


    public void testAirtel() {

        Intent i = new HoverParameters.Builder(context)
                .request("4a125903")
                .buildIntent();
        context.startActivityForResult(i, 0);
    }
}
