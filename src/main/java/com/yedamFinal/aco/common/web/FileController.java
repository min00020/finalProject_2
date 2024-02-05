package com.yedamFinal.aco.common.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.yedamFinal.aco.common.serviceImpl.FileServiceImpl;

@Controller
public class FileController {
	@Autowired
	private FileServiceImpl fileSerivce;
	
	
}
