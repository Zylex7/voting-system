package com.zylex.votingsystem.web;

import com.zylex.votingsystem.AuthorizedUser;
import com.zylex.votingsystem.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        log.error("Exception at request " + req.getRequestURL(), e);
        Throwable rootCause = ValidationUtil.getRootCause(e);
        ModelAndView mav = new ModelAndView("exception/exception");
        mav.addObject("exception", rootCause);
        mav.addObject("message", rootCause.toString());
        AuthorizedUser authorizedUser = SecurityUtil.safeGet();
        if (authorizedUser != null) {
            mav.addObject("userTo", authorizedUser.getUserTo());
        }
        return mav;
    }
}
