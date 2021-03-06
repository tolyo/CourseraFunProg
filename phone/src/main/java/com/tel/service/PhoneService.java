package com.tel.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class PhoneService {

    private static final Map<BigInteger, String> countryCode = new HashMap<>();

    public void load(BigInteger s, String country) {
        Assert.notNull(s, "Number required");
        Assert.notNull(country, "Country required");
        countryCode.put(s, country);
    }

    public Optional<String> get(BigInteger number) {
        Assert.notNull(number, "Number required");
        if (countryCode.containsKey(number)) return Optional.ofNullable(countryCode.get(number));
        else return get(shortenNumber(number));
    }

    private BigInteger shortenNumber(BigInteger number) {
        Assert.notNull(number, "Number required");
        String numberString = number.toString();
        numberString = numberString.substring(0, numberString.length() - 1);
        if (numberString.equals("")) return null;
        else return new BigInteger(numberString);
    }
}
