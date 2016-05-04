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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;

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
    private HashMap<String, String> mBillingAddress;
    private HashMap<String, String> mShippingAddress;

    private OnFragmentInteractionListener mListener;
    private UserInstance currentUser;

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
        currentUser = UserInstance.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //terrible but let's just use local vars to set everything at first
        this.view = inflater.inflate(R.layout.settings_fragment, container, false);

        final LinearLayout set_billing_address = (LinearLayout) this.view.findViewById(R.id.set_billing_address);
        final LinearLayout set_shipping_address = (LinearLayout) this.view.findViewById(R.id.set_shipping_address);
        final LinearLayout set_credit_card = (LinearLayout) this.view.findViewById(R.id.set_credit_card);
        set_billing_address.setVisibility(View.GONE);
        set_shipping_address.setVisibility(View.GONE);
        set_credit_card.setVisibility(View.GONE);

        ImageButton editShippingInfoBtn = (ImageButton) this.view.findViewById(R.id.editShippingAddressBtn);
        editShippingInfoBtn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        set_shipping_address.setVisibility(View.VISIBLE);
                        launchViewToEditShippingAddress();
                    }
                }
        );
        ImageButton editBillingAddressBtn = (ImageButton) this.view.findViewById(R.id.editBillingAddressBtn);
        editBillingAddressBtn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        set_billing_address.setVisibility(View.VISIBLE);
                        launchViewToEditBillingAddress();
                    }
                }
        );

        ImageButton editCreditCardInfo = (ImageButton) this.view.findViewById(R.id.editCreditCardInfoBtn);
        editCreditCardInfo.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        set_credit_card.setVisibility(View.VISIBLE);
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

        usernameTextView.setText(currentUser.email);
        passwordTextView.setText("********");

        mShippingAddress = currentUser.shipAdd;
        mBillingAddress = currentUser.billAdd;

        shippingAddressLine1TextView.setText(mShippingAddress.get("street_name"));
        shippingAddressLine2TextView.setText(mShippingAddress.get("city") + " " + mShippingAddress.get("state") + ", " + mShippingAddress.get("zip"));
        billingAddressLine1TextView.setText(mBillingAddress.get("street_name"));
        billingAddressLine2TextView.setText(mBillingAddress.get("city") + " " + mBillingAddress.get("state") + ", " + mBillingAddress.get("zip"));
        creditCardTypeTextView.setText(currentUser.cardType);

        String string_card_number = currentUser.cardNumber.toString();
        cardNumberTextView.setText("**** **** **** " + string_card_number);

        EditText BillingStreetAddrEditText = (EditText) view.findViewById(R.id.billing_street_name);
        EditText BillingCityEditText = (EditText) view.findViewById(R.id.billing_city);
        EditText BillingStateEditText = (EditText) view.findViewById(R.id.billing_state);
        EditText BillingZipEditText = (EditText) view.findViewById(R.id.billing_zip);
        BillingStreetAddrEditText.setText(mBillingAddress.get("street_name"));
        BillingCityEditText.setText(mBillingAddress.get("city"));
        BillingStateEditText.setText(mBillingAddress.get("state"));
        BillingZipEditText.setText(mBillingAddress.get("zip"));

        EditText ShippingStreetAddrEditText = (EditText) view.findViewById(R.id.shipping_street_name);
        EditText ShippingCityEditText = (EditText) view.findViewById(R.id.shipping_city);
        EditText ShippingStateEditText = (EditText) view.findViewById(R.id.shipping_state);
        EditText ShippingZipEditText = (EditText) view.findViewById(R.id.shipping_zip);
        ShippingStreetAddrEditText.setText(mShippingAddress.get("street_name"));
        ShippingCityEditText.setText(mShippingAddress.get("city"));
        ShippingStateEditText.setText(mShippingAddress.get("state"));
        ShippingZipEditText.setText(mShippingAddress.get("zip"));

        ListView listView = (ListView) view.findViewById(R.id.refill_history);
        RefillHistoryArrayAdapter adapter = new RefillHistoryArrayAdapter(getActivity(), currentUser.refillHistory);
        listView.setAdapter(adapter);
    }

    // Button Methods
    public void launchViewToEditShippingAddress( ) {
        View view = getView();
        final EditText streetAddrEditText = (EditText) view.findViewById(R.id.shipping_street_name);
        final EditText cityStateEditText = (EditText) view.findViewById(R.id.shipping_city);
        final EditText stateEditText = (EditText) view.findViewById(R.id.shipping_state);
        final EditText zipEditText = (EditText) view.findViewById(R.id.shipping_zip);
        final LinearLayout set_shipping_address = (LinearLayout) this.view.findViewById(R.id.set_shipping_address);

        Button saveButton = (Button) view.findViewById(R.id.confirm_edit_shipping);
        saveButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        mShippingAddress.put("street_name", streetAddrEditText.getText().toString());
                        mShippingAddress.put("city", cityStateEditText.getText().toString());
                        mShippingAddress.put("state", stateEditText.getText().toString());
                        mShippingAddress.put("zip", zipEditText.getText().toString());
                        currentUser.shipAdd = mShippingAddress;
                        updateView();
                        set_shipping_address.setVisibility(View.GONE);
                    }
                }
        );


        ImageButton cancelButton = (ImageButton) view.findViewById(R.id.cancel_edit_shipping);
        cancelButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        set_shipping_address.setVisibility(View.GONE);
                    }
                }
        );
    }

    public void launchViewToEditBillingAddress( ) {

        View view = getView();
        final EditText streetAddrEditText = (EditText) view.findViewById(R.id.billing_street_name);
        final EditText cityStateEditText = (EditText) view.findViewById(R.id.billing_city);
        final EditText stateEditText = (EditText) view.findViewById(R.id.billing_state);
        final EditText zipEditText = (EditText) view.findViewById(R.id.billing_zip);
        final LinearLayout set_billing_address = (LinearLayout) this.view.findViewById(R.id.set_billing_address);

        Button saveButton = (Button) view.findViewById(R.id.confirm_edit_billing);
        saveButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        mBillingAddress.put("street_name", streetAddrEditText.getText().toString());
                        mBillingAddress.put("city", cityStateEditText.getText().toString());
                        mBillingAddress.put("state", stateEditText.getText().toString());
                        mBillingAddress.put("zip", zipEditText.getText().toString());
                        currentUser.shipAdd = mShippingAddress;
                        updateView();
                        set_billing_address.setVisibility(View.GONE);
                    }
                }
        );

        ImageButton cancelButton = (ImageButton) view.findViewById(R.id.cancel_edit_billing);
        cancelButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        set_billing_address.setVisibility(View.GONE);
                    }
                }
        );

    }

    public void launchViewToEditCreditCardInfo( ) {

        View view = getView();
        final EditText cardNumberTextEdit = (EditText) view.findViewById(R.id.cardNumberTextEdit);
        final LinearLayout set_credit_card = (LinearLayout) this.view.findViewById(R.id.set_credit_card);
        Button saveButton = (Button) view.findViewById(R.id.confirmEditButton);
        saveButton.setOnClickListener(
                new View.OnClickListener() {

                    public void onClick(View v) {
                        String input = cardNumberTextEdit.getText().toString();
                        if (input.length() >= 4) {
                            String card_number_partial = input.substring(input.length() - 4, input.length());
                            currentUser.cardNumber = Integer.parseInt(card_number_partial);
                            updateView();
                        }
                        set_credit_card.setVisibility(View.GONE);
                    }
                }
        );

        ImageButton cancelButton = (ImageButton) view.findViewById(R.id.cancelEditButton);
        cancelButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        set_credit_card.setVisibility(View.GONE);
                    }
                }
        );
    }
}
