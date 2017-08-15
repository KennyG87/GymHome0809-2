package com.example.kennykao;

import CoachesLogin.CoachesVO;
import StudentsLogin.StudentsVO;

public class NewUser {
	private Integer status;
	private String name;
	private String nickname;
	private String username;
	private String password;
	private Integer sex;
	private String id;
	private String email;
	private String intro;
	private byte[] image;

	public NewUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NewUser(Integer status, String name, String nickname, String username, String password, Integer sex, String id, String email, String intro, byte[] image) {
		super();
		this.status = status;
		this.name = name;
		this.nickname = nickname;
		this.username = username;
		this.password = password;
		this.sex = sex;
		this.id = id;
		this.email = email;
		this.intro = intro;
		this.image = image;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}


	public StudentsVO getStudentsVO() {
		return null;
	}

	public CoachesVO getCoachesVO() {
		return null;
	}
}
