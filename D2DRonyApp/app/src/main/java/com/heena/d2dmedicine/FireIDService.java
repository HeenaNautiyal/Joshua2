package com.heena.d2dmedicine;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Heena on 12/20/2016.
 */
public class FireIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        String tkn = FirebaseInstanceId.getInstance().getToken();
        Log.d("Not", "Token [" + tkn + "]");

    }
}
