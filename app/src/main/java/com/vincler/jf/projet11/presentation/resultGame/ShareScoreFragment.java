package com.vincler.jf.projet11.presentation.resultGame;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.vincler.jf.projet11.R;

public class ShareScoreFragment extends Fragment {

    private ShareScoreViewModel viewModel;
    ExtendedFloatingActionButton returnToMenuFab;
    ImageView messengerButton;
    ImageView smsButton;
    ImageView mailButton;
    private int bundleScore;
    private String textShareScore;

    // instantiate this fragment
    public static ShareScoreFragment newInstance(int bundleScore) {
        ShareScoreFragment shareScoreFragment = new ShareScoreFragment();
        Bundle args = new Bundle();
        args.putInt("score", bundleScore);
        shareScoreFragment.setArguments(args);

        return shareScoreFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundleScore = getArguments().getInt("score", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_share_score, container, false);
        returnToMenuFab = root.findViewById(R.id.fragment_share_score_return_menu_fab);
        messengerButton = root.findViewById(R.id.activity_share_score_messenger_imageView);
        smsButton = root.findViewById(R.id.activity_share_score_sms_imageView);
        mailButton = root.findViewById(R.id.activity_share_score_mail_imageView);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configureViewModels();
        textShareScore = getTextSharedScore();
        configureSetOnClickListeners();
    }

    private void configureViewModels() {
        viewModel = new ViewModelProvider(this).get(ShareScoreViewModel.class);

        viewModel.phoneNumber.observe(getViewLifecycleOwner(), this::shareBySms
        );
        viewModel.mailAddress.observe(getViewLifecycleOwner(), this::shareByMail);
    }

    private void configureSetOnClickListeners() {
        returnToMenuFab.setOnClickListener(view1 -> {
            getActivity().finish();
        });

        messengerButton.setOnClickListener(view12 -> shareByMessenger());
        smsButton.setOnClickListener(view12 -> phoneAlertDialog());
        mailButton.setOnClickListener(view12 -> mailAlertDialog());
    }

    private String getTextSharedScore() {
        return getResources().getString(R.string.share_your_score_mess1)
                + bundleScore
                + getResources().getString(R.string.share_your_score_mess2);
    }

    private void shareByMail(String addressMail) {
        if (addressMail != null && !addressMail.isEmpty()) {
            Intent mailIntent = new Intent(Intent.ACTION_VIEW);
            Uri data = Uri.parse("mailto:?subject=" + getResources().getString(R.string.share_your_score_title_mail) + "&body=" + textShareScore + "&to=" + addressMail);
            mailIntent.setData(data);
            startActivity(Intent.createChooser(mailIntent, "Send mail..."));
        }
    }

    private void shareBySms(String phoneNumber) {
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phoneNumber));
            intent.putExtra("sms_body", textShareScore);
            startActivity(intent);
        }
    }

    private void phoneAlertDialog() {
        AlertDialog.Builder alertDialogPhoneBuilder = new AlertDialog.Builder(getActivity());
        alertDialogPhoneBuilder.setTitle(R.string.enterthephonenumber);
        final EditText input = new EditText(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(35);
        input.setFilters(fArray);
        alertDialogPhoneBuilder.setView(input);
        alertDialogPhoneBuilder.setPositiveButton("OK", (dialogInterface, i) -> {
            viewModel.phoneNumber.postValue(input.getText().toString());
        });
        alertDialogPhoneBuilder.show();
    }

    private void mailAlertDialog() {
        AlertDialog.Builder alertDialogMailBuilder = new AlertDialog.Builder(getActivity());
        alertDialogMailBuilder.setTitle(R.string.entertheemailaddress);
        final EditText input = new EditText(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(35);
        input.setFilters(fArray);
        alertDialogMailBuilder.setView(input);
        alertDialogMailBuilder.setPositiveButton("OK", (dialogInterface, i) -> {
            viewModel.mailAddress.postValue(input.getText().toString());
        });
        alertDialogMailBuilder.show();
    }

    private void shareByMessenger() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent
                .putExtra(Intent.EXTRA_TEXT,
                        textShareScore);
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.facebook.orca");
        try {
            startActivity(sendIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(), "Please Install Facebook Messenger", Toast.LENGTH_LONG).show();
        }
    }
}