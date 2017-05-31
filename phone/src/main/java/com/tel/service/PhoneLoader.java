package com.tel.service;

import com.neovisionaries.i18n.CountryCode;
import info.debatty.java.stringsimilarity.Levenshtein;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Optional;

@Service
public class PhoneLoader implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(PhoneLoader.class);
    private static final Levenshtein levenshtein = new Levenshtein();
    private static final double LEVENSHTEIN_DISTANCE = 2;

    @Autowired
    PhoneService phoneService;

    @Value("${phone.code.url}")
    String phoneCodeUrl;

    private void loadPhones() {
        try {
            Document doc = Jsoup.connect(phoneCodeUrl).get();
            // Load alphabetical listing by country or region
            Element table = doc.getElementsByClass("wikitable").get(1);
            Elements trs = table.select("tr");
            trs.forEach(tr -> {
                // find all data items in row
                Elements tds = tr.select("td");
                if (tds.size() == 0) return;
                // find country name in first column
                String country = validatedCountryName(tds.get(0).text());
                // find all codes in second column
                Elements codes = tds.get(1).select("[title]");
                codes.forEach(code -> {
                    String countryCode = code.text().replaceAll("\\+|\\s+","");
                    log.info("Loading country code(s): " + countryCode + " for " + country);
                    // handle countries with multiple codes
                    if (countryCode.contains(",")) {
                        Arrays
                        .asList(countryCode.split(","))
                        .forEach(x -> phoneService.load(new BigInteger(x), country));
                    } else {
                        phoneService.load(new BigInteger(countryCode), country);
                    }
                });
            });
        } catch (UnknownHostException e) {
            log.error("Application requires an active Internet connection to load data");
            System.exit(1);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private String validatedCountryName(String countryName) {
        if (CountryCode.findByName(countryName).size() > 0) return countryName;
        else {
            log.info("Country name " + countryName + " not found");
            Optional<String> correctedName = Arrays
                    .stream(CountryCode.values())
                    .filter(x -> levenshtein.distance(x.getName().toLowerCase(), countryName.toLowerCase()) < LEVENSHTEIN_DISTANCE)
                    .map(y -> y.getName())
                    .findAny();
            if (correctedName.isPresent()) return correctedName.get();
            else return  countryName;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        loadPhones();
    }
}
