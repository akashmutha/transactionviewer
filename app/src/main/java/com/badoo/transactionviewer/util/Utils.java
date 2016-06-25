package com.badoo.transactionviewer.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by mutha on 25/06/16.
 */
public class Utils {

    /**
     * Read file and put in to a jsonArray
     */
    public static String readFileDump(BufferedReader inputReader) {
        StringBuffer stringBuffer = new StringBuffer();
        String inputString;
        try {
            //Reading data line by line and storing it into the stringbuffer
            while ((inputString = inputReader.readLine()) != null) {
                stringBuffer.append(inputString + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }
}
