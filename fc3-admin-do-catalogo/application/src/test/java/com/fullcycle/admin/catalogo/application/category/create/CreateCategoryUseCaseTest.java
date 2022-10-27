package com.fullcycle.admin.catalogo.application.category.create;

import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Objects;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;

class CreateCategoryUseCaseTest {

    //1. Teste do caminho feliz
    //2. Teste passando uma propriedade inválida (name)
    //3. Teste criando uma categoria inativa
    //4. Teste simulando um erro generico vindo do gateway

    @Test
    void givenAValidCommand_whenCallCreateCategory_shouldReturnCategoryId(){
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        //Command é um padrão de projeto, mas poderia ser um CreateCategoryDTO
        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);
        final var gateway =  Mockito.mock(CategoryGateway.class);

        Mockito.when(gateway.create(any()))
                .thenAnswer(returnsFirstArg());

        final var useCase = new DefaultCreateCategoryUseCase(gateway);
        final var actualOutput = useCase.execute(aCommand);


        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());

        //Comparar o que veio de entrada quando chamamos o gateway.create com o que esperamos que tenha vindo
        verify(gateway, Mockito.times(1))
                .create(Mockito.argThat(aCategory ->{
                    return Objects.equals(expectedName, aCategory.getName())
                            && Objects.equals(expectedDescription, aCategory.getDescription())
                            && Objects.equals(expectedIsActive, aCategory.isActive())
                            && nonNull(aCategory.getID())
                            && nonNull(aCategory.getCreatedAt())
                            && nonNull(aCategory.getUpdatedAt())
                            && isNull(aCategory.getDeletedAt());
                    }
                ));

    }
}
