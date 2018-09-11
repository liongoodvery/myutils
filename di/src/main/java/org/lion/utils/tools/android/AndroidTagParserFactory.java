package org.lion.utils.tools.android;

import org.lion.beans.AndroidTag;
import org.lion.utils.Strings;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lion on 17-9-15.
 */
public class AndroidTagParserFactory {
    enum TagType {
        Activity, Fragment, Adapter, Dialog, COMMON
    }

    public static AndroidTagParser getParse(TagType tagType, boolean userCommonParser) {
        if (userCommonParser) {
            return new CommonAndroidTagParser(tagType);
        }
        if (tagType == TagType.Fragment) {
            return new FragmentAndroidTagParser();
        }
        if (tagType == TagType.Adapter) {
            return new AdapterAndroidTagParser();
        }
        if (tagType == TagType.Dialog) {
            return new DialogAndroidTagParser();
        }

        return new ActivityAndroidTagParser();


    }


}

abstract class AbsAndroidTagParser implements AndroidTagParser {

    @Override
    public TagResult parse(List<AndroidTag> androidTags) {
        List<String> declarations = new ArrayList<>();
        List<String> assignments = new ArrayList<>();
        TagResult tagResult = new TagResult(declarations, assignments);

        for (AndroidTag androidTag : androidTags) {
            declarations.add(getDeclaration(androidTag));
            assignments.add(getAssignment(androidTag));
        }
        return tagResult;
    }

    protected abstract String getAssignment(AndroidTag androidTag);

    protected abstract String getDeclaration(AndroidTag androidTag);

    protected String getVarName(String name) {
        return Strings.underLineToCamel("m", name);
    }
}

class ActivityAndroidTagParser extends AbsAndroidTagParser {
    public static final String DECLATATION_FORMAT = "private %s %s;";
    public static final String ASSIGNMENT_FORMAT = "%s = (%s)findViewById(R.id.%s);";

    @Override
    protected String getAssignment(AndroidTag androidTag) {
        return String.format(ASSIGNMENT_FORMAT, getVarName(androidTag.getId()),
                androidTag.getName(),
                androidTag.getId());
    }

    @Override
    protected String getDeclaration(AndroidTag androidTag) {
        return String.format(DECLATATION_FORMAT, androidTag.getName(), getVarName(androidTag.getId()));
    }
}

class FragmentAndroidTagParser extends AbsAndroidTagParser {
    public static final String DECLATATION_FORMAT = "private %s %s;";
    public static final String ASSIGNMENT_FORMAT = "%s = (%s)mRootView.findViewById(R.id.%s);";

    @Override
    protected String getAssignment(AndroidTag androidTag) {
        return String.format(ASSIGNMENT_FORMAT, getVarName(androidTag.getId()),
                androidTag.getName(),
                androidTag.getId());
    }

    @Override
    protected String getDeclaration(AndroidTag androidTag) {
        return String.format(DECLATATION_FORMAT, androidTag.getName(), getVarName(androidTag.getId()));
    }
}


class DialogAndroidTagParser extends AbsAndroidTagParser {
    public static final String DECLATATION_FORMAT = "private %s %s;";
    public static final String ASSIGNMENT_FORMAT = "%s = (%s)mDialog.findViewById(R.id.%s);";

    @Override
    protected String getAssignment(AndroidTag androidTag) {
        return String.format(ASSIGNMENT_FORMAT, getVarName(androidTag.getId()),
                androidTag.getName(),
                androidTag.getId());
    }

    @Override
    protected String getDeclaration(AndroidTag androidTag) {
        return String.format(DECLATATION_FORMAT, androidTag.getName(), getVarName(androidTag.getId()));
    }
}

class AdapterAndroidTagParser extends AbsAndroidTagParser {
    public static final String DECLATATION_FORMAT = "public %s %s;";
    public static final String ASSIGNMENT_FORMAT = "%s = (%s)itemView.findViewById(R.id.%s);";

    @Override
    protected String getAssignment(AndroidTag androidTag) {
        return String.format(ASSIGNMENT_FORMAT, getVarName(androidTag.getId()),
                androidTag.getName(),
                androidTag.getId());
    }

    @Override
    protected String getDeclaration(AndroidTag androidTag) {
        return String.format(DECLATATION_FORMAT, androidTag.getName(), getVarName(androidTag.getId()));
    }
}


class CommonAndroidTagParser extends AbsAndroidTagParser {
    public static final String DECLATATION_FORMAT = "%s %s = %sfindViewById(R.id.%s);";
    public static final String ASSIGNMENT_FORMAT = "%s = (%s)itemView.findViewById(R.id.%s);";
    private final AndroidTagParserFactory.TagType tagType;
    private String prefix;

    CommonAndroidTagParser(AndroidTagParserFactory.TagType tagType) {
        this.tagType = tagType;
        prefix = "";
        if (tagType == AndroidTagParserFactory.TagType.Adapter) {
            prefix = "itemView.";
        }

        if (tagType == AndroidTagParserFactory.TagType.Dialog) {
            prefix = "mDialog.";
        }
    }

    @Override
    protected String getAssignment(AndroidTag androidTag) {
        return "";
    }

    @Override
    protected String getDeclaration(AndroidTag androidTag) {

        return String.format(DECLATATION_FORMAT,
                androidTag.getName(),
                Strings.underLineToCamel("", androidTag.getId()),
                prefix,
                androidTag.getId());
    }
}

