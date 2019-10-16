package kr.ac.kopo.configuration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import kr.ac.kopo.configuration.SpringConfiguration;
import kr.ac.kopo.service.SampleService;
import kr.ac.kopo.entity.Sample;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
public class SpringConfigurationTest {

	@Autowired
	SampleService sampleService;

	@Test
	public void test() {
		Sample sample = new Sample();
		sample.setName("park");
		sample.setNumber(10);
		sampleService.save(sample);
	}

}
