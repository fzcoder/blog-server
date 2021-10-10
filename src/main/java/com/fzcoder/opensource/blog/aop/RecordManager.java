package com.fzcoder.opensource.blog.aop;

import com.fzcoder.opensource.blog.aop.annotation.Record;
import com.fzcoder.opensource.blog.aop.annotation.RecordParam;
import com.fzcoder.opensource.blog.aop.annotation.RecordParamType;
import com.fzcoder.opensource.blog.dto.ArticleForm;
import com.fzcoder.opensource.blog.service.RecordService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class RecordManager {

    private RecordService recordService;

    public RecordManager(RecordService recordService) {
        this.recordService = recordService;
    }

    @Pointcut("@annotation(com.fzcoder.opensource.blog.aop.annotation.Record)")
    private void pointCutExp() {}

    private void advice(ProceedingJoinPoint joinPoint) throws Exception{
        // 获取参数列表
        Object[] args = joinPoint.getArgs();
        Class<?>[] argsClass = new Class[args.length];
        // 参数Map
        Map<String, Object> argsMap = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            argsClass[i] = args[i].getClass();
            argsMap.put(argsClass[i].getTypeName(), args[i]);
        }
        // 获取含有该切入点的类对象
        Class<?> bean = joinPoint.getTarget().getClass();
        Method method = bean.getMethod(joinPoint.getSignature().getName(), argsClass);
        // 获取 @Record 注解
        Record annotation = method.getAnnotation(Record.class);
        // 获取参数列表
        Parameter[] parameters = method.getParameters();
        // 参数类型名Map
        Map<RecordParamType, String> paramsTypeNameMap = new HashMap<>();
        // 遍历参数列表
        for (Parameter parameter : parameters) {
            // 判断参数是否包含 @RecordParam 注解
            if (parameter.isAnnotationPresent(RecordParam.class)) {
                // 获取注解
                RecordParam param = parameter.getAnnotation(RecordParam.class);
                paramsTypeNameMap.put(param.value(), parameter.getType().getTypeName());
            }
        }
        // 判断记录类型
        switch (annotation.type()) {
            case ARTICLE:
                // 获取实体
                Object entity = null;
                if (paramsTypeNameMap.containsKey(RecordParamType.ENTITY)) {
                    entity = argsMap.get(paramsTypeNameMap.get(RecordParamType.ENTITY));
                }
                // 获取日期
                Object date = null;
                if (paramsTypeNameMap.containsKey(RecordParamType.DATE)) {
                    date = argsMap.get(paramsTypeNameMap.get(RecordParamType.DATE));
                }
                // 获取状态
                Object status = null;
                if (paramsTypeNameMap.containsKey(RecordParamType.STATUS)) {
                    status = argsMap.get(paramsTypeNameMap.get(RecordParamType.STATUS));
                }
                // 判断操作类型
                switch (annotation.operationType()) {
                    case CREATE:
                        if (entity instanceof ArticleForm && date instanceof Date) {
                            recordService.handleInsertArticleEvent((ArticleForm) entity, (Date) date);
                        }
                        break;
                    case READ:
                        break;
                    case UPDATE:
                        if (entity instanceof ArticleForm && date instanceof Date && status instanceof Integer) {
                            recordService.handleUpdateArticleEvent((ArticleForm) entity,
                                    (Date) date, (Integer) status);
                        }
                        break;
                    case DELETE:
                        if (entity instanceof ArticleForm && date instanceof Date) {
                            recordService.handleDeleteArticleEvent((ArticleForm) entity, (Date) date);
                        }
                        break;
                    default:
                        break;
                }
                break;
            case CATEGORY:
                break;
            case TAG:
                break;
            default:
                break;
        }
    }


    @Around("pointCutExp()")
    public Object handleRecords(ProceedingJoinPoint joinPoint) {
        Object result = null;
        try {
            // 1.执行切入点方法
            result = joinPoint.proceed(joinPoint.getArgs());
            // 2.后置通知
            // 2.1判断方法的返回值是否为Boolean类型
            if (result instanceof Boolean) {
                // 2.2判断方法返回结果是否为true
                if ((Boolean) result) {
                    // 2.3添加记录(对切入点方法进行增强)
                    advice(joinPoint);
                }
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;
    }
}
