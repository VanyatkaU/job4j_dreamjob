package ru.job4j.dreamjob.dreamjob.repository;

import ru.job4j.dreamjob.dreamjob.model.File;

import java.util.Optional;

public interface FileRepository {

    File save(File file);

    Optional<File> findById(int id);

    boolean deleteById(int id);
}
