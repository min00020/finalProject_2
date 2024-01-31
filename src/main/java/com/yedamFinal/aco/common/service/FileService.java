package com.yedamFinal.aco.common.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	public void makeDir(String path);
	public String profileUpload(MultipartFile file);
}
