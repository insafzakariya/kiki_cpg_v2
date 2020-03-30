package com.kiki_cpg.development.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OTPService {
    @Autowired
    MobitelWsClient mobitelWsClient;

    public String genarateOTP(String mobile_no) {
        Random rand = new Random();
        String code = String.format("%04d", rand.nextInt(10000));
        mobitelWsClient.testMobitelConnection();
        mobitelWsClient.logInToMobitelESMS("esmsusr_1u7l ","1ho422g");
        int resultCode=mobitelWsClient.sendSms("KiKi",mobile_no, code);
        mobitelWsClient.logOutFromMobitelESMS();
        if (resultCode==200) {
            return code;
        }else {
            return "error";
        }

    }

    public String sendMsg(String mobile_no,String code) {
        Random rand = new Random();
        MobitelWsClient mobitelWsClient=new MobitelWsClient();
        mobitelWsClient.testMobitelConnection();
        mobitelWsClient.logInToMobitelESMS("esmsusr_1u7l ","1ho422g");
        int resultCode=mobitelWsClient.sendSms("KiKi",mobile_no, code);
        mobitelWsClient.logOutFromMobitelESMS();
        if (resultCode==200) {
            return code;
        }else {
            return "error";
        }

    }
}
