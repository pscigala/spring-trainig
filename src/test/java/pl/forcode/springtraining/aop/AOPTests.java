package pl.forcode.springtraining.aop;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import pl.forcode.springtraining.aop.MetricsCollector.SimpleMetrics;

import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Przemysław Ścigała on 05.04.2020.
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AOPTests {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ApplicationContext ctx;

	@Test
	void should_log_execution_time_using_around_advice() {
		AdvisedBean bean = ctx.getBean(AdvisedBean.class);
		MetricsCollector metricsCollector = ctx.getBean(MetricsCollector.class);

		bean.doSomeWork();
		List<SimpleMetrics> metrics = metricsCollector.getMetrics();

		assertTrue(bean.isWorkFinished());
		assertThat(metrics, is(not(empty())));
	}


}
