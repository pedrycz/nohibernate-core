NoHibernate
==========

NoHibernate provides mapping of Hibernate managed entities in non-relational way. It is especially useful for storing in database interfaces with many different child classes, which is almost impossible with pure Hibernate.

[![](https://jitpack.io/v/ppedrycz/nohibernate-core.svg)](https://jitpack.io/#ppedrycz/nohibernate-core)

### Gradle setup
```gradle
repositories {
    ...
    maven {
        url 'https://jitpack.io'
    }
}

dependencies {
    ...
    compile("com.github.ppedrycz:nohibernate-core:0.2")
}
```

### Maven setup
```maven
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.ppedrycz</groupId>
        <artifactId>nohibernate-core</artifactId>
        <version>0.2</version>
    </dependency>
</dependencies>
```

### Library initialization
```Java
public class MyApplication {
    public static void main(String[] args) {
        
        NoHibernateUtils.initialize();
        
        /* rest of your code */

    }
}
```

### Example usage
```Java
public class Geometry { /* fields, getters, setters */ }
public class Point extends Geometry { /* fields, getters, setters */ }
public class Rectangle extends Geometry { /* fields, getters, setters */ }
public class Circle extends Geometry { /* fields, getters, setters */ }
public class Square extends Geometry { /* fields, getters, setters */ }
public class Polygon extends Geometry { /* fields, getters, setters */ }
public class Triangle extends Geometry { /* fields, getters, setters */ }

@Entity
public class Sprite {

    /* constructors, other fields, getters, setters */

    @Type(type = NoHibernate.Type.BASIC)
    private Geometry geometry;

}

```
