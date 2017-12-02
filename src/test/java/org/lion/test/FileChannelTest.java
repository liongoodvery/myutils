package org.lion.test;

import org.junit.Before;
import org.junit.Test;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lion on 17-10-14.
 */
public class FileChannelTest {
    private String Dir;

    @Before
    public void before() {
        Dir = System.getenv("PWD");
    }

    @Test
    public void test8() throws Exception {
        RandomAccessFile file = new RandomAccessFile("/home/lion/IdeaProjects/myutils/src/test/java/org/lion/test/FileChannelTest.java", "r");
        FileChannel channel = file.getChannel();
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        channel.read(buffer);
        buffer.flip();

        channel.transferTo(0, channel.size(), Channels.newChannel(System.out));
    }

    @Test
    public void test34() throws Exception {
        Pattern pattern = Pattern.compile("(\\d+)_?(\\d*)");
        Matcher matcher = pattern.matcher("2012_11");
        if (matcher.matches()) {
            System.out.println(matcher.group(0));
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
        }

    }


    @Test
    public void test49() throws Exception {
        String txt = "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tr><th align=\"left\" style=\"font-weight: normal\">(1)我的答案<span class=\"my_label_guard\" style=\"display: none\"></span></th><th align=\"left\" style=\" font-weight: normal\">正确答案<span class=\"right_label_guard\" style=\"display: none\"></span></th></tr><tr> <th align=\"left\" style=\"font-weight: normal\"><span style=\"color:#ff3333\">①无</span></th><th align=\"left\" style=\" font-weight: normal\"><span style=\"color:#41d3bd\">①<span class=\"display_no_answer\" style=\"display:none;\"></span></span></th></tr></table><p>解题过程: </p>/span></th><th align=\"left\" style=\" font-weight: normal\">正确答案<span class=\"right_label_guard\" style=\"display: none\"></span></th></tr><tr> <th align=\"left\" style=\"font-weight: normal\"><span style=\"color:#ff3333\">①无</span></th><th align=\"left\" style=\" font-weight: normal\"><span style=\"color:#41d3bd\">①<span class=\"display_no_answer\" style=\"display:none;\"></span></span></th></tr><tr> <th align=\"left\" style=\"font-weight: normal\"><span style=\"color:#ff3333\">②无</span></th><th align=\"left\" style=\" font-weight: normal\"><span style=\"color:#41d3bd\">②<span class=\"display_no_answer\" style=\"display:none;\"></span></span></th></tr></table><p>解题过程: </p>\n/span></th><th align=\"left\" style=\" font-weight: normal\">正确答案<span class=\"right_label_guard\" style=\"display: none\"></span></th></tr><tr> <th align=\"left\" style=\"font-weight: normal\"><span style=\"color:#ff3333\">①无</span></th><th align=\"left\" style=\" font-weight: normal\"><span style=\"color:#41d3bd\">①<span class=\"display_no_answer\" style=\"display:none;\"></span></span></th></tr></table><p>解题过程: </p>\n/span></th><th align=\"left\" style=\" font-weight: normal\">正确答案<span class=\"right_label_guard\" style=\"display: none\"></span></th></tr><tr> <th align=\"left\" style=\"font-weight: normal\"><span style=\"color:#ff3333\">①无</span></th><th align=\"left\" style=\" font-weight: normal\"><span style=\"color:#41d3bd\">①<span class=\"display_no_answer\" style=\"display:none;\"></span></span></th></tr><tr> <th align=\"left\" style=\"font-weight: normal\"><span style=\"color:#ff3333\">②无</span></th><th align=\"left\" style=\" font-weight: normal\"><span style=\"color:#41d3bd\">②<span class=\"display_no_answer\" style=\"display:none;\"></span></span></th></tr></table><p>解题过程: </p>\n";
        Pattern pattern = Pattern.compile("(<th.*?</th>)");
        Matcher matcher = pattern.matcher(txt);
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }
}
