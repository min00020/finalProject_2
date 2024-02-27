package com.yedamFinal.aco.common.mapper;

import com.yedamFinal.aco.common.AttachedFileVO;
import com.yedamFinal.aco.common.TextEditorImageVO;

public interface FileMapper {
	public int insertFile(AttachedFileVO file);
	public AttachedFileVO selectFile(int fileNo);
	
	public int insertImage(TextEditorImageVO image);
}
