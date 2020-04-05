package pl.forcode.springtraining;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.test.annotation.DirtiesContext;
import pl.forcode.springtraining.MyBeanWithoutPublicConstructorFactoryMethodComponent.MyBeanWithoutPublicConstructor;
import pl.forcode.springtraining.lifecycle.MyPrototypeBeanWithLifecycle;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static pl.forcode.springtraining.lifecycle.MyPrototypeBeanWithLifecycle.State.AFTER_PROPERTIES_SET;
import static pl.forcode.springtraining.lifecycle.MyPrototypeBeanWithLifecycle.State.DESTROY;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class SpringTrainingApplicationTests {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ApplicationContext ctx;

	@Autowired
	BeanFactory beanFactory;

	@Test
	void application_ctx_injected() {
		assertNotNull(ctx);
	}


	@Test
	void get_bean_definition_names() {
		List<String> beanDefinitionNames = Arrays.asList(ctx.getBeanDefinitionNames());

		assertThat(beanDefinitionNames, is(not(empty())));
	}

	@Test
	void get_my_bean_by_class() {
		MyBean myBean = ctx.getBean(MyBean.class);
		assertNotNull(myBean);
	}

	@Test
	void get_prototype_beans_without_public_constructor_by_factory_method() {
		MyBeanWithoutPublicConstructor myBeanOne = ctx.getBean(MyBeanWithoutPublicConstructor.class);
		MyBeanWithoutPublicConstructor myBeanTwo = ctx.getBean(MyBeanWithoutPublicConstructor.class);

		assertNotNull(myBeanOne);
		assertNotNull(myBeanTwo);
		assertEquals("nameFromFactoryMethod", myBeanOne.getName());
		assertEquals("nameFromFactoryMethod", myBeanTwo.getName());
		assertNotEquals(myBeanOne, myBeanTwo);
	}

	@Test
	void get_prototype_beans_without_public_constructor_by_factory_method_with_args() {
		String name = "nameFromTest";
		MyBeanWithoutPublicConstructor bean = ctx.getBean(MyBeanWithoutPublicConstructor.class, name);

		assertNotNull(bean);
		assertEquals(name, bean.getName());
	}

	@Test
	void should_remove_my_bean_definition_from_bean_registry() {
		MyBean myBeanBeforeRemove = ctx.getBean(MyBean.class);
		BeanDefinitionRegistry beanDefinitionRegistry = (BeanDefinitionRegistry) beanFactory;

		beanDefinitionRegistry.removeBeanDefinition("myBean");

		assertNotNull(myBeanBeforeRemove);
		assertThrows(NoSuchBeanDefinitionException.class, () -> ctx.getBean(MyBean.class));
	}

	@Test
	void should_register_my_bean_as_new_bean() {
		MyBean originalBean = ctx.getBean(MyBean.class);
		AbstractBeanDefinition newBeanDefinition = BeanDefinitionBuilder.genericBeanDefinition(MyBean.class)
				.getBeanDefinition();

		((BeanDefinitionRegistry) beanFactory).registerBeanDefinition("newMyBean", newBeanDefinition);
		Object newMyBean = ctx.getBean("newMyBean");

		assertNotNull(newMyBean);
		assertNotEquals(originalBean, newMyBean);
		assertThat(newMyBean, instanceOf(MyBean.class));
	}


	@Test
	void should_register_my_bean_as_new_bean_and_throw_no_unique_exception_when_resolved_first_time_by_class() {
		AbstractBeanDefinition newBeanDefinition = BeanDefinitionBuilder.genericBeanDefinition(MyBean.class)
				.getBeanDefinition();

		((BeanDefinitionRegistry) beanFactory).registerBeanDefinition("newMyBean", newBeanDefinition);

		assertNotNull(ctx.getBean("newMyBean"));
		assertThrows(NoUniqueBeanDefinitionException.class, () -> ctx.getBean(MyBean.class));
	}

	@Test
	void should_internal_cache_bean_definition_by_resolution_type() {
		AbstractBeanDefinition newBeanDefinition = BeanDefinitionBuilder.genericBeanDefinition(MyBean.class)
				.getBeanDefinition();

		ctx.getBean(MyBean.class); //the bean will be cached in internal metadata cache
		((BeanDefinitionRegistry) beanFactory).registerBeanDefinition("newMyBean", newBeanDefinition);

		assertNotNull(ctx.getBean("newMyBean"));
		assertDoesNotThrow(() -> ctx.getBean(MyBean.class));
	}

	@Test
	void should_clear_internal_bean_definition_cache_after_registration_of_new_bean_and_throw_no_unique_bean_exception() {
		AbstractBeanDefinition newBeanDefinition = BeanDefinitionBuilder.genericBeanDefinition(MyBean.class)
				.getBeanDefinition();

		ctx.getBean(MyBean.class); //the bean will be cached in internal metadata cache
		((BeanDefinitionRegistry) beanFactory).registerBeanDefinition("newMyBean", newBeanDefinition);
		((DefaultListableBeanFactory) beanFactory).clearMetadataCache();//this time lets clear metadata cache

		assertNotNull(ctx.getBean("newMyBean"));
		assertThrows(NoUniqueBeanDefinitionException.class, () -> ctx.getBean(MyBean.class));
	}

	@Test
	void lets_count_beans_because_we_can() {
		int count = BeanFactoryUtils.countBeansIncludingAncestors((ListableBeanFactory) beanFactory);
		log.info("Beans count {}, count", count);
	}

	@Test
	void should_invoke_after_properties_set_for_each_prototype_instance() {
		MyPrototypeBeanWithLifecycle beanOne = ctx.getBean(MyPrototypeBeanWithLifecycle.class);
		MyPrototypeBeanWithLifecycle beanTwo = ctx.getBean(MyPrototypeBeanWithLifecycle.class);

		assertEquals(AFTER_PROPERTIES_SET, beanOne.getState());
		assertEquals(AFTER_PROPERTIES_SET, beanTwo.getState());
	}

	@Test
	void destroy_does_not_apply_for_prototype_beans_after_remove() {
		MyPrototypeBeanWithLifecycle beanWithLifecycle = ctx.getBean(MyPrototypeBeanWithLifecycle.class);
		BeanDefinitionRegistry beanDefinitionRegistry = (BeanDefinitionRegistry) beanFactory;
		beanDefinitionRegistry.removeBeanDefinition("myPrototypeBeanWithLifecycle");

		assertNotEquals(DESTROY, beanWithLifecycle.getState());
	}

	@Test
	void multiple_post_construct_works() {
		MyBean bean = ctx.getBean(MyBean.class);

		assertNotNull(bean.getPostConstructCounter());
	}

	@Test
	void inject_file_as_resources_using_value_annotation() {
		ValueAnnotatedBeanExample bean = ctx.getBean(ValueAnnotatedBeanExample.class);
		Resource exampleTemplateResource = bean.getResourceInjectedByValueAnnotation();
		String resourceAsString = bean.resourceAsString();

		assertNotNull(exampleTemplateResource);
		assertNotNull(resourceAsString);
		assertEquals("exampleFile", resourceAsString);
	}

}
