# ez-dtp的简单实现思路

### 什么是动态线程池？

在线程池日常实践中我们常常会遇到以下问题：

+ 代码中创建了一个线程池却不知道核心参数设置多少比较合适。
+ 参数设置好后，上线发现需要调整，改代码重启服务非常麻烦。
+ 线程池相对于开发人员来说是个黑箱，运行情况在出现问题 前很难被感知。

因此，动态可监控线程池一种针对以上痛点开发的线程池管理工具。主要可实现功能有：提供对 Spring 应用内线程池实例的全局管控、应用运行时动态变更线程池参数以及线程池数据采集和监控阈值报警。



### 已经实现的优秀开源动态线程池

hippo4j、dynamic-tp.....



### 实现思路

#### 核心管理类

需要能实现对线程池的

+ 服务注册

+ 获取已经注册好的线程池

+ 以及对注册号线程池参数的刷新。



对于每一个线程池，我们使用一个`线程池名字`作为标识每个线程池的唯一ID。



> 伪代码实现

```java
public class DtpRegistry {
    /**
     * 储存线程池
     */
    private static final Map<String, Executor> EXECUTOR_MAP = new ConcurrentHashMap<>();
    
    /**
     * 获取线程池
     * @param executorName 线程池名字
     */
    public static Executor getExecutor(String executorName) {
        return EXECUTOR_MAP.get(executorName);
    }
    
    /**
     * 线程池注册
     * @param executorName 线程池名字
     */
    public static void registry(String executorName, Executor executor) {
        //注册
        EXECUTOR_MAP.put(executorName, executorWrapper);
    }
    
    
    /**
     * 刷新线程池参数
     * @param executorName 线程池名字
     * @param properties 线程池参数
     */
    public static void refresh(String executorName, ThreadPoolProperties properties) {
        Executor executor = EXECUTOR_MAP.get(executorName)
        //刷新参数
        //.......
    }
	
}
```



#### 如何创建线程池？

##### STEP 1. 我们可以使用`yml配置文件`的方式配置一个线程池，将线程池实例的创建交由Spring容器。



> 相关配置

```java
public class DtpProperties {
    
    private List<ThreadPoolProperties> executors;
    
}

public class ThreadPoolProperties {
    /**
     * 标识每个线程池的唯一名字
     */
    private String poolName;
    private String poolType = "common";

    /**
     * 是否为守护线程
     */
    private boolean isDaemon = false;

    /**
     * 以下都是核心参数
     */
    private int corePoolSize = 1;
    private int maximumPoolSize = 1;
    private long keepAliveTime;
    private TimeUnit timeUnit = TimeUnit.SECONDS;
    private String queueType = "arrayBlockingQueue";
    private int queueSize = 5;
    private String threadFactoryPrefix = "-td-";
    private String RejectedExecutionHandler;
}
```



yml example:

```yml
spring:
  dtp:
    executors:
      # 线程池1
      - poolName: dtpExecutor1
        corePoolSize: 5
        maximumPoolSize: 10
      # 线程池2
      - poolName: dtpExecutor2
        corePoolSize: 2
        maximumPoolSize: 15
```



##### STEP 2 根据配置信息添加线程池的`BeanDefinition`



> 关键类

```java
@Slf4j
public class DtpImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware {
    private Environment environment;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        log.info("注册");
        //绑定资源
        DtpProperties dtpProperties = new DtpProperties();
        ResourceBundlerUtil.bind(environment, dtpProperties);
        List<ThreadPoolProperties> executors = dtpProperties.getExecutors();
        if (Objects.isNull(executors)) {
            log.info("未检测本地到配置文件线程池");
            return;
        }
        //注册beanDefinition
        executors.forEach((executorProp) -> {
            BeanUtil.registerIfAbsent(registry, executorProp);
        });
    }


    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}


/**
 *
 * 工具类
 *
 */
public class BeanUtil{
    public static void registerIfAbsent(BeanDefinitionRegistry registry, ThreadPoolProperties executorProp) {
        register(registry, executorProp.getPoolName(), executorProp);
    }

    public static void register(BeanDefinitionRegistry registry, String beanName, ThreadPoolProperties executorProp) {
        Class<? extends Executor> executorType = ExecutorType.getClazz(executorProp.getPoolType());
        Object[] args = assembleArgs(executorProp);
        register(registry, beanName, executorType, args);
    }

    public static void register(BeanDefinitionRegistry registry, String beanName, Class<?> clazz, Object[] args) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
        for (Object arg : args) {
            builder.addConstructorArgValue(arg);
        }
        registry.registerBeanDefinition(beanName, builder.getBeanDefinition());
    }
    
    private static Object[] assembleArgs(ThreadPoolProperties executorProp) {
        return new Object[]{
                executorProp.getCorePoolSize(),
                executorProp.getMaximumPoolSize(),
                executorProp.getKeepAliveTime(),
                executorProp.getTimeUnit(),
                QueueType.getInstance(executorProp.getQueueType(), executorProp.getQueueSize()),
                new NamedThreadFactory(
                        executorProp.getPoolName() + executorProp.getThreadFactoryPrefix(),
                        executorProp.isDaemon()
                ),
                //先默认不做设置
                RejectPolicy.ABORT.getValue()
        };
    }
}
    

```

下面解释一下这个类的作用，`environment`实例中储存着spring启动时解析的yml配置，所以我们spring提供的`Binder`将配置绑定到我们前面定义的`DtpProperties`类中，方便后续使用。接下来的比较简单，就是将线程池的`BeanDefinition`注册到IOC容器中，让spring去帮我们实例化这个bean。



##### STEP 3. 将已经实例化的线程池注册到核心类 DtpRegistry 中

我们注册了 beanDefinition 后，spring会帮我们实例化出来， 在这之后我们可以根据需要将这个bean进行进一步的处理，spring也提供了很多机制让我们对bean的生命周期管理进行更多的扩展。对应到这里我们就是将实例化出来的线程池注册到核心类 DtpRegistry 中进行管理。



>这里我们使用 BeanPostProcessor 进行处理。

```java
@Slf4j
public class DtpBeanPostProcessor implements BeanPostProcessor {
    private DefaultListableBeanFactory beanFactory;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof DtpExecutor) {
            //直接纳入管理
            DtpRegistry.registry(beanName, (DtpExecutor) bean);
        }
        return bean;
    }
}
```

这里的逻辑很简单， 就是判断一下这个bean是不是线程池，是就统一管理起来。



##### STEP 4. 启用 BeanDefinitionRegistrar 和 BeanPostProcessor

在springboot程序中，只要加一个`@MapperScan`注解就能启用mybatis的功能，我们可以学习其在spring中的启用方式，自定义一个注解：

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(DtpImportSelector.class)
public @interface EnableDynamicThreadPool {
}
```

其中，比较关键的是`@Import`注解，spring会导入注解中的类`DtpImportSelector`

而`DtpImportSelector`这个类实现了：

```java
public class DtpImportSelector implements DeferredImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{
                DtpImportBeanDefinitionRegistrar.class.getName(),
                DtpBeanPostProcessor.class.getName()
        };
    }
}
```



这样，只要我们再启动类或者配置类上加上`@EnableDynamicThreadPool`这个注解，spring就会将`DtpImportBeanDefinitionRegistrar`和`DtpBeanPostProcessor`这两个类加入spring容器管理，从而实现我们的线程池的注册。

```java
@SpringBootApplication
@EnableDynamicThreadPool
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```





#### 如何实现线程池配置的动态刷新

首先明确一点，对于线程池的实现类，例如：ThreadPoolExecutor等，都有提供核心参数对应的 `set` 方法，让我们实现参数修改。因此，在核心类`DtpRegistry`中的refresh方法，我们可以这样写：

```java
public class DtpRegistry {
    /**
     * 储存线程池
     */
    private static final Map<String, ThreadPoolExecutor> EXECUTOR_MAP = new ConcurrentHashMap<>();
    /**
     * 刷新线程池参数
     * @param executorName 线程池名字
     * @param properties 线程池参数
     */
    public static void refresh(String executorName, ThreadPoolProperties properties) {
        ThreadPoolExecutor executor = EXECUTOR_MAP.get(executorName)
        
        //设置参数
        executor.setCorePoolSize(...);
        executor.setMaximumPoolSize(...);
        ......
    }
	
}
```



而这些新参数怎么来呢?我们可以引入Nacos、Apollo等配置中心，实现他们的`监听器方法`，在监听器方法里调用`DtpRegistry`的refresh方法刷新即可。
