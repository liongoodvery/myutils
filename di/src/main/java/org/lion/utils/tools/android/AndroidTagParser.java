package org.lion.utils.tools.android;

import org.lion.beans.AndroidTag;

import java.util.List;

/**
 * Created by lion on 17-9-15.
 */
public interface AndroidTagParser {
    TagResult parse(List<AndroidTag> androidTags);
}
