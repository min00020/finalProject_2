package com.yedamFinal.aco.admin.mapper;

import java.util.List;

import com.yedamFinal.aco.admin.AdminEmoVO;
import com.yedamFinal.aco.admin.AdminMainVO;
import com.yedamFinal.aco.admin.AdminMemberVO;
import com.yedamFinal.aco.admin.AdminQnaVO;
import com.yedamFinal.aco.admin.AdminReportVO;
import com.yedamFinal.aco.admin.AdminSettleVO;

public interface AdminMapper { 
	public List<AdminMemberVO> getAdMemberList();
	public List<AdminReportVO> getAdReportList();
	public List<AdminQnaVO>    getAdQnaList();
	public List<AdminSettleVO> getAdSettleList();
	public List<AdminEmoVO> getAdEmoList();
	//이모티콘 등록
	public int insertEmo(AdminEmoVO adminEmoVO);
	//이모티콘 상점 이모티콘상세
	public AdminEmoVO getEmoDetail(AdminEmoVO adminEmoVO);
	public List<AdminMainVO> getAdCntList();
	public List<AdminMainVO> getAdNoticeList();
	//	공지등록
	public int insertNotice(AdminMainVO adminMainVO);
}
