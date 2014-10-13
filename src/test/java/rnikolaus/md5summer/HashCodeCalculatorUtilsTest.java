/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rnikolaus.md5summer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rapnik
 */
public class HashCodeCalculatorUtilsTest {

    public HashCodeCalculatorUtilsTest() {
    }

    @Test
    public void testReadAndWrite() throws IOException {
        Map<String, String> map = new TreeMap<>();
        for (String s : new String[]{"a", "b", "c", "stuff with spaces"}) {
            String code=(s + "code").replaceAll(" ", "_");
            String path=s + "path"; 
            map.put(path,code);
        }
        File f = new File("testfile");
        HashCodeCalculatorUtils.writeMap(map, f);
        Map<String,String> result = HashCodeCalculatorUtils.readMap(f);
        assertEquals(map.size(), result.size());
        for (String s:map.keySet()){
            assertEquals(map.get(s), result.get(s));
        }
        Files.deleteIfExists(f.toPath());
    }
    
    @Test
    public void testDeleted(){
        Map<String,String> mapOld = new TreeMap<>();
        for (String s: new String[]{"a","b","c"}){
            mapOld.put(s+"path", s+"code");
        }
        Map<String,String> mapNew = new TreeMap<>(mapOld);
        mapNew.remove("apath");
        Map<String,String> result = HashCodeCalculatorUtils.getDeleted(mapOld, mapNew);
        assertEquals(mapOld.get("apath"), result.get("apath"));
        assertEquals(1, result.size());
    }

    @Test
    public void testCreated(){
        Map<String,String> mapOld = new TreeMap<>();
        for (String s: new String[]{"a","b","c"}){
            mapOld.put(s+"path", s+"code");
        }
        Map<String,String> mapNew = new TreeMap<>(mapOld);
        mapOld.remove("apath");
        Map<String,String> result = HashCodeCalculatorUtils.getCreated(mapOld, mapNew);
        assertEquals(mapNew.get("apath"), result.get("apath"));
        assertEquals(1, result.size());
    }
    
    @Test
    public void testChanged(){
        Map<String,String> mapOld = new TreeMap<>();
        for (String s: new String[]{"a","b","c"}){
            mapOld.put(s+"path", s+"code");
        }
        Map<String,String> mapNew = new TreeMap<>(mapOld);
        mapNew.put("apath","xxx");
        Map<String,String> result = HashCodeCalculatorUtils.getChanged(mapOld, mapNew);
        assertEquals("xxx", result.get("apath"));
        assertEquals(1, result.size());
        
    }

}
