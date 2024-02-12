package com.yedamFinal.aco.common.mapper;

import com.yedamFinal.aco.common.AttachedFileVO;

public interface FileMapper {
	public int insertFile(AttachedFileVO file);
	public AttachedFileVO selectFile(int fileNo);
}
