/**
 * @Title: RoutingSendDirect.java
 * @Package com.rabbitmq.producer
 * @Description: TODO
 * Copyright: Copyright (c) 2016 
 * Company:AutoHome
 * 
 * @author Comsys-Administrator
 * @date 2016年11月3日 下午4:28:55
 * @version V1.0
 */
package com.rabbitmq.producer;

import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author Administrator
 *
 */
public class RoutingSendDirect {
	private static String EXCHANGE_NAME = "direct_logs";
	//路由关键字
	private static final String[] routingKeys = new String[]{"info","warning","error","debug"};
	private static final String HOST = "10.168.11.166";
	private static final String USERNAME = "admin";
	private static final String PASSWORD = "admin1234";
	private static final int PORT = 5672;
	public static void main(String[] args) throws Exception, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(HOST);
		factory.setPort(PORT);
		factory.setUsername(USERNAME);
		factory.setPassword(PASSWORD);
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		//声明交换器
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");
		/*for (int i = 0; i < 10000; i++) {
			String message = "Hello World!" +i;
			channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
			System.out.println("[x] Sent " + message + "");
		}*/
		for(String severity : routingKeys){
			String message = "Send the message level:" + severity;
			channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes());
			System.out.println("[x] Sent " +severity+ ":" + message + "");
		}
		channel.close();
		connection.close();
	}
}
