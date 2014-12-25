/**  
 *@Copyright:Copyright (c) 2008 - 2100  
 *@Company:xiaomishu  
 */
package com.soledede.classfy.bayes.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.soledede.classfy.bayes.entity.Dictiona;
import com.soledede.classfy.bayes.entity.Users;
import com.soledede.classfy.bayes.service.DicService;
import com.soledede.classfy.bayes.service.UserService;

/**
 * @Title:
 * @Description:
 * @Author:wengbenjue
 * @Since:2014年7月10日
 * @Version:1.1.0
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private DicService dicService;

	@RequestMapping(value = "/manager", method = RequestMethod.GET)
	public ModelAndView hello2(HttpServletRequest req,
			HttpServletResponse repson) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("message", "HelloMVC");
		mv.setViewName("/user/users");
		return mv;
	}

	@RequestMapping(value = "/manager1", method = RequestMethod.GET)
	public String hellos(Model mv, HttpServletRequest req,
			HttpServletResponse repson) {
		mv.addAttribute("message", "HelloMVC");
		return "/user/users";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String adduser(Model mv, HttpServletRequest req,
			HttpServletResponse repson) {
		userService.saveUser(new Users("weng", 23, "锁了的的"));
		mv.addAttribute("message", "Sucess");
		return "/user/users";
	}

	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public ModelAndView count() {

		int c = userService.userCount();

		ModelAndView mv = new ModelAndView();
		mv.addObject("message", c);
		mv.setViewName("/user/users");
		return mv;
	}

	@RequestMapping(value = "/dic", method = RequestMethod.GET)
	public ModelAndView dic() {

		List<Dictiona> list = new ArrayList<Dictiona>();
		try {
			list = dicService.findByTagTypeId(9);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(list.size());

		ModelAndView mv = new ModelAndView();
		mv.addObject("message", list.size());
		mv.setViewName("/user/users");
		return mv;
	}

}
