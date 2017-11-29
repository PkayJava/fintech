package com.angkorteam.fintech.spring;

import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;

public class NumberGeneratorImpl implements NumberGenerator {

    private RandomStringGenerator generator;

    public NumberGeneratorImpl() {
        this.generator = new RandomStringGenerator.Builder().withinRange('0', '9').filteredBy(CharacterPredicates.DIGITS).build();
    }

    @Override
    public String generate(int length) {
        return this.generator.generate(length);
    }

}
