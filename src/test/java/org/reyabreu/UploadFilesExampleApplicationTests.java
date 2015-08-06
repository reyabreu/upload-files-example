package org.reyabreu;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.reyabreu.UploadFilesExampleApplication;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = UploadFilesExampleApplication.class)
@WebAppConfiguration
public class UploadFilesExampleApplicationTests {

	@Test
	public void contextLoads() {
	}

}
