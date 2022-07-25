package com.example.demo.controller;

import com.example.demo.dto.fcm.AndroidPushPeriodicNotifications;
import com.example.demo.service.firebase.AndroidPushNotificationsService;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class NotificationController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    AndroidPushNotificationsService androidPushNotificationsService;

    //    @Scheduled(fixedRate = 10000) //10초 > 실제 서버 동작 시 1000*60*60*24 ( 24시간 )
    @GetMapping(value = "/send")
    public @ResponseBody ResponseEntity<String> send() throws JSONException, InterruptedException, IOException {
        String notifications = AndroidPushPeriodicNotifications.PeriodicNotificationJson();

        HttpEntity<String> request = new HttpEntity<>(notifications);

        CompletableFuture<String> pushNotification = androidPushNotificationsService.send(request);
        CompletableFuture.allOf(pushNotification).join();

        try {
            String firebaseResponse = pushNotification.get();
            return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
        } catch (InterruptedException e) {
            logger.debug("got interrupted!");
            throw new InterruptedException();
        } catch (ExecutionException e) {
            logger.debug("execution error!");
        }

        return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
    }
}
