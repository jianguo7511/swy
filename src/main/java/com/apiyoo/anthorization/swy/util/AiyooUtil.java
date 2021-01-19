package com.apiyoo.anthorization.swy.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AiyooUtil {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss ");

    public Map<String, String> getAuthInfo() {
         SimpleDateFormat dtf = new SimpleDateFormat("yyyyMMdd");
        String base = "ABCDEFGHJKLMNPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        StringBuffer authCode = new StringBuffer();

        Map<String, String> returnMap = new HashMap<String, String>();
        String nowString = formatter.format(LocalDateTime.now());
        String authtime = formatter2.format(LocalDateTime.now());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 1);
        Date next_year = calendar.getTime();
        String nextYear = dtf.format(next_year);
        sb.append(nowString);
        sb.append(" —— ");
        sb.append(nextYear);

        for (int i = 0; i < 16; ++i) {
            int number = random.nextInt(base.length());
            authCode.append(base.charAt(number));
        }
        returnMap.put("aptimeblock", sb.toString());
        returnMap.put("authcode", authCode.toString());
        returnMap.put("authtime", authtime);
        return returnMap;
    }

    public Long getTime(){
        long time = 0L;
        String nowString = formatter.format(LocalDateTime.now());
        return  Long.parseLong(nowString);
    }


}