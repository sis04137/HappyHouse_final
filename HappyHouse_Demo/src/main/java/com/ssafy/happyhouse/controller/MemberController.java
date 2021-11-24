package com.ssafy.happyhouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.ssafy.happyhouse.domain.member.Member;
import com.ssafy.happyhouse.dto.member.MemberLoginRequestDto;
import com.ssafy.happyhouse.dto.member.MemberResponseDto;
import com.ssafy.happyhouse.dto.member.MemberSaveRequestDto;
import com.ssafy.happyhouse.dto.member.MemberUpdateRequestDto;
import com.ssafy.happyhouse.service.member.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@Slf4j
@RequestMapping("/api/member")
@RequiredArgsConstructor
@RestController
public class MemberController {
	@Autowired
	MemberService memberService;

	
	@GetMapping("/list")
	ResponseEntity<?> findAll(){
		return new ResponseEntity<List<Member>>(memberService.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	ResponseEntity<?> findById(@PathVariable Long id){
		return new ResponseEntity<Member>(memberService.findById(id), HttpStatus.OK);
	}
	
	@PostMapping("/save")
	ResponseEntity<?> saveMember(@RequestBody MemberSaveRequestDto requestDto){
		return new ResponseEntity<Long>(memberService.saveMember(requestDto), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	ResponseEntity<?> updateMember(@PathVariable Long id, @RequestBody MemberUpdateRequestDto requestDto){
		return new ResponseEntity<Long>(memberService.updateUser(id, requestDto), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	ResponseEntity<?> deleteMember(@PathVariable Long id){
		return new ResponseEntity<Long>(memberService.deleteUser(id), HttpStatus.OK);
	}
	
	@PostMapping("/login")
	ResponseEntity<?> login(@RequestBody MemberLoginRequestDto requestDto){
		return new ResponseEntity<Member>(memberService.login(requestDto), HttpStatus.OK);
	}
	
	@PostMapping("/findpass")
	ResponseEntity<?> findPass(@RequestBody MemberLoginRequestDto requestDto){
		return new ResponseEntity<Member>(memberService.login(requestDto), HttpStatus.OK);
	}
}
