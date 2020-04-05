package pl.forcode.springtraining.aware;

import lombok.Getter;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.stereotype.Component;

import static pl.forcode.springtraining.aware.MyBeanNameAware.MANUALLY_SET_BEAN_NAME;

/**
 * Created by Przemysław Ścigała on 04.04.2020.
 */
@Component(MANUALLY_SET_BEAN_NAME)
@Getter
public class MyBeanNameAware implements BeanNameAware {

	public static final String MANUALLY_SET_BEAN_NAME = "manuallySetBeanName";

	private String beanName;

	@Override
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

}
