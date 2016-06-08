package testActiveMQConsumer;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 消息的消费者（接收者）
 * @author wjb
 * 
 */
public class JMSConsumer {
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;//默认连接用户名
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;//默认连接密码
    private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;//默认连接地址
    
    public static void main(String[] args) {
    	//创建工厂
    	ActiveMQConnectionFactory connFactory;
    	//创建连接
    	Connection connection = null;
    	//创建会话，接收或者发送消息的线程
    	Session session;
    	//消息的目的地
    	Destination destination;
    	//消息的消费者
    	MessageConsumer messageConsumer;
    	//实例化连接工厂
    	connFactory = new ActiveMQConnectionFactory(JMSConsumer.USERNAME, JMSConsumer.PASSWORD, JMSConsumer.BROKEURL);
    	
    	try{
    		//通过连接工厂获取连接
    		connection = connFactory.createConnection();
    		//启动连接
    		connection.start();
    		//创建session
    		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    		//创建一个连接HellowWorld的消息队列
    		destination = session.createQueue("HelloWorld");
    		//创建消息消费者
    		messageConsumer = session.createConsumer(destination);
    		
    		while(true){
    			TextMessage textMessage = (TextMessage)messageConsumer.receive(100000);
    			if(textMessage != null){
    				System.out.println("收到消息： " + textMessage.getText());
    			}else{
    				break;
    			}
    		}
    		
    	}catch(JMSException e){
    		e.printStackTrace();
    	}
    	
	}
    
}
