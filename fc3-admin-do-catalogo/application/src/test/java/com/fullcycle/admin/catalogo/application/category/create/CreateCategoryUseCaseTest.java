package com.fullcycle.admin.catalogo.application.category.create;

import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class CreateCategoryUseCaseTest {

    //1. Teste do caminho feliz
    //2. Teste passando uma propriedade inválida (name)
    //3. Teste criando uma categoria inativa
    //4. Teste simulando um erro generico vindo do gateway
    @InjectMocks
    private DefaultCreateCategoryUseCase useCase;

    @Mock
    private CategoryGateway gateway;

    @BeforeEach
    void clean(){
        Mockito.reset(gateway);
    }

    @Test
    void givenAValidCommand_whenCallCreateCategory_shouldReturnCategoryId(){
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        //Command é um padrão de projeto, mas poderia ser um CreateCategoryDTO
        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        Mockito.when(gateway.create(any()))
                .thenAnswer(returnsFirstArg());


        final var actualOutput = useCase.execute(aCommand);


        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());

        //Comparar o que veio de entrada quando chamamos o gateway.create com o que esperamos que tenha vindo
        verify(gateway, Mockito.times(1))
                .create(Mockito.argThat(aCategory ->{
                    return Objects.equals(expectedName, aCategory.getName())
                            && Objects.equals(expectedDescription, aCategory.getDescription())
                            && Objects.equals(expectedIsActive, aCategory.isActive())
                            && Objects.nonNull(aCategory.getID())
                            && Objects.nonNull(aCategory.getCreatedAt())
                            && Objects.nonNull(aCategory.getUpdatedAt())
                            && Objects.isNull(aCategory.getDeletedAt());
                    }
                ));
    }

    @Test
    void givenAValidCategory_whenCallCreateCategory_thenShouldReturnCategoryInactivate(){
        final String expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        Mockito.when(gateway.create(any()))
                .thenAnswer(returnsFirstArg());

        final var aCategoryOutput = useCase.execute(aCommand);

        assertNotNull(aCategoryOutput);
        assertNotNull(aCategoryOutput.id());

        verify(gateway, Mockito.times(1))
                .create(Mockito.argThat(aCategory ->{
                            return Objects.equals(expectedName, aCategory.getName())
                                    && Objects.equals(expectedDescription, aCategory.getDescription())
                                    && Objects.equals(expectedIsActive, aCategory.isActive())
                                    && Objects.nonNull(aCategory.getID())
                                    && Objects.nonNull(aCategory.getCreatedAt())
                                    && Objects.nonNull(aCategory.getUpdatedAt())
                                    && Objects.nonNull(aCategory.getDeletedAt());
                        }
                ));
    }

    @Test
    void givenAValidCommand_whenGatewayThrowsRandomException_shouldReturnException(){
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedMessageError = "Gateway error";

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);


        Mockito.when(gateway.create(any()))
                .thenThrow(new IllegalStateException("Gateway error"));

        final var actualException = assertThrows(IllegalStateException.class, () -> useCase.execute(aCommand));

        assertEquals(expectedMessageError, actualException.getMessage());
        verify(gateway, Mockito.times(1))
                .create(any());
    }

    @Test
    void givenAnInValidName_whenCallsCreateCategory_thenShouldReturnDomainException(){
        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expetedMessageException = "'name' should not be null";
        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        final var categoryUseCase = new DefaultCreateCategoryUseCase(gateway);
        final var actualException =  assertThrows( DomainException.class, () -> categoryUseCase.execute(aCommand));

        assertEquals(expetedMessageException, actualException.getMessage());

        Mockito.verify(gateway, times(0)).create(any());
    }

}
