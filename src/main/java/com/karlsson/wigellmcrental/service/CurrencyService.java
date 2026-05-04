package com.karlsson.wigellmcrental.service;

import org.springframework.stereotype.Service;

@Service
public class CurrencyService {
    // Fast växelkurs SEK till GBP
    private static final double SEK_TO_GBP = 0.0798;


    //Konverterar pris från SEK till GBP och avrundar till 2 decimaler
    public static double sekToGBP(double sek) {
        return Math.round(sek * SEK_TO_GBP * 100.0) / 100.0;
    }
}

