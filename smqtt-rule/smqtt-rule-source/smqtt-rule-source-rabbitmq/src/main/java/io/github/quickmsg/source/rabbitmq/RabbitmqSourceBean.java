package io.github.quickmsg.source.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.github.quickmsg.common.rule.source.Source;
import io.github.quickmsg.common.rule.source.SourceBean;
import io.github.quickmsg.common.utils.JacksonUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * rabbitmq source
 * @author leafseelight
 * @date 2021/10/09
 */
@Slf4j
public class RabbitmqSourceBean implements SourceBean {

    /**
     * 连接对象
     */
    private Connection connection = null;
    /**
     * 消息队列
     */
    private Map<String, Channel> channelHashMap = new HashMap<>();


    @Override
    public Boolean support(Source source) {
        return source == Source.RABBIT_MQ;
    }

    /**
     * 初始化rocketmq
     *
     * @param sourceParam 参数
     * @return {@link Boolean}
     */
    @Override
    public Boolean bootstrap(Map<String, Object> sourceParam) {
        try {
            //创建连接工厂
            ConnectionFactory factory = new ConnectionFactory();
            factory.setAutomaticRecoveryEnabled(true);
            factory.setTopologyRecoveryEnabled(true);
            factory.setNetworkRecoveryInterval(3000);
            //设置RabbitMQ相关信息
            factory.setHost(sourceParam.get("host").toString());
            factory.setPort(Integer.parseInt(sourceParam.get("port").toString()));
            factory.setUsername(sourceParam.get("username").toString());
            factory.setPassword(sourceParam.get("password").toString());
            //创建一个新的连接
            connection = factory.newConnection();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 转发数据
     *
     * @param object 对象
     * @return {@link Object}
     */
    @Override
    public void transmit(Map<String, Object> object) {
        String json = JacksonUtil.bean2Json(object);
        log.info("transmit={}",json);
        String clientId = (String) object.get("clientIdentifier");
        String topic = (String) object.get("topic");
        String msg = (String) object.get("msg");
        //boolean clusterMsg = (boolean) object.get("clusterMsg");
        log.info("【Hex消息】clientId={},topic={},msg={}",clientId,topic,msg);
    }

    @Override
    public void close() {
        try {
            //关闭通道和连接
            for (Map.Entry<String, Channel> stringChannelEntry : channelHashMap.entrySet()) {
                Channel channel = stringChannelEntry.getValue();
                channel.close();
            }
            connection.close();
        } catch (Exception e) {
            log.error("#Close.Exception: {}",e.getMessage());
        }
    }


}
