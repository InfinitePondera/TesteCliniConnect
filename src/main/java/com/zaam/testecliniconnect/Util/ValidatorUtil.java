package com.zaam.testecliniconnect.Util;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class ValidatorUtil {
    public static boolean ValidateEmail(String email) {
        return Pattern.compile("^(.+)@(\\\\S+)$").matcher(email).matches();
    }

    public static boolean ValidateCPF(String cpf) {
        return Pattern.compile("[0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}\\-?[0-9]{2}").matcher(cpf).matches();
    }

    public static boolean ValidateDataNascimento(String dateStr) {
        LocalDate date = LocalDate.parse(dateStr);
        return date.isBefore(LocalDate.now());
    }
}
