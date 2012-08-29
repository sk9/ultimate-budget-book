package de.g18.ubb.android.client.validation;

import de.g18.ubb.android.client.resource.CommonValidationResource;
import de.g18.ubb.android.client.resource.Resource;
import de.g18.ubb.common.util.StringUtil;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class ValidationUtil {

    private ValidationUtil() {
        // prevent instantiation...
    }

    public static String createEmptyMessage() {
        return StringUtil.EMPTY;
    }

    public static String createMustNotBeEmptyMessage(Resource aResource) {
        return CommonValidationResource.MUST_NOT_BE_EMPTY.formatted(aResource.formatted());
    }
}
