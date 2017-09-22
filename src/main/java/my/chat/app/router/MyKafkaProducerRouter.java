package my.chat.app.router;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaComponent;
import org.apache.camel.component.properties.PropertiesComponent;
import org.springframework.stereotype.Component;

@Component
public class MyKafkaProducerRouter extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		PropertiesComponent pc = getContext().getComponent("properties",
				PropertiesComponent.class);
		pc.setLocation("classpath:application.properties");
		KafkaComponent kafkaComponent = (KafkaComponent) getContext().getComponent("kafka");
		kafkaComponent.setBrokers("{{kafka.host}}:{{kafka.port}}");
		from("direct:kafkaStart").routeId("ToKafka")
				.to("kafka:{{my.chat.topic}}").log("${headers}");
	}
}
