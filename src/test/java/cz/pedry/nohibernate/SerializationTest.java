package cz.pedry.nohibernate;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.pedry.nohibernate.model.Circle;
import cz.pedry.nohibernate.utils.NoHibernateUtils;
import org.junit.Assert;
import org.junit.Test;

public class SerializationTest {

    @Test
    public void simplePojoShouldBeSerializedProperly() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Object circleObject = new Circle(0.0, 1.0, 2.0);
        String circleString = NoHibernateUtils.object2CustomString(circleObject, objectMapper);
        String expectedString = "{\"class\":\"cz.pedry.nohibernate.model.Circle\",\"data\":{\"x\":0.0,\"y\":1.0,\"r\":2.0}}";
        Assert.assertEquals(expectedString, circleString);
    }

    @Test
    public void simplePojoShouldBeDeserializedProperly() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Object circleObject = new Circle(0.0, 1.0, 2.0);
        String circleString = "{\"class\":\"cz.pedry.nohibernate.model.Circle\",\"data\":{\"x\":0.0,\"y\":1.0,\"r\":2.0}}";
        Object expectedObject = NoHibernateUtils.customString2Object(circleString, objectMapper);
        Assert.assertEquals(circleObject.toString(), expectedObject.toString());
    }

}
