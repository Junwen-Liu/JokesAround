package net.javacode.junwen.jokearound;

public class UserPair {
	protected String user1;
	protected String user2;
	public String getUser1() {
		return user1;
	}
	public void setUser1(String user1) {
		this.user1 = user1;
	}
	public String getUser2() {
		return user2;
	}
	public void setUser2(String user2) {
		this.user2 = user2;
	}
	public UserPair(String user1, String user2) {
		super();
		this.user1 = user1;
		this.user2 = user2;
	}
	
}
