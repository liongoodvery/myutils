package org.lion.utils;

import java.nio.file.Path;

/**
 * Created by more on 2016-06-20 08:41:40.
 * The interface is used together with {@link WordCount}
 */
public interface IWordFilter {
    /**
     * check a word whether can pass a test.
     *
     * @param word the word to be checked
     * @return true if the word can pass the test
     */
    boolean filter(String word);
}
