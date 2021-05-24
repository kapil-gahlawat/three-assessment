package com.three.assignment.core.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Collections;
import java.util.Dictionary;

import org.junit.jupiter.api.Test;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

import osgimock.org.apache.felix.framework.util.MapToDictionary;

public class ExternalizerUtilsTest {

    private static final String EXTERNALIZER_CONFIG_ID = "com.day.cq.commons.impl.ExternalizerImpl";

    @Test
    void shouldReturnValidDomainName() throws IOException {

        String sampleDomainEntry = "publicDomain https://www.three.ie";
        String sampleDomain = "https://www.three.ie";
        ConfigurationAdmin configurationAdmin = mock(ConfigurationAdmin.class);
        Configuration configuration = mock(Configuration.class);
        @SuppressWarnings("unchecked")
        Dictionary<String, Object> properties = mock(Dictionary.class);

        when(configurationAdmin.getConfiguration(EXTERNALIZER_CONFIG_ID)).thenReturn(configuration);
        when(configuration.getProperties()).thenReturn(properties);
        when(properties.get("externalizer.domains")).thenReturn(sampleDomainEntry);

        assertEquals(sampleDomain, ExternalizerUtils.getBaseHref(configurationAdmin));
    }

    @Test
    void shouldReturnEmptyWhenNoPropertiesFound() throws IOException {
        ConfigurationAdmin configurationAdmin = mock(ConfigurationAdmin.class);
        Configuration configuration = mock(Configuration.class);
        @SuppressWarnings("unchecked")
        Dictionary<String, Object> properties = new MapToDictionary(Collections.emptyMap());

        when(configurationAdmin.getConfiguration(EXTERNALIZER_CONFIG_ID)).thenReturn(configuration);
        when(configuration.getProperties()).thenReturn(properties);

        assertEquals("", ExternalizerUtils.getBaseHref(configurationAdmin));
    }
}
