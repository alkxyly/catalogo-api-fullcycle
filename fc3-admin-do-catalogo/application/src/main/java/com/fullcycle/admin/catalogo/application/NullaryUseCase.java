package com.fullcycle.admin.catalogo.application;

/**
 * Para Casos de uso que não recebem nada e retornam alguma coisa
 * @param <OUT>
 */
public abstract  class NullaryUseCase<OUT> {

    public abstract  OUT execute();
}
