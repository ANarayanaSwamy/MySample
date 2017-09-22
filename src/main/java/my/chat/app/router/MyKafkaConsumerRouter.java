package my.chat.app.router;

import my.chat.app.data.MessageCache;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaComponent;
import org.apache.camel.component.properties.PropertiesComponent;
import org.springframework.stereotype.Component;

@Component
public class MyKafkaConsumerRouter extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		PropertiesComponent pc = getContext().getComponent("properties",
				PropertiesComponent.class);
		pc.setLocation("classpath:application.properties");
		KafkaComponent kafkaComponent = (KafkaComponent) getContext()
				.getComponent("kafka");
		kafkaComponent.setBrokers("{{kafka.host}}:{{kafka.port}}");
		Processor cacheProcesser = new MyCacheProcesser();
		from("kafka:{{my.chat.topic}}?groupId={{consumer.group}}")
				.routeId("FromKafka").process(cacheProcesser).log("${body}");
	}
}

class MyCacheProcesser implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		String message = (String) exchange.getIn().getBody();
		MessageCache.cache(message);
	}

}