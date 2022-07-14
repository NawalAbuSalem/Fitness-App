package com.nns.youcan.Views.signup;

public interface SignUpInterface {
    public void getMobileNumber(String mobileNumber);
    public void finishVerification(String code);
    public void resendMobileNumber();
    public void startAskPersonalInformation();
}
