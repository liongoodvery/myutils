
package org.lion.utils.tools.jar2maven.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Params {

    @SerializedName("q")
    @Expose
    private String q;
    @SerializedName("indent")
    @Expose
    private String indent;
    @SerializedName("fl")
    @Expose
    private String fl;
    @SerializedName("sort")
    @Expose
    private String sort;
    @SerializedName("wt")
    @Expose
    private String wt;
    @SerializedName("version")
    @Expose
    private String version;

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getIndent() {
        return indent;
    }

    public void setIndent(String indent) {
        this.indent = indent;
    }

    public String getFl() {
        return fl;
    }

    public void setFl(String fl) {
        this.fl = fl;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getWt() {
        return wt;
    }

    public void setWt(String wt) {
        this.wt = wt;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
