package com.fullcycle.admin.catalogo.infraestructure.category.delete;

import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.category.CategoryID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteCategoryUseCaseTest {

    @InjectMocks
    private DefaultDeleteCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    void cleanUp(){
        Mockito.reset(categoryGateway);
    }

    @Test
    void givenAValidId_whenCallsDeleteCategory_shouldBeOK(){
        final var aCategory = Category.newCategory("Filmes", "A categoria mais assistida", true);
        final var expectedId = aCategory.getID();

        doNothing()
                .when(categoryGateway).deleteById(eq(expectedId));

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));
        Mockito.verify(categoryGateway, times(1)).deleteById(eq(expectedId));
    }

    @Test
    void givenAInValidId_whenCallsDeleteCategory_shouldBeOK(){
        final var expectedId = CategoryID.from("123");

        doNothing()
                .when(categoryGateway).deleteById(eq(expectedId));

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));
        Mockito.verify(categoryGateway, times(1)).deleteById(eq(expectedId));
    }
    @Test
    void givenAValidId_whenGatewayThrowException_shouldReturnException(){
        final var aCategory = Category.newCategory("Filmes", "A categoria mais assistida", true);
        final var expectedId = aCategory.getID();

        doThrow(new IllegalStateException("Gateway error"))
                .when(categoryGateway).deleteById(eq(expectedId));

        Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(expectedId.getValue()));
        Mockito.verify(categoryGateway, times(1)).deleteById(eq(expectedId));
    }

}
