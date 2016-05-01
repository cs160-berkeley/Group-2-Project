package com.example.group2.pillpal;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private View view;

    private OnFragmentInteractionListener mListener;

    public SettingsFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //terrible but let's just use local vars to set everything at first
        this.view = inflater.inflate(R.layout.settings_fragment, container, false);


        ImageButton editShippingInfoBtn = (ImageButton) this.view.findViewById(R.id.editShippingAddressBtn);
        editShippingInfoBtn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        launchViewToEditShippingAddress();
                    }
                }
        );
        ImageButton editBillingAddressBtn = (ImageButton) this.view.findViewById(R.id.editBillingAddressBtn);
        editBillingAddressBtn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        launchViewToEditBillingAddress();
                    }
                }
        );

        ImageButton editCreditCardInfo = (ImageButton) this.view.findViewById(R.id.editCreditCardInfoBtn);
        editCreditCardInfo.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        launchViewToEditCreditCardInfo();
                    }
                }
        );

        updateView();
        return this.view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    // update view method
    public void updateView() {
        TextView usernameTextView = (TextView) this.view.findViewById(R.id.userNameTextView);
        TextView passwordTextView = (TextView) this.view.findViewById(R.id.passwordTextView);
        TextView shippingAddressLine1TextView = (TextView) this.view.findViewById(R.id.shippingAddressLine1);
        TextView shippingAddressLine2TextView = (TextView) this.view.findViewById(R.id.shippingAddressLine2);
        TextView billingAddressLine1TextView = (TextView) this.view.findViewById(R.id.billingAddressLine1);
        TextView billingAddressLine2TextView = (TextView) this.view.findViewById(R.id.billingAddressLine2);
        TextView creditCardTypeTextView = (TextView) this.view.findViewById(R.id.cardType);
        TextView cardNumberTextView = (TextView) this.view.findViewById(R.id.cardNumber);

        usernameTextView.setText(ProfileInfo.sharedInstance.username);
        passwordTextView.setText(ProfileInfo.sharedInstance.password);
        shippingAddressLine1TextView.setText(ProfileInfo.sharedInstance.shippingAddressStreet);
        shippingAddressLine2TextView.setText(ProfileInfo.sharedInstance.shippingAddressCityState);
        billingAddressLine1TextView.setText(ProfileInfo.sharedInstance.billingAddressStreet);
        billingAddressLine2TextView.setText(ProfileInfo.sharedInstance.billingAddressCityState);
        creditCardTypeTextView.setText(ProfileInfo.sharedInstance.cardType);
        cardNumberTextView.setText(ProfileInfo.sharedInstance.cardNo);
    }

    // Button Methods
    public void launchViewToEditShippingAddress( ) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());


        View funView = inflater.inflate(R.layout.alert_dialog_set_address, null);
        final EditText streetAddrEditText = (EditText) funView.findViewById(R.id.streetAddressEditText);
        final EditText cityStateEditText = (EditText) funView.findViewById(R.id.cityStateAddressEditText);

        builder.setView(funView);
        final AlertDialog dialog = builder.create();

        Button saveButton = (Button) funView.findViewById(R.id.confirmEditButton);
        saveButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        ProfileInfo.sharedInstance.shippingAddressStreet = streetAddrEditText.getText().toString();
                        ProfileInfo.sharedInstance.shippingAddressCityState = cityStateEditText.getText().toString();
                        updateView();
                        dialog.dismiss();
                    }
                }
        );


        Button cancelButton = (Button) funView.findViewById(R.id.cancelEditButton);
        cancelButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                }
        );

        dialog.show();
    }

    public void launchViewToEditBillingAddress( ) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());


        View funView = inflater.inflate(R.layout.alert_dialog_set_address, null);
        final EditText streetAddrEditText = (EditText) funView.findViewById(R.id.streetAddressEditText);
        final EditText cityStateEditText = (EditText) funView.findViewById(R.id.cityStateAddressEditText);

        builder.setView(funView);
        final AlertDialog dialog = builder.create();

        Button saveButton = (Button) funView.findViewById(R.id.confirmEditButton);
        saveButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        ProfileInfo.sharedInstance.billingAddressStreet = streetAddrEditText.getText().toString();
                        ProfileInfo.sharedInstance.billingAddressCityState = cityStateEditText.getText().toString();
                        updateView();
                        dialog.dismiss();
                    }
                }
        );


        Button cancelButton = (Button) funView.findViewById(R.id.cancelEditButton);
        cancelButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                }
        );

        dialog.show();
    }

    public void launchViewToEditCreditCardInfo( ) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        View funView = inflater.inflate(R.layout.alert_dialog_set_credit_card, null);
        final EditText cardNumberTextEdit = (EditText) funView.findViewById(R.id.cardNumberTextEdit);

        builder.setView(funView);
        final AlertDialog dialog = builder.create();

        Button saveButton = (Button) funView.findViewById(R.id.confirmEditButton);
        saveButton.setOnClickListener(
                new View.OnClickListener() {

                    public void onClick(View v) {
                        ProfileInfo.sharedInstance.cardNo = cardNumberTextEdit.getText().toString();
                        updateView();
                        dialog.dismiss();
                    }
                }
        );

        Button cancelButton = (Button) funView.findViewById(R.id.cancelEditButton);
        cancelButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                }
        );

        dialog.show();
    }
}
