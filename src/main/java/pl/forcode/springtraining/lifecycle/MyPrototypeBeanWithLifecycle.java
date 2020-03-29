package pl.forcode.springtraining.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by Przemysław Ścigała on 29.03.2020.
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MyPrototypeBeanWithLifecycle implements InitializingBean, DisposableBean {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private State state;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.state = State.AFTER_PROPERTIES_SET;
		log.info("Bean state: {}", this.state);
	}

	@Override
	public void destroy() throws Exception {
		this.state = State.DESTROY;
		log.info("Bean state: {}", this.state);
	}

	public State getState() {
		return this.state;
	}

	public enum State {
		AFTER_PROPERTIES_SET,
		DESTROY
	}
}
