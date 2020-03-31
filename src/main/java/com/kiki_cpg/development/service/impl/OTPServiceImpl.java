package com.kiki_cpg.development.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kiki_cpg.development.service.MobitelWsClientService;
import com.kiki_cpg.development.service.OTPService;

import java.util.Random;

@Service
public class OTPServiceImpl implements OTPService {
    @Autowired
    MobitelWsClientService mobitelWsClient;

    @Override
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

    @Override
    public String sendMsg(String mobile_no,String code) {
        //Random rand = new Random();
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
