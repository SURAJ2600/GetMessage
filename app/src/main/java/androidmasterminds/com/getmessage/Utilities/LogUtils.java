package androidmasterminds.com.getmessage.Utilities;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Suraj on 14/9/18.
 */

public class LogUtils {


        private static LogUtils myObj;

        /**
         * Create private constructor
         */
    private LogUtils() {

        }

        /**
         * Create a static method to get instance.
         */
    public static LogUtils getInstance() {
        if (myObj == null) {
            myObj = new LogUtils();
        }
        return myObj;
    }

    public static void makeLogD(String TAG, String message) {
        Log.d(TAG, "" + message);
    }

    public static void makeLogE(String TAG, String message) {
        Log.e(TAG, "" + message);
    }









    public static void showToast(Activity activity, String message) {
        Toast.makeText(activity, "" + message, Toast.LENGTH_SHORT).show();
    }

}
