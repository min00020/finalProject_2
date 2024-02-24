package com.yedamFinal.aco.freeboard.service;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.yedamFinal.aco.freeboard.FreeBoardJoinVO;
import com.yedamFinal.aco.freeboard.FreeBoardVO;
import com.yedamFinal.aco.freeboard.MainTotalVO;
import com.yedamFinal.aco.noticeboard.NoticeBoardVO2;
import com.yedamFinal.aco.question.QuestionVO2;
import com.yedamFinal.aco.sideboard.SideVO2;

public interface FreeBoardService {
	
	//조회
	public List<FreeBoardVO> getFreeBoardAll(Model model,int pg);
	
	//단건조회
	public List<FreeBoardJoinVO> getFreeBoard(int fboardNo, Model model);
	
	//등록
	public Map<String, Object> insertFreeBoard(FreeBoardVO freeBoardVO, MultipartFile[] files);
	
	//수정
	public Map<String, Object> modifyFreeBoard(String title, String content, int fboardNo);
	
	//삭제
	public int deleteFreeBoard(int fboardNo);
	
	//검색
	public List<FreeBoardVO> getSearchFreeBoard(Model model,String search, int pg);
	
	//메인페이지 자유게시판 불러오기
	public List<FreeBoardVO> getFreeBoardMainPage();
	
	//메인페이지 공지사항 불러오기
	public List<NoticeBoardVO2> getNoticeBoardMainPage();
	
	//메인페이지 질문&답변 불러오기
	public List<QuestionVO2> getQuestionBoardMainPage();
	
	//메인페이지 사이드 프로젝트 불러오기
	public List<SideVO2> getSideProjectBoardMainPage();
	
	//메인통합검색
	public List<MainTotalVO> getMainTotalSearch(Model model, String search,String searchType,int pg);
	
	//메인통합검색 개수
	public int getMainTotalSearchCnt(String search,String searchType);
}
