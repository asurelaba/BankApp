package com.solvd.utilities.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvd.db.service.AddressService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

public class JsonParserUtil<Entity> {

    private static final Logger LOGGER = LogManager.getLogger(JsonParserUtil.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    public void serialize(Entity entity, File outputFile) {
        try {
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            df.setTimeZone(TimeZone.getTimeZone("EDT"));
            objectMapper.setDateFormat(df);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputFile, entity);
        } catch (IOException e) {
            LOGGER.error("error in serialize" + e);
        }
    }

    public void serialize(List<Entity> entities, File outputFile) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputFile, entities);
        } catch (IOException e) {
            LOGGER.error("error in serialize" + e);
        }
    }

    public Entity deserialize(File inputFile, Class entityClass) {
        try {
            return (Entity) objectMapper.readValue(inputFile, entityClass);
        } catch (IOException e) {
            LOGGER.error("error in deserialize" + e);
        }
        return null;
    }
}
