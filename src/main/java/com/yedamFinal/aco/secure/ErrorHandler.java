package com.yedamFinal.aco.secure;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorHandler implements ErrorController{


    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Model model){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if(status != null){
            int statusCode = Integer.valueOf(status.toString());
            model.addAttribute("errorStatus", statusCode);
            return "common/error";
        }
        else {
        	return "common/errorPage";
        }
        
    } 
}