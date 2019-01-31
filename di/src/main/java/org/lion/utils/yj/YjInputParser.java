package org.lion.utils.yj;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import org.jsoup.Jsoup;
import org.lion.beans.TypeName;
import org.lion.utils.Strings;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.lang.model.element.Modifier;

import edu.yjyx.student.module.main.api.input.BaseInput;
import io.reactivex.Observable;
import io.reactivex.observables.ConnectableObservable;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class YjInputParser {
    String docId = "";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_HEAD = "HEAD";
    public static final String METHOD_DELETE = "DELETE";
    public static final String[] METHODS = new String[]{
            METHOD_PUT,
            METHOD_GET,
            METHOD_POST,
            METHOD_HEAD,
            METHOD_DELETE,
    };

    Pattern pattern = Pattern.compile(".*/api/([\\w|/]*)?( |`|\\?).*");


    public static void main(String[] args) {
        String docId = "";
        if (args.length == 0) {
            System.out.println("docId:");
            Scanner scanner = new Scanner(System.in);
            docId = scanner.nextLine();
        } else {
            docId = args[0];
        }

        new YjInputParser(docId).run();

    }

    public YjInputParser(String docId) {
        this.docId = docId;
    }

    public void run() {

        ConnectableObservable<String> source = Observable.fromCallable(this::getMd)
                .map(s -> s.split("\\n"))
                .flatMap(Observable::fromArray)
                .replay();
        source.connect();

        String action = parseInputFile(source);

        parseRestful(source, action);

    }

    private void parseRestful(ConnectableObservable<String> source, String action) {
        String url = source.skipWhile(s -> !(s.trim().equals("**请求URL：**")))
                .takeUntil(s -> (s.trim().equals("**请求方式：**")))
                .skip(1)
                .take(1)
                .map(this::restUrlMapper)
                .blockingFirst();

        String method = source.skipWhile(s -> !(s.trim().equals("**请求方式：**")))
                .takeUntil(s -> (s.trim().equals("**参数：**")))
                .skip(1)
                .take(1)
                .map(this::restMethodMapper)
                .blockingFirst();

        String methodName = Strings.underLineToCamel("", action);
        String outClass = "BaseResponse";
        System.out.println("====================RestMethod====================");
        if (method.equals(METHOD_GET) || method.equals(METHOD_HEAD)) {
            System.out.println(String.format("@%s(\"%s\")", method, url));
            System.out.println(String.format("NetworkObservable<%s> %s(@QueryMap Map<String, String> params);",
                    outClass, methodName));
        }

        if (method.equals(METHOD_POST) || method.equals(METHOD_PUT)) {
            System.out.println("@FormUrlEncoded");
            System.out.println(String.format("@%s(\"%s\")", method, url));
            System.out.println(String.format("NetworkObservable<%s> %s(@FieldMap Map<String, String> params);",
                    outClass, methodName));
        }
    }

    private String restUrlMapper(String s) {
        Matcher matcher = pattern.matcher(s);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }

        return "";
    }

    private String restMethodMapper(String s) {

        String m = s.toUpperCase();
        for (String method : METHODS) {
            if (m.contains(method)) {
                return method;
            }
        }
        return "";
    }

    private String parseInputFile(ConnectableObservable<String> source) {
        Observable<TypeName> replay = source
                .skipWhile(s -> !(s.trim().equals("**参数：**")))
                .takeUntil(s -> (s.trim().equals("**正常返回参数说明**")))
                .skip(4)
                .takeUntil(s -> !!s.equals(""))
                .skipLast(1)
//                .doOnNext(System.out::println)
                .map(s -> {
                    String[] splits = s.trim().split("\\|");
                    return new TypeName(splits[1].trim(), splits[2].trim(), splits[3].trim(), splits[4].trim());
                })
                .skipWhile(typeName -> !"action".equals(typeName.getName()))
                .map(typeName -> {
                    if (typeName.getType().equals("是") || typeName.getType().equals("否")) {
                        return new TypeName(typeName.getName(), typeName.getRequired(), typeName.getType(), typeName.getDefaultValue());
                    }
                    return typeName;
                });


//        source.connect();

        String action = replay.filter(this::isAction)
                .map(TypeName::getDefaultValue)
                .blockingFirst();

        TypeSpec.Builder builder = replay
//                .doOnNext(System.out::println)
                .map(this::getField)
                .reduce(getTypeBuilder(action), TypeSpec.Builder::addField)
                .blockingGet();
        String s1 = replay
                .map(TypeName::getName)
                .reduce(new StringBuilder(" return toMap(new String[]{"),
                        (sb, typeName) -> sb.append('"').append(typeName).append('"').append(",").append('\n'))
                .map(stringBuilder -> stringBuilder.toString())
                .toObservable()
                .mergeWith(Observable.just("   }, new Object[]{"))
                .reduce("", (s, s2) -> s + s2)
                .toObservable()
                .mergeWith(replay.map(TypeName::getName))
                .reduce((s, s2) -> s + s2 + "," + '\n')
                .toObservable()
                .mergeWith(Observable.just("   })"))
                .reduce(String::concat)
                .blockingGet();
        MethodSpec methodSpec = MethodSpec.methodBuilder("toMap")
                .addStatement(s1)
                .returns(Map.class)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .build();

        TypeSpec typeSpec = builder.addMethod(methodSpec).superclass(BaseInput.class).build();

        JavaFile javaFile = JavaFile.builder("", typeSpec)
                .build();

        try {
            javaFile.writeTo(System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return action;
    }

    private TypeSpec.Builder getTypeBuilder(String action) {
        String name = "".equals(action) ? "TestInput" : action + "Input";
        name = Strings.underLineToCamel("", name);
        name = Strings.capWord(name);
        return TypeSpec.classBuilder(name)
                .addModifiers(Modifier.PUBLIC);
    }


    private FieldSpec getField(TypeName typeName) {
        FieldSpec.Builder builder = FieldSpec.builder(detectClass(typeName.getType()), typeName.getName())
                .addModifiers(Modifier.PUBLIC);
        if (isAction(typeName)) {
            builder
                    .addModifiers(Modifier.FINAL)
                    .initializer("$S", typeName.getDefaultValue());
        }
        return builder
                .build();
    }

    private boolean isAction(TypeName typeName) {
        return "action".equals(typeName.getName());
    }

    private Class<?> detectClass(String className) {
        String c = className.toLowerCase();
        switch (c) {
            case "int":
                return Integer.class;
            case "long":
                return Long.class;
            case "string":
                return String.class;
            case "float":
            case "double":
                return Double.class;
            case "bool":
            case "boolean":
                return Boolean.class;
        }
        return String.class;
    }

    private String getMd() throws IOException {
//        System.out.println("YjInputParser.getMd");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://192.168.1.113/index.php?s=/home/page/index/page_id/" + docId)
                .build();
        String html = client.newCall(request)
                .execute()
                .body()
                .string();
        return Jsoup.parse(html)
                .select("#page_md_content")
                .text();

    }
}
