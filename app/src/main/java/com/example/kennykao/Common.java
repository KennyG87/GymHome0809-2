package com.example.kennykao;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class Common {
//    public static String URL = "http://192.168.196.189:8080/Spot_MySQL_Web/";
//    public static String URL = "http://10.0.2.2:8081/GymHomeApp/StudentsServlet";
    public static String URL = "http://10.0.2.2:8081/GymHomeApp/";
    public final static String PREF_FILE = "preference";
    private static final String TAG = "Common";




    // check if the device connect to the network
    public static boolean networkConnected(Activity activity) {
        ConnectivityManager conManager =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static void showToast(Context context, int messageResId) {
        Toast.makeText(context, messageResId, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


}
