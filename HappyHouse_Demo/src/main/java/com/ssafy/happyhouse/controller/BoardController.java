package com.ssafy.happyhouse.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.ssafy.happyhouse.domain.board.Board;
import com.ssafy.happyhouse.dto.board.BoardRequestDto;
import com.ssafy.happyhouse.dto.board.BoardResponseDto;
import com.ssafy.happyhouse.dto.board.BoardResponsePagingDto;
import com.ssafy.happyhouse.dto.news.NewsDto;
import com.ssafy.happyhouse.service.board.BoardService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequestMapping("/board")
@RequiredArgsConstructor
@RestController
public class BoardController {
	private final BoardService boardService;
	
	@ApiOperation(value = "게시판 글작성", notes = "새로운 게시글 정보를 입력한다.")
	@PostMapping
	ResponseEntity<?> saveNotice(@RequestBody BoardRequestDto requestDto){
		return new ResponseEntity<Long>(boardService.saveNotice(requestDto), HttpStatus.OK);
	}
	
	@ApiOperation(value = "게시판 글목록", notes = "모든 게시글의 정보를 반환한다.", response = List.class)
	@GetMapping
	ResponseEntity<?> findAll(){
		return new ResponseEntity<List<BoardResponseDto>>(boardService.findAll(), HttpStatus.OK);
	}
	
	@ApiOperation(value = "게시판 페이지 반환", notes = "해당 페이지의 게시글을 호출한다")
	@GetMapping("/list/{pageNum}")
	ResponseEntity<?> getPagingList(@PathVariable int pageNum){
		Page<BoardResponseDto> list = boardService.getPagingList(pageNum);
		log.info("!!!TOTAL!!!: {}",list.getPages());
		BoardResponsePagingDto responseDto = BoardResponsePagingDto.builder().list(list).totalPage(list.getPages()).build();
		return new ResponseEntity<BoardResponsePagingDto>(responseDto, HttpStatus.OK);
//		return new ResponseEntity<Page<BoardResponseDto>>(list, HttpStatus.OK);
	}
	
	@ApiOperation(value = "게시판 글보기(키워드)", notes = "키워드에 해당하는 게시글의 정보를 반환한다.")
	@GetMapping("/search/{keyword}")
	public ResponseEntity<?> getArticle(@PathVariable("keyword") String keyword) throws Exception {
		return new ResponseEntity<List<BoardResponseDto>>(boardService.findAll(keyword), HttpStatus.OK);
	}
	
	@ApiOperation(value = "게시판 글보기", notes = "글번호에 해당하는 게시글의 정보를 반환한다.")
	@GetMapping("/{id}")
	ResponseEntity<?> findById(@PathVariable Long id){
		return new ResponseEntity<BoardResponseDto>(boardService.findById(id), HttpStatus.OK);
	}
	
	@ApiOperation(value = "게시판 글수정", notes = "새로운 게시글 정보를 입력한다.")
	//얘 기존에는 requestDto로 한번에 받아왔는데 이제 id는 path로 내려옴
	@PutMapping("/{id}")
	ResponseEntity<?> updateNotice(@PathVariable Long id, @RequestBody BoardRequestDto requestDto){
		return new ResponseEntity<Long>(boardService.updateNotice(id, requestDto), HttpStatus.OK);
	}
	@ApiOperation(value = "게시판 글삭제", notes = "글번호에 해당하는 게시글의 정보를 삭제한다.")
	@DeleteMapping("/{id}")
	ResponseEntity<?> deleteNotice(@PathVariable Long id){
		return new ResponseEntity<Long>(boardService.deleteNotice(id), HttpStatus.OK);
	}
	
	@GetMapping("/newsget")
	ResponseEntity<?> newsget() throws IOException{
		List<NewsDto> list = new ArrayList<NewsDto>();

        Document doc = Jsoup.connect("https://land.naver.com/news/headline.naver").get();

        // dl 태그에 해당하는 태그 가져옴
        Elements dl = doc.select(".headline_list dl");

        // 가져올 헤드라인 수
        int headlineNum = 5;
//        int headlineNum = dl.size();

        for(int i = 0; i < headlineNum; i++) {
            NewsDto news = new NewsDto();
            Element dlEl = dl.get(i);    // i번째에 해당하는 dl 태그
            Elements DtEl = dlEl.select("dt"); // dl 태그 안에서 dt 태그들을 선택

            // 첫번째 dt 태그 안에 첫 번째 a태그를 선택하고 href 속성값을 출력
            Elements aEl = DtEl.get(0).select("a");
            news.setUrl("https://land.naver.com/" + aEl.attr("href"));

            if(aEl.select("img").size() != 0) {
                // 이미지가 존재하면 처리
                Element imgEl = aEl.get(0).select("img").get(0);
                news.setTitle(DtEl.get(1).select("a").get(0).ownText());
                news.setImgUrl(imgEl.attr("src"));
            } else {
                // 이미지가 없을 때 처리
                // 태그 내부에 text만 가져옴
                news.setTitle(aEl.get(0).ownText());
            }

            // dd 태그를 가져옴
            Element ddEl = dlEl.select("dd").get(0);
            news.setSummary(ddEl.ownText());

            // dd 태그 내부에 span 태그 가져옴
            Elements spanEl = ddEl.select("span");
            news.setAgency(spanEl.get(0).ownText());
            news.setPubDate(spanEl.get(1).ownText());

            list.add(news);
        }

        return new ResponseEntity<List<NewsDto>>(list, HttpStatus.OK);
	}
	
}
