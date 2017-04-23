package cz.pedry.nohibernate.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import cz.pedry.nohibernate.NoHibernate;

public class NoHibernateUtils {

    public NoHibernateUtils() { }

    public static String object2CustomString(Object o, ObjectMapper objectMapper) {
        try {
            String javaClass = o.getClass().getName();
            ObjectNode javaData = objectMapper.convertValue(o, ObjectNode.class);
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put(NoHibernate.FIELD_CLASS, javaClass);
            objectNode.set(NoHibernate.FIELD_DATA, javaData);
            return objectMapper.writeValueAsString(objectNode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object customString2Object(String s, ObjectMapper objectMapper) {
        try {
            JsonNode jsonNode = objectMapper.readTree(s);
            String javaClass = jsonNode.get(NoHibernate.FIELD_CLASS).textValue();
            return objectMapper.treeToValue(jsonNode.get(NoHibernate.FIELD_DATA), Class.forName(javaClass));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
