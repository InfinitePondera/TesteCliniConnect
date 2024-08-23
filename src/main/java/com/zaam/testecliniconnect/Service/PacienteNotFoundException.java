package com.zaam.testecliniconnect.Service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Paciente não encontrado!")
public class PacienteNotFoundException extends RuntimeException{
}
