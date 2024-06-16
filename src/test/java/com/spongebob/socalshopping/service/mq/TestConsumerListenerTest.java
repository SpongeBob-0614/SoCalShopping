package com.spongebob.socalshopping.service.mq;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestConsumerListenerTest {
    @Resource
    RocketMQService rocketMQService;

    @Test
    public void testMessage() throws Exception {
        rocketMQService.sendMessage("consumerTopic", "Today is " + new Date());
    }
}