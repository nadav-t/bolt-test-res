package mend.users.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Aspect
@Component
public class UsersLoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(UsersLoggingAspect.class);

    @Before("execution(* mend.users.resources.*.*(..)) || execution(* mend.users.services.*.*(..))")
    public void logBeforeControllerMethod(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        logger.info("Executing class: {}  method: {}. Input: {}", className, methodName, args);
    }

    @AfterReturning(pointcut = "execution(* mend.users.resources.*.*(..)) || execution(* mend.users.services.*.*(..))", returning = "result")
    public void logAfterControllerMethod(JoinPoint joinPoint, Object result) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        logger.info("Completed execution: class: {} method: {}", className, methodName);

        if (result != null) {
            if (result instanceof Flux<?>) {
                handleFluxResult((Flux<?>) result);
            } else if (result instanceof Mono<?>) {
                handleMonoResult((Mono<?>) result);
            } else {
                logger.info("Output: {}", result);
            }
        }
    }

    private void handleFluxResult(Flux<?> fluxResult) {
        fluxResult.collectList().subscribe(
                resultList -> logger.info("Output: {}", resultList),
                error -> logger.error("Output: Error collecting Flux result: {}", error.getMessage())
        );
    }

    private void handleMonoResult(Mono<?> result) {
        // ignore result - should fix it
        // handled in service layer
    }
}
