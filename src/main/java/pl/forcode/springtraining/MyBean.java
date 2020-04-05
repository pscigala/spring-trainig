package pl.forcode.springtraining;

import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by Przemysław Ścigała on 26.03.2020.
 */
@Component
@Getter
public class MyBean {

	private String myBeanField = "myBeanFieldValue";
	private Integer postConstructCounter;

	public MyBean() {
	}

	public MyBean(String myBeanField) {
		this.myBeanField = myBeanField;
	}

	@PostConstruct
	public void postConstructOne() {
		postConstructCounter = 1;
	}

	@PostConstruct
	public void postConstructTwo() {
		postConstructCounter = 2;
	}

	@PostConstruct
	public void postConstructThree() {
		postConstructCounter = 3;
	}

}
