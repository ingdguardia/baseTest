import java.util.List;

import org.testng.TestNG;
import org.testng.collections.Lists;

public class TestRunner {

	public static void main(String[] args) {

		TestNG testNG = new TestNG();
		List<String> suites = Lists.newArrayList();
		// System.out.println(System.getProperty("user.dir"));
		suites.add(System.getProperty("user.dir") + "/testNG_ProjectName.xml");// path to xml.."
		// suites.add(System.getProperty("user.dir") + "\\testNG_ProjectName2.xml");
		testNG.setTestSuites(suites);
		testNG.run();
	}
}
