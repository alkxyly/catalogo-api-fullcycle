package com.fullcycle.admin.catalogo.infraestructure.category.retrive.list;

import com.fullcycle.admin.catalogo.infraestructure.UseCase;
import com.fullcycle.admin.catalogo.domain.category.CategorySearchQuery;
import com.fullcycle.admin.catalogo.domain.pagination.Pagination;

public abstract class ListCategoriesUseCase
        extends UseCase<CategorySearchQuery, Pagination<CategoryListOutput>> {
}
