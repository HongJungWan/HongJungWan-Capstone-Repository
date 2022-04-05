package com.example.demo.oauth.provider;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class NaverUserInfo implements OAuth2UserInfo{

    private final Map<String,Object> attributes;

    @Override
    public String getPhonnumber() {
        return (String) attributes.get("phonnumber");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }
}
