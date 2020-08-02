package nat.phc.blog.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "tb_user")
public class SobUser {

	public SobUser() {

	}

	public SobUser(String id, String userName, String roles,
				   String avatar, String email,
				   String sign, String state,
				   String regIp, String loginIp,
				   Date createTime, Date updateTime) {
		this.id = id;
		this.userName = userName;
		this.roles = roles;
		this.avatar = avatar;
		this.email = email;
		this.sign = sign;
		this.state = state;
		this.regIp = regIp;
		this.loginIp = loginIp;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.password = "null";
	}


	@Id
	private String id;
	@Column(name = "user_name")
	private String userName;
	@Column(name = "password")
	private String password;
	@Column(name = "roles")
	private String roles;
	@Column(name = "avatar")
	private String avatar;
	@Column(name = "email")
	private String email;
  	@Column(name = "sign")
	private String sign;
	@Column(name = "state")
	private String state;
	@Column(name = "reg_ip")
	private String regIp;
	@Column(name = "login_ip")
	private String loginIp;
	@Column(name = "create_time")
	private Date createTime;
	@Column(name = "update_time")
	private Date updateTime;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(String user_name) {
		this.userName = user_name;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}


	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}


	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}


	public String getRegIp() {
		return regIp;
	}

	public void setRegIp(String reg_ip) {
		this.regIp = reg_ip;
	}


	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String login_ip) {
		this.loginIp = login_ip;
	}


	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date create_time) {
		this.createTime = create_time;
	}


	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date update_time) {
		this.updateTime = update_time;
	}

}
