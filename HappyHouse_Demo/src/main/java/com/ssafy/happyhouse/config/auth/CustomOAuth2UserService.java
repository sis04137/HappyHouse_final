package com.ssafy.happyhouse.config.auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.domain.member.Member;
import com.ssafy.happyhouse.domain.member.MemberRepository;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@Slf4j
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    private MemberRepository userRepository;
    @Autowired
    private HttpSession httpSession;
    
    private Member mem; 

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(oAuth2UserRequest);
        log.info("!!!!getInoAuthUserService}");

        // 현재 진행중인 서비스를 구분하기 위해 문자열로 받음. oAuth2UserRequest.getClientRegistration().getRegistrationId()에 값이 들어있다. {registrationId='naver'} 이런식으로
        String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();

        // OAuth2 로그인 시 키 값이 된다. 구글은 키 값이 "sub"이고, 네이버는 "response"이고, 카카오는 "id"이다. 각각 다르므로 이렇게 따로 변수로 받아서 넣어줘야함.
        String userNameAttributeName = oAuth2UserRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

	// OAuth2 로그인을 통해 가져온 OAuth2User의 attribute를 담아주는 of 메소드.
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
//
        Member user = saveOrUpdate(attributes);
        log.info("USER: {}", user);
        httpSession.setAttribute("user", new SessionUser(user));

        System.out.println(attributes.getAttributes());
        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
                , attributes.getAttributes()
                , attributes.getNameAttributeKey());
    }

    // 혹시 이미 저장된 정보라면, update 처리
    private Member saveOrUpdate(OAuthAttributes attributes) {
        Member user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.updateAuth(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
    
    public Long getMemberId() {
    	return mem.getId();
    }
}