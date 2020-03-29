package pl.forcode.springtraining;

import org.springframework.stereotype.Component;

/**
 * Created by Przemysław Ścigała on 26.03.2020.
 */
@Component
public class MyBean {

	String myBeanField = "myBeanFieldValue";

	public MyBean() {
	}

	public MyBean(String myBeanField) {
		this.myBeanField = myBeanField;
	}

}
