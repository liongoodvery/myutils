package org.lion.utils.tools.android;

import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.lion.beans.AndroidTag;
import org.lion.utils.Commons;
import org.lion.utils.Strings;
import org.lion.utils.tools.android.AndroidTagParserFactory.TagType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by more on 2016-05-08 12:17:13.
 * The class generate code according the ids in the android layout xml file.
 */
public class AndroidLayoutPhaseID {
    List<AndroidTag> androidTags;
    List<String> fileNames;
    String folder = "/home/share/yjyx/student/student/src/main/res/layout/";

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("filename required");
            return;
        }
        new AndroidLayoutPhaseID().start(args[0]);
    }

    public AndroidLayoutPhaseID() {
        androidTags = new ArrayList<>();
        fileNames = new ArrayList<>();

    }

    public void start(String fileName) {
        boolean isJava = false;
        TagType type = TagType.Activity;
        if (fileName.endsWith("java")) {
            String name = Commons.fileNameWithoutExt(fileName);
            fileName = folder + xmlFileName(fileName);
            type = getJavaTagType(name);
            isJava = true;
        } else if (fileName.endsWith("xml")) {
            String name = Commons.fileNameWithoutExt(fileName);
            Path path = Paths.get(fileName);
            folder = path.getParent().toString();
            type = getXmlTagType(name);
        } else {
            return;
        }

        SAXReader reader = new SAXReader();
        fileNames.add(fileName);
        try {
            androidTags.clear();
            while (fileNames.size() > 0) {
                String file = fileNames.remove(0);
                handleFile(reader, file);
            }
            AndroidTagParser parse = AndroidTagParserFactory.getParse(type);
            TagResult tagResult = parse.parse(androidTags);
            System.out.println();
            System.out.println("//view declarations starts");
            List<String> declarations = tagResult.getDeclarations();
            for (String declaration : declarations) {
                System.out.println(declaration);
            }
            System.out.println("//view declarations ends\n");

            System.out.println("//view assignments starts");
            List<String> assignments = tagResult.getAssignments();
            for (String assignment : assignments) {
                System.out.println(assignment);
            }
            System.out.println("//view assignments ends\n");

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private void handleFile(SAXReader reader, String file) throws DocumentException {
        Document document = reader.read(file);
        Element root = document.getRootElement();
        treeWalk(document);
    }

    private void treeWalk(Document document) {
        treeWalk(document.getRootElement());
    }

    private void treeWalk(Element element) {
        for (int i = 0, size = element.nodeCount(); i < size; i++) {
            Node node = element.node(i);
            if (node instanceof Element) {
                Element e = (Element) node;
                Attribute id = e.attribute("id");
                if (id != null) {
                    AndroidTag androidTag = new AndroidTag(
                            Commons.splitAndroidId(id.getValue()),
                            Commons.simpleClassName(e.getName()));
                    androidTags.add(androidTag);
                }

                if ("include".equals(e.getName())) {
                    Attribute layout = e.attribute("layout");
                    if (layout != null) {
                        fileNames.add(Paths.get(folder, Commons.splitAndroidId(layout.getValue()) + ".xml").toString());
                    }
                }
                treeWalk(e);
            }
        }
    }

    private String xmlFileName(String fileName) {
        Pattern pattern = Pattern.compile("\\s*return\\s*R\\.layout\\.(.*);");
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            for (String line : lines) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    return matcher.group(1) + ".xml";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    TagType getJavaTagType(String name) {
        if (Strings.isEmpty(name)) {
            return TagType.Activity;
        }

        if (name.toLowerCase().endsWith("fragment")) {
            return TagType.Fragment;
        }
        if (name.toLowerCase().endsWith("adapter")) {
            return TagType.Adapter;
        }
        return TagType.Activity;
    }

    TagType getXmlTagType(String name) {
        if (Strings.isEmpty(name)) {
            return TagType.Activity;
        }

        if (name.toLowerCase().startsWith("fragment")) {
            return TagType.Fragment;
        }
        if (name.toLowerCase().startsWith("item")) {
            return TagType.Adapter;
        }
        return TagType.Activity;
    }
}
