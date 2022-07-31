package com.example.demo.dto.fcm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AndroidPushPeriodicNotifications {
    public static String PeriodicNotificationJson() throws JSONException {
        LocalDate localDate = LocalDate.now();

        String sampleData[] = {"dsIROp7dQqaVFJ62zMwsat:APA91bG6y5VDXBnpmjTNGlTHLzzZSQfv62Ar27-c-ZCAKRd0e3bL2qKF13uEzqdsxyPF3fuXGdglFe03R-mNwGUXa_bAeN_xiHOLvuAQjdoaUz4ZOfLLfgL49j8KJJlKzH1DOhInc7t0"};

        JSONObject body = new JSONObject();

        List<String> tokenlist = new ArrayList<>();

        for (int i = 0; i < sampleData.length; i++) {
            tokenlist.add(sampleData[i]);
        }

        JSONArray array = new JSONArray();

        for (int i = 0; i < tokenlist.size(); i++) {
            array.put(tokenlist.get(i));
        }

        body.put("registration_ids", array);

        JSONObject notification = new JSONObject();
        notification.put("title", "TEST");
        notification.put("body", "App Push");

        body.put("notification", notification);

        System.out.println(body.toString());

        return body.toString();
    }
}
