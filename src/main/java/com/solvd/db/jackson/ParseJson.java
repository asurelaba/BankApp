package com.solvd.db.jackson;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ParseJson<Entity> {
    private ObjectMapper objectMapper = new ObjectMapper();

    public void serialize(Entity entity, File outputFile) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputFile, entity);
        } catch (IOException e) {
            System.out.println("error in serialize" + e);
        }
    }

    public void serialize(List<Entity> entities, File outputFile) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputFile, entities);
        } catch (IOException e) {
            System.out.println("error in serialize" + e);
        }
    }

    public Entity deserialize(File inputFile, Class entityClass) {
        try {
            return (Entity) objectMapper.readValue(inputFile, entityClass);
        } catch (IOException e) {
            System.out.println("error in deserialize" + e);
        }
        return null;
    }
}
