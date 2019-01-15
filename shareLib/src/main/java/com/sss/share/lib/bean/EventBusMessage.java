package com.sss.share.lib.bean;

/**
 * EventBus 消息模型
 * Created by Administrator on 2018/5/21.
 */

public class EventBusMessage {
    /**
     * 消息标识
     */
    private int tag;
    /**
     * 附带的消息
     */
    private Object message;

    public EventBusMessage(int tag) {
        this.tag = tag;
    }

    public EventBusMessage(int tag, Object message) {
        this.tag = tag;
        this.message = message;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }
}
