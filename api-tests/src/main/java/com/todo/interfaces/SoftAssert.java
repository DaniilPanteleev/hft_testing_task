package com.todo.interfaces;

import com.todo.asserts.CustomSoftAssertions;

@FunctionalInterface
public interface SoftAssert {

    void doSoftAssert(CustomSoftAssertions customSoftAssertions);

}
