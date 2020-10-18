package madrsty.demo.adminmad.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import com.google.firebase.auth.FirebaseAuth;

import madrsty.demo.adminmad.Model.RequestModel;
import madrsty.demo.adminmad.Model.StudentModel;

public class common {
    public static final String STUDENT_REQUESTS_KEY = "StudentRequest";
    public static final String STUDENT_KEY = "Students" ;
    public static final String ADMIN_KEY = "Admins" ;
    public static RequestModel selectedRequest;
    public static StudentModel currentStudent;


    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();

            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;
                }
            }
        }
        return false;
    }

    public static String getuID() {
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();


        return id;
    }

}
