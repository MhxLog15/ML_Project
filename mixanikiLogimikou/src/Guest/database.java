package Guest;

import java.util.List;

public class database {
	java.sql.Connection connection = null;
	java.sql.Statement statement = null;
	java.sql.ResultSet result = null;

	public void NewConnection() {
		try {
			String url = "jdbc:mysql://127.0.0.1:3306";
			String user = "root";
			String password = "mixanikilogismikou2016";
			try {
				Class.forName("com.mysql.jdbc.Driver");
				connection = java.sql.DriverManager.getConnection(url, user, password);
				statement = connection.createStatement();
				result = statement.executeQuery("show databases");
				boolean bool = false;
				while (result.next()) {
					if (result.getString(1).equals("mixlog")) {
						bool = true;
						break;
					}
				}
				result.close();
				if (!bool) {
					statement.executeUpdate("CREATE DATABASE `mixlog` /*!40100 COLLATE 'greek_bin' */");
					statement.executeQuery("use mixlog");
					statement.executeUpdate(
							"CREATE TABLE spots (id INT NOT NULL AUTO_INCREMENT,name TEXT NOT NULL,descr TEXT NOT NULL,x INT NOT NULL,y INT NOT NULL,PRIMARY KEY (id)) COLLATE='greek_bin' ENGINE=InnoDB");
				}
				statement.executeQuery("use mixlog");
			} catch (java.sql.SQLException exception) {
				System.out.println(exception.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void GetSpots(List<spot> spotlist) {
		spot tempspot;
		try {
			result = statement.executeQuery("select * from spots");
			while (result.next()) {
				tempspot = new spot();
				tempspot.SetId(Integer.valueOf(result.getString(1)));
				tempspot.SetTitle(result.getString(2));
				tempspot.SetDescr(result.getString(3));
				tempspot.SetX(Integer.valueOf(result.getString(4)));
				tempspot.SetY(Integer.valueOf(result.getString(5)));
				spotlist.add(tempspot);
			}
			result.close();
		} catch (java.sql.SQLException exception) {
			System.out.println(exception.getMessage());
		}
	}

	public void CloseConnection() {
		try {
			if (result != null) {
				result.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (java.sql.SQLException exception) {
			System.out.println(exception.getMessage());
		}
	}
}
