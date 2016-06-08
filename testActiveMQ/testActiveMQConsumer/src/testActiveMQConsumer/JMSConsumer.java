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
 * ��Ϣ�������ߣ������ߣ�
 * @author wjb
 * 
 */
public class JMSConsumer {
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;//Ĭ�������û���
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;//Ĭ����������
    private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;//Ĭ�����ӵ�ַ
    
    public static void main(String[] args) {
    	//��������
    	ActiveMQConnectionFactory connFactory;
    	//��������
    	Connection connection = null;
    	//�����Ự�����ջ��߷�����Ϣ���߳�
    	Session session;
    	//��Ϣ��Ŀ�ĵ�
    	Destination destination;
    	//��Ϣ��������
    	MessageConsumer messageConsumer;
    	//ʵ�������ӹ���
    	connFactory = new ActiveMQConnectionFactory(JMSConsumer.USERNAME, JMSConsumer.PASSWORD, JMSConsumer.BROKEURL);
    	
    	try{
    		//ͨ�����ӹ�����ȡ����
    		connection = connFactory.createConnection();
    		//��������
    		connection.start();
    		//����session
    		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    		//����һ������HellowWorld����Ϣ����
    		destination = session.createQueue("HelloWorld");
    		//������Ϣ������
    		messageConsumer = session.createConsumer(destination);
    		
    		while(true){
    			TextMessage textMessage = (TextMessage)messageConsumer.receive(100000);
    			if(textMessage != null){
    				System.out.println("�յ���Ϣ�� " + textMessage.getText());
    			}else{
    				break;
    			}
    		}
    		
    	}catch(JMSException e){
    		e.printStackTrace();
    	}
    	
	}
    
}
