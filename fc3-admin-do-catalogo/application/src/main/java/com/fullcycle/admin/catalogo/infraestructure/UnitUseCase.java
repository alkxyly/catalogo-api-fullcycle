package com.fullcycle.admin.catalogo.infraestructure;

/**
 * Para casos de uso que não retornam nada
 */
public abstract class UnitUseCase<IN> {

    public abstract void execute(IN anIn);
}
