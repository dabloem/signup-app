package org.company.service;

public final class Predicate {
	public Predicate(){
		throw new RuntimeException("Predicate can be instantiated");
	}

	public static void nonNull(Object o) throws SignupRequestNotFoundException {
		if (o == null) {
			throw new SignupRequestNotFoundException();
		}
	}
}