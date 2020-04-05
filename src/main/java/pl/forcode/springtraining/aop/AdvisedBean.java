package pl.forcode.springtraining.aop;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by Przemysław Ścigała on 05.04.2020.
 */
@Component
@Slf4j
@Getter
@Setter
public class AdvisedBean {

	private boolean workFinished;

	void doSomeWork() {
		log.info("Im doing some important work...");

		int count = 0;
		for (int i = 0; i < 10; i++) {
			count++;
		}
		workFinished = true;
		log.info("Work finished.");

	}
}
