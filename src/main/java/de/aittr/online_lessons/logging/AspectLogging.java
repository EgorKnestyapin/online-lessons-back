package de.aittr.online_lessons.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Class that configures logging for the project.
 *
 * @author EgorKnestyapin
 * @version 1.0.0
 */
@Aspect
@Component
public class AspectLogging {

    private final Logger logger = LoggerFactory.getLogger(AspectLogging.class);

    @Pointcut("execution(* de.aittr.online_lessons.services..*(..))")
    public void runAllServicesMethods() {
    }

    @Before("runAllServicesMethods()")
    public void beforeEachServiceMethod(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        StringBuilder builder = new StringBuilder(String.format("Вызван метод %s класса %s", methodName, className));
        if (args.length != 0) {
            builder.append(" c параметрами: ");
            for (Object arg : args) {
                builder.append(arg).append(", ");
            }
            builder.setLength(builder.length() - 2);
            builder.append(".");
        }
        logger.info(builder.toString());
    }

    @AfterReturning(pointcut = "runAllServicesMethods()", returning = "result")
    public void afterReturningEachServiceMethod(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        logger.info(String.format("Метод %s класса %s успешно завершил работу с результатом %s", methodName, className,
                result));
    }

    @AfterThrowing(pointcut = "runAllServicesMethods()", throwing = "e")
    public void afterThrowingEachServiceMethod(JoinPoint joinPoint, Exception e) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        logger.info(String.format("Метод %s класса %s выбросил ошибку %s", methodName, className, e.getMessage()));
    }
}
