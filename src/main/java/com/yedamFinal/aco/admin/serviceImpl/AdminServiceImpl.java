package com.yedamFinal.aco.admin.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedamFinal.aco.admin.AdminEmoVO;
import com.yedamFinal.aco.admin.AdminMainVO;
import com.yedamFinal.aco.admin.AdminMemberVO;
import com.yedamFinal.aco.admin.AdminQnaVO;
import com.yedamFinal.aco.admin.AdminReportVO;
import com.yedamFinal.aco.admin.AdminSettleVO;
import com.yedamFinal.aco.admin.mapper.AdminMapper;
import com.yedamFinal.aco.admin.service.AdminService;


@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminMapper adminMapper;
	
	@Override
	public List<AdminMemberVO> getAdMemberList(){
		return adminMapper.getAdMemberList();
	}

	@Override
	public List<AdminReportVO> getAdReportList() {
		return adminMapper.getAdReportList();
	}

	@Override
	public List<AdminQnaVO> getAdQnaList() {
		return adminMapper.getAdQnaList();
	}

	@Override
	public List<AdminSettleVO> getAdSettleList() {
		return adminMapper.getAdSettleList();
	}

	@Override
	public List<AdminEmoVO> getAdEmoList() {
		return adminMapper.getAdEmoList();
	}

	@Override
	public List<AdminMainVO> getAdCntList() {
		return adminMapper.getAdCntList();
	}

	@Override
	public List<AdminMainVO> getAdNoticeList() {
		return adminMapper.getAdNoticeList();
	}
	//공지등록
	@Override
	public int insertNotice(AdminMainVO adminMainVO) {
		return adminMapper.insertNotice(adminMainVO);
	}
}
