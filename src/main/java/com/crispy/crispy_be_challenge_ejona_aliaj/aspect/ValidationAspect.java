package com.crispy.crispy_be_challenge_ejona_aliaj.aspect;

import com.crispy.crispy_be_challenge_ejona_aliaj.controller.request.validation.Validatable;
import com.crispy.crispy_be_challenge_ejona_aliaj.exception.ApiValidationException;
import com.crispy.crispy_be_challenge_ejona_aliaj.exception.ValidationMessage;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * This aspect helps to return bean validation errors in a more readable form. <br/>
 * It is executed when the method is annotated with @ValidateInputRequest annotation. <br/>
 * The dto that is going to be validated has to implement Validatable interface.
 *
 */
@Slf4j
@Aspect
@Component
public class ValidationAspect {

    private final Validator validator;

    public ValidationAspect(Validator validator) {
        this.validator = validator;
    }

    @Around(value = "@annotation(com.crispy.crispy_be_challenge_ejona_aliaj.controller.request.validation.ValidateInputRequest)")
    public Object validateInput(ProceedingJoinPoint pjp) throws Throwable {

        log.info("AOP intercept for method - " + pjp.getSignature());

        Optional<Object> requestObject = Arrays.stream(pjp.getArgs()).filter(p -> p instanceof Validatable).findFirst();

        if (requestObject.isPresent()) {
            Set<ConstraintViolation<Object>> constraintViolations = validator.validate(requestObject.get());
            List<ValidationMessage> list = new ArrayList<>(0);
            if (!CollectionUtils.isEmpty(constraintViolations)) {
                for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
                    ValidationMessage validationMessage = new ValidationMessage(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
                    list.add(validationMessage);
                }
                throw new ApiValidationException("Validation Errors", list);
            }
        }
        //just obtain the obj from the ProceedingJoinPoint
        Object obj = pjp.proceed();

        log.info("Completed executing the AOP intercept");
        return obj;
    }
}
