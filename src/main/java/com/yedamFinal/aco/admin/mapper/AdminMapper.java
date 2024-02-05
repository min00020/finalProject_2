package com.yedamFinal.aco.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yedamFinal.aco.admin.AdminEmoVO;
import com.yedamFinal.aco.admin.AdminMainVO;
import com.yedamFinal.aco.admin.AdminMemberVO;
import com.yedamFinal.aco.admin.AdminQnaVO;
import com.yedamFinal.aco.admin.AdminReportVO;
import com.yedamFinal.aco.admin.AdminSettleVO;

public interface AdminMapper { 
	public List<AdminMainVO> getAdNoticeList(int pageNo);
	public int selectAdNoticeCount();
	public int deleteNotice(int noticeBoardNo);
	public List<AdminMemberVO> getAdMemberList(int pageNo);
	public int selectAdMemberCount();
	public int selectAdLeaveMemberCount(String leaveStatus);
	public List<AdminReportVO> getAdReportList();
	public List<AdminQnaVO>    getAdQnaList();
	public List<AdminSettleVO> getAdSettleList();
	public List<AdminEmoVO> getAdEmoList();
	public List<AdminEmoVO> getMainEmoList();
	//이모티콘 등록
	public int insertEmo(AdminEmoVO adminEmoVO);
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
	public List<AdminEmoVO> getSaleAdEmoList(String emoState);
	// 멤버 드롭박스
	public List<AdminEmoVO> getAdDropMemberList(@Param(value="pageNo") int pageNo, @Param(value="leaveStatus") String leaveStatus);
	
}
