package nat.phc.blog.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table (name ="tb_user")
public class SobUser {

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
	private String reg_ip;
  	@Column(name = "login_ip")
	private String login_ip;
  	@Column(name = "create_time")
	private Date create_time;
  	@Column(name = "update_time")
	private Date update_time;


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


	public String getReg_ip() {
		return reg_ip;
	}

	public void setRegIp(String reg_ip) {
		this.reg_ip = reg_ip;
	}


	public String getLogin_ip() {
		return login_ip;
	}

	public void setLoginIp(String login_ip) {
		this.login_ip = login_ip;
	}


	public Date getCreate_time() {
		return create_time;
	}

	public void setCreateTime(Date create_time) {
		this.create_time = create_time;
	}


	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdateTime(Date update_time) {
		this.update_time = update_time;
	}

}
