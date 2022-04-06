package com.example.demo.oauth.provider;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class KakaoUserInfo implements OAuth2UserInfo{

    private final Map<String,Object> idAttributes;
    private final Map<String,Object> normalAttributes;


    @Override
    public String getPhonNumber() {
        if((String)normalAttributes.get("phonNumber")==null){
            return idAttributes.get("id")+"@kakao.com";
        }
        return (String)normalAttributes.get("phonNumber");
    }

    @Override
    public String getName() {
        return (String)((Map)normalAttributes.get("profile")).get("nickname");
    }
}
