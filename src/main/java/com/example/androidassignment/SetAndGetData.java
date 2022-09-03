package com.example.androidassignment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseUser;

public class SetAndGetData {
    /**
     * This Class Is Singleton Class Used For Setting & Getting Values Throughout The Project
     */
    private static SetAndGetData setAndGetData = null;
    private BottomNavigationView bottomNavigationView;
    private boolean camWebView;

    public FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }

    public void setFirebaseUser(FirebaseUser firebaseUser) {
        this.firebaseUser = firebaseUser;
    }

    private FirebaseUser firebaseUser;

    public BottomNavigationView getBottomNavigationView() {
        return bottomNavigationView;
    }

    public void setBottomNavigationView(BottomNavigationView bottomNavigationView) {
        this.bottomNavigationView = bottomNavigationView;
    }

    public boolean isCamWebView() {
        return camWebView;
    }

    public void setCamWebView(boolean camWebView) {
        this.camWebView = camWebView;
    }

    public SetAndGetData() {
    }

    public static SetAndGetData getInstance() {
        if (setAndGetData == null)
            setAndGetData = new SetAndGetData();

        return setAndGetData;
    }
}
