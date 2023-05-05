package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class IndexControllerTest {

    private final IndexController indexController = new IndexController();

    @Test
    public void whenRequestIndexThenGetIndexPage() {

        assertThat(indexController.getIndex()).isEqualTo("index");
    }

}