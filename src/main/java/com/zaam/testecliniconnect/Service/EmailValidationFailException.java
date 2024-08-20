package com.zaam.testecliniconnect.Service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Email não é válido!")
public class EmailValidationFailException extends RuntimeException {
}
