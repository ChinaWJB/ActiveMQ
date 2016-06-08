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
 * 点对点模式
 */
public class JMSProducer {
	//默认连接用户名
	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
	//默认连接密码
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
	//默认连接地址
	private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;
	//发送的消息数量
	private static final int SENDNUM = 10;
	
	public static void main(String[] args){
		//连接工厂
		ActiveMQConnectionFactory connFactory;
		//连接
		Connection connection = null;
		//会话 接收挥着发送消息的线程
		Session session;
		//消息的目的地
		Destination destination;
		//消息的生产者
		MessageProducer messProducer;
		//实例化连接工厂
		connFactory = new ActiveMQConnectionFactory(JMSProducer.USERNAME, JMSProducer.PASSWORD, JMSProducer.BROKEURL);
		
		try{
			//通过连接工厂获得连接
			connection = connFactory.createConnection();
			//启动线程
			connection.start();
			//创建session
			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			//创建一个名称为HelloWorld的消息队列
			destination = session.createQueue("HelloWorld");
			//创建消息生产者
			messProducer = session.createProducer(destination);
			//发送者
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
			//创建一条文本消息
			TextMessage message = session.createTextMessage("ActiveMQ 发送消息： " + i);
			System.out.println("发送消息： ActiveMQ 发送消息：：：" + i);
			//通过消息生产者发出消息
			messProducer.send(message);
		}
	}
	
}
