package com.fullcycle.admin.catalogo.application;

/**
 * Para casos de uso que não retornam nada
 */
public abstract class UnitUseCase<IN> {

    public abstract void execute(IN anIn);
}
