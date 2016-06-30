package com.tomato.bizmsg.listener;

import com.tomato.mq.client.support.SystemConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.tomasky.msp.core.support.processor.MessageProcessorAdapter;
import com.tomasky.msp.support.ContextContainer;
import com.tomato.mq.client.event.listener.MsgEventListener;
import com.tomato.mq.client.event.model.MsgEvent;
import com.tomato.mq.client.event.publisher.MsgEventPublisher;
import com.tomato.mq.support.message.MessageType;

/**
 *
 * Created by Administrator on 2015/4/20.
 */

@Component("bizMessageListener")
public class MessageListener implements MsgEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageListener.class);

    public MessageListener() {
        //设置VM param -Dmq.profiles.active=dev
        MsgEventPublisher.getInstance().addListener(this, "biz_msg_consumer", MessageType.BIZ_MESSAGE);
    }

    @Override
    public void onEvent(MsgEvent msgEvent) {
        LOGGER.debug(String.format("【消息进入到消息中心监听服务，消息内容: %s】", msgEvent.getSource()));
        MessageProcessorAdapter adapter = (MessageProcessorAdapter) ContextContainer.getContext().getBean("messageProcessorAdapter");
        adapter.setMessage(msgEvent.getSource());
        adapter.process();
    }
}