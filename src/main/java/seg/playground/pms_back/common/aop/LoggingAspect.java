package seg.playground.pms_back.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import seg.playground.pms_back.common.util.RestUtil;

@Slf4j
@Order
@Aspect
@Component
public class LoggingAspect {

    @Around("(execution(* seg.playground.pms_back.*.controller..*Controller.*(..))"
            + " || execution(* seg.playground.pms_back.*.service..*Service.*(..))"
            + " || execution(* seg.playground.pms_back.*.mapper..*Mapper.*(..))"
            + " || execution(* seg.playground.pms_back.*.repository..*Repository.*(..)))"
            + " && !@annotation(seg.playground.pms_back.common.annotation.Except)")
    public Object logging(ProceedingJoinPoint pjp) throws Throwable {
        String type;
        String classNm = pjp.getSignature().getDeclaringTypeName();
        String methodNm = pjp.getSignature().getName();

        if (classNm.contains("Controller")) {
            type = "Controller";
        } else if (classNm.contains("Service")) {
            type = "Service";
        } else if (classNm.contains("Repository")) {
            type = "Repository";
        } else if (classNm.contains("Mapper")) {
            type = "Mapper";
        } else {
            type = "Etc";
        }

        Object result = pjp.proceed();

        Object res;
        if (result instanceof ResponseEntity<?> resEntity) {
            res = resEntity.getBody();
        } else {
            res = result;
        }
        log.info(" {} - [RES] : '{}.{}()', Params={}", type, classNm, methodNm, RestUtil.getJsonToPrettyString(res));

        return result;
    }

}
