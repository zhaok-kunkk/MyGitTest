package com.itqf.controller;



import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.kaptcha.Producer;
import com.itqf.annotation.MyLog;
import com.itqf.domain.SysUser;
import com.itqf.service.SysUserService;
import com.itqf.utils.Constant;
import com.itqf.utils.DataGridResult;
import com.itqf.utils.Lg;
import com.itqf.utils.Query;
import com.itqf.utils.R;
import com.itqf.utils.ShiroUtils;

@Controller
public class SysUserController {

	@Resource
	private SysUserService sysUserService;
	
	@Resource
	private Producer producer;
	
	@MyLog("登录")
	@RequestMapping("/sys/login")
	@ResponseBody
	public R login(@RequestBody SysUser sysUser) {
		
		//SysUser user = sysUserService.login(sysUser.getUsername());
		/*if (user.getPassword().equals(sysUser.getPassword())) {
			
			return R.ok();
		}
		else {
			
			
		}*/
		Subject subject = SecurityUtils.getSubject();
		String pwd=new Md5Hash(sysUser.getPassword(),sysUser.getUsername(),1024).toString();
		System.out.println("登陆密码"+pwd);
		UsernamePasswordToken token=new UsernamePasswordToken(sysUser.getUsername(),pwd);
		if (sysUser.getRememberMe()!=null) {
			token.setRememberMe(true);
		}
		String code  = (String) ShiroUtils.getSessionAttribute(Constant.KAPTCHA_KEY);
		 if (code==null) {
			return R.error("验证码已过期");
		}
		 if (!code.equals(sysUser.getCaptcha())) {
			 return R.error("验证码不正确");
		 }
		try {
			subject.login(token);//底层 自定义realm进行授权和认证
			
			Lg.log(subject.isPermitted("sys:user:delete")?"具有改权限":"没有该权限");
			
			return R.ok();
		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return R.error("账号或密码错误");
	}
	
	/*
	 * 生成验证码
	 */
	@RequestMapping("/captcha.jpg")
	public void createCatcha(HttpServletResponse response) throws Exception {
		
		//设置响应头
		response.setHeader("Cache-Control", "no-store,no-cache");
		//设置响应内容
		response.setContentType("image/jpeg");
				
		//创建文字验证码
		String text = producer.createText();
		//把验证码存储带session中
		ShiroUtils.getSession().setAttribute("kaptcha", text);
				
		//创建图片验证码
		BufferedImage bufferedImage =   producer.createImage(text);
				
		ImageIO.write(bufferedImage, "jpg", response.getOutputStream());
		
	}
	
	@MyLog("查询用户列表")
	@RequestMapping("/sys/user/list")
	@ResponseBody
	public DataGridResult userList(Integer offset,Integer limit,String search){
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("offset", offset);
		map.put("limit", limit);
		if(search==null) {
			search="";
		}
		map.put("search", search);
		return sysUserService.findAllUser(new Query(map));
	}
	
	@MyLog("添加用户")
	@RequestMapping("/sys/user/save")
	@ResponseBody
	public R addUser(@RequestBody SysUser user ) {
		//System.out.println("666");
		String pwd=new Md5Hash(user.getPassword(),user.getUsername(),1024).toString();
		System.out.println(pwd+"-----------");
		SysUser sysUser = ShiroUtils.getUserEntity();
		System.out.println(sysUser.getUsername());
		user.setPassword(pwd);
		user.setCreateUserId(sysUser.getUserId());
		user.setCreateTime(new Date(System.currentTimeMillis()));
		return sysUserService.addUser(user);
	}
	
	@MyLog("删除用户")
	@RequestMapping("/sys/user/del")
	@ResponseBody
	public R delUser(@RequestBody List<Integer> ids) {
		for (Integer integer : ids) {
			if(integer<2) {
				return R.error("管理员，不能删除");
			}
		}
		return sysUserService.deleteBatch(ids);
	}
	
	
	@RequestMapping("/sys/user/info/{menuId}")
	@ResponseBody
	public R selectUserById(@PathVariable int menuId) {
		System.out.println(menuId+"----------");
		return sysUserService.selectUserById(menuId);
	}
	
	@MyLog("修改用户信息")
	@RequestMapping("/sys/user/update")
	@ResponseBody
	public R updateUser(@RequestBody SysUser user) {
		
		return sysUserService.updateUser(user);
		
	}
	
	@RequestMapping("sys/user/info")
	@ResponseBody
	public R userInfo() {
		SysUser sysUser = ShiroUtils.getUserEntity();
		return R.ok().put("user", sysUser);
	}
	
	//@MyLog("退出")
	@RequestMapping("/logout")
	public String logout() {
		
		//清空session
		ShiroUtils.getSession().removeAttribute(Constant.KAPTCHA_KEY);
		ShiroUtils.logout();
		return "redirect:login.xml";
	}
	
	
	@RequestMapping("sys/user/password")
	@ResponseBody
	public R updatePwd(String password,String newPassword) {
		SysUser sysUser = ShiroUtils.getUserEntity();
		String pwd2=new Md5Hash(sysUser.getPassword(),sysUser.getUsername(),1024).toString();
		String pwd3=new Md5Hash(password,sysUser.getUsername(),1024).toString();
		System.out.println("旧密码："+pwd2 +"------"+password);
		System.out.println("新密码："+pwd3 +"------"+newPassword);
		
		if (pwd2.equals(pwd3)) {
			return R.error("原密码错误");
		}else {
			sysUser.setPassword(newPassword);
			String pwd=new Md5Hash(sysUser.getPassword(),sysUser.getUsername(),1024).toString();
			sysUser.setPassword(pwd);
			sysUserService.updatePwd(sysUser);
			return R.ok();
		}
		
		
	}
}
