package com.three.assignment.core.models;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.Via;
import org.osgi.service.cm.ConfigurationAdmin;

import com.three.assignment.core.factory.model.MobileDeviceOffering;
import com.three.assignment.core.services.MobileDeviceOfferingService;
import com.three.assignment.core.utils.ExternalizerUtils;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class MobileDeviceModel {

    @Inject
    @Optional
    @Via("resource")
    @Default(booleanValues = true)
    private boolean loop;

    @Inject
    @Optional
    @Via("resource")
    @Default(booleanValues = true)
    private boolean nav;

    @Inject
    @Optional
    @Via("resource")
    @Default(booleanValues = false)
    private boolean dots;

    @Inject
    private MobileDeviceOfferingService mobileDeviceOfferingService;

    @Inject
    private ConfigurationAdmin configurationAdmin;

    private List<MobileDeviceOffering> mobileDeviceOfferingList;

    private String domainName;

    @PostConstruct
    private void init() {
        mobileDeviceOfferingList = mobileDeviceOfferingService.getMobileData();
        domainName = ExternalizerUtils.getBaseHref(configurationAdmin);
    }

    public String getDomainName() {
        return domainName;
    }

    public List<MobileDeviceOffering> getMobileDeviceOfferingList() {
        return mobileDeviceOfferingList;
    }

    public boolean isLoop() {
        return loop;
    }

    public boolean isNav() {
        return nav;
    }

    public boolean isDots() {
        return dots;
    }
}
