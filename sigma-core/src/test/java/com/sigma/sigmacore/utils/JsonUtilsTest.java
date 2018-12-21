package com.sigma.sigmacore.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sigma.sigmacore.Person;
import lombok.experimental.var;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @version 1.0
 * date-time: $CREAT_DATE$-$CREAT_TIME$
 * desc:
 * @author zhenpeng
 **/
public class JsonUtilsTest {

    @Test
    public void serialize_null_will_return_string_null() throws IOException {
        var ret = JsonUtils.serialize(null);
        assertEquals("null", ret);
    }

    @Test
    public void serialize_person() throws IOException {
        Person person = new Person(18, "zhenpeng");
        var ret = JsonUtils.serialize(person);
        assertEquals("{\"age\":18,\"name\":\"zhenpeng\"}", ret);
    }

    @Test
    public void serialize_person_list() throws IOException {
        Person person = new Person(18, "zhenpeng");
        Person person1 = new Person(28, "pengzhen");
        List<Person> personList = new ArrayList<>();
        personList.add(person);
        personList.add(person1);

        var ret = JsonUtils.serialize(personList);
        assertEquals("[{\"age\":18,\"name\":\"zhenpeng\"},{\"age\":28,\"name\":\"pengzhen\"}]", ret);
    }

    @Test
    public void serialize_person_pretty() throws IOException {
        Person person = new Person(18, "zhenpeng");
        var ret = JsonUtils.serialize(person, true);
        System.out.println(ret);
    }

    @Test(expected = NullPointerException.class)
    public void deserialize_null_should_throw() throws IOException {
        var ret = JsonUtils.deserialize(null, Person.class);
    }

    @Test(expected = IOException.class)
    public void deserialize_empty() throws IOException {
        JsonUtils.deserialize("", Person.class);
    }

    @Test(expected = IOException.class)
    public void deserialize_not_right() throws IOException {
        JsonUtils.deserialize("[]", Person.class);
    }

    @Test(expected = IOException.class)
    public void deserialize_not_right_2() throws IOException {
        JsonUtils.deserialize("{\"age\":\"sd\"}", Person.class);
    }

    @Test
    public void deserialize_success() throws IOException {
        var ret = JsonUtils.deserialize("{\"age\":18,\"name\":\"zhenpeng\"}", Person.class);
        Assert.assertEquals(ret, new Person(18, "zhenpeng"));
    }

    @Test
    public void deserialize_success_refType() throws IOException {
        var ret = JsonUtils.deserialize("[{\"age\":18,\"name\":\"zhenpeng\"},{\"age\":28,\"name\":\"pengzhen\"}]", new TypeReference<List<Person>>() {
        });
        Assert.assertEquals(ret.size(), 2);
    }

    @Test
    public void deserialize_objectMapper() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        var person = JsonUtils.deserialize(objectMapper, "{\"age\":18,\"name\":\"zhenpeng\"}", new TypeReference<Person>() {
        });

        Assert.assertNotNull(person);
    }
}