package net.javacode.junwen.jokearound;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
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
public List<User> listRegisterUsers() throws SQLException {
    List<User> listRegisterUsers = new ArrayList<>();
     
    String sql = "select userName, email from User";
    connect();
     
    PreparedStatement statement = jdbcConnection.prepareStatement(sql);
    ResultSet resultSet = statement.executeQuery(sql);
     
    while (resultSet.next()) {
    	String userName = resultSet.getString("userName");
        String email = resultSet.getString("email");
         
        User user = new User(userName, email);
        listRegisterUsers.add(user);
    }
     
    resultSet.close();
    statement.close();
     
    disconnect();
     
    return listRegisterUsers;
}

public int insertUser(User user) throws SQLException {
    String sql = "INSERT INTO user (userName, firstName, lastName, password, email) VALUES (?, ?, ?, ?, ?)";
    connect();
     
    PreparedStatement statement = jdbcConnection.prepareStatement(sql);
    statement.setString(1, user.getUsername());
    statement.setString(2, user.getFirstname());
    statement.setString(3, user.getLastname());
    statement.setString(4, user.password);
    statement.setString(5, user.email);
     
    boolean rowInserted = statement.executeUpdate() > 0;
    statement.close();
    
    int userID = 0;
    if(rowInserted) {
    sql = "select LAST_INSERT_ID() as userID";
    PreparedStatement statement2 = jdbcConnection.prepareStatement(sql);
    ResultSet resultSet = statement2.executeQuery(sql);
    
    if (resultSet.next())
    {
    	userID = resultSet.getInt("userID");
    }
    statement2.close();
    }
    
    disconnect();
    return userID;
}

public List<User> listStatUsers(String tag1, String tag2) throws SQLException {
    List<User> listStatUsers = new ArrayList<>();
     
    String sql = "select distinct userName, J1.createdDate from joke J1 join joke J2 on J1.userID = J2.userID join user u on u.userID = j1.userID and date(J1.createdDate) = date(J2.createdDate) and J1.jokeID <> J2.jokeID join joke_tag T1 on T1.jokeID = J1.jokeID join joke_tag T2 on T2.jokeID = J2.jokeID and T1.tag =? and T2.tag =?";
    connect();
     
    PreparedStatement statement = jdbcConnection.prepareStatement(sql);
    statement.setString(1, tag1);
    statement.setString(2, tag2);
     
    ResultSet resultSet = statement.executeQuery();
     
    while (resultSet.next()) {
    	String userName = resultSet.getString("userName");
        Date createdDate = resultSet.getDate("createdDate");
        User user = new User(userName, createdDate);
        listStatUsers.add(user);
    }
     
    resultSet.close();
    statement.close();
     
    disconnect();
     
    return listStatUsers;
}
public List<Joke> listHighlyJoke(String userID) throws SQLException {
    List<Joke> listJoke = new ArrayList<>();
     
    String sql = "select distinct J.*, R.score from joke J join joke_review R on J.jokeID = R.jokeID join user u on u.userID = j.userID where u.userName = ? and not exists (select count(*) as cnt from joke_review R1 where R1.jokeID = R.jokeID and R1.score in ('poor', 'fair') group by R1.jokeID  limit 1)";
    connect();
     
    PreparedStatement statement = jdbcConnection.prepareStatement(sql);
    statement.setString(1, userID);
    ResultSet resultSet = statement.executeQuery();
     
    while (resultSet.next()) {
        String title = resultSet.getString("title");
        String description = resultSet.getString("description");
        String createDate  = resultSet.getString("createdDate");
        String score  = resultSet.getString("score");
         
        Joke joke = new Joke(title, description, createDate,score);
        listJoke.add(joke);
    }
     
    resultSet.close();
    statement.close();
     
    disconnect();
     
    return listJoke;
}

public List<User> mostActiveUsers() throws SQLException {
    List<User> listActiveUsers = new ArrayList<>();
     
    String sql = "select u.userName, count(j.jokeID) as 'total' from joke j, user u where j.createdDate >= '2018-03-01' and j.userID = u.userID group by j.userID having count(j.jokeID) = ( select count(jokeID) as cnt from joke  where createdDate >= '2018-03-01' group by userID order by cnt desc limit 1)";
    connect();
     
    PreparedStatement statement = jdbcConnection.prepareStatement(sql);
    ResultSet resultSet = statement.executeQuery();
     
    while (resultSet.next()) {
    	String userName = resultSet.getString("userName");
        int total = resultSet.getInt("total");
        User user = new User(userName, total);
        listActiveUsers.add(user);
    }
     
    resultSet.close();
    statement.close();
     
    disconnect();
     
    return listActiveUsers;
}

public List<String> checkCommonUsers(String uname1, String uname2) throws SQLException {
    List<String> listUsers = new ArrayList<>();
     
    String sql = "select distinct u3.userName from user_favorite_friend F1 join user_favorite_friend F2 on F1.friendID = F2.friendID join user u1 on F1.userID = u1.userID join user u2 on F2.userID = u2.userID join user u3 on F1.friendID = u3.userID where u1.userName = ? and u2.userName=?;";
    connect();
     
    PreparedStatement statement = jdbcConnection.prepareStatement(sql);
    statement.setString(1, uname1);
    statement.setString(2, uname2);
    ResultSet resultSet = statement.executeQuery();
     
    while (resultSet.next()) {
    	String userName = resultSet.getString("userName");
        listUsers.add(userName);
    }
     
    resultSet.close();
    statement.close();
     
    disconnect();
     
    return listUsers;
}

public List<String> listNeverPstExcJUsers() throws SQLException {
    List<String> listUsers = new ArrayList<>();
     
    String sql = "select distinct u.userName from joke j , user u where j.userID=u.userID and j.jokeID not in (select jokeID from joke_review where score = 'excellent' group by jokeID having count(userID) >=3);";
    connect();
     
    PreparedStatement statement = jdbcConnection.prepareStatement(sql);
    ResultSet resultSet = statement.executeQuery();
     
    while (resultSet.next()) {
    	String userName = resultSet.getString("userName");
        listUsers.add(userName);
    }
     
    resultSet.close();
    statement.close();
     
    disconnect();
     
    return listUsers;
}

public List<String> userNeverPstPoorR() throws SQLException {
    List<String> listUsers = new ArrayList<>();
     
    String sql = "select distinct u.userName from joke_review R, user u where R.userID=u.userID and R.userID not in (select R1.userID from joke_review R1 where R1.score = 'poor')";
    connect();
     
    PreparedStatement statement = jdbcConnection.prepareStatement(sql);
    ResultSet resultSet = statement.executeQuery();
     
    while (resultSet.next()) {
    	String userName = resultSet.getString("userName");
        listUsers.add(userName);
    }
     
    resultSet.close();
    statement.close();
     
    disconnect();
     
    return listUsers;
}

public List<String> userAlwaysPstPoorR() throws SQLException {
    List<String> listUsers = new ArrayList<>();
     
    String sql = "select distinct u.userName from user u, joke_review R where R.userID=u.userID and R.userID not in (select R1.userID from joke_review R1 where R1.score <> 'poor')";
    connect();
     
    PreparedStatement statement = jdbcConnection.prepareStatement(sql);
    ResultSet resultSet = statement.executeQuery();
     
    while (resultSet.next()) {
    	String userName = resultSet.getString("userName");
        listUsers.add(userName);
    }
     
    resultSet.close();
    statement.close();
     
    disconnect();
     
    return listUsers;
}

public List<String> userNeverRecPoorR() throws SQLException {
    List<String> listUsers = new ArrayList<>();
     
    String sql = "select distinct u.userName from joke j, user u where j.userID=u.userID and j.userID not in (select J1.userID from joke_review R1 join joke J1 on J1.jokeID = R1.jokeID where R1.score = 'poor')";
    connect();
     
    PreparedStatement statement = jdbcConnection.prepareStatement(sql);
    ResultSet resultSet = statement.executeQuery();
     
    while (resultSet.next()) {
    	String userName = resultSet.getString("userName");
        listUsers.add(userName);
    }
     
    resultSet.close();
    statement.close();
     
    disconnect();
     
    return listUsers;
}

public List<UserPair> userPairExcellentR() throws SQLException {
    List<UserPair> listUsers = new ArrayList<>();
     
    String sql = "with cte as (select distinct R.userID as 'reviewerID', J.userID from joke_review R join joke J on J.jokeID = R.jokeID where (select count(J2.jokeID) from joke_review R2 join joke J2 on J2.jokeID = R2.jokeID where 1=1 and J2.userID = J.userID and R2.userID = R.userID and R2.score = 'excellent') = (select count(distinct J3.jokeID) from joke_review R3 join joke J3 on J3.jokeID = R3.jokeID where 1=1 and J3.userID = J.userID) and (select count(R2.jokeID) from joke_review R2 join joke J2 on J2.jokeID = R2.jokeID where 1=1 and R.userID = J2.userID) > 0 and (select count(J2.jokeID) from joke_review R2 join joke J2 on J2.jokeID = R2.jokeID where 1=1 and J2.userID = R.userID and R2.userID = J.userID and R2.score = 'excellent') = (select count(distinct J3.jokeID) from joke_review R3 join joke J3 on J3.jokeID = R3.jokeID where 1=1 and J3.userID = R.userID))select distinct if(u1.userName > u2.userName, u1.userName, u2.userName) as reviewerName, if(u1.userName > u2.userName, u2.userName, u1.userName) as userName from cte as T1 join cte as T2  on T1.reviewerID = T2.userID  and T1.userID = T2.reviewerID join user u1 on T1.reviewerID = u1.userID join user u2 on T1.userID = u2.userID";
    connect();
     
    PreparedStatement statement = jdbcConnection.prepareStatement(sql);
    ResultSet resultSet = statement.executeQuery();
     
    while (resultSet.next()) {
    	String userName = resultSet.getString("userName");
    	String reviewerName = resultSet.getString("reviewerName");
    	UserPair upair = new UserPair(userName, reviewerName);
        listUsers.add(upair);
    }
     
    resultSet.close();
    statement.close();
     
    disconnect();
     
    return listUsers;
}
}
