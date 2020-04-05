package pl.forcode.springtraining.factory;

import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.stereotype.Component;

import static java.time.LocalDateTime.now;

/**
 * Created by Przemysław Ścigała on 04.04.2020.
 */
@Component
public class CustomFactoryBean extends AbstractFactoryBean<BeanCreatedByCustomFactory> {

	@Override
	public Class<BeanCreatedByCustomFactory> getObjectType() {
		return BeanCreatedByCustomFactory.class;
	}

	@Override
	protected BeanCreatedByCustomFactory createInstance() throws Exception {
		return new BeanCreatedByCustomFactory(now(), this.getClass().getName());
	}


}
