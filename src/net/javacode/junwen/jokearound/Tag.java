package net.javacode.junwen.jokearound;

public class Tag {
	protected int jokeID;
	protected String tag;
	
	public int getJokeID() {
		return jokeID;
	}
	public void setJokeID(int jokeID) {
		this.jokeID = jokeID;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public Tag(int jokeID, String tag) {
		super();
		this.jokeID = jokeID;
		this.tag = tag;
	}
	
	
}
