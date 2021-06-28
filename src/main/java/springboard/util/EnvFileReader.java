package springboard.util;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.io.support.ResourcePropertySource;

public class EnvFileReader {
	
	public static String getValue(String envFile, String keyName) {
		
		// 스프링 컨테이너 생성
		ConfigurableApplicationContext ctx =
			new GenericApplicationContext();
		
		// Environment 생성
		ConfigurableEnvironment env = ctx.getEnvironment();
		MutablePropertySources propertySources =
			env.getPropertySources();
		
		String envStr = "";
		
		try {
			// 매개변수를 통해 파일 경로 설정 및 속성 읽어오기
			String envPath = "classpath:"+envFile;
			propertySources.addLast(new ResourcePropertySource(envPath));
			
			// 프로퍼티 파일의 키값을 이용해서 설정값 읽어오기
			envStr = env.getProperty(keyName);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return envStr;
		
	}
}
