
# Transactional

## ACID

首先总结一下数据库事务正确执行的四个要素（ACID）：
   
* ####原子性（Atomicity）
  
  即事务是不可分割的最小工作单元，事务内的操作要么全做，要么全不做，不能只做一部分；
* **一致性**（Consistency）

  在事务执行前数据库的数据处于正确的状态，而事务执行完成后数据库的数据还是处于正确的状态，即数据完整性约束没有被破坏；比如我们做银行转账的相关业务，A转账给B，要求A转的钱B一定要收到。如果A转了钱而B没有收到，那么数据库数据的一致性就得不到保障，在做高并发业务时要注意合理的设计。
* **隔离性**（Isolation）

  并发事务执行之间无影响，在一个事务内部的操作对其他事务是不产生影响，这需要事务隔离级别来指定隔离性；
* **持久性**（Durability）

  事务一旦执行成功，它对数据库的数据的改变必须是永久的，不会因各种异常导致数据不一致或丢失。
  
## SQL定义了下面的4个等级的事务隔离级别

* 未提交读（READ UNCOMMITTED ）
  
  最低隔离级别，一个事务能读取到别的事务未提交的更新数据，很不安全，可能出现丢失更新、脏读、不可重复读、幻读；
* 提交读（READ COMMITTED）
  
  一个事务能读取到别的事务提交的更新数据，不能看到未提交的更新数据，不会出现丢失更新、脏读，但可能出现不可重复读、幻读；
* 可重复读（REPEATABLE READ）
  
  保证同一事务中先后执行的多次查询将返回同一结果，不受其他事务影响，不可能出现丢失更新、脏读、不可重复读，但可能出现幻读；
* 序列化（SERIALIZABLE）
  
  最高隔离级别，不允许事务并发执行，而必须串行化执行，最安全，不可能出现更新、脏读、不可重复读、幻读，但是效率最低。

隔离级别越高，数据库事务并发执行性能越差，能处理的操作越少。

所以一般地，推荐使用REPEATABLE READ级别保证数据的读一致性。
对于幻读的问题，可以通过加锁来防止。
MySQL支持这四种事务等级，默认事务隔离级别是REPEATABLE READ。Oracle数据库支持READ COMMITTED 和 SERIALIZABLE这两种事务隔离级别，所以Oracle数据库不支持脏读。Oracle数据库默认的事务隔离级别是READ COMMITTED。


## 各种锁

下面总结一下MySQL中的锁，有好几种分类。其它RDBMS也差不多是这样。

首先最重要的分类就是乐观锁(Optimistic Lock)和悲观锁(Pessimistic Lock)，
这实际上是两种锁策略。

### 乐观锁
  
  顾名思义就是非常乐观，非常相信真善美，每次去读数据都认为其它事务没有在写数据，所以就不上锁，快乐的读取数据，而只在提交数据的时候判断其它事务是否搞过这个数据了，如果搞过就rollback。乐观锁相当于一种检测冲突的手段，可通过为记录添加版本或添加时间戳来实现。
### 悲观锁
  
  对其它事务抱有保守的态度，每次去读数据都认为其它事务想要作祟，所以每次读数据的时候都会上锁，直到取出数据。悲观锁大多数情况下依靠数据库的锁机制实现，以保证操作最大程度的独占性，但随之而来的是各种开销。悲观锁相当于一种避免冲突的手段。
  
选择标准：如果并发量不大，或数据冲突的后果不严重，则可以使用乐观锁；而如果并发量大或数据冲突后果比较严重（对用户不友好），那么就使用悲观锁。

从读写角度，分共享锁（S锁，Shared Lock）和排他锁（X锁，Exclusive Lock），也叫读锁（Read Lock）和写锁（Write Lock）。
理解：

持有S锁的事务只读不可写。如果事务A对数据D加上S锁后，其它事务只能对D加上S锁而不能加X锁。
持有X锁的事务可读可写。如果事务A对数据D加上X锁后，其它事务不能再对D加锁，直到A对D的锁解除。
从锁的粒度角度，主要分为表级锁（Table Lock）和行级锁（Row Lock）。
表级锁将整个表加锁，性能开销最小。用户可以同时进行读操作。当一个用户对表进行写操作时，用户可以获得一个写锁，写锁禁止其他的用户读写操作。写锁比读锁的优先级更高，即使有读操作已排在队列中，一个被申请的写锁仍可以排在所队列的前列。
行级锁仅对指定的记录进行加锁，这样其它进程可以对同一个表中的其它记录进行读写操作。行级锁粒度最小，开销大，能够支持高并发，可能会出现死锁。

MySQL的MyISAM引擎使用表级锁，而InnoDB支持表级锁和行级锁，默认是行级锁。
还有BDB引擎使用页级锁，即一次锁定一组记录，并发性介于行级锁和表级锁之间。

### 三级锁协议
三级加锁协议是为了保证正确的事务并发操作，事务在读、写数据库对象是需要遵循的加锁规则。

一级封锁协议：事务T在修改数据R之前必须对它加X锁，直到事务结束方可释放。而若事务T只是读数据，不进行修改，则不需加锁，因此一级加锁协议下可能会出现脏读和不可重复读。
二级加锁协议：在一级加锁协议的基础上，加上这样一条规则——事务T在读取数据R之前必须对它加S锁，直到读取完毕以后释放。二级加锁协议下可能会出现不可重复读。
三级加锁协议：在一级加锁协议的基础上，加上这样一条规则——事务T在读取数据R之前必须对它加S锁，直到事务结束方可释放。三级加锁协议避免了脏读和不可重复读的问题。

## 事物传播行为介绍

* @Transactional(propagation=Propagation.REQUIRED) ：如果有事务, 那么加入事务, 没有的话新建一个(默认情况下)
* @Transactional(propagation=Propagation.NOT_SUPPORTED) ：容器不为这个方法开启事务
* @Transactional(propagation=Propagation.REQUIRES_NEW) ：不管是否存在事务,都创建一个新的事务,原来的挂起,新的执行完毕,继续执行老的事务
* @Transactional(propagation=Propagation.MANDATORY) ：必须在一个已有的事务中执行,否则抛出异常
* @Transactional(propagation=Propagation.NEVER) ：必须在一个没有的事务中执行,否则抛出异常(与Propagation.MANDATORY相反)
* @Transactional(propagation=Propagation.SUPPORTS) ：如果其他bean调用这个方法,在其他bean中声明事务,那就用事务.如果其他bean没有声明事务,那就不用事务.


## 事物超时设置

* @Transactional(timeout=30) //默认是30秒

## 事务隔离级别

* @Transactional(isolation = Isolation.READ_UNCOMMITTED)：读取未提交数据(会出现脏读, 不可重复读) 基本不使用
* @Transactional(isolation = Isolation.READ_COMMITTED)：读取已提交数据(会出现不可重复读和幻读)
* @Transactional(isolation = Isolation.REPEATABLE_READ)：可重复读(会出现幻读)
* @Transactional(isolation = Isolation.SERIALIZABLE)：串行化

MYSQL: 默认为REPEATABLE_READ级别

SQLSERVER: 默认为READ_COMMITTED

* 脏读 : 一个事务读取到另一事务未提交的更新数据
* 不可重复读 : 在同一事务中, 多次读取同一数据返回的结果有所不同, 换句话说, 
  后续读取可以读到另一事务已提交的更新数据. 相反, "可重复读"在同一事务中多次
  读取数据时, 能够保证所读数据一样, 也就是后续读取不能读到另一事务已提交的更新数据
* 幻读 : 一个事务读到另一个事务已提交的insert数据


## 使用
```java
@Transactional(rollbackFor=Exception.class) //指定回滚,遇到异常Exception时回滚
public void methodName() {
　　　throw new Exception("注释");
}
@Transactional(noRollbackFor=Exception.class)//指定不回滚,遇到运行期例外(throw new RuntimeException("注释");)会回滚
public ItimDaoImpl getItemDaoImpl() {
　　　throw new RuntimeException("注释");
}
```

* @Transactional 注解应该只被应用到 public 可见度的方法上。 
  
  如果你在 protected、private 或者 package-visible 的方法上使用 @Transactional 注解，它也不会报错， 但是这个被注解的方法将不会展示已配置的事务设置。
* @Transactional 注解可以被应用于接口定义和接口方法、类定义和类的 public 方法上。

  然而，请注意仅仅 @Transactional 注解的出现不足于开启事务行为，它仅仅 是一种元数据，能够被可以识别 @Transactional 注解和上述的配置适当的具有事务行为的beans所使用。上面的例子中，其实正是 元素的出现 开启 了事务行为。
* Spring团队的建议是你在具体的类（或类的方法）上使用 @Transactional 注解，
  而不要使用在类所要实现的任何接口上。
  
  你当然可以在接口上使用 @Transactional 注解，但是这将只能当你设置了基于接口的代理时它才生效。因为注解是不能继承的，这就意味着如果你正在使用基于类的代理时，那么事务的设置将不能被基于类的代理所识别，而且对象也将不会被事务代理所包装（将被确认为严重的）。因此，请接受Spring团队的建议并且在具体的类上使用 @Transactional 注解。

Ref:https://www.cnblogs.com/yldIndex/p/spring_Transactional.html

在@Transactional注解中，可以propagation属性用来配置事务传播，支持7种不同的传播机制：

* REQUIRED：业务方法需要在一个事务中运行，如果方法运行时，已处在一个事务中，那么就加入该事务，否则自己创建一个新的事务。这是spring默认的传播行为。
* NOT_SUPPORTED：声明方法不需要事务。如果方法没有关联到一个事务，容器不会为他开启事务，如果方法在一个事务中被调用，该事务会被挂起，调用结束后，原先的事务会恢复执行。
* REQUIRESNEW：不管是否存在事务，该方法总会为自己发起一个新的事务。如果方法已经运行在一个事务中，则原有事务挂起，新的事务被创建。
* MANDATORY：该方法只能在一个已经存在的事务中执行，业务方法不能发起自己的事务。如果在没有事务的环境下被调用，容器抛出例外。
* SUPPORTS：该方法在某个事务范围内被调用，则方法成为该事务的一部分。如果方法在该事务范围外被调用，该方法就在没有事务的环境下执行。
* NEVER：该方法绝对不能在事务范围内执行。如果在就抛异常。只有该方法没有关联到任何事务，才正常执行。
* NESTED：如果一个活动的事务存在，则运行在一个嵌套的事务中。如果没有活动事务，则按REQUIRED属性执行。它使用了一个单独的事务，这个事务拥有多个可以回滚的保存点。内部事务的回滚不会对外部事务造成影响。它只对DataSourceTransactionManager事务管理器起效。