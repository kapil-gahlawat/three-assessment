package com.three.assignment.core.factory.model;

import org.apache.commons.lang3.StringUtils;

public class MobileDeviceOffering {

    private static final String IMAGE_PURPOSE = "list";

    private VariantGroupInformation calculateVariantGroupInformation;

    public String getDeviceName() {
        return calculateVariantGroupInformation.getName();
    }

    public String getDeviceImage() {
        for (Attachment attachment : calculateVariantGroupInformation.getAttachment()) {
            if (StringUtils.equalsIgnoreCase(attachment.getPurpose(), IMAGE_PURPOSE)) {
                return attachment.getUrl();
            }
        }
        return null;
    }
}
