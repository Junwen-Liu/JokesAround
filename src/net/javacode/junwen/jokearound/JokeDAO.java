package net.javacode.junwen.jokearound;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import net.javacode.junwen.jokearound.*;


public class JokeDAO {
	private String jdbcURL;
    private String jdbcUsername;
    private String jdbcPassword;
    private Connection jdbcConnection;
     
    public JokeDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }
    
    protected void connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            jdbcConnection = DriverManager.getConnection(
                                        jdbcURL, jdbcUsername, jdbcPassword);
        }
    }
     
    protected void disconnect() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }
    
    public User loginCheck(String uname, String pwd) throws SQLException{
    	User user = new User();
    	String sql = "select * from user where userName = ? and password = ?";
    	connect();
    	
    	PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, uname);
        statement.setString(2, pwd);
        
        ResultSet resultSet = statement.executeQuery();
         
        if(resultSet.next()) {
	            user.username = resultSet.getString("userName");
	            user.userid = resultSet.getInt("userID");
	            user.email = resultSet.getString("email");
	            user.firstname = resultSet.getString("firstName");
	            user.isroot = resultSet.getBoolean("isRoot");
        }
        statement.close();
        disconnect();
        return user;
    }
    
    public void executeSql(String sql) throws SQLException{
        
        connect();
         
        Statement statement = jdbcConnection.createStatement();
		statement.executeUpdate(sql);
         
        statement.close();
        //disconnect();
    	
    }
    
    public List<Joke> listAllJokes(String uname) throws SQLException {
        List<Joke> listJoke = new ArrayList<>();
         
        //String sql = "SELECT u.userName, j.*, group_concat(tag separator ', ') as tags, uj.userID as fav FROM joke j inner join user u on u.userID = j.userID left join Joke_tag t on j.jokeID = t.JokeID left join user_favorite_joke uj on uj.jokeID = j.jokeID and uj.userID = ? group by j.jokeID order by j.jokeID desc";
        String sql = "SELECT u.userName, u.userID, j.*, group_concat(distinct tag separator ', ') as tags, uj.userID as fav, jr.score as score, jr.remark as rev, uff.friendID as isfrnd  FROM joke j inner join user u on u.userID = j.userID  left join Joke_tag t on j.jokeID = t.JokeID left join joke_review jr on j.jokeID = jr.jokeID and jr.userID = ?  left join user_favorite_joke uj on uj.jokeID = j.jokeID and uj.userID = ? left join user_favorite_friend uff on uff.friendID = j.userID and uff.userID = ? group by j.jokeID order by j.jokeID desc";
        connect();
         
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, uname);
        statement.setString(2, uname);
        statement.setString(3, uname);
        ResultSet resultSet = statement.executeQuery();
         
        while (resultSet.next()) {
        	int jokeId = resultSet.getInt("jokeID");
        	List<Review> listReviews = this.listReviews(jokeId);
        	//List<Review> listReviews = null;
        	int userId = resultSet.getInt("userID");
            String userName = resultSet.getString("userName");
            String title = resultSet.getString("title");
            String description = resultSet.getString("description");
            String createDate  = resultSet.getString("createdDate");
            String tags = resultSet.getString("tags");
            int fav = resultSet.getInt("fav");
            String score = resultSet.getString("score");
            String rev = resultSet.getString("rev");
            int isfrnd = resultSet.getInt("isfrnd");
             
            Joke joke = new Joke(jokeId, userId, userName, title, description, createDate, tags, fav, score, rev, isfrnd, listReviews);
            listJoke.add(joke);
        }
         
        resultSet.close();
        statement.close();
         
        disconnect();
         
        return listJoke;
    }
    
    public List<Review> listReviews(int jokeId) throws SQLException {
        List<Review> listReview = new ArrayList<>();
        
        String sql = "SELECT u.userName, sjr.score, sjr.remark, sjr.createdDate FROM joke_review sjr, user u where sjr.userID = u.userID and jokeID= ?";
        connect();
         
        PreparedStatement statement1 = jdbcConnection.prepareStatement(sql);
        statement1.setInt(1, jokeId);

        ResultSet resultSet1 = statement1.executeQuery();
         
        while (resultSet1.next()) {
            String userName = resultSet1.getString("userName");
            String score = resultSet1.getString("score");
            String remark = resultSet1.getString("remark");
            String createdDate  = resultSet1.getString("createdDate");
             
            Review review = new Review(userName, score, remark, createdDate);
            listReview.add(review);
        }
         
        resultSet1.close();
        statement1.close();
         
        //disconnect();
         
        return listReview;
    }
    
    public List<Joke> listUserJokes(String uname) throws SQLException {
        List<Joke> listJoke = new ArrayList<>();
         
        String sql = "select u.userName, j.title, j.description, j.createdDate,  group_concat(distinct tag separator ', ') as tags  from joke j inner join user u on u.userID = j.userID and u.userName = ?  left join joke_tag t on t.jokeID = j.JokeID group by j.jokeID";
        connect();
         
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, uname);
        ResultSet resultSet = statement.executeQuery();
         
        while (resultSet.next()) {
        	String userName = resultSet.getString("userName");
            String title = resultSet.getString("title");
            String description = resultSet.getString("description");
            String createDate  = resultSet.getString("createdDate");
            String tags = resultSet.getString("tags");
             
            Joke joke = new Joke(userName, title, description, createDate, tags);
            listJoke.add(joke);
        }
         
        resultSet.close();
        statement.close();
         
        disconnect();
         
        return listJoke;
    }

    
    public Joke getJoke(int id) throws SQLException {
        Joke joke = null;
        String sql = "SELECT j.*, group_concat(tag separator ', ') as tags FROM joke j left join Joke_tag t on j.jokeID = t.JokeID where j.jokeID = ?  group by j.jokeID";
         
        connect();
         
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, id);
         
        ResultSet resultSet = statement.executeQuery();
         
        if (resultSet.next()) {
            String title = resultSet.getString("title");
            String description = resultSet.getString("description");
            String tags = resultSet.getString("tags");
             
            joke = new Joke(id, title, description, tags);
        }
         
        resultSet.close();
        statement.close();
         
        return joke;
    }
    
    public int insertJoke(Joke joke) throws SQLException {
        String sql = "INSERT INTO joke (userID, title, description) VALUES (?, ?, ?)";
        connect();
         
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, joke.getuserID());
        statement.setString(2, joke.getTitle());
        statement.setString(3, joke.getDescription());
         
        boolean rowInserted = statement.executeUpdate() > 0;
        statement.close();
        
        int jokeID = 0;
        if(rowInserted) {
        sql = "select LAST_INSERT_ID() as jokeID";
        PreparedStatement statement2 = jdbcConnection.prepareStatement(sql);
        ResultSet resultSet = statement2.executeQuery(sql);
        
        if (resultSet.next())
        {
        	jokeID = resultSet.getInt("jokeID");
        }
        statement2.close();
        }
        
        disconnect();
        return jokeID;
    }
     
    public boolean updateJoke(Joke joke) throws SQLException {
        String sql = "UPDATE joke SET title = ?, description = ?";
        sql += " WHERE jokeID = ?";
        connect();
         
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, joke.getTitle());
        statement.setString(2, joke.getDescription());
        statement.setInt(3, joke.getJokeID());
         
        boolean rowUpdated = statement.executeUpdate() > 0;
        statement.close();
        
        this.deleteTags(joke.getJokeID());
        String[] tags = joke.getTags().split(";");
        for(String tag : tags) {
        	Tag newtag = new Tag(joke.getJokeID(), tag);
        	this.insertTag(newtag);
        }
        disconnect();
        return rowUpdated;     
    }
    
    
    public boolean deleteJoke(int jokeID) throws SQLException {
        String sql = "DELETE FROM joke where jokeID = ?";
         
        connect();
         
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, jokeID);
         
        boolean rowDeleted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowDeleted;     
    }
    
    public boolean insertTag(Tag tag) throws SQLException {
    	String sql = "Insert into joke_tag (jokeID, tag) values (?,?)";
    	connect();
    	
    	PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, tag.getJokeID());
        statement.setString(2, tag.getTag());
         
        boolean rowInserted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowInserted;
    }
    
    public boolean deleteTags(int jokeID) throws SQLException {
        String sql = "DELETE FROM joke_tag where jokeID = ?";
         
        connect();
         
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, jokeID);
         
        boolean rowDeleted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowDeleted;     
    }
    
    public boolean checkJokeInsert(int userID) throws SQLException{
    	
    	String sql = "select count(*) as total FROM joke where userID = ? and Date(createdDate) = current_Date()";
        
        connect();
        
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, userID);
        
        ResultSet resultSet = statement.executeQuery();
        
        boolean surpassTotal = false;
        if (resultSet.next())
        {
        	surpassTotal = resultSet.getInt("total") >= 5 ? true : false;
        }
        return surpassTotal;
    }
    
public boolean checkUserReview(int userID) throws SQLException{
    	
    	String sql = "select count(*) as total FROM joke_review where userID = ? and Date(createdDate) = current_Date()";
        
        connect();
        
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, userID);
        
        ResultSet resultSet = statement.executeQuery();
        
        boolean surpassTotal = false;
        if (resultSet.next())
        {
        	surpassTotal = resultSet.getInt("total") >= 5 ? true : false;
        }
        return surpassTotal;
    }

public boolean insertReview(Review review) throws SQLException {
    String sql = "INSERT INTO joke_review (jokeID, userID, score, remark) VALUES (?, ?, ?, ?)";
    connect();
     
    PreparedStatement statement = jdbcConnection.prepareStatement(sql);
    statement.setInt(1, review.getJokeID());
    statement.setInt(2, review.getUserID());
    statement.setString(3, review.getScore());
    statement.setString(4, review.getRemark());
     
    boolean rowInserted = statement.executeUpdate() > 0;
    statement.close();
    
    disconnect();
    return rowInserted;
}

public List<User> listFavuser(int uid) throws SQLException {
    List<User> listUser = new ArrayList<>();
     
    String sql = "select u.userID as 'uID', u.userName, uff.userID from user u left join user_favorite_friend uff on u.userID=uff.friendID and uff.userID=? where u.userID!=?";
    connect();
     
    PreparedStatement statement = jdbcConnection.prepareStatement(sql);
    statement.setInt(1, uid);
    statement.setInt(2, uid);
    ResultSet resultSet = statement.executeQuery();
     
    while (resultSet.next()) {
    	int userID= resultSet.getInt("uID");
    	String userName = resultSet.getString("userName");
        boolean isfrnd = resultSet.getInt("userID") > 0 ? true : false;
         
        User user = new User(userID, userName, isfrnd);
        listUser.add(user);
    }
     
    resultSet.close();
    statement.close();
     
    disconnect();
     
    return listUser;
}

public boolean addFavUser(int userID, int friendID) throws SQLException {
    String sql = "INSERT INTO user_favorite_friend (userID, friendID) VALUES (?, ?)";
    connect();
     
    PreparedStatement statement = jdbcConnection.prepareStatement(sql);
    statement.setInt(1, userID);
    statement.setInt(2, friendID);
     
    boolean rowInserted = statement.executeUpdate() > 0;
    statement.close();
    
    disconnect();
    return rowInserted;
}

public boolean delFavUser(int userID, int friendID) throws SQLException {
    String sql = "DELETE from user_favorite_friend where userID=? and friendID=?";
    connect();
     
    PreparedStatement statement = jdbcConnection.prepareStatement(sql);
    statement.setInt(1, userID);
    statement.setInt(2, friendID);
     
    boolean rowDeleted = statement.executeUpdate() > 0;
    statement.close();
    
    disconnect();
    return rowDeleted;
}

public boolean addFavJoke(int userID, int jokeID) throws SQLException {
    String sql = "INSERT INTO user_favorite_joke (userID, jokeID) VALUES (?, ?)";
    connect();
     
    PreparedStatement statement = jdbcConnection.prepareStatement(sql);
    statement.setInt(1, userID);
    statement.setInt(2, jokeID);
     
    boolean rowInserted = statement.executeUpdate() > 0;
    statement.close();
    
    disconnect();
    return rowInserted;
}

public boolean delFavJoke(int userID, int jokeID) throws SQLException {
    String sql = "DELETE from user_favorite_joke where userID=? and jokeID=?";
    connect();
     
    PreparedStatement statement = jdbcConnection.prepareStatement(sql);
    statement.setInt(1, userID);
    statement.setInt(2, jokeID);
     
    boolean rowDeleted = statement.executeUpdate() > 0;
    statement.close();
    
    disconnect();
    return rowDeleted;
}

}
