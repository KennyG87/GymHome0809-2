package com.example.kennykao;

import java.io.Serializable;


public class MembersVO implements Serializable {
	private int mem_no;
	MembersVO membersVO = new MembersVO();
	public MembersVO(int mem_no, String mem_acc, String mem_rank, String mem_nickname, Integer mr_num) {
		this.mem_no = mem_no;
		this.mem_acc = mem_acc;
		this.mem_rank = mem_rank;
		this.mem_nickname = mem_nickname;
		this.mr_num = mr_num;
	}

	private String mem_acc;
	private String mem_rank;
	private String mem_nickname;
	private Integer mr_num;

	public MembersVO(){
		
	}

	public int getMem_no() {
		return mem_no;
	}

	public void setMem_no(int mem_no) {
		this.mem_no = mem_no;
	}

	public String getMem_acc() {
		return mem_acc;
	}

	public void setMem_acc(String mem_acc) {
		this.mem_acc = mem_acc;
	}

	public String getMem_rank() {
		return mem_rank;
	}

	public void setMem_rank(String mem_rank) {
		this.mem_rank = mem_rank;
	}

	public String getMem_nickname() {
		return mem_nickname;
	}

	public void setMem_nickname(String mem_nickname) {
		this.mem_nickname = mem_nickname;
	}

	public Integer getMr_num() {
		return mr_num;
	}

	public void setMr_num(Integer mr_num) {
		this.mr_num = mr_num;
	}
}
