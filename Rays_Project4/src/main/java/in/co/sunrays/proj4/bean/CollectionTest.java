package in.co.sunrays.proj4.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * @author Kamal
 *
 */
public class CollectionTest {

	public static void main(String[] args) {

		StudentBean bean = new StudentBean();

		StudentBean bean2 = new StudentBean();

		bean2.setFirstName("bhawna");
		bean.setFirstName("antar");
		bean.setLastName("gurjar");
		bean.setEmail("antar@gmail.com");
		bean.setMobileNo("9893954103");
		List c = new ArrayList();
		c.add(0, bean);
		c.add(1, bean2);
		System.out.println(c.size());
		StudentBean bean3 = (StudentBean) c.get(1);
		String name = bean3.getFirstName();
		System.out.println(name);
		
		c.remove(1);
		System.out.println(c.size());
		String name1 = bean3.getFirstName();
		
		System.out.println(name1);
	}
}
