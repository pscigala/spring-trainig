package pl.forcode.springtraining;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 * Created by Przemysław Ścigała on 28.03.2020.
 */
@Component
public class MyBeanWithoutPublicConstructorFactoryMethodComponent {

	@Bean
	@Scope(SCOPE_PROTOTYPE)
	public MyBeanWithoutPublicConstructor factoryMethod() {
		return new MyBeanWithoutPublicConstructor("nameFromFactoryMethod");
	}

	@Bean
	@Scope(SCOPE_PROTOTYPE)
	public MyBeanWithoutPublicConstructor factoryMethod(String name) {
		return new MyBeanWithoutPublicConstructor(name);
	}


	public class MyBeanWithoutPublicConstructor {
		private String name;

		private MyBeanWithoutPublicConstructor(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}
}

