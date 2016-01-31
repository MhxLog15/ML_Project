package Admin;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import Admin.database;
import Admin.spot;

public class databaseTest {
	@Test
	public void CloseConnection() {
		database database = new database();
		List<spot> spotList1 = new ArrayList<spot>();
		database.NewConnection();
		database.GetSpots(spotList1);
		int i = spotList1.size();
		database.CloseConnection();
		List<spot> spotList2 = new ArrayList<spot>();
		database.GetSpots(spotList2);
		if (i > spotList2.size())
			Assert.assertEquals(true, true);
		else
			fail("Connection ERROR");
	}

	@Test
	public void GetSpots() {
		database database = new database();
		List<spot> spotlist = new ArrayList<spot>();
		database.NewConnection();
		database.CloseConnection();
		database.GetSpots(spotlist);
		assertEquals("Result:", 0, spotlist.size());
	}
}
