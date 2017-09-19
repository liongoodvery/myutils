package org.lion.test;

import org.junit.Assert;
import org.junit.Test;
import org.lion.utils.Commons;

/**
 * Created by lion on 17-9-15.
 */
public class CommonsTest {
    @Test
    public void testSplitAndroidId() throws Exception {
        Assert.assertEquals("timepicker", Commons.splitAndroidId("@+id/timepicker"));
        Assert.assertEquals("", Commons.splitAndroidId("@+id/"));
        Assert.assertEquals("@+id", Commons.splitAndroidId("@+id"));
        Assert.assertEquals("", Commons.splitAndroidId(""));
    }

    @Test
    public void testSimpleClassName() throws Exception {
        Assert.assertEquals("a", Commons.simpleClassName("a"));
        Assert.assertEquals("a", Commons.simpleClassName("b.a"));
        Assert.assertEquals("", Commons.simpleClassName(""));
        Assert.assertEquals("", Commons.simpleClassName("a."));
    }

    @Test
    public void testFileNameWithoutExt() throws Exception {
        Assert.assertEquals("a", Commons.fileNameWithoutExt("a.java"));
        Assert.assertEquals("a", Commons.fileNameWithoutExt("b/a.java"));
        Assert.assertEquals("a", Commons.fileNameWithoutExt("b/a"));
        Assert.assertEquals("", Commons.fileNameWithoutExt(""));

    }
}
