package org.nutz.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.nutz.json.JsonFormat.FilterType;
import org.nutz.lang.Streams;

/**
 * JsonCompileExtend测试
 * @author juqkai(juqkai@gmail.com)
 */
public class JsonCompileFilterTest {
    List<String> mates = new ArrayList<String>();
    @Before
    public void init(){
        
    }
    @Test
    public void includeTest(){
        mates.add("age");
        JsonFormat format = new JsonFormat();
        format.setMates(mates);
        format.setFilterType(FilterType.include);
        Map<?, ?> map = (Map<?, ?>) Json.fromJson(Streams.fileInr("org/nutz/json/person.txt"), format);
        assertEquals(1, map.size());
    }
    @Test
    public void includeTest2(){
        mates.add("father.realname");
        mates.add("father.name");
        JsonFormat format = new JsonFormat();
        format.setMates(mates);
        format.setFilterType(FilterType.include);
        Map<?, ?> map = (Map<?, ?>) Json.fromJson(Streams.fileInr("org/nutz/json/person.txt"), format);
        Map<?, ?> father = (Map<?, ?>) map.get("father");
        assertEquals(2, father.size());
    }
    @Test
    public void excludeTest(){
        mates.add("father.realname");
        mates.add("father.name");
        JsonFormat format = new JsonFormat();
        format.setMates(mates);
        format.setFilterType(FilterType.exclude);
        Map<?, ?> map = (Map<?, ?>) Json.fromJson(Streams.fileInr("org/nutz/json/person.txt"), format);
        Map<?, ?> father = (Map<?, ?>) map.get("father");
        assertEquals(2, father.size());
        assertEquals(69, father.get("age"));
        assertEquals("1940-8-15", father.get("birthday"));
    }
    
    @Test
    public void listIncludeTest(){
        mates.add("users.name");
        JsonFormat format = new JsonFormat();
        format.setMates(mates);
        format.setFilterType(FilterType.include);
        Map<?, ?> map = (Map<?, ?>) Json.fromJson(Streams.fileInr("org/nutz/json/mateList.txt"), format);
        assertEquals(map.size(), 1);
        List<?> users = (List<?>) map.get("users");
        assertEquals(users.size(), 2);
        Map<?, ?> map2 = (Map<?, ?>) users.get(0);
        assertEquals(map2.size(), 1);
        assertEquals(map2.get("name"), "1");
    }
    @Test
    public void listexcludeTest(){
        mates.add("users.name");
        JsonFormat format = new JsonFormat();
        format.setMates(mates);
        format.setFilterType(FilterType.exclude);
        Map<?, ?> map = (Map<?, ?>) Json.fromJson(Streams.fileInr("org/nutz/json/mateList.txt"), format);
        assertEquals(map.size(), 2);
        List<?> users = (List<?>) map.get("users");
        assertEquals(users.size(), 2);
        Map<?, ?> map2 = (Map<?, ?>) users.get(0);
        assertEquals(map2.size(), 1);
        assertEquals(map2.get("age"), 12);
    }
}
