package cz.pedry.nohibernate.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import cz.pedry.nohibernate.NoHibernate;

import java.io.IOException;

/**
 * Utility class containing methods used with serialization and deserialization
 */
public final class NoHibernateUtils {

    /**
     * Jackson ObjectMapper instance used to serialize/deserialize objects
     */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * Class loader used to load classes by name, by default current class loader
     */
    private static ClassLoader classLoader = NoHibernateUtils.class.getClassLoader();

    /**
     * Private constructor to avoid creating NoHibernateUtils objects
     */
    private NoHibernateUtils() { }

    /**
     * Serialize object to JSON with two sections: HEAD (containing JAVA_CLASS) and BODY (containing object itself)
     * @param o uncomplicated object you want to serialize
     * @return object serialized to JSON
     */
    public static String object2CustomString(Object o) {
        if (o == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
        ObjectNode objectNode = OBJECT_MAPPER.createObjectNode();
        ObjectNode headNode = OBJECT_MAPPER.createObjectNode();
        headNode.put(NoHibernate.Field.Head.JAVA_CLASS, o.getClass().getName());
        ObjectNode bodyNode = OBJECT_MAPPER.convertValue(o, ObjectNode.class);
        objectNode.set(NoHibernate.Field.HEAD, headNode);
        objectNode.set(NoHibernate.Field.BODY, bodyNode);
        try {
            return OBJECT_MAPPER.writeValueAsString(objectNode);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Object \"" + o + "\" cannot be translated to string");
        }
    }

    /**
     * Deserialize object serialized with {@link #object2CustomString(Object)}
     * @param s object in JSON format serialized with {@link #object2CustomString(Object)}
     * @return deserialized object
     */
    public static Object customString2Object(String s) {
        if (classLoader == null) {
            throw new NullPointerException("ClassLoader not provided");
        }
        String javaClass = null;
        try {
            JsonNode jsonNode = OBJECT_MAPPER.readTree(s);
            javaClass = jsonNode.get(NoHibernate.Field.HEAD).get(NoHibernate.Field.Head.JAVA_CLASS).textValue();
            if (javaClass == null || javaClass.equals("")) {
                throw new IllegalArgumentException("JSON \"" + s + "\" has no \"" +
                        NoHibernate.Field.Head.JAVA_CLASS + "\" parameter in \"" + NoHibernate.Field.HEAD + "\"");
            }
            return OBJECT_MAPPER.treeToValue(jsonNode.get(NoHibernate.Field.BODY), classLoader.loadClass(javaClass));
        } catch (IOException e) {
            throw new IllegalArgumentException("Provided string \"" + s + "\" is not in JSON format");
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Provided \"" + NoHibernate.Field.Head.JAVA_CLASS + "\" parameter \"" +
                    javaClass + "\" cannot be found on classpath of current class loader");
        }
    }

    /**
     * Initializes library with custom class loader
     * @param classLoader class loader that should be used to load classes by name
     */
    public static void setClassLoader(ClassLoader classLoader) {
        NoHibernateUtils.classLoader = classLoader;
    }

}
