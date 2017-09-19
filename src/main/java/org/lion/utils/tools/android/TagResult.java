package org.lion.utils.tools.android;

import java.util.List;

/**
 * Created by lion on 17-9-15.
 */
public class TagResult {
    List<String> declarations;
    List<String> assignments;

    public TagResult() {
    }

    public TagResult(List<String> declarations, List<String> assignments) {
        this.declarations = declarations;
        this.assignments = assignments;
    }

    public List<String> getDeclarations() {
        return declarations;
    }

    public void setDeclarations(List<String> declarations) {
        this.declarations = declarations;
    }

    public List<String> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<String> assignments) {
        this.assignments = assignments;
    }

}
