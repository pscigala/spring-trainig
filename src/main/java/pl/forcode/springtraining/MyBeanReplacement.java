package pl.forcode.springtraining;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by Przemysław Ścigała on 26.03.2020.
 */
@Component
public class MyBeanReplacement {

	String myBeanField = "myBeanFieldValue";

	public MyBeanReplacement() {
	}

	public MyBeanReplacement(String myBeanField, String arg2) {
		this.myBeanField = myBeanField;
	}
}
