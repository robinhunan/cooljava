package com.model.web;

import java.util.Date;

import com.model.page.PageDto;
import com.util.DateUtil;

public class FriendLink extends PageDto{
	private String webname;
	private String alink;
	private String email;
	private String dispos;
	private String content;
	private String addtime;
	private String timeF;
	
	public String getWebname() {
		return webname;
	}
	public void setWebname(String webname) {
		this.webname = webname;
	}
	public String getAlink() {
		return alink;
	}
	public void setAlink(String alink) {
		this.alink = alink;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDispos() {
		return dispos;
	}
	public void setDispos(String dispos) {
		this.dispos = dispos;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	
	
}
