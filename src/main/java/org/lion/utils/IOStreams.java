package org.lion.utils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

public class IOStreams {
    /**
     * Get a FileOutputStream of the specified file
     *
     * @param file file pathname
     * @return a FileOutputStream
     */
    public static OutputStream getFileOut(String file) {
        return getFileOut(file, false);
    }

    /**
     * Get a FileOutputStream of the specified file
     *
     * @param file   file path name
     * @param append true if append to the file
     * @return a FileOutputStream
     */
    public static OutputStream getFileOut(String file, boolean append) {

        OutputStream ret = null;
        try {
            ret = new FileOutputStream(file, append);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return ret;
    }

//    public static OutputStream getFileOut(File file) {
//        return getFileOut(file.getAbsolutePath(), false);
//    }

//    public static OutputStream getFileOut(File file, boolean append) {
//        return getFileOut(file.getAbsolutePath(), append);
//    }

    /**
     * Get a FileInputStream of the specified file
     *
     * @param file the file path name
     * @return A FileInputStream
     */
    public static InputStream getFileIn(String file) {
        InputStream ret = null;
        try {
            ret = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return ret;
    }

//    public static InputStream getFileIn(File file) {
//        return getFileIn(file.getAbsolutePath());
//    }

    /**
     * Get a BufferedInputStream
     *
     * @param in the specified InputStream
     * @return a BufferedInputStream
     */
    public static InputStream getBufferIn(InputStream in) {
        return new BufferedInputStream(in);
    }

    /**
     * Get a BufferedInputStream
     *
     * @param file the specified file
     * @return a BufferedInputStream
     */
    public static InputStream getBufferIn(String file) {
        return new BufferedInputStream(getFileIn(file));
    }

//    public static InputStream getBufferIn(File file) {
//        return new BufferedInputStream(getFileIn(file));
//    }

    /**
     * Get a BufferedOutputStream
     *
     * @param out a OutputStream
     * @return a BufferedOutputStream
     */
    public static OutputStream getBufferOut(OutputStream out) {
        return new BufferedOutputStream(out);
    }

    public static OutputStream getBufferOut(String file) {
        return new BufferedOutputStream(getFileOut(file));
    }

//    public static OutputStream getBufferOut(File file) {
//        return new BufferedOutputStream(getFileOut(file));
//    }

    /**
     * Get a BufferedOutputStream of the specified file
     *
     * @param file   the file pathname
     * @param append true if append to the file
     * @return a BufferedOutputStream
     */
    public static OutputStream getBufferOut(String file, boolean append) {
        return new BufferedOutputStream(getFileOut(file, append));
    }

//    public static OutputStream getBufferOut(File file, boolean append) {
//        return new BufferedOutputStream(getFileOut(file, append));
//    }

    /**
     * Get a FileReader of the specified file
     *
     * @param file the pathname of a file
     * @return a FileReader
     */
    public static Reader getFileReader(String file) {
        try {
            return new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get a FileWriter to the specified file
     *
     * @param file   the pathname of the file
     * @param append true if append to the file
     * @return a FileWriter
     */
    public static Writer getFileWriter(String file, boolean append) {
        try {
            return new FileWriter(file, append);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Get a FileWriter of a specified file, truncate the existing file.
     *
     * @param file the pathname of the file
     * @return a FileWriter
     */
    public static Writer getFileWriter(String file) {
        return getFileWriter(file, false);
    }

    /**
     * Get a BufferedReader
     *
     * @param in the specified Reader
     * @return a BufferedReader
     */
    public static Reader getBufferReader(Reader in) {
        return new BufferedReader(in);
    }

    /**
     * Get a BufferedReader of a specified file
     *
     * @param file the pathname of the file
     * @return a BufferedReader
     */
    public static Reader getBufferReader(String file) {
        try {
            return new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get a BufferedWriter of a specified Writer
     *
     * @param out the Writer
     * @return a BufferedWriter
     */
    public static Writer getBufferWriter(Writer out) {
        return new BufferedWriter(out);
    }

    /**
     * Get a BufferedWriter of a specified file, truncate the existing file
     *
     * @param file the specified file
     * @return a BufferedWriter
     */
    public static Writer getBufferWriter(String file) {
        return getFileWriter(file, false);
    }

    /**
     * Get a BufferedWriter of a specified file
     *
     * @param file   the specified file
     * @param append true if append to the file
     * @return a BufferedWriter
     */
    public static Writer getBufferWriter(String file, boolean append) {
        try {
            return new BufferedWriter(new FileWriter(file, append));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Copy an InputStream to an OutputStream
     *
     * @param in  a InputStream
     * @param out a OutputStream
     */
    public static void copy(InputStream in, OutputStream out) {
        byte[] buf = new byte[1 << 13];
        int len;
        try {
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            closeStream(out);
            closeStream(in);
        }
    }

    public static void copy(String src, String srcCharset, String dest, String destCharset, boolean append) {
        final int BUFF_SIZE = 1 << 13;
        char[] buf = new char[BUFF_SIZE];
        Reader reader;
        Writer writer;
        try {
            reader = new InputStreamReader(getFileIn(src), srcCharset);
            writer = new OutputStreamWriter(getFileOut(dest, append), destCharset);
            int len;
            while ((len = reader.read(buf)) != -1) {
                writer.write(buf, 0, len);
            }
            writer.close();
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void copy(String src, String srcCharset, String dest, String destCharset) {
        copy(src, srcCharset, dest, destCharset, false);
    }

    public static void copy(String src, String dest) {
        String charset = Charset.defaultCharset().toString();
        copy(src, charset, dest, charset, false);
    }

    public static void copyDir(String src, String dest) {
        if (src.equals(dest)) {
            throw new RuntimeException("Dest can not be the Source");
        }

        File srcDir = new File(src);
        if (!srcDir.exists()) {
            throw new RuntimeException("Source does not exists");
        }

        File destDir = new File(dest);
        if (destDir.exists()) {
            throw new RuntimeException("dest Dir already exists");
        }


        copyDir0(srcDir, destDir);


    }

    private static void copyDir0(File src, File dest) {

    }


    public static void deleteDir(String dir) {

    }

    public static void tree(String dir) {
        tree(System.out, dir);
    }

    public static void tree(PrintStream out, String dir) {
        File init = new File(dir);
        if (!init.exists()) {
            throw new RuntimeException("File does not exist");
        }
        out.println(init.getName());
        tree0(out, init, 1);
    }

    private static void tree0(PrintStream out, File init, final int level) {
        File[] files = init.listFiles();
        if (files == null) {
            return;
        }

        Set<File> fileSet = new HashSet<>();
        Set<File> dirSet = new HashSet<>();
        for (File file : files) {
            if (file.isDirectory()) {
                dirSet.add(file);
            } else if (file.isFile()) {
                fileSet.add(file);
            }
        }
        for (File file : fileSet) {
            printIndent(out, level);
            out.println(file.getName());
        }
        for (File file : dirSet) {
            printIndent(out, level);
            out.println(file.getName());
            tree0(out, file, level + 1);
        }
    }

    private static void printIndent(PrintStream out, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level - 1; i++) {
            sb.append("|   ");
        }
        sb.append("|----");
        out.print(sb);
    }

    /**
     * Convert an InputStream to a String using the specific encoding
     *
     * @param in       the InputStream to be decoded
     * @param encoding the encoding to decode the InputStream
     * @return a String or null if in is null
     */
    public static String stream2String(InputStream in, String encoding) {
        if (in == null) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buff = new byte[1 << 13];
        int len = -1;
        try {
            while ((len = in.read(buff)) != -1) {
                System.out.println(len);
                out.write(buff, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(out.toByteArray(), Charset.forName(encoding));
    }
    public static void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public static String stream2String(InputStream in) {
        return stream2String(in, "utf-8");
    }
}