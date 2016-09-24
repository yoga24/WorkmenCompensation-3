package com.cyorg.uiic.workmencompensationrainbow3.utils;

import android.os.Environment;

public class WcConstants {

    public static final String FILE_DIRECTORY = Environment.getExternalStorageDirectory().getAbsolutePath() + "/cyorg/WorkmenCompensation";
    public static final String SHARED_PREFRENCES_FILE_NAME = "WC_SHARED_PREFERNCES";
    private static String fileName;
    private static String filePath;
    private static boolean changesMadeAfterFileSave = false;
    private static boolean redefineValues;

    public static String getFileName() {
        return fileName;
    }

    public static void setFileName(String fileName) {
        WcConstants.fileName = fileName;
    }

    public static String getFilePath() {
        return filePath;
    }

    public static void setFilePath(String filePath) {
        WcConstants.filePath = filePath;
    }

    public static boolean isChangesMadeAfterFileSave() {
        return changesMadeAfterFileSave;
    }

    public static void setChangesMadeAfterFileSave(boolean changesMadeAfterFileSave) {
        WcConstants.changesMadeAfterFileSave = changesMadeAfterFileSave;
    }

    public static boolean isRedefineValues() {
        return redefineValues;
    }

    public static void setRedefineValues(boolean redefineValues) {
        WcConstants.redefineValues = redefineValues;
    }

}
