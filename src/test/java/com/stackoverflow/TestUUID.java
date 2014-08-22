package com.stackoverflow;

import org.junit.Test;

import javax.annotation.Nonnull;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class TestUUID
{
    @Test
    public void testUUID() throws Exception
    {
        final UUID v3 = UUID.nameUUIDFromBytes("www.example.org".getBytes());
        assertEquals("fd24c332-7b7b-3fe7-9fee-28bbdbf37350", v3.toString());
    }

    @Test
    public void jsUUID() throws Exception
    {
        final ScriptEngineManager sem = new ScriptEngineManager();
        final ScriptEngine se = sem.getEngineByName("JavaScript");

        se.eval(readScript("md5.js"));
        se.eval(readScript("uuid.js"));
        System.out.println(se.eval("underscore.UUID.v3('www.example.com');"));
    }

    private InputStreamReader readScript(@Nonnull final String name)
    {
        final InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
        return new InputStreamReader(is);
    }
}
