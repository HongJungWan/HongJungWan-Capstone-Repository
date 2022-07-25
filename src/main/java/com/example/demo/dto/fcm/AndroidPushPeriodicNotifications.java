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

        String sampleData[] = {"cXkTrS6MR_m3a9-jKrjqj_:APA91bEbhEgZcUP1HJeyLUCDL6lz3i8ZqwXLwyRM6mmTEHaAu5YF3PC9L0lMFIqHYpCiQzaWhU4j-cszW6x014yHfa4_BzwCPzWh2jqoDDTpwBBFIrP81jYcPN1acUXxOedwYw2oTbYt"};

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
