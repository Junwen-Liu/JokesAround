package net.javacode.junwen.jokearound;

import java.util.List;

public class Joke {
	protected int jokeID;
	protected int userID;
	protected String userName;
    protected String title;
    protected String description;
    protected String createDate;
    protected String tags;
    protected int fav;
    protected String score;
    protected String rev;
    protected int isfrnd;
    protected List<Review> listReview;
    
	public List<Review> getListReview() {
		return listReview;
	}

	public void setListReview(List<Review> listReview) {
		this.listReview = listReview;
	}

	public int getIsfrnd() {
		return isfrnd;
	}

	public void setIsfrnd(int isfrnd) {
		this.isfrnd = isfrnd;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getRev() {
		return rev;
	}

	public void setRev(String rev) {
		this.rev = rev;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getJokeID() {
		return jokeID;
	}

	public void setJokeID(int jokeID) {
		this.jokeID = jokeID;
	}

	public int getuserID() {
		return userID;
	}

	public void setuserID(int userID) {
		this.userID = userID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public int getFav() {
		return fav;
	}

	public void setFav(int fav) {
		this.fav = fav;
	}

	public Joke(String title, String description, String createDate, String score) {
		super();
		this.title = title;
		this.description = description;
		this.createDate = createDate;
		this.score = score;
	}

	public Joke(int jokeId, int userId, String userName, String title, String description, String createDate, String tags, int fav, String score, String rev, int isfrnd, List<Review> listReview) {
		super();
		this.jokeID = jokeId;
		this.userID = userId;
		this.userName = userName;
		this.title = title;
		this.description = description;
		this.createDate = createDate;
		this.tags = tags;
		this.fav = fav;
		this.score = score;
		this.rev = rev;
		this.isfrnd = isfrnd;
		this.listReview = listReview;
	}

	public Joke(int jokeID, String title, String description, String tags) {
		super();
		this.jokeID = jokeID;
		this.title = title;
		this.description = description;
		this.tags = tags;
	}

	public Joke(int userID, String title, String description) {
		super();
		this.userID = userID;
		this.title = title;
		this.description = description;
	}

	public Joke(String userName, String title, String description, String createDate, String tags) {
		super();
		this.userName = userName;
		this.title = title;
		this.description = description;
		this.createDate = createDate;
		this.tags = tags;
	}
    
    
}
