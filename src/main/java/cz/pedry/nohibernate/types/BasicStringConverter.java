package cz.pedry.nohibernate.types;

import cz.pedry.nohibernate.utils.NoHibernateUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BasicStringConverter implements AttributeConverter<Object, String> {

    @Override
    public String convertToDatabaseColumn(Object o) {
        return NoHibernateUtils.object2CustomString(o);
    }

    @Override
    public Object convertToEntityAttribute(String s) {
        return NoHibernateUtils.customString2Object(s);
    }

}
