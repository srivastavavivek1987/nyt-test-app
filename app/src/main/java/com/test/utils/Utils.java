package com.test.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.test.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static boolean isNetworkOnline(Context ctx) {
        boolean status = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                status = true;
            } else {
                netInfo = cm.getNetworkInfo(1);
                if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED)
                    status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return status;

    }

    public static String getUrl(String url, String value) {
        return String.format(url, value);
    }

    public static String formatDate(String date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'-5:00'");
            Date newDate = format.parse(date);

            format = new SimpleDateFormat("MMM dd, yyyy");
            return format.format(newDate);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String formatPubDate(String date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
            Date newDate = format.parse(date);

            format = new SimpleDateFormat("MMM dd, yyyy");
            return format.format(newDate);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
