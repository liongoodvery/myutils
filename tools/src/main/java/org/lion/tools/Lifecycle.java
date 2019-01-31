package org.lion.tools;

/**
 * Description:
 *
 * @author: lion
 * @time: 2019-01-30 15:27
 * @email: lion.good.very.first@gmail.com
 */
public interface Lifecycle {
    void start();

    default void stop() {
    }
}
