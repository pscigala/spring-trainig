package pl.forcode.springtraining.aop;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Przemysław Ścigała on 05.04.2020.
 */
@Component
@Getter
public class MetricsCollector {

	private final List<SimpleMetrics> metrics = new ArrayList<>();

	void collectMetric(long time, String method) {
		this.metrics.add(new SimpleMetrics(time, method));
	}

	@RequiredArgsConstructor
	@Getter
	public static class SimpleMetrics {

		private final long time;
		private final String method;

	}

}
