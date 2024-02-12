package com.yedamFinal.aco.common.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.yedamFinal.aco.admin.AdminEmoUseImgVO;
import com.yedamFinal.aco.admin.AdminEmoVO;
import com.yedamFinal.aco.common.AttachedFileVO;

public interface FileService {
	public void makeDir(String path);
	public String profileUpload(MultipartFile file);
	public boolean uploadAttachFiles(MultipartFile[] files, int memberNo, String boardType, int boardNo);
	public AttachedFileVO getFile(int fileNo);
	public boolean emoticonUpload(MultipartFile[] files, AdminEmoVO adminEmoVO, List<AdminEmoUseImgVO> useImgList);
}
