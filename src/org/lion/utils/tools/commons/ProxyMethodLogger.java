package org.lion.utils.tools.commons;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * Created by more on 2016-05-31 21:28:04.
 * A Tool to write method trace info for a debug class.
 */
public class ProxyMethodLogger extends ProxyLogger
{
    public ProxyMethodLogger(String[] args)
    {
        super(args);
    }

    /**
     * @param args the first argument is the file to be handled,the second argument is some information
     *             to determine the runtime environment.
     */
    public static void main(String[] args) throws IOException
    {
        new ProxyMethodLogger(args).log();
    }


    @Override
    public void writeOut(List<String> lines, String fileName)
    {
        try
        {
            Files.write(Paths.get(fileName),
                        lines,
                        Charset.forName("UTF-8"),
                        StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }


    @Override
    public String getJavaFormat(String function)
    {
        return String.format("System.out.println(\"%s()\")", function);
    }

    @Override
    public String getAndroidFormat(String function)
    {
        return String.format("Logger.t(TAG).i(\"%s()\");", function);
    }
}
