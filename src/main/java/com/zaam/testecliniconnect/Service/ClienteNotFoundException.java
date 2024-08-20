package com.zaam.testecliniconnect.Service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Cliente não encontrado!")
public class ClienteNotFoundException extends RuntimeException{
}
