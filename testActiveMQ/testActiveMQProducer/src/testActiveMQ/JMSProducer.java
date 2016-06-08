package testActiveMQ;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * ��Ե�ģʽ
 */
public class JMSProducer {
	//Ĭ�������û���
	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
	//Ĭ����������
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
	//Ĭ�����ӵ�ַ
	private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;
	//���͵���Ϣ����
	private static final int SENDNUM = 10;
	
	public static void main(String[] args){
		//���ӹ���
		ActiveMQConnectionFactory connFactory;
		//����
		Connection connection = null;
		//�Ự ���ջ��ŷ�����Ϣ���߳�
		Session session;
		//��Ϣ��Ŀ�ĵ�
		Destination destination;
		//��Ϣ��������
		MessageProducer messProducer;
		//ʵ�������ӹ���
		connFactory = new ActiveMQConnectionFactory(JMSProducer.USERNAME, JMSProducer.PASSWORD, JMSProducer.BROKEURL);
		
		try{
			//ͨ�����ӹ����������
			connection = connFactory.createConnection();
			//�����߳�
			connection.start();
			//����session
			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			//����һ������ΪHelloWorld����Ϣ����
			destination = session.createQueue("HelloWorld");
			//������Ϣ������
			messProducer = session.createProducer(destination);
			//������
			sendMessage(session, messProducer);
			
			session.commit();			
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			if(connection != null){
				try{
					connection.close();
				}catch(JMSException e){
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public static void sendMessage(Session session, MessageProducer messProducer) throws JMSException{
		for(int i = 0; i<JMSProducer.SENDNUM; i++){
			//����һ���ı���Ϣ
			TextMessage message = session.createTextMessage("ActiveMQ ������Ϣ�� " + i);
			System.out.println("������Ϣ�� ActiveMQ ������Ϣ������" + i);
			//ͨ����Ϣ�����߷�����Ϣ
			messProducer.send(message);
		}
	}
	
}
