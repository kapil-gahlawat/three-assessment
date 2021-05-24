package com.three.assignment.core.utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Dictionary;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ExternalizerUtils {

    private static final Logger LOG = LoggerFactory.getLogger(ExternalizerUtils.class);
    private static final String DOMAIN_PROPERTY = "externalizer.domains";
    private static final String DOMAIN_KEY = "publicDomain";
    private static final String EXTERNALIZER_CFG_PID = "com.day.cq.commons.impl.ExternalizerImpl";

    private ExternalizerUtils() {
        // No instances
    }

    public static String getBaseHref(ConfigurationAdmin configurationAdmin) {
        try {
            Configuration configuration = configurationAdmin.getConfiguration(EXTERNALIZER_CFG_PID);
            Dictionary<String, Object> properties = configuration.getProperties();
            String domain = getDomainName(properties);
            if (StringUtils.isNotBlank(domain)) {
                return domain;
            }
        } catch (IOException ex) {
            LOG.warn("Unable to read {} configuration.", EXTERNALIZER_CFG_PID, ex);
        }
        return StringUtils.EMPTY;
    }

    private static String getDomainName(Dictionary<String, Object> properties) {
        if (properties != null) {
            String[] domains = PropertiesUtil.toStringArray(properties.get(DOMAIN_PROPERTY));
            if (domains != null) {
                return Arrays.stream(domains)
                        .map(domain -> domain.split(" "))
                        .filter(domainKeyValue -> StringUtils.equalsIgnoreCase(domainKeyValue[0], DOMAIN_KEY))
                        .map(domainKeyValue -> domainKeyValue[1])
                        .findFirst().map(Object::toString)
                        .orElse(StringUtils.EMPTY);
            }
        }
        return "";
    }
}
