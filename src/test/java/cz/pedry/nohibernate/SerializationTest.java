package cz.pedry.nohibernate;

import cz.pedry.nohibernate.model.Circle;
import cz.pedry.nohibernate.utils.NoHibernateUtils;
import org.junit.Assert;
import org.junit.Test;

public class SerializationTest {

    @Test
    public void simplePojoShouldBeSerializedProperly() throws Exception {
        Object circleObject = new Circle(0.0, 1.0, 2.0);
        String circleString = NoHibernateUtils.object2CustomString(circleObject);
        String expectedString = "{\"HEAD\":{\"JAVA_CLASS\":\"cz.pedry.nohibernate.model.Circle\"},\"BODY\":{\"x\":0.0,\"y\":1.0,\"r\":2.0}}";
        Assert.assertEquals(expectedString, circleString);
    }

    @Test
    public void simplePojoShouldBeDeserializedProperly() throws Exception {
        String circleString = "{\"HEAD\":{\"JAVA_CLASS\":\"cz.pedry.nohibernate.model.Circle\"},\"BODY\":{\"x\":0.0,\"y\":1.0,\"r\":2.0}}";
        Object circleObject = NoHibernateUtils.customString2Object(circleString);
        Object expectedObject = new Circle(0.0, 1.0, 2.0);
        Assert.assertEquals(expectedObject.toString(), circleObject.toString());
    }

}
