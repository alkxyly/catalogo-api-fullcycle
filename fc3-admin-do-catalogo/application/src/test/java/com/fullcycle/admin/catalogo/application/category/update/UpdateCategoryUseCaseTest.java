package com.fullcycle.admin.catalogo.application.category.update;

import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

import java.util.Objects;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UpdateCategoryUseCaseTest {

    @InjectMocks
    private DefaultUpdateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    void clean(){
        reset(categoryGateway);
    }

    //1. Teste do caminho feliz
    //2. Teste passando uma propriedade inválida (name)
    //3. Teste atualizando uma categoria para inativa
    //4. Teste simulando um erro generico vindo do gateway
    //5. Teste atualizando categoria passando um id inválido

    @Test
    void givenAValidCommand_whenCallsUpdateCategory_shouldReturnCategoryId() {
        final var aCategory =
                Category.newCategory("Filme", null, true);

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;


        final var expectedId = aCategory.getID();

        final var aCommand = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive);

        when(categoryGateway.findById(eq(expectedId)))
                    .thenReturn(Optional.of(aCategory.clone()));

        when(categoryGateway.update(any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        verify(categoryGateway, times(1))
                .findById(eq(expectedId));

        verify(categoryGateway, times(1))
                .update(argThat(
                        aUpdateCategory ->
                            Objects.equals(expectedName, aUpdateCategory.getName())
                                    && Objects.equals(expectedDescription, aUpdateCategory.getDescription())
                                    && Objects.equals(expectedIsActive, aUpdateCategory.isActive())
                                    && Objects.equals(expectedId, aUpdateCategory.getID())
                                    && Objects.equals(aCategory.getCreatedAt(), aUpdateCategory.getCreatedAt())
                                    && aCategory.getUpdatedAt().isBefore(aUpdateCategory.getUpdatedAt())
                                    && Objects.isNull(aUpdateCategory.getDeletedAt())
                ));
    }

    @Test
    void givenAInvalidName_whenCallsUpdateCategory_thenShouldReturnDomainException() {
        final var aCategory =
                Category.newCategory("Filme", null, true);

        final var expectedId = aCategory.getID();
        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var aCommand =
                UpdateCategoryCommand.with(expectedId.getValue(),expectedName, expectedDescription, expectedIsActive);

        when(categoryGateway.findById(expectedId)).thenReturn(Optional.of(Category.with(aCategory)));

        final var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

        Mockito.verify(categoryGateway, times(0)).update(any());
    }
}
