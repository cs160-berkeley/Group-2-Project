package com.example.group2.pillpal;

/**
 * Created by namhyun on 4/30/16.
 */

public class ProfileInfo {

    // these would be DB calls
    public String password = "password1";
    public String username = "jane_smith@gmail.com";
    public String shippingAddressStreet = "2333 Durant Ave";
    public String shippingAddressCityState = "Berkeley, CA, 94704";
    public String billingAddressStreet = "2333 Durant Ave";
    public String billingAddressCityState = "Berkeley, CA, 94704";
    public String cardType = "Visa";
    public String cardNo = "4344-4344-3424-4324";

    //  singleton for holding/modifying info
    public static ProfileInfo sharedInstance = new ProfileInfo();


}
