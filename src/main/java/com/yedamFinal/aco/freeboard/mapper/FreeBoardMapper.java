package com.yedamFinal.aco.freeboard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yedamFinal.aco.freeboard.FreeBoardVO;
import com.yedamFinal.aco.freeboard.MainTotalVO;
import com.yedamFinal.aco.noticeboard.NoticeBoardVO;
import com.yedamFinal.aco.noticeboard.NoticeBoardVO2;
import com.yedamFinal.aco.question.QuestionVO;
import com.yedamFinal.aco.question.QuestionVO2;
import com.yedamFinal.aco.sideboard.SideVO;
import com.yedamFinal.aco.sideboard.SideVO2;

@Mapper
public interface FreeBoardMapper {
	//게시글 전체조회
	public List<FreeBoardVO> getFreeBoardAll(int pg);
	public int getFreeBoardAllCnt();

	//게시글 단건조회
	public FreeBoardVO getFreeBoard(@Param("fboardNo") int fboardNo);
	
	//게시글 등록
	public int insertFreeBoard(FreeBoardVO freeBoardVO);
	
	//게시글 조회수
	public int updateFreeBoardViewCnt(int fboardNo);
	
	//게시글 수정
	public int updateFreeBoard(String title, String content, int fboardNo);
	
	//게시글 삭제
	public int deleteFreeBoard(int fboardNo);
	
	//게시글 검색
	public List<FreeBoardVO> searchFreeBoard(@Param("search") String search,@Param("pg") int pg);
	public int searchFreeBoardCnt(String search);
	
	//메인페이지 표시할 게시판들
	//메인페이지 자유게시판 불러오기
	public List<FreeBoardVO> getFreeBoardMainPage();
	
	//메인페이지 공지사항 불러오기
	public List<NoticeBoardVO2> getNoticeBoardMainPage();
	
	//메인페이지 질문&답변 게시판 불러오기
	public List<QuestionVO2> getQuestionBoardMainPage();
	
	//메인페이지 사이드게시판 불러오기
	public List<SideVO2> getSideProjectBoardMainPage();
	
	//메인페이지 검색
	public List<MainTotalVO> getMainTotalSearch(@Param("search") String search,@Param("pg") int pg);
	public int getMainTotalSearchCnt(String search);
	
}
