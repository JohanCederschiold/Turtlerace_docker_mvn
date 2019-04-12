package dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import service.Racecontender;

public class Database {
	
	
	public void registerResults(List <Racecontender> contenders )  {
		
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver problem");
			e.printStackTrace();
		}
		Properties properties = new Properties();
		properties.setProperty("user", "postgres");
		
		Connection connection;
		try {
			connection = DriverManager.getConnection("jdbc:postgresql://postgres/", properties);
//		Skapa en tabell om ingen redan existerar
			Statement statement = connection.createStatement();
			statement.execute("create table if not exists raceresults (Turtlename text, Position integer)");
			
			for (int i = 0 ; i < contenders.size() ; i++ ) {
//		Lägg till poster
				PreparedStatement prepStatement = connection.prepareStatement("insert into raceresults values(? , ?)");
				prepStatement.setString(1, contenders.get(i).getTurtle().getName());
				prepStatement.setInt(2, contenders.get(i).getFinishingPosition());
				prepStatement.execute();
			}
			
			connection.close();
		} catch (SQLException e) {
			System.out.println("Some problem with SQL");
			e.printStackTrace();
		}
		
		
		
	}
	
	public List<Racecontender> getAllResults() {
		
		return null;
		
	}

}

