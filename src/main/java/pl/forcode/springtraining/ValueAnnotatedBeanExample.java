package pl.forcode.springtraining;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by Przemysław Ścigała on 05.04.2020.
 */
@Component
@Getter
public class ValueAnnotatedBeanExample {

	@Value("classpath:/exampleFile.txt")
	private Resource resourceInjectedByValueAnnotation;

	public String resourceAsString() {
		try (Reader reader = new InputStreamReader(resourceInjectedByValueAnnotation.getInputStream(), UTF_8)) {
			return FileCopyUtils.copyToString(reader);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
