package com.todo.services;

import com.google.common.collect.Iterables;
import com.todo.asserts.CustomSoftAssertions;
import com.todo.exceptions.TAFException;
import com.todo.interfaces.SoftAssert;
import com.todo.interfaces.Step;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public class StepsService {

    protected static Map<Long, CustomSoftAssertions> softAssertionsTL = new HashMap<>();

    private static Consumer<String> errHandleSupplier = (stepId) -> {
        Allure.getLifecycle().updateStep(stepId, s -> s.setStatus(Status.FAILED));
        Allure.getLifecycle().stopStep(stepId);
    };

    public static void step(String stepName, Step stepsToExecute) {
        String stepId = UUID.randomUUID().toString();

        Allure.getLifecycle().startStep(stepId, new StepResult().setName(stepName).setStatus(Status.PASSED));
        try {
            stepsToExecute.doStep();
        } catch (Throwable e) {
            errHandleSupplier.accept(stepId);
            throw new AssertionError(e);
        }
        if (Optional.ofNullable(softAssertionsTL.get(Thread.currentThread().getId())).isPresent()) {
            if (softAssertionsTL.get(Thread.currentThread().getId()).hasNewErrors()) {
                errHandleSupplier.accept(stepId);
            }
        }
        Allure.getLifecycle().stopStep(stepId);
    }

    public static void expectedStep(String stepName, Step stepsToExecute) {
        step("[Expected] " + stepName, stepsToExecute);
    }

    public static void softAssert(SoftAssert softAssert) {
        CustomSoftAssertions csa = new CustomSoftAssertions();
        softAssertionsTL.put(Thread.currentThread().getId(), csa);
        try {
            softAssert.doSoftAssert(csa);
        } catch (Throwable e) {
            if (!csa.getStepSet().isEmpty()) {
                errHandleSupplier.accept(Iterables.getLast(csa.getStepSet()));
            } else {
                throw new TAFException(e);
            }
        }
        csa.assertAll();
        softAssertionsTL.remove(Thread.currentThread().getId());
    }

}
