package com.serhii.myproject.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ApplicationExceptionHandler {
    public final Logger logger = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    @ExceptionHandler(MyTeamAppException.class)
    public ModelAndView handleException(Exception exception) {
        logger.info("One of Cinrollers exception thrown");

        ModelAndView modelAndView = new ModelAndView("error400", HttpStatus.BAD_REQUEST);
        modelAndView.addObject("errorInfo", exception.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(NullEntityReferenceException.class)
    public ModelAndView handleNullEntityReferenceException(NullEntityReferenceException exception) {
        logger.info("NullEntityReferenceException thrown");

        ModelAndView modelAndView = new ModelAndView("error500", HttpStatus.INTERNAL_SERVER_ERROR);
        modelAndView.addObject("errorInfo", exception.getMessage());
        return modelAndView;
    }

    public ModelAndView handleEntityNotFoundException(EntityNotFoundException exception) {
        logger.info("EntityNotFoundException thrown");

        ModelAndView modelAndView = new ModelAndView("error404", HttpStatus.NOT_FOUND);
        modelAndView.addObject("errorInfo", exception.getMessage());
        return modelAndView;
    }
}
