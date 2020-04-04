package pl.forcode.springtraining.factory;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Created by Przemysław Ścigała on 04.04.2020.
 */
@Getter
public class BeanCreatedByCustomFactory {

	private LocalDateTime creationDateTime;
	private String factoryName;

	BeanCreatedByCustomFactory(LocalDateTime creationDateTime, String factoryName) {
		this.creationDateTime = creationDateTime;
		this.factoryName = factoryName;
	}
}
