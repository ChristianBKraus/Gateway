package jupiterpa.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import jupiterpa.gateway.filters.pre.LoggingFilter;

@EnableZuulProxy
@SpringBootApplication
public class Application  {
	
	public static void main(String args[]){
		SpringApplication.run(Application.class, args);
	}
	
	  @Bean
	  public LoggingFilter simpleFilter() {
	    return new LoggingFilter();
	  }	
}
