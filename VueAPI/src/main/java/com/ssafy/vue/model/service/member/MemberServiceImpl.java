package com.ssafy.vue.model.service.member;

import java.util.List;

import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.ssafy.vue.dto.member.MemberLoginRequestDto;
import com.ssafy.vue.dto.member.MemberSaveRequestDto;
import com.ssafy.vue.dto.member.MemberUpdateRequestDto;
import com.ssafy.vue.model.member.Member;
import com.ssafy.vue.model.member.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor // Autowired없이 생성자로 빈 주입
@Service
public class MemberServiceImpl implements MemberService {
	// bean
//	private final MemberRepository memberRepository;
//	private final MemberMapper memberMapper;

	private final MemberRepository memberRepository;
	
	
	@Autowired
	private JavaMailSender mailSender;
	@Value("${spring.mail.username}")
	String sendFrom;
		
	@Autowired 
	Environment env;
	
	int perPage = 10; // 페이지당 member갯수


	//전체리스트(페이징없음)
	@Override
	public List<Member> findAll(){
		return memberRepository.findAll();
	}
	
	// id로 member 찾기
	@Transactional
	@Override
	public Member findById(Long id) {
		Member member = memberRepository.findById(id).orElseThrow(()->new IllegalArgumentException("존재하지 않는 사용자입니다. "));
		return member;
	}

	//회원가입
	@Transactional
	@Override
	public Long saveMember(MemberSaveRequestDto requestDto) {
		Long id = memberRepository.save(requestDto.toEntity()).getId();
		
		String title = "[HappyHouse] 가입 안내";
		String content = "  <div>\r\n" + 
				"    <h1>"+ requestDto.getName() +"님의 가입을 환영합니다!</h1>\r\n" + 
				"    <p>happyhouse는 ssafy의 vue 관통프로젝트를 위해 제작되었습니다.</p>\r\n" + 
				"    <p>비밀번호 변경/분실 사항을 포함한 기타 문의사항은 해당 메일로 답신해 주세요.</p>\r\n" + 
				"    <p>서울 12반 혜란이랑 구아 씀</p>\r\n" + 
				"  </div>";
		
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true,"UTF-8");

				message.setTo(requestDto.getEmail());
				message.setFrom(sendFrom);	//env.getProperty("spring.mail.username")
				message.setSubject(title);
				message.setText(content, true); //ture : html 형식 사용
					
//Mail에 img 삽입
//				ClassPathResource resource = new ClassPathResource("img 주소/img 이름.png");
//				message.addInline("img", resource.getFile());
			}
		};
			
		
		try{
			mailSender.send(preparator);
			log.info("메일 성공");
		} catch (MailException e){
			return id;
		}
		
		return id;
	}
	
	//update info : member가 호출하는 함수
	@Transactional
	@Override
	public Long updateUser(Long id, MemberUpdateRequestDto requestDto) {
		Member member = memberRepository.findById(id).orElseThrow(()->new IllegalArgumentException("존재하지 않는 사용자입니다. "));
		if(member.getPassword().equals(requestDto.getPassword())) {
			log.debug("PreEmail: {}", member.getEmail());
			member.updateInfo(requestDto);	
			log.debug("PostEmail: {}", member.getEmail());
		}
		else
			throw new IllegalArgumentException("비밀번호가 잘못되었습니다");
		memberRepository.save(member);
		return id;
	}
	
	@Transactional
	@Override
	public Long deleteUser(Long id) {
		memberRepository.deleteById(id);
		return id;
	}
	
	@Transactional
	@Override
	public Member login(MemberLoginRequestDto requestDto) {
		Member member = memberRepository.findByEmail(requestDto.getEmail()).orElseThrow(()->new IllegalArgumentException("존재하지 않는 사용자입니다. "));
		if(member.getPassword().equals(requestDto.getPassword())) {
			return member;
		}
		else {
			throw new IllegalArgumentException("비밀번호가 잘못되었습니다.");
		}
	}

}
