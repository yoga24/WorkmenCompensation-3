package com.cyorg.uiic.workmencompensationrainbow3.utils;

import android.os.Environment;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.cyorg.uiic.workmencompensationrainbow3.model.SingleEntryModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CommonUtils {

    public static final String FILE_DIRECTORY = Environment.getExternalStorageDirectory().getAbsolutePath() + "/cyorg/WorkmenCompensation";
    private static final String TAG = "CommonUtils";
    public static boolean redefineValues;
    public static int rate;
    public static String insurerName;
    public static int discount;
    public static double totalSumInsured = 0.00;
    public static double discountedPremium = 0.00;
    public static double serviceTax = 0.00;
    public static double totalPremium = 0.00;
    public static double finalPremium = 0.00;
    public static String fileName;
    public static String filePath;
    public static boolean changesMadeAfterFileSave = false;
    private static List<SingleEntryModel> dataList;

    public static void testData() {
        for (int i = 0; i <= 10; i++) {
            SingleEntryModel entryModel = new SingleEntryModel();
            entryModel.setNoPersons(10);
            entryModel.setPremium(10000);
            entryModel.setSalary(100000);
        }
    }

//    public static void intializeAttribs(Map<String,Object> map) {
//
//        if(! (boolean) map.get(WcConstants.REDEFINE_VALUES)) {
//            dataList = new ArrayList<>();
//        }
//
//        rate = (int) map.get(WcConstants.RATE);
//        insurerName = (String) map.get(WcConstants.INSURER);
//        discount = (int) map.get(WcConstants.DISCOUNT);
//
//    }

    public static void resetForm(ViewGroup viewGroup) {

        for (int index = 0; index < viewGroup.getChildCount(); index++) {
            View view = viewGroup.getChildAt(index);

            if (view instanceof EditText) {
                ((EditText) view).getText().clear();
            }

            if (view instanceof ViewGroup && (((ViewGroup) view).getChildCount() > 0))
                resetForm((ViewGroup) view);
        }
    }

    public static void addData(SingleEntryModel entry) {
        if (dataList == null)
            dataList = new ArrayList<>();
        dataList.add(entry);
        Log.i("tag", "List Size -> " + dataList.size());
    }

    public static List<SingleEntryModel> getDataList() {
        if (dataList == null)
            dataList = new ArrayList<>();
        return dataList;
    }

    public static boolean emptyStringCheck(Editable editable) {
        int length = editable.length();
        return 0 == editable.length() || editable.toString().trim().length() == 0 || "".equals(editable.toString().trim());
    }

    public static void finalizeCalculation() {


        resetValues();

        for (SingleEntryModel entryModel : getDataList()) {
            totalSumInsured = totalSumInsured + entryModel.getSumInsured();
            totalPremium = totalPremium + entryModel.getPremium();
        }

        discountedPremium = (totalPremium * (100 - discount)) / 100;
        serviceTax = discountedPremium * 15 / 100;
        finalPremium = discountedPremium + serviceTax;

    }

    public static void resetValues() {
        totalSumInsured = 0.00;
        discountedPremium = 0.00;
        serviceTax = 0.00;
        totalPremium = 0.00;
        finalPremium = 0.00;
    }


    private static void setFileNameAndPath() {

        fileName = insurerName.toUpperCase() + "-" + Calendar.getInstance().getTimeInMillis() + ".pdf";
        filePath = CommonUtils.FILE_DIRECTORY + "/" + fileName;

        Log.i(TAG, "Generated File :: " + filePath);
    }

}
