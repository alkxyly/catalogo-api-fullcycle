package com.fullcycle.admin.catalogo.application;

import com.fullcycle.admin.catalogo.domain.category.Category;

public class UseCase {

    public Category execute(){
        return Category.newCategory("Séries", "Assista as novas séries", true);
    }
}
