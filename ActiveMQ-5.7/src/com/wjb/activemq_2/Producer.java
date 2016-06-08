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
		//ͨ��username,password,url�������ӹ����ӿ�
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
		try {
			//ͨ�����ӹ�������һ���µ����ӽӿ�
			Connection connection = connectionFactory.createConnection();
			connection.start();
			//ͨ�����ӽӿڴ���һ���Ự�ӿ�
			Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			//�Ự�ӿڴ����й������Ŀ��ӿ�
			Destination destination = session.createQueue(subject);
			//�Ự�ӿ��ٸ���Ŀ��ӿ�������һ����Ϣ�����߽ӿ�
			MessageProducer producer = session.createProducer(destination);
			for(int i = 0; i<=5; i++){
				//���ûỰ����һ���յ�map��Ϣ
				MapMessage message = session.createMapMessage();
				Date date = new Date();
				message.setLong("count", date.getTime());
				Thread.sleep(1000);
				//ͨ�������߽ӿ�Send����Ϣ������ActiveMQ������
				producer.send(message);
				System.out.println("--������Ϣ�� " + date);
			}
			session.commit();
			//�رջỰ
			session.close();
			//�ر�����
			connection.close();
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (InterruptedException e){
			e.printStackTrace();
		}
		
	}
}
