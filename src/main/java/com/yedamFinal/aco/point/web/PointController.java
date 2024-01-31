package com.yedamFinal.aco.point.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PointController {

	@GetMapping("/point")
	public String getPointMainForm(Model model) {

		return "common/pointCharging";
	}
}
