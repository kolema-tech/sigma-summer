
# ack
* Basic.Ack 发回给 RabbitMQ 以告知，可以将相应 message 从 RabbitMQ 的消息缓存中移除。
* Basic.Ack 未被 consumer 发回给 RabbitMQ 前出现了异常，RabbitMQ 发现与该 consumer 对应的连接被断开，之后将该 message 以轮询方式发送给其他 consumer （假设存在多个 consumer 订阅同一个 queue）。
* 在 no_ack=true 的情况下，RabbitMQ 认为 message 一旦被 deliver 出去了，就已被确认了，所以会立即将缓存中的 message 删除。所以在 consumer 异常时会导致消息丢失。
* 来自 consumer 侧的 Basic.Ack 与 发送给 Producer 侧的 Basic.Ack 没有直接关系。

# 持久化

* 消息队列持久化包括3个部分：
* exchange持久化，在声明时指定durable => 1
* queue持久化，在声明时指定durable => 1
消息持久化，在投递时指定delivery_mode => 2（1是非持久化）