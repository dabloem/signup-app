package org.company.service;

public final class Predicate {

    public Predicate() {
        throw new RuntimeException("Predicate can be instantiated");
    }

    public static void nonNull(Object o) {
        if (o == null) {
            throw new SignupRequestNotFoundException();
        }
    }
}