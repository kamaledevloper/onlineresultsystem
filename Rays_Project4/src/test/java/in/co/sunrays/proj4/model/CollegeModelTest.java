package in.co.sunrays.proj4.model;

import static org.junit.Assert.*;

import org.junit.Test;

import in.co.sunrays.proj4.bean.CollegeBean;
import in.co.sunrays.proj4.exception.ApplicationException;

public class CollegeModelTest {

	@Test
	public void testNextPK() {

		CollegeModel model = new CollegeModel();

		CollegeBean bean = new CollegeBean();
		try {
			bean = model.findByPK(1);
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertNotEquals(bean, null);
	}

}
