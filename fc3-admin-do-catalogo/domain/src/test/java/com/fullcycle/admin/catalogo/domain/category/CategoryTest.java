package com.fullcycle.admin.catalogo.domain.category;

import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import com.fullcycle.admin.catalogo.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryTest {

    @Test
     void givenAValidParams_whenCallNewCategory_thenInstantiateACategory(){
        final var expectedName= "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var actualCategory =
                Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertNotNull(actualCategory);
        Assertions.assertNotNull(actualCategory.getID());
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertEquals(expectedIsActive, actualCategory.isActive());

        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    void givenAnInValidNullName_whenCallNewCategoryAndValidate_thenShouldReciveError(){
       final String expectedName = null;
       final var expectedDescription = "A categoria mais assistida";
       final var expectedIsActive = true;
       final var expectedErrorCount = 1;
       final var expectedErrorMessage = "'name' should not be null";

       final var actualCategory =
               Category.newCategory(expectedName, expectedDescription, expectedIsActive);

       final var actualException = Assertions.assertThrows(DomainException.class,
               () -> actualCategory.validate(new ThrowsValidationHandler()));

       assertEquals(expectedErrorCount,actualException.getErrors().size());
       assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }
}
