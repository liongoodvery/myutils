package org.lion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class RxTransform {
    public static void main(String[] args) {
        try {
            walk();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void walk() throws IOException {
        Files.walk(Paths.get("/home/lion/yjyx/student/student/src/main"))
                .filter(path -> path.toString().endsWith(".java"))
                .forEach(RxTransform::handle);
    }

    private static void handle(Path path) {
        try {
            List<String> list = Files.readAllLines(path).stream()
                    .map(s -> {
//                        if ("import rx.android.schedulers.AndroidSchedulers;".equals(s)) {
//                            return "import io.reactivex.android.schedulers.AndroidSchedulers;";
//                        }
//
//                        if ("import rx.schedulers.Schedulers;".equals(s)) {
//                            return "import io.reactivex.schedulers.Schedulers;";
//                        }
//
//                        if ("import rx.Observable;".equals(s)) {
//                            return "import io.reactivex.Observable;";
//                        }
//                        if ("import rx.Subscriber;".equals(s)) {
//                            return "import edu.yjyx.student.utils.Subscriber;";
//                        }
//                        if ("import edu.yjyx.library.model.StatusCode;".equals(s)) {
//                            return "import edu.yjyx.main.model.StatusCode;";
//                        }
//                        if ("import rx.Observer;".equals(s)) {
//                            return "import io.reactivex.Observer;";
//                        }
                        if (s.startsWith("public class") && s.contains("BaseActivityV2")) {
                            return s.replace("BaseActivityV2", "BaseActivity");
                        }
                        return s;
                    }).collect(Collectors.toList());
            Files.write(path, list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
