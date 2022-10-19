package com.fullcycle.admin.catalogo.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class MainTest {


    @Test

    void testMain(){
        Assertions.assertNotNull(new Main());
        Main.main(new String[]{});
    }
}
