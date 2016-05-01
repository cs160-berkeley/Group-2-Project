package com.example.group2.pillpal;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        View _view = inflater.inflate(R.layout.settings_fragment, container, false);
        updateView(_view);
        return _view;
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
    public void updateView(View _view) {
        TextView usernameTextView = (TextView) _view.findViewById(R.id.userNameTextView);
        TextView passwordTextView = (TextView) _view.findViewById(R.id.passwordTextView);
        TextView shippingAddressLine1TextView = (TextView) _view.findViewById(R.id.shippingAddressLine1);
        TextView shippingAddressLine2TextView = (TextView) _view.findViewById(R.id.shippingAddressLine2);
        TextView billingAddressLine1TextView = (TextView) _view.findViewById(R.id.billingAddressLine1);
        TextView billingAddressLine2TextView = (TextView) _view.findViewById(R.id.billingAddressLine2);
        TextView creditCardTypeTextView = (TextView) _view.findViewById(R.id.cardType);
        TextView cardNumberTextView = (TextView) _view.findViewById(R.id.cardNumber);

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
    public void launchViewToEditShippingAddress(View view) {

    }

    public void launchViewToEditBillingAddress(View view) {

    }

    public void launchViewToEditCardInfo(View view) {

    }
}
