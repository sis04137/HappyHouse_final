package com.ssafy.happyhouse.dto.news;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewsDto {

    // 제목
    private String title;
    // 사진 경로
    private String imgUrl;
    // 기사 연결
    private String url;
    // 요약
    private String summary;
    // 뉴스사
    private String agency;
    // 출간일
    private String pubDate;
}
