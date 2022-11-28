package com.fullcycle.admin.catalogo.infraestructure;

public abstract class UseCase<IN, OUT> {

    public abstract OUT execute(IN anIn);
}
