package com.vincler.jf.projet11.presentation.menu;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseUser;
import com.vincler.jf.projet11.R;
import com.vincler.jf.projet11.utils.Utils;

import java.util.Arrays;
import java.util.List;
public class MenuActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123; // For connect by Firebase
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureFirebase();
        // InsertInitialData.createInitialData();     // for insert initial data
        callMenuFragment();
    }

    private void callMenuFragment() {
        Fragment menuFragment = MenuFragment.newInstance();
        Utils.addFragmentInMenuActivity(this, menuFragment);
    }

    private void configureFirebase() {
        //firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTheme(R.style.LoginTheme)
                        //.setLogo(R.drawable.logo)
                        .build(),
                RC_SIGN_IN);
    }
}