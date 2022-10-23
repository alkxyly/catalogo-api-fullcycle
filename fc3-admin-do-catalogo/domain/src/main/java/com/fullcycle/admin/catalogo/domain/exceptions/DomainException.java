package com.fullcycle.admin.catalogo.domain.exceptions;

import com.fullcycle.admin.catalogo.domain.validation.Error;

import java.util.List;

public class DomainException extends  NoStackTraceException{

    private final List<Error> errors;

    public DomainException(final String message, final List<Error> anErros){
        super(message);
        this.errors = anErros;
    }

    public static DomainException with(final Error error){
        return new DomainException(error.message(), List.of(error));
    }

    public static DomainException with(final List<Error> anErrors){
        return new DomainException("",anErrors);
    }

    public List<Error> getErrors() {
        return errors;
    }
}
