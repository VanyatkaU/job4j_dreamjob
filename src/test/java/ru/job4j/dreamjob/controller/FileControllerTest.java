package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import ru.job4j.dreamjob.dto.FileDto;
import ru.job4j.dreamjob.model.File;
import ru.job4j.dreamjob.service.FileService;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FileControllerTest {

    private FileService fileService;

    private FileController fileController;

    private FileDto fileDto;

    @BeforeEach
    public void initServices() {
        fileService = mock(FileService.class);
        fileController = new FileController(fileService);
        fileDto = mock(FileDto.class);
    }

    @Test
    public void whenRequestFileByIdThenGetFile() {
        var file = new File("name", "path1");
        when(fileService.getFileById(file.getId())).thenReturn(Optional.of(fileDto));

        var responseEntity = fileController.getById(file.getId());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void whenRequestFileByIdThenGetError() {
        var file = new File("name", "path1");
        when(fileService.getFileById(file.getId())).thenReturn(Optional.empty());

        var responseEntity = fileController.getById(file.getId());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}