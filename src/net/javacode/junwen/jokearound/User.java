package net.javacode.junwen.jokearound;

import java.util.Date;

public class User {
	protected int userid;
	protected int age;
    protected String username;
    protected String firstname;
    protected String lastname;
    protected String password;
    protected String email;
    protected String gendar;
    protected boolean isroot;
    protected boolean isfrnd;
    protected Date createdDateJ;
    protected int totaljokes;
    
    


	public int getTotaljokes() {
		return totaljokes;
	}

	public void setTotaljokes(int totaljokes) {
		this.totaljokes = totaljokes;
	}

	public User(String username, Date createdDateJ) {
		super();
		this.username = username;
		this.createdDateJ = createdDateJ;
	}

	public Date getCreatedDateJ() {
		return createdDateJ;
	}

	public void setCreatedDateJ(Date createdDateJ) {
		this.createdDateJ = createdDateJ;
	}

	public User() {
		super();
	}
	
	public boolean isIsfrnd() {
		return isfrnd;
	}

	public void setIsfrnd(boolean isfrnd) {
		this.isfrnd = isfrnd;
	}

	public User(int userid, String username, boolean isfrnd) {
		super();
		this.username = username;
		this.isfrnd = isfrnd;
		this.userid = userid;
	}

	public User(String username, int totaljokes) {
		super();
		this.username = username;
		this.totaljokes = totaljokes;
	}

	public User(String username, String email) {
		super();
		this.username = username;
		this.email = email;
	}

	public User(String username, String firstname, String lastname, String password, String email) {
		super();
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
		this.email = email;
	}

	public User(String username, String firstname, String email) {
		super();
		this.username = username;
		this.firstname = firstname;
		this.email = email;
	}

	public User(int age, String username, String firstname, String lastname, String password, String email,
			String gendar, boolean isroot) {
		super();
		this.age = age;
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
		this.email = email;
		this.gendar = gendar;
		this.isroot = isroot;
	}
	public User(int userid, int age, String username, String firstname, String lastname, String password, String email,
			String gendar) {
		super();
		this.userid = userid;
		this.age = age;
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
		this.email = email;
		this.gendar = gendar;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGendar() {
		return gendar;
	}
	public void setGendar(String gendar) {
		this.gendar = gendar;
	}

	public boolean isIsroot() {
		return isroot;
	}

	public void setIsroot(boolean isroot) {
		this.isroot = isroot;
	}

}
