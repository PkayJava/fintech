package com.angkorteam.fintech.spring;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;

public class StringGeneratorImpl implements StringGenerator {

    private RandomStringGenerator generator;

    public StringGeneratorImpl() {
        this.generator = new RandomStringGenerator.Builder().withinRange('0', 'z').filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS).build();

    }

    @Override
    public String generate(int length) {
        return StringUtils.upperCase(this.generator.generate(length));
    }

}
