package com.ssafy.happyhouse.domain.member;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ssafy.happyhouse.domain.member.Member;
import com.ssafy.happyhouse.domain.member.MemberMapper;
import com.ssafy.happyhouse.domain.member.MemberRepository;
import com.ssafy.happyhouse.dto.member.MemberDtoMapper;
import com.ssafy.happyhouse.dto.member.MemberSaveRequestDto;

//@ExtendWith(SpringExtension.class)
//@AutoConfigureTestDatabase(replace = Replace.NONE)
//@DataJpaTest
@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {
	@Autowired
	MemberRepository accountRepository;	//JPA
	@Autowired
	MemberMapper accountMapper;			//Mybatis
	
//	@After
//    public void cleanUp(){
//        accountRepository.deleteAll();
//    }
	@Transactional
	@Test
	public void JpaSave() {
		Member account = Member.builder()
				.user_name("test")
				.user_pass("test")
				.user_email("test")
				.user_addr("test")
				.user_tel("test")
				.build();
		accountRepository.save(account);
		List<Member> list = accountRepository.findAll();
		assertEquals("test", list.get(0).getEmail());
	}
	
	@Transactional
	@Test
	public void DefaultRole() {
		MemberSaveRequestDto requestDto = MemberSaveRequestDto.builder().address("add").email("email@gmail.com").name("이이름").password("1234").tel("010-1111-4213").build();
		accountRepository.save(MemberDtoMapper.INSTANCE.toEntity(requestDto));
		List<Member> list = accountRepository.findAll();
		assertEquals(Role.MEMBER, list.get(0).getRole());
	}
	
	
//	@Test
//	public void MyBatisSave() {
//		Member account = new Member("test", "test", "test", "test", "test");
//		accountMapper.saveAccount(account);
//		List<Member> list = accountMapper.getList();
//		assertEquals("test", list.get(0).getEmail());
//	}
//	
//	@Test
//	public void JpaMyBatisSave() {
//		Member account = new Member("test", "test", "test", "test", "test");
//		accountRepository.save(account);
//		List<Member> list = accountMapper.getList();
//		assertEquals("test", list.get(0).getEmail());
//	}
	
	//failed
//	@Test
//	public void UniRepo() {
//		Account account = new Account("test", "test", "test", "test", "test");
//		accountRepository.save(account);
//		List<Account> list = accountRepository.getList();
//		assertEquals("test", list.get(0).getUser_email());
//	}
	
	
	
}
