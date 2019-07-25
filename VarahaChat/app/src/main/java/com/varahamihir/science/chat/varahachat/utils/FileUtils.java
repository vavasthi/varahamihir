package com.varahamihir.science.chat.varahachat.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;

public class FileUtils {
    private static String TEMPORARY_FILES_DIRECTORY = "tmp";
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
    public static File getPrivateTmpStorageDir(Context context) {

        File appRootDir = context.getExternalFilesDir(null);
        File tmpDir = context.getExternalFilesDir(TEMPORARY_FILES_DIRECTORY);
        File file = tmpDir;
        if (!tmpDir.mkdirs()) {
            if (!appRootDir.mkdirs()) {
                appRootDir = context.getFilesDir();
                tmpDir = new File(appRootDir, TEMPORARY_FILES_DIRECTORY);
                if (!tmpDir.mkdirs()) {
                    file = appRootDir;
                }
                else {
                    file = tmpDir;
                }
            }
            else {
                file = appRootDir;
            }
        }
        return file;
    }


}
