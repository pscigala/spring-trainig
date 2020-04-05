package pl.forcode.springtraining;

import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Przemysław Ścigała on 26.03.2020.
 */
@Component
@Getter
public class MyBean {

	private String myBeanField = "myBeanFieldValue";
	private List<Integer> postConstructCounter = new ArrayList<>();

	public MyBean() {
	}

	public MyBean(String myBeanField) {
		this.myBeanField = myBeanField;
	}

	@PostConstruct
	public void postConstructOne() {
		postConstructCounter.add(1);
	}

	@PostConstruct
	public void postConstructTwo() {
		postConstructCounter.add(2);
	}

	@PostConstruct
	public void postConstructThree() {
		postConstructCounter.add(3);
	}

}
