package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.util.exception.ErrorInfo;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;



/**
 * User: gkislin
 * Date: 23.09.2014
 */
@ControllerAdvice(annotations = RestController.class) //будет применяться к классам с @RestController
public class ExceptionInfoHandler {
    Logger LOG = LoggerFactory.getLogger(ExceptionInfoHandler.class);

//  http://stackoverflow.com/a/22358422/548473
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY) //устанавливаем статус ответа на ошибку
    @ExceptionHandler(NotFoundException.class) //тип обрабатываемой ошибки
    @ResponseBody //указывает, что возвращаемое в методе значение должно быть связано с телом web- ответа
    @Order(Ordered.HIGHEST_PRECEDENCE) //приоритет обработки. в данном случае высочайший.
    public ErrorInfo handleError(HttpServletRequest req, NotFoundException e) {
        return logAndGetErrorInfo(req, e, false);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler(BindException.class)
    @ResponseBody
    @Order(Ordered.HIGHEST_PRECEDENCE + 2)
    public ErrorInfo badRequest(HttpServletRequest req, BindException e) {
        return logAndGetErrorInfo(req, e, true);
    }


    @ResponseStatus(value = HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    @Order(Ordered.HIGHEST_PRECEDENCE + 1)
    public ErrorInfo conflict(HttpServletRequest req, DataIntegrityViolationException e) {// DataIntegrityViolationException - дубилмрование ключей
        return logAndGetErrorInfo(req, e, true);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @Order(Ordered.LOWEST_PRECEDENCE)
    public ErrorInfo handleError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, true);
    }

    public ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, boolean logException) {
        if (logException) {
            LOG.error("Exception at request " + req.getRequestURL(), e);
        } else {
            LOG.warn("Exception at request " + req.getRequestURL() + ": " + e.toString());
        }
        return new ErrorInfo(req.getRequestURL(), e);
    }
}
