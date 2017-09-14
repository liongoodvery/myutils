package org.lion.utils.tools.commons;

import java.io.IOException;
import java.util.List;

/**
 * Created by more on 2016-05-31 21:28:04.
 * A Tool to write method trace info for a debug class.
 */
public class ProxyMethodLogger2 extends ProxyLogger
{
    public ProxyMethodLogger2(String[] args)
    {
        super(args);
    }

    /**
     * @param args the first argument is the file to be handled,the second argument is some information
     *             to determine the runtime environment.
     */
    public static void main(String[] args)  {
        try {
            new ProxyMethodLogger2(args).log();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void writeOut(List<String> lines, String fileName)
    {
        for (String line : lines)
        {
            System.out.println(line);
        }
    }

    @Override
    public String getJavaFormat(String function)
    {
        return String.format("System.out.println(\"%s()\")", function);
    }

    @Override
    protected String getAndroidFormat(String function)
    {
        return String.format("Log.i(TAG,\"%s()\");", function);
    }

}
