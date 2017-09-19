package org.lion.test;

import org.junit.Assert;
import org.junit.Test;
import org.lion.utils.Strings;

/**
 * Created by lion on 17-9-15.
 */
public class StringsTest {
    @Test
    public void testCapWord() throws Exception {
        Assert.assertEquals("", Strings.capWord(null));
        Assert.assertEquals("", Strings.capWord(""));
        Assert.assertEquals("A", Strings.capWord("a"));
        Assert.assertEquals("A", Strings.capWord("A"));
        Assert.assertEquals("Abc", Strings.capWord("abc"));
    }

    @Test
    public void testUnderLineToCamel() throws Exception {
        Assert.assertEquals("", Strings.underLineToCamel(null, null));
        Assert.assertEquals("", Strings.underLineToCamel("", null));
        Assert.assertEquals("", Strings.underLineToCamel("m", null));
        Assert.assertEquals("mA", Strings.underLineToCamel("m", "a"));
        Assert.assertEquals("mAbcDef", Strings.underLineToCamel("m", "abc_def"));
        Assert.assertEquals("abcDef", Strings.underLineToCamel("", "abc_def"));
        Assert.assertEquals("abcDef", Strings.underLineToCamel(null, "abc_def"));
    }

    @Test
    public void testIsEmpty() throws Exception {
        Assert.assertTrue(Strings.isEmpty(null));
        Assert.assertTrue(Strings.isEmpty(""));
        Assert.assertTrue(!Strings.isEmpty("a"));


    }
}
