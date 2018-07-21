
package org.lion.utils.tools.jar2maven.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Doc {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("g")
    @Expose
    private String g;
    @SerializedName("a")
    @Expose
    private String a;
    @SerializedName("v")
    @Expose
    private String v;
    @SerializedName("p")
    @Expose
    private String p;


    @SerializedName("ec")
    @Expose
    private List<String> ec = null;
    @SerializedName("tags")
    @Expose
    private List<String> tags = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getG() {
        return g;
    }

    public void setG(String g) {
        this.g = g;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public List<String> getEc() {
        return ec;
    }

    public void setEc(List<String> ec) {
        this.ec = ec;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

}
