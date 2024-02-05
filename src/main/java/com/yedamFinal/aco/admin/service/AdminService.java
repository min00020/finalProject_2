package com.yedamFinal.aco.admin.service;

import java.util.List;
import java.util.Map;

import com.yedamFinal.aco.admin.AdminEmoVO;
import com.yedamFinal.aco.admin.AdminMainVO;
import com.yedamFinal.aco.admin.AdminQnaVO;
import com.yedamFinal.aco.admin.AdminReportVO;
import com.yedamFinal.aco.admin.AdminSettleVO;


public interface AdminService {
	public Map<String,Object> getAdNoticeList(int pageNo);
	public boolean deleteNotice(int noticeBoardNo);
	public Map<String,Object> getAdMemberList(int pageNo,String leaveStatus);
	public List<AdminReportVO> getAdReportList();
	public List<AdminQnaVO>    getAdQnaList();
	public List<AdminSettleVO> getAdSettleList();	
	public List<AdminMainVO> getAdCntList();

	public List<AdminEmoVO> getAdEmoList();
	public List<AdminEmoVO> getMainEmoList();
	//이모티콘 등록
	public int insertEmo(AdminEmoVO adminEmoVO);
	//public int insertEmo(AdminEmoVO adminEmoVO, MultipartFile[] file);	
	//이모티콘 판매종료
	public boolean updateEmo(int emoNo);
	//이모티콘 판매재개
	public boolean updateEmo2(int emoNo);
	//이모티콘 상점 이모티콘상세
	public AdminEmoVO getEmoDetail(AdminEmoVO adminEmoVO);
	//이모티콘 구매 내역
	public List<AdminEmoVO> getEmoBuyList(int memberNo);
	//	공지등록
	public int insertNotice(AdminMainVO adminMainVO);
	// 이모티콘 드롭박스
	public List<AdminEmoVO> getSaleAdEmoList(String emoState);
}
