package com.ssafy.happyhouse.service.member;

import java.util.List;

import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ssafy.happyhouse.domain.member.Member;
import com.ssafy.happyhouse.domain.member.MemberMapper;
import com.ssafy.happyhouse.domain.member.MemberRepository;
import com.ssafy.happyhouse.dto.member.MemberDtoMapper;
import com.ssafy.happyhouse.dto.member.MemberLoginRequestDto;
import com.ssafy.happyhouse.dto.member.MemberResponseDto;
import com.ssafy.happyhouse.dto.member.MemberSaveRequestDto;
import com.ssafy.happyhouse.dto.member.MemberUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor // Autowired없이 생성자로 빈 주입
@Service
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;
	private final MemberMapper memberMapper;
	
	/*for pagination*/
	int perPage = 10; // 페이지당 member갯수
	
	/*for mail*/
	@Autowired
	private JavaMailSender mailSender;
	@Value("${spring.mail.username}")
	String sendFrom;
		
	@Autowired 
	Environment env;

	// 페이징처리된 Member List 반환
	@Transactional
	@Override
	public Page<MemberResponseDto> findPaging(int pageNum) {
		PageHelper.startPage(pageNum, perPage);
		Page<MemberResponseDto> page = memberMapper.getPagingList();
		return page;
	}

	//전체리스트(페이징없음)
	@Override
	public List<Member> findAll(){
		return memberRepository.findAll();
	}
	
	// id로 member 찾기
	@Transactional
	@Override
	public Member findById(Long id) {
		Member member = memberRepository.findById(id).orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원입니다."));
		return member;
	}

	//회원가입
	@Transactional
	@Override
	public Long saveMember(MemberSaveRequestDto requestDto) {
		Member member = memberRepository.loginEmail(requestDto.getEmail());
		if(member != null)	throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
		Long id = memberRepository.save(MemberDtoMapper.INSTANCE.toEntity(requestDto)).getId();
		
		
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
				message.setText(content, true); //html 형식 사용
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

	//해당 이메일의 비밀번호를 재설정하고 이메일로 보내준다
	@Transactional
	@Override
	public Long sendNewPass(String requestEmail) {
		
		Member member = memberRepository.loginEmail(requestEmail);
		if(member != null)	throw new IllegalArgumentException("해당하는 이메일이 없습니다.");
		
		String randomPass = "randomPass11";
		member.setPassword(randomPass);
		
		
		String title = "[HappyHouse] 비밀번호 변경 안내";
		String content = "  <div>\r\n" + 
				"    <h1>" +"비밀번호 변경 안내</h1>\r\n" + 
				"    <p>비밀번호 변경이 요청되었습니다. 아래의 비밀번호로 재로그인 하신 후 새로운 비밀번호로 변경해 주세요.</p>\r\n" + 
				"    <p>"+ randomPass + "</p>\r\n" + 
				"    <p>혹시 본인이 요청하지 않았다면 접속해서 비밀번호를 변경해 주세요. 자동 로그인 등으로 이메일 정보가 남아있을 수 있습니다.</p>\r\n" + 
				"    <p>서울 12반 혜란이랑 구아 씀</p>\r\n" + 
				"  </div>";
		
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true,"UTF-8");

				message.setTo(requestEmail);
				message.setFrom(sendFrom);	//env.getProperty("spring.mail.username")
				message.setSubject(title);
				message.setText(content, true); //html 형식 사용
			}
		};
			
		
		try{
			mailSender.send(preparator);
			log.info("메일 성공");
		} catch (MailException e){
			return member.getId();
		}
		
		
		return member.getId();
	}
	
	@Transactional
	@Override
	public Long getLast() {
		return memberRepository.findFirstByOrderByIdDesc().getId();
	}
}
