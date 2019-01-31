package org.lion.utils.tools.jar2maven;

import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;
import org.lion.utils.tools.jar2maven.pojo.Doc;
import org.lion.utils.tools.jar2maven.pojo.MavenLocation;
import org.lion.utils.tools.jar2maven.pojo.MavenResponHeader;
import org.lion.utils.tools.jar2maven.pojo.Response;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class NetworkLocationSolver extends LocationSolver {


    @Override
    public boolean solve(JarInfo jarInfo) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        String path = new File(jarInfo.path).getName();
        String last = StringUtils.substringAfterLast(path, "-");
        String version = StringUtils.substringBefore(last, ".jar");
        String name = StringUtils.substringBeforeLast(path, "-");
        System.out.print(String.format("version=%s, name=%s, \n", version, name));
        Gson gson = new Gson();

        String url = "http://search.maven.org/solrsearch/select?q=a:%22" + name + "%22%20AND%20v:%22" + version + "%22&wt=json";
        Request request = new Request.Builder().url(url).build();
        try {
            String result = okHttpClient.newCall(request).execute().body().string();
            MavenResponHeader header = gson.fromJson(result, MavenResponHeader.class);
            Response response = header.getResponse();
            if (response.getNumFound() == 1) {
                Doc doc = response.getDocs().get(0);
                MavenLocation location = getMavenLocation(doc);
                jarInfo.location = location;
                return true;
            } else {
                List<MavenLocation> locations = jarInfo.penddingLocations;
                locations.clear();
                for (Doc doc : response.getDocs()) {
                    locations.add(getMavenLocation(doc));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private MavenLocation getMavenLocation(Doc doc) {
        MavenLocation location = new MavenLocation();
        location.setGroupId(doc.getG());
        location.setArtifactId(doc.getA());
        location.setVersion(doc.getV());
        return location;
    }
}