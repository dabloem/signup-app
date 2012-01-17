package org.company.test.service;

import org.company.service.Predicate;
import org.company.service.SignupRequestNotFoundException;
import org.junit.Test;

public class PredicateTest {

    @Test(expected = SignupRequestNotFoundException.class)
    public void testNonNull() {
        Predicate.nonNull(null);
    }
}
