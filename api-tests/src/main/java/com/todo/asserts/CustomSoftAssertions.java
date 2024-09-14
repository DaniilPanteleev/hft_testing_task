package com.todo.asserts;

import io.qameta.allure.Allure;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.error.AssertionErrorCreator;

import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Кастомный софт ассерт необходим для корректного отображения зафейленых подстепов
 * в аллюре, при использовании стандартного {@link Allure#step(String, Allure.ThrowableRunnable) step}
 * упавшие подстепы не фэйлятся в отсчёте.
 */
public class CustomSoftAssertions extends SoftAssertions {

    private final AssertionErrorCreator assertionErrorCreator = new AssertionErrorCreator();

    private HashSet<String> stepSet = new HashSet<>();

    private AtomicInteger errorsCounter = new AtomicInteger(0);

    public AssertionErrorCreator getAssertionErrorCreator() {
        return assertionErrorCreator;
    }

    public HashSet<String> getStepSet() {
        return stepSet;
    }

    public AtomicInteger getErrorsCounter() {
        return errorsCounter;
    }

    public Boolean hasNewErrors() {
        if (assertionErrorsCollected().size() > errorsCounter.get()) {
            errorsCounter.set(assertionErrorsCollected().size());
            return true;
        }
        return false;
    }

    @Override
    public void onAssertionErrorCollected(AssertionError assertionError) {
        stepSet.add(Allure.getLifecycle().getCurrentTestCaseOrStep().get());
        super.onAssertionErrorCollected(assertionError);
    }

}
