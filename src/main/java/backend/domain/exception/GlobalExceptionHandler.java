package backend.domain.exception;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import backend.application.dto.ErroRespostaDTO;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice(basePackages = "backend")
public class GlobalExceptionHandler {

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErroRespostaDTO> tratarRegraNegocio(
    IllegalArgumentException ex,
    HttpServletRequest request) {

    ErroRespostaDTO erro = new ErroRespostaDTO(
      HttpStatus.BAD_REQUEST.value(),
      "Requisição Inválida",
      ex.getMessage(),
      request.getRequestURI()
    );

    return ResponseEntity
      .status(HttpStatus.BAD_REQUEST)
      .body(erro);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> tratarValidacoes(
     MethodArgumentNotValidException ex) {

    Map<String, String> erros = new HashMap<>();

    ex.getBindingResult()
      .getFieldErrors()
      .forEach(error ->
        erros.put(
          error.getField(),
          error.getDefaultMessage()
        )
      );

    return ResponseEntity
      .status(HttpStatus.BAD_REQUEST)
      .body(erros);
  }

  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<ErroRespostaDTO> tratarRecursoNaoEncontrado(
    HttpServletRequest request) {

    ErroRespostaDTO erro = new ErroRespostaDTO(
      HttpStatus.NOT_FOUND.value(),
      "Não Encontrado",
      "Recurso não encontrado.",
      request.getRequestURI()
    );

    return ResponseEntity
      .status(HttpStatus.NOT_FOUND)
      .body(erro);
  }

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ErroRespostaDTO> tratarResponseStatus(
    ResponseStatusException ex,
    HttpServletRequest request) {

    ErroRespostaDTO erro = new ErroRespostaDTO(
      ex.getStatusCode().value(),
      ex.getStatusCode().toString(),
      ex.getReason(),
      request.getRequestURI()
    );

    return ResponseEntity
      .status(ex.getStatusCode())
      .body(erro);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErroRespostaDTO> tratarGeral(
    Exception ex,
    HttpServletRequest request) {

    ErroRespostaDTO erro = new ErroRespostaDTO(
      HttpStatus.INTERNAL_SERVER_ERROR.value(),
      "Erro Interno do Servidor",
      ex.getMessage(),
      request.getRequestURI()
    );

    return ResponseEntity
      .status(HttpStatus.INTERNAL_SERVER_ERROR)
      .body(erro);
  }
}