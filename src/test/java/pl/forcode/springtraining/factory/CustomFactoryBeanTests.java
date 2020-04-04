package pl.forcode.springtraining.factory;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Created by Przemysław Ścigała on 04.04.2020.
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CustomFactoryBeanTests {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ApplicationContext ctx;

	@Test
	void custom_bean_factory_works() {
		BeanCreatedByCustomFactory bean = ctx.getBean(BeanCreatedByCustomFactory.class);

		assertNotNull(bean);
		assertNotNull(bean.getCreationDateTime());
		assertEquals(CustomFactoryBean.class.getName(), bean.getFactoryName());
	}


	@Test
	void why_bean_created_by_custom_factory_has_its_name_instead_its_own_name() {
		Object customFactoryBean = ctx.getBean("customFactoryBean");

		assertThat(customFactoryBean, instanceOf(BeanCreatedByCustomFactory.class));
		assertThat(customFactoryBean, not(instanceOf(CustomFactoryBean.class)));
		log.info("That is little confusing. Isn't it?");
	}

	@Test
	void what_is_default_name_of_the_custom_factory_bean() {
		List<String> customFactoryBeanNames = Arrays.asList(ctx.getBeanNamesForType(CustomFactoryBean.class));

		assertNotNull(customFactoryBeanNames);
		assertThat(customFactoryBeanNames, is(not(empty())));
		log.info("Tadaam! Default bean name for custom factory is: {}", customFactoryBeanNames.get(0));
	}


	@Test
	void custom_factory_bean_name_is_prefixed_with_ampersand() {
		List<String> customFactoryBeanNames = Arrays.asList(ctx.getBeanNamesForType(CustomFactoryBean.class));

		assertNotNull(customFactoryBeanNames);
		assertThat(customFactoryBeanNames, is(not(empty())));

		assertThat(customFactoryBeanNames.get(0), startsWith("&"));
	}

	@Test
	void should_return_custom_factory_bean_by_name_prefixed_with_ampersand() {
		Object notRealCustomFactoryBean = ctx.getBean("customFactoryBean");
		Object realCustomFactoryBean = ctx.getBean("&customFactoryBean");

		assertNotNull(notRealCustomFactoryBean);
		assertThat(notRealCustomFactoryBean, not(instanceOf(CustomFactoryBean.class)));

		assertNotNull(realCustomFactoryBean);
		assertThat(realCustomFactoryBean, instanceOf(CustomFactoryBean.class));
	}

}