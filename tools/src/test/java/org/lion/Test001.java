package org.lion;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.net.URLEncoder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description:
 *
 * @author: lion
 * @time: 2018-12-27 20:19
 * @email: lion.good.very.first@gmail.com
 */

public class Test001 {
    @Test
    public void test13() throws Exception {
        String text = "action://shopping?id=2555";

        Pattern pattern = Pattern.compile("action://shopping\\?id=(\\d+)");

        Matcher matcher =
                pattern.matcher(text);

        if (matcher.matches()) {
            System.out.println(matcher.group(1));
        }

    }

    @Test
    public void test33() throws Exception {
        String text = "http://www.zgyjyx.net/retail/signUp?invitecode=0000000&id=444";

        Pattern pattern = Pattern.compile("(http|https)://(qa|www)\\.zgyjyx.net/retail/signUp(.*)");
        Matcher matcher = pattern.matcher(text);
        System.out.println(matcher.matches());


    }

    @Test
    public void test43() throws Exception {
        List<Integer> list = null;
        for (Integer integer : list) {
            System.out.println(integer);
        }
    }


    @Test
    public void test54() throws Exception {
        String text = "[[\"choice\",[{\"id\":66956,\"level\":1,\"is_checked\":1,\"requireprocess\":false},{\"id\":66960,\"level\":3,\"is_checked\":0,\"requireprocess\":false},{\"id\":66961,\"level\":2,\"is_checked\":0,\"requireprocess\":false},{\"id\":66965,\"level\":3,\"is_checked\":0,\"requireprocess\":false},{\"id\":66967,\"level\":2,\"is_checked\":0,\"requireprocess\":false}]],[[38,[{\"id\":37394,\"level\":1,\"is_checked\":0,\"requireprocess\":[]},{\"id\":37398,\"level\":2,\"is_checked\":0,\"requireprocess\":[]},{\"id\":37400,\"level\":3,\"is_checked\":0,\"requireprocess\":[]},{\"id\":37403,\"level\":2,\"is_checked\":0,\"requireprocess\":[]},{\"id\":37406,\"level\":2,\"is_checked\":0,\"requireprocess\":[]}]],[36,[{\"id\":37413,\"level\":1,\"is_checked\":0,\"requireprocess\":[]}]]]]";
        Pattern pattern = Pattern.compile("\"id\":(\\d+)");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            System.out.println(matcher.group(1));
        }

    }

    @Test
    public void test64() throws Exception {

        String text = "http://file.allitebooks.com/20150926/Applied Architecture Patterns on the Microsoft Platform, 2nd Edition.pdf";
        String last = StringUtils.substringAfterLast(text, "/");
        System.out.println(last);

        String beforeLast = StringUtils.substringBeforeLast(text, "/");
        System.out.println(beforeLast);

        System.out.println(beforeLast + "/" + URLEncoder.encode(last));

    }
}
