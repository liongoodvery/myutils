package org.lion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
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
        HashMap<String, String> hashMap = new HashMap<>();
//        hashMap.put("import edu.yjyx.student.utils.DebugUtil;", "import edu.yjyx.base.DebugUtil;");
        hashMap.put("import edu.yjyx.student.module.main.PaymentManager;", "import edu.yjyx.payment.PaymentManager;");
        try {
            List<String> list = Files.readAllLines(path).stream()
                    .map(s -> {
                        String value = hashMap.get(s);
                        return value == null ? s : value;
                    }).collect(Collectors.toList());
            Files.write(path, list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
