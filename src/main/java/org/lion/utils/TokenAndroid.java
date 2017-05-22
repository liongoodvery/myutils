package org.lion.utils;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

/**
 * Created by lion on 4/6/17.
 */
public class TokenAndroid {
    public static final List<String> FileNamefilters;
    public static final Set<String> nameKeyfilters;
    public static final String out;
    public static final String initDir;
    public static final Integer versionCode;
    public static final String versionName;
    public static final String splitor;
    public static final int maxLine;
    public static Map<String, Token> tokenMap;
    public static final String androidId;
    public static final String wordRegex;

    static {
        FileNamefilters = new ArrayList<String>() {{
            add("java");
            add("xml");
        }};
        String tokes = "string the name msgid int public if to return final android of is null link static for this void in new private xliff String import g The License be param and item or boolean that id not android the view Override with an by code on true false will value class 1 may under View data see This 0 it permdesc else hide from float product License as use case long s to d are when TAG distributed size context type should at you permlab throws state set content can 2 Log name key throw byte java used li file method If id package title result text TYPE td get mediasize try length a which has index util default obtain Context de child event for position super native catch message ee specific device append Object in either values org start user os You http copy object we required default ACTION Android lockscreen IS intent type permissions item NUMBER must Returns attr Intent only break of no www all OF language call protected under except AS media writing OR current ANY style have listener layout apache Parcel applicable software KIND action flags Open Version Source express Unless limitations implied assertEquals Apache mode list WITHOUT licenses layout Copyright LICENSE CONDITIONS governing WARRANTIES agreed law Licensed compliance BASIS Project MODE RemoteException notification STATE time support Bundle info given add activity styleable input is one source extends callback width any called was app tablet out offset or end com service its number count other height application ArrayList drawable specified mode on top FLAG request ID target left widget description app KEY quantity been may kg array LayoutParams IllegalArgumentException media parent dest List and internal Uri so toString format label system button distributed USB action equals drawable ViewGroup test right at no graphics password ext tv plurals but by whether animation pin this CharSequence http string not res Parcelable abstract window using false array NAME first iso message put writeInt after provider with sb same synchronized Integer display na Activity wifi attrs storage interface bottom EXTRA Widget Drawable into permission Sets returned params instance attr path an annotation use text before ISO The dialog color readInt n keyguard attempts there Fi Set Exception Wi URI For being tag public Builder Rect cursor does msg Math adapter supported buffer code reply symbol usb session Android failed 8 data java mContext GL while then up sim associated 0f file contains IOException run KeyEvent accessibility instanceof true DEBUG Note enabled more views deprecated than do ul available fragment status parcel error picker o SIM DEFAULT you status information error resource matrix color implements function IBinder output builder uri lp xml RecyclerView assertTrue PIN alpha image switch tr where transition format content change args hardware application print example thread field row level NonNull menu route frame recycle com group such returns ref pattern be camera create la CONTENT TextView implementation Element ERROR audio specific TEXT calling screen query location mRS next que changed need mService currently Bitmap methods";
        nameKeyfilters = new HashSet<>();
        String[] splits = tokes.split("\\s");
        for (String split : splits) {
            nameKeyfilters.add(split);
        }
        out = "/home/lion/out.android.sql";
        initDir = "/home/share/software/android-sdk-linux";
        versionCode = 23;
        versionName = "android-" + versionCode;
        splitor = System.getProperty("file.separator", "/");
        maxLine = 1 << 16;
        tokenMap = new HashMap<>();
        androidId = "android:id=\"@";
        wordRegex = "[\\w$-]{2,}";
    }

    public static void main(String[] args) throws IOException {
        Files.walkFileTree(Paths.get(initDir), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                String path = file.toAbsolutePath().toString();
                String suffix = getSuffix(path);
                if (FileNamefilters.contains(suffix) && path.contains(splitor + versionName + splitor)) {
                    handleFile(file);
                }
                return FileVisitResult.CONTINUE;
            }
        });
        write2File(out);
    }

    public static void handleFile(Path file) throws IOException {
        String filePath = file.toAbsolutePath().toString();
        List<String> lines = Files.readAllLines(file);
        System.out.println("handle file:" + filePath);
        for (int i = 0; i < lines.size(); i++) {
            handleLine(filePath, lines.get(i), i);
        }

    }

    public static void handleLine(String file, String line, int lineNumber) {
//        findAndroidId(file, line, lineNumber);

        tokenizerLine(file, line, lineNumber);
    }

    private static void findAndroidId(String file, String line, int lineNumber) {
        if (!getSuffix(file).equals("xml")) {
            return;
        }
        int startIndex = line.indexOf(androidId);
        if (startIndex == -1) {
            return;
        }

        int endIndex = line.indexOf("\"", startIndex + androidId.length());
        if (endIndex == -1) {
            return;
        }
        String mid = line.substring(startIndex, endIndex);
        String[] splits = mid.split("=");
        if (splits.length != 2) {
            return;
        }


        String s1 = splits[1].trim();
        if (s1.length() < 3 || !s1.substring(0, 1).equals("\"")) {
            return;
        }


        handleWord(s1.substring(1), file, lineNumber, "androidId");
    }

    private static void tokenizerLine(String file, String line, int lineNumber) {
        StringTokenizer tokenizer = new StringTokenizer(line, ",\t\n\f\r()[]{} ;.=/\\*\"'<>.?`~!@#$%^&-_+:");
        while (tokenizer.hasMoreElements()) {
            String s = tokenizer.nextToken();
            handleWord(s, file, lineNumber, null);
        }
    }

    private static void handleWord(String word, String file, int lineNumber, String type) {
        if (!word.matches(wordRegex) || nameKeyfilters.contains(word)) {
            return;
        }
        if (!tokenMap.containsKey(word)) {
            tokenMap.put(word, new Token(word));
        }
        Token token = tokenMap.get(word);
        token.addWord(file, lineNumber, type);
    }

    public static void write2File(String out) throws IOException {
        List<String> output = new ArrayList<>(maxLine);
        Path outPath = Paths.get(out);
        if (Files.exists(outPath)) {
            Files.delete(outPath);
        }
        for (Token token : tokenMap.values()) {
            if (output.size() >= maxLine / 2) {
                Files.write(outPath, output, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                output.clear();
            }
            output.add(parseOutputLine(token));
        }

        Files.write(outPath, output, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    private static String parseOutputLine(Token token) {
        StringBuilder sb = new StringBuilder();
        String sqlToken = String.format(DataBase.SQL_INSERT_TOKEN,
                token.getTokenId(),
                token.getToken(),
                token.getCount(),
                token.getVersionCode()
        );
        sb.append("\n")
                .append(sqlToken)
                .append("\n");
        for (Word word : token.getWords()) {
            String wordSql = String.format(DataBase.SQL_INSERT_WORD,
                    word.getWordId(),
                    word.getTokenId(),
                    word.getPath(),
                    word.getLineNumber(),
                    word.getType()
            );
            sb.append(wordSql).append("\n");
        }
        return sb.toString();
    }

    public static String parseId() {
        return UUID.randomUUID().toString();
    }

    private static class Token {
        private String tokenId;
        private String token;
        private Long count;
        private List<Word> words;
        private Integer versionCode;

        public Token() {
        }

        public Token(String token) {
            this.token = token;
            words = new ArrayList<>();
            count = 0L;
            tokenId = parseId();
            versionCode = TokenAndroid.versionCode;
        }


        public void addWord(String path, Integer lineNumber, String type) {
            if (type == null) {
                type = getSuffix(path);
            }
            Word word = new Word();
            word.setTokenId(tokenId);
            word.setWordId(parseId());
            word.setPath(path);
            word.setLineNumber(lineNumber);
            word.setType(type);
            words.add(word);
            ++count;
        }


        public Integer getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(Integer versionCode) {
            this.versionCode = versionCode;
        }

        public List<Word> getWords() {
            return words;
        }

        public void setWords(List<Word> words) {
            this.words = words;
        }

        public String getTokenId() {
            return tokenId;
        }

        public void setTokenId(String tokenId) {
            this.tokenId = tokenId;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public Long getCount() {
            return count;
        }

        public void setCount(Long count) {
            this.count = count;
        }

    }

    private static String getSuffix(String path) {
        int indexOf = path.lastIndexOf(".");
        String type = "";
        if (indexOf != -1 && indexOf + 1 < path.length()) {
            type = path.substring(indexOf + 1);
        }
        return type;
    }

    private static class Word {
        private String wordId;
        private String tokenId;
        private String path;
        private Integer lineNumber;
        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getWordId() {
            return wordId;
        }

        public void setWordId(String wordId) {
            this.wordId = wordId;
        }

        public String getTokenId() {
            return tokenId;
        }

        public void setTokenId(String tokenId) {
            this.tokenId = tokenId;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public Integer getLineNumber() {
            return lineNumber;
        }

        public void setLineNumber(Integer lineNumber) {
            this.lineNumber = lineNumber;
        }
    }

    public static class DataBase {
        public static final String TABLE_TOKEN = "token";
        public static final String TABLE_WORD = "word";

        public static final String SQL_INSERT_TOKEN;
        public static final String SQL_INSERT_WORD;

        static {
            SQL_INSERT_TOKEN = Commons.parseSqlInsertFormat(TABLE_TOKEN,
                    new String[]{TokenTable.TOKEN_ID,
                            TokenTable.TOKEN,
                            TokenTable.COUNT,
                            TokenTable.VERSION_CODE,
                    });
            SQL_INSERT_WORD = Commons.parseSqlInsertFormat(TABLE_WORD,
                    new String[]{
                            WordTable.WORD_ID,
                            WordTable.TOKEN_ID,
                            WordTable.PATH,
                            WordTable.LINE_NUMBER,
                            WordTable.TYPE,
                    });

        }

        static class TokenTable {
            public static final String TOKEN_ID = "tokenId";
            public static final String TOKEN = "token";
            public static final String COUNT = "count";
            public static final String VERSION_CODE = "versionCode";
        }

        static class WordTable {
            public static final String WORD_ID = "wordId";
            public static final String TOKEN_ID = "tokenId";
            public static final String PATH = "path";
            public static final String LINE_NUMBER = "lineNumber";
            public static final String TYPE = "type";
        }
    }


}
