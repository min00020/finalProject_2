package com.yedamFinal.aco.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yedamFinal.aco.admin.AdminEmoUseImgVO;
import com.yedamFinal.aco.admin.AdminEmoVO;
import com.yedamFinal.aco.admin.AdminMainVO;
import com.yedamFinal.aco.admin.AdminMemberVO;
import com.yedamFinal.aco.admin.AdminQnaVO;
import com.yedamFinal.aco.admin.AdminReportVO;
import com.yedamFinal.aco.admin.AdminSettleVO;
import com.yedamFinal.aco.admin.AdminTagChartVO;

public interface AdminMapper { 
	public List<AdminMainVO> getAdNoticeList(int pageNo);
	public int selectAdNoticeCount();
	public int deleteNotice(int noticeBoardNo);
	
	public List<AdminMemberVO> getAdMemberList(int pageNo);
	public int selectAdMemberCount();
	public int selectAdLeaveMemberCount(String leaveStatus);
	
	public List<AdminReportVO> getAdReportList(int pageNo);
	public int selectAdReportCount();
	public int selectAdStateReportCount(String reportStatus);
	
	public List<AdminQnaVO>    getAdQnaList(int pageNo);
	public int selectAdQnaCount();
	public int selectAdStateQnaCount(String answerStatus);
	
	
	public List<AdminSettleVO> getAdSettleList(int pageNo);
	public int selectAdSettleCount();
	public int selectAdStateSettleCount(String processStatus);
	public int updateSettlementStatus(int settlementNo);
	public int updateAllSettlementStatus();
	public List<AdminSettleVO> getAdAllSettleList();
	
	
	public List<AdminEmoVO> getAdEmoList(int pageNo);
	public int selectAdEmoCount();
	public int selectAdStateEmoCount(String emoStatus);
	
	public List<AdminEmoVO> getMainEmoList(int pageNo);
	public int MainEmoListCount();
	
	//통계기간 조회
	public List<AdminTagChartVO> getTagListByCount(String date);
	public List<AdminEmoVO> getEmoSaleList(String date);
	//통계달력 기간 조회
	public List<AdminTagChartVO> getTagListByCountByPeriod(String sday, String eday);
	public List<AdminEmoVO> getEmoSaleListByPeriod(String sday, String eday);
	
	//이모티콘 등록
	public int insertEmo(AdminEmoVO adminEmoVO);
	public int insertUseEmo(AdminEmoUseImgVO adminEmoUseImgVO);
	//이모티콘 판매 종료
	public int updateEmo(int emoNo);
	//이모티콘 판매 재개
	public int updateEmo2(int emoNo);
	//이모티콘 상점 이모티콘상세
	public AdminEmoVO getEmoDetail(AdminEmoVO adminEmoVO);
	//이모티콘 구매 내역
	public List<AdminEmoVO> getEmoBuyList(int memberNo);
		
	public List<AdminMainVO> getAdCntList();
	//	공지등록
	public int insertNotice(AdminMainVO adminMainVO);
	// 이모티콘 드롭박스
	public List<AdminEmoVO> getAdDropEmoList(@Param(value="pageNo") int pageNo, @Param(value="emoStatus") String emoStatus);
	// 멤버 드롭박스
	public List<AdminMemberVO> getAdDropMemberList(@Param(value="pageNo") int pageNo, @Param(value="leaveStatus") String leaveStatus);
	// 신고 드롭박스
	public List<AdminReportVO> getAdDropReportList(@Param(value="pageNo") int pageNo, @Param(value="reportStatus") String reportStatus);
	// 문의 드롭박스
	public List<AdminQnaVO> getAdDropQnaList(@Param(value="pageNo") int pageNo, @Param(value="answerStatus") String answerStatus);
	// 정산 드롭박스
	public List<AdminSettleVO> getAdDropSettleList(@Param(value="pageNo") int pageNo, @Param(value="processStatus") String processStatus);
	//이모티콘 구매하기 버튼 (포인트 차감)
	public int buyEmo(AdminEmoVO adminEmoVO);
	//이모티콘 구매하기 버튼 (마이 이모티콘 추가)
	public int insertMyemoticon(AdminEmoVO adminEmoVO);
	//이모티콘 구매목록 삭제
	public int deleteEmo(AdminEmoVO adminEmoVO);
	//내이모티콘 (댓글)
	public List<AdminEmoVO> getMyEmoList(int memberNo);
}
