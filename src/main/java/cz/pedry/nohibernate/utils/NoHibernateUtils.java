package cz.pedry.nohibernate.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import cz.pedry.nohibernate.NoHibernate;

import java.io.IOException;

public class NoHibernateUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public NoHibernateUtils() { }

    public static String object2CustomString(Object o) {
        if (o == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
        String javaClass = o.getClass().getName();
        ObjectNode javaData = OBJECT_MAPPER.convertValue(o, ObjectNode.class);
        ObjectNode objectNode = OBJECT_MAPPER.createObjectNode();
        objectNode.put(NoHibernate.FIELD_CLASS, javaClass);
        objectNode.set(NoHibernate.FIELD_DATA, javaData);
        try {
            return OBJECT_MAPPER.writeValueAsString(objectNode);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Object \"" + o + "\" cannot be translated to string");
        }
    }

    public static Object customString2Object(String s) {
        String javaClass = null;
        try {
            JsonNode jsonNode = OBJECT_MAPPER.readTree(s);
            javaClass = jsonNode.get(NoHibernate.FIELD_CLASS).textValue();
            if (javaClass == null || javaClass.equals("")) {
                throw new IllegalArgumentException("String \"" + s + "\" has no \"" +
                        NoHibernate.FIELD_CLASS + "\" parameter");
            }
            return OBJECT_MAPPER.treeToValue(jsonNode.get(NoHibernate.FIELD_DATA), Class.forName(javaClass));
        } catch (IOException e) {
            throw new IllegalArgumentException("Provided string \"" + s + "\" is not in JSON format");
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Provided \"" + NoHibernate.FIELD_CLASS + "\" parameter \"" +
                    javaClass + "\" cannot be found on classpath");
        }
    }

}
