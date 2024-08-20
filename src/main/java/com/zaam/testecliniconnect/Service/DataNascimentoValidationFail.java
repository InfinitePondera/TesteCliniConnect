package com.zaam.testecliniconnect.Service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Data de nascimento não é válida!")
public class DataNascimentoValidationFail extends RuntimeException {
}
