package insigma.test.dubbo;

import star.fw.ServiceLocator;
import org.junit.Test;

public class TestDubbo {

	@Test
	public void cps_services_config() throws InterruptedException {
		System.setProperty("spring.configfile", "spring-modules.xml");
		ServiceLocator.start();
		System.out.println("start...");
		while (true) {
			Thread.sleep(100000);
		}
	}

}
