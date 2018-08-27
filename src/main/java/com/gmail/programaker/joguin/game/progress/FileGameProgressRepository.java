package com.gmail.programaker.joguin.game.progress;

import java.io.*;
import java.util.Optional;

/** GameProgressRepository implementation that persists the last GameProgress in a file */
public class FileGameProgressRepository implements GameProgressRepository {
    private final File file;

    public FileGameProgressRepository(File file) {
        this.file = file;
    }

    @Override
    public boolean save(GameProgress gameProgress) {
        try {
            if (!savedProgressExists()) {
                File dir = file.getParentFile();

                if (!dir.exists()) {
                    if (!dir.mkdirs()) {
                        return false;
                    }
                }
            }

            try (ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(file))) {
                o.writeObject(gameProgress);
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean savedProgressExists() {
        return file.exists();
    }

    @Override
    public Optional<GameProgress> restore() {
        if (!savedProgressExists()) {
            return Optional.empty();
        }

        try {
            try (ObjectInputStream i = new ObjectInputStream(new FileInputStream(file))) {
                return Optional.of((GameProgress) i.readObject());
            }
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
