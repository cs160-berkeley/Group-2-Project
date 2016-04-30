package com.example.group2.pillpal;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOverlay;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RefillsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RefillsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RefillsFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static Map<String, String> address;
    static
    {
        address = new HashMap<String, String>();
        address.put("street_name", "2000 Bancroft Way");
        address.put("city", "Berkeley");
        address.put("state", "California");
        address.put("zip", "94704");
    }
    private static Map<String, String> current_pill;
    static
    {
        current_pill = new HashMap<String, String>();
        current_pill.put("name", "Aranelle");
        current_pill.put("dosage", "Once every day at noon.");
    }
    private static ArrayList<HashMap<String, String>> refill_history;
    static
    {
        refill_history = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> first_refill = new HashMap<String, String>();
        HashMap<String, String> second_refill = new HashMap<String, String>();
        first_refill.put("name", "Aranelle");
        first_refill.put("date", "March 10, 2016");
        second_refill.put("name", "Aranelle");
        second_refill.put("date", "April 8, 2016");
        refill_history.add(first_refill);
        refill_history.add(second_refill);
    }

    private static Boolean refill_requested = false;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RefillsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RefillsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RefillsFragment newInstance(String param1, String param2) {
        RefillsFragment fragment = new RefillsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.refills_fragment, container, false);

        TextView address_line_one = (TextView) v.findViewById(R.id.addressLineOne);
        TextView address_line_two = (TextView) v.findViewById(R.id.addressLineTwo);
        TextView pill_name = (TextView) v.findViewById(R.id.pillName);
        TextView pill_dosage = (TextView) v.findViewById(R.id.pillDosage);
        TextView last_refill = (TextView) v.findViewById(R.id.lastRefillInfo);

        address_line_one.setText(address.get("street_name"));
        address_line_two.setText(address.get("city_name") + " " + address.get("state") + ", " + address.get("zip"));

        pill_name.setText(current_pill.get("name"));
        pill_dosage.setText(current_pill.get("dosage"));

        HashMap<String, String> lastRefill = refill_history.get(refill_history.size() - 1);
        last_refill.setText(lastRefill.get("name") + " - " + lastRefill.get("date"));

        Button refillsButton = (Button) v.findViewById(R.id.refills_button);
        Button confirmationButton = (Button) v.findViewById(R.id.confirmation_button);
        Button statusButton = (Button) v.findViewById(R.id.status_button);

        refillsButton.setOnClickListener(this);
        confirmationButton.setOnClickListener(this);
        statusButton.setOnClickListener(this);

        LinearLayout refill_confirmation = (LinearLayout) v.findViewById(R.id.refill_confirmation);
        LinearLayout refill_status = (LinearLayout) v.findViewById(R.id.refill_status);


        refill_confirmation.setVisibility(View.GONE);
        refill_status.setVisibility(View.GONE);



        if (!refill_requested) {
            refillsButton.setText("Request Refills");
        } else {
            refillsButton.setText("View Refill Status");
        }
        return v;
    }

    @Override
    public void onClick(View v) {
        // implements your things
//        ImageView imageView = (ImageView) getView().findViewById(R.id.foo);
//        View v = inflater.inflate(R.layout.refills_fragment, container, false);
        LinearLayout refill_status = (LinearLayout) getView().findViewById(R.id.refill_status);
        LinearLayout refill_confirmation = (LinearLayout) getView().findViewById(R.id.refill_confirmation);
        int button_id = v.getId();
        switch (button_id) {
            case R.id.refills_button:
                if (refill_requested) {
                    refill_status.setVisibility(View.VISIBLE);
                } else {
                    refill_confirmation.setVisibility(View.VISIBLE);
                    refill_requested = true;
                }
                break;
            case R.id.confirmation_button:
                refill_confirmation.setVisibility(View.INVISIBLE);
                break;
            case R.id.status_button:
                refill_status.setVisibility(View.INVISIBLE);
                break;
        }

//        FragmentTransaction tx = getFragmentManager().beginTransaction();
//        RefillStatusFragment refillStatusFragment = new RefillStatusFragment();
//        Bundle args = new Bundle();
//        args.putBoolean("refill_requested", refill_requested);
//        refillStatusFragment.setArguments(args);
//        tx.replace(R.id.main, refillStatusFragment);
//        tx.addToBackStack(null);
//        tx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        tx.commit();
//
//        Intent sendintent = new Intent(v.getContext(), PhoneToWatchService.class);
//        sendintent.putExtra("DATA", "refill");
//        v.getContext().startService(sendintent);
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
}
