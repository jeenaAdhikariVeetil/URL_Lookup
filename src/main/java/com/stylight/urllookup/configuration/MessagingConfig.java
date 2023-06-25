/**
 * 
 */
package com.stylight.urllookup.configuration;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.stylight.urllookup.constant.UrlConstant;

/**
 * @author Jeena A V
 *
 */

@Configuration
public class MessagingConfig {

	@Bean
	public Queue getParamQueue() {
		return new Queue(UrlConstant.PARAM_QUEUE);
	}

	@Bean
	public TopicExchange getParamexchange() {
		return new TopicExchange(UrlConstant.PARAM_TOPIC);
	}

	@Bean
	public Binding paramBinding(@Qualifier("getParamQueue") Queue queue,
			@Qualifier("getParamexchange") TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(UrlConstant.PARAM_KEY);
	}

	@Bean
	public Queue getPrettyQueue() {
		return new Queue(UrlConstant.PRETTY_QUEUE);
	}

	@Bean
	public TopicExchange getPrettyexchange() {
		return new TopicExchange(UrlConstant.PRETTY_TOPIC);
	}

	@Bean
	public Binding prettyBinding(@Qualifier("getPrettyQueue") Queue queue,
			@Qualifier("getPrettyexchange") TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(UrlConstant.PRETTY_KEY);
	}

	@Bean
	public MessageConverter converter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public AmqpTemplate template(ConnectionFactory connectionFcatory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFcatory);
		rabbitTemplate.setMessageConverter(converter());
		return rabbitTemplate;
	}
}
