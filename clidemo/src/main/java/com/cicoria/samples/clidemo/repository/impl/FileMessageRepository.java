package com.cicoria.samples.clidemo.repository.impl;

import com.cicoria.samples.clidemo.model.Message;
import com.cicoria.samples.clidemo.repository.MessageRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FileMessageRepository implements MessageRepository<Message, String> {
    private static final Logger log = LoggerFactory.getLogger(FileMessageRepository.class);

    private final String location;

    public FileMessageRepository(String location) {
        this.location = location;
        log.info("using location " + this.location);
    }

    @Override
    public Message findOne(String s) {
       throw new UnsupportedOperationException();
    }

    @Override
    public Set<Message> findAll()
    {
        Set<Message> rv = new HashSet<>();
        try {
            for(String file: listFilesUsingFileWalkAndVisitor(this.location))
            {
                if (Files.isReadable(Path.of(file))){
                    ObjectMapper mapper = new ObjectMapper();
                    Map<?, ?> map = mapper.readValue(Paths.get(file).toFile(), Map.class);
                    for (Map.Entry<?, ?> entry : map.entrySet()) {
                        log.info(entry.getKey() + "=" + entry.getValue());
                    }
                }
                else {
                    log.error("File {} is not readable", file);
                }
                rv.add(new Message(file));
            }

        }
        catch (IOException ex) {
            ;
        }
        return rv;
    }


    public Set<String> listFilesUsingFileWalkAndVisitor(String dir) throws IOException {
        Set<String> fileList = new HashSet<>();
        Files.walkFileTree(Paths.get(dir), new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                    throws IOException {
                if (!Files.isDirectory(file)) {
                    fileList.add(file.toAbsolutePath().toString());
                }
                return FileVisitResult.CONTINUE;
            }
        });
        return fileList;
    }
}
