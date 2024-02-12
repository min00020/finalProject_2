package com.yedamFinal.aco.common.service;

import org.springframework.web.multipart.MultipartFile;

import com.yedamFinal.aco.common.AttachedFileVO;

public interface FileService {
	public void makeDir(String path);
	public String profileUpload(MultipartFile file);
	public boolean uploadAttachFiles(MultipartFile[] files, int memberNo, String boardType, int boardNo);
	public AttachedFileVO getFile(int fileNo);

}
