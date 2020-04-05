package pl.forcode.springtraining.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * Created by Przemysław Ścigała on 05.04.2020.
 */
@Aspect
@RequiredArgsConstructor
@Component
@Slf4j
public class MyAspect {

	private final MetricsCollector metricsCollector;

	@Around("execution(* pl.forcode.springtraining.aop.AdvisedBean.doSomeWork())")
	void collectMetricsAroundSpecificMethod(ProceedingJoinPoint pjp) {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start(pjp.getSignature().getName());
		String method = pjp.getSignature().getName();
		log.info("Before Proceeed");
		try {
			pjp.proceed();
		} catch (Throwable throwable) {
			method = "Execption-" + method;
			log.info("Exception occured");
			throwable.printStackTrace();
		} finally {
			stopWatch.stop();
			log.info("Aspect stopwatch Stop");
			metricsCollector.collectMetric(stopWatch.getLastTaskTimeMillis(), method);
		}
	}


}
