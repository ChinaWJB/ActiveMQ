package com.wjb.activemq_2;

import java.util.Date;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer {
	public static void main(String[] args) {		
//		String user = ActiveMQConnection.DEFAULT_USER;
//		String password = ActiveMQConnection.DEFAULT_PASSWORD;
//		String url = ActiveMQConnection.DEFAULT_BROKER_URL;
		String subject = "TOOL.DEFAULT";
		//通过username,password,url创建连接工厂接口
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
		try {
			//通过连接工厂创建一个新的连接接口
			Connection connection = connectionFactory.createConnection();
			connection.start();
			//通过连接接口创建一个会话接口
			Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			//会话接口创建有关主题的目标接口
			Destination destination = session.createQueue(subject);
			//会话接口再根据目标接口来创建一个消息生产者接口
			MessageProducer producer = session.createProducer(destination);
			for(int i = 0; i<=5; i++){
				//调用会话生成一个空的map消息
				MapMessage message = session.createMapMessage();
				Date date = new Date();
				message.setLong("count", date.getTime());
				Thread.sleep(1000);
				//通过生产者接口Send将消息发布到ActiveMQ服务器
				producer.send(message);
				System.out.println("--发送消息： " + date);
			}
			session.commit();
			//关闭会话
			session.close();
			//关闭连接
			connection.close();
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (InterruptedException e){
			e.printStackTrace();
		}
		
	}
}
