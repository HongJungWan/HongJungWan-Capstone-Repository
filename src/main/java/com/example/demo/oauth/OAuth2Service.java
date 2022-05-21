package com.example.demo.oauth;

import com.example.demo.domain.entity.User;
import com.example.demo.domain.value.Role;
import com.example.demo.dto.security.PrincipalDetails;
import com.example.demo.oauth.provider.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2Service extends DefaultOAuth2UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User=super.loadUser(userRequest);
        OAuth2UserInfo oAuth2UserInfo=null;


        User buildUser = User.createUser()
                .phonNumber(oAuth2UserInfo.getPhonNumber())
                .password(bCryptPasswordEncoder.encode("hong"))
                .name(oAuth2UserInfo.getName())
                .role(Role.ROLE_USER)
                .build();
        return new PrincipalDetails(buildUser,oAuth2User.getAttributes());
    }
}
