package com.example.demo.oauth.provider;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class KakaoUserInfo implements OAuth2UserInfo{

    private final Map<String,Object> idAttributes;
    private final Map<String,Object> normalAttributes;


    @Override
    public String getPhonnumber() {
        if((String)normalAttributes.get("phonnumber")==null){
            return idAttributes.get("id")+"@kakao.com";
        }
        return (String)normalAttributes.get("phonnumber");
    }

    @Override
    public String getName() {
        return (String)((Map)normalAttributes.get("profile")).get("nickname");
    }
}
