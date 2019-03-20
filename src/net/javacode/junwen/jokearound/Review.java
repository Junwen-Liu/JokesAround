package net.javacode.junwen.jokearound;

public class Review {
protected int jokeID;
protected int userID; 
protected String userName;
protected String score;
protected String remark;
protected String createdDate;

public String getCreatedDate() {
	return createdDate;
}
public void setCreatedDate(String createdDate) {
	this.createdDate = createdDate;
}
public int getJokeID() {
	return jokeID;
}
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public void setJokeID(int jokeID) {
	this.jokeID = jokeID;
}
public int getUserID() {
	return userID;
}
public void setUserID(int userID) {
	this.userID = userID;
}
public String getScore() {
	return score;
}
public void setScore(String score) {
	this.score = score;
}
public String getRemark() {
	return remark;
}
public void setRemark(String remark) {
	this.remark = remark;
}
public Review(int jokeID, int userID, String score, String remark) {
	super();
	this.jokeID = jokeID;
	this.userID = userID;
	this.score = score;
	this.remark = remark;
}
public Review(String userName, String score, String remark, String createdDate) {
	super();
	this.userName = userName;
	this.score = score;
	this.remark = remark;
	this.createdDate = createdDate;
}


}
