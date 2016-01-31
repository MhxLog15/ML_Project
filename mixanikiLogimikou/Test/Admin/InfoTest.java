package Admin;
import junit.framework.Assert;

import org.junit.Test;

import Admin.InfoView;
import Admin.spot;

public class InfoTest {

	@Test
	public void main() {
		spot tempspot = new spot();
		InfoView info = new InfoView();
		tempspot.SetId(0);
		tempspot.SetTitle("TestSpot1Name");
		tempspot.SetDescr("TestSpot1Descr");
		try{
		info.main(123, 456, tempspot);
		Assert.assertEquals(true,true);
		}catch(Exception e )
		{
			Assert.assertEquals(true,false);
		}
	}

}
