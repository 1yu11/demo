package xxxdemo.demo;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xxxdemo.demo.controller.PropertiesUtil;
import xxxdemo.demo.controller.PushCallback;
import xxxdemo.demo.pojo.PushPayload;

public class MqttPushClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(MqttPushClient.class);
    private static MqttClient client;

    public static MqttClient getClient() {
        return client;
    }

    public static void setClient(MqttClient client) {
        MqttPushClient.client = client;
    }
    private static volatile MqttPushClient mqttPushClient = null;

    public static MqttPushClient getInstance(){

        if(null == mqttPushClient){
            synchronized (MqttPushClient.class){
                if(null == mqttPushClient){
                    mqttPushClient = new MqttPushClient();
                }
            }

        }
        return mqttPushClient;

    }
    public MqttPushClient() {
        connect();
    }

    public void connect(){
        try {
            client = new MqttClient(PropertiesUtil.host, PropertiesUtil.clientid, new MemoryPersistence());

            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(false);
            options.setUserName(PropertiesUtil.username);
            options.setPassword(PropertiesUtil.password.toCharArray());
            options.setConnectionTimeout(PropertiesUtil.timeout);
            options.setKeepAliveInterval(PropertiesUtil.keepalive);
            try {
                client.setCallback(new PushCallback());
                client.connect(options);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发布，默认qos为0，非持久化
     * @param topic
     * @param pushMessage
     */
    public void publish(String topic, PushPayload pushMessage) {
        publish(0, false, topic, pushMessage);
    }


    /**
     * 发布
     * @param qos
     * @param retained
     * @param topic
     * @param pushMessage
     */
    public void publish(int qos, boolean retained, String topic, PushPayload pushMessage) {
        MqttMessage message = new MqttMessage();
        message.setQos(qos);
        message.setRetained(retained);
        message.setPayload(pushMessage.toString().getBytes());
        MqttTopic mTopic = client.getTopic(topic);

        if(null == mTopic){
            LOGGER.error("topic not exist");
        }
        MqttDeliveryToken token;
        try {
            token = mTopic.publish(message);
            token.waitForCompletion();
        } catch (MqttPersistenceException e) {
            e.printStackTrace();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 订阅某个主题，qos默认为0
     * @param topic
     */
    public void subscribe(String topic){
        subscribe(topic,2);
    }

    /**
     * 订阅某个主题
     * @param topic
     * @param qos
     */
    public void subscribe(String topic,int qos){
        try {
            MqttPushClient.getClient().subscribe(topic, qos);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws Exception {
        String kdTopic = "/World";
        PushPayload pushMessage = PushPayload.getPushPayloadBuider().setMobile("15345715326")
                .setContent("designModel")
                .bulid();
   MqttPushClient.getInstance().subscribe(kdTopic, 0);
     //MqttPushClient.getInstance().publish(0, false, kdTopic, pushMessage);
        //192.168.43.249
    }
}

