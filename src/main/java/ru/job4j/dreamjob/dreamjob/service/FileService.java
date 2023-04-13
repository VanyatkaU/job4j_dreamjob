package ru.job4j.dreamjob.dreamjob.service;

import ru.job4j.dreamjob.dreamjob.dto.FileDto;
import ru.job4j.dreamjob.dreamjob.model.File;

import java.util.Optional;

public interface FileService {

    File save(FileDto fileDto);

    Optional<FileDto> getFileById(int id);

    boolean deleteById(int id);
}
