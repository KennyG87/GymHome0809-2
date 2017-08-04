package StudentsLogin;

import java.io.Serializable;


@SuppressWarnings("serial")
public class StudentsVO implements Serializable {
	private String stu_acc;
	private int stu_no;
	private String stu_psw;
	private int stu_acc_sta;
	private String stu_name;
	private int stu_sex;
	private String stu_id;
	private String stu_mail;
	private String stu_into;
	private byte[] stu_pic;
	private double stu_sto;
	
	public String getStu_acc() {
		return stu_acc;
	}
	public void setStu_acc(String stu_acc) {
		this.stu_acc = stu_acc;
	}
	public int getStu_no() {
		return stu_no;
	}
	public void setStu_no(int stu_no) {
		this.stu_no = stu_no;
	}
	public String getStu_psw() {
		return stu_psw;
	}
	public void setStu_psw(String stu_psw) {
		this.stu_psw = stu_psw;
	}
	public int getStu_acc_sta() {
		return stu_acc_sta;
	}
	public void setStu_acc_sta(int stu_acc_sta) {
		this.stu_acc_sta = stu_acc_sta;
	}
	public String getStu_name() {
		return stu_name;
	}
	public void setStu_name(String stu_name) {
		this.stu_name = stu_name;
	}
	public int getStu_sex() {
		return stu_sex;
	}
	public void setStu_sex(int stu_sex) {
		this.stu_sex = stu_sex;
	}
	public String getStu_id() {
		return stu_id;
	}
	public void setStu_id(String stu_id) {
		this.stu_id = stu_id;
	}
	public String getStu_mail() {
		return stu_mail;
	}
	public void setStu_mail(String stu_mail) {
		this.stu_mail = stu_mail;
	}
	public String getStu_into() {
		return stu_into;
	}
	public void setStu_into(String stu_into) {
		this.stu_into = stu_into;
	}
	public byte[] getStu_pic() {
		return stu_pic;
	}
	public void setStu_pic(byte[] stu_pic) {
		this.stu_pic = stu_pic;
	}
	public double getStu_sto() {
		return stu_sto;
	}
	public void setStu_sto(double stu_sto) {
		this.stu_sto = stu_sto;
	}

	
}
