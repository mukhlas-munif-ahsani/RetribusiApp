package com.munifahsan.retribusiapp.EventBuss;

public interface EventBus {
    void register(Object subscriber);
    void unregister(Object subscriber);
    void post(Object event);
}
