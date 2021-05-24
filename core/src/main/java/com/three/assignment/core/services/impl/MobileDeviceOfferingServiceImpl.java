package com.three.assignment.core.services.impl;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.three.assignment.core.factory.model.MobileDeviceOffering;
import com.three.assignment.core.handler.MobileDeviceOfferingResponseHandler;
import com.three.assignment.core.services.MobileDeviceOfferingService;

/**
 * This Service is used to get Mobile Offering data from the external API (e.g: three.ie)
 */
@Component(service = MobileDeviceOfferingService.class, immediate = true)
@Designate(ocd = MobileDeviceOfferingServiceImpl.Configuration.class)
public class MobileDeviceOfferingServiceImpl implements MobileDeviceOfferingService {

    @ObjectClassDefinition(name = "Mobile Device Offering Service Configuration")
    @interface Configuration {

        @AttributeDefinition(
                name = "Mobile Device Offering Service URL",
                description = "Enter URL to get Mobile Device Offering data",
                type = AttributeType.STRING)
        String mobileDeviceApiUrl() default "https://www.three.ie/rp-server/commerce/v1/mobileDeviceOffering?salesChannel=selfService&pricePreference=minPriceWithCommitment&sort=-price&groupByVariantGroup=true&filters=category.id==8449299_8368419&levelOfData=offering&qualificationCriteria=orderingActivity==newSubscription";
    }

    private static final Logger LOG = LoggerFactory.getLogger(MobileDeviceOfferingServiceImpl.class);

    private String mobileDeviceApiUrl;

    @Activate
    protected void activate(final Configuration configuration) {
        mobileDeviceApiUrl = configuration.mobileDeviceApiUrl();
    }

    @Override
    public List<MobileDeviceOffering> getMobileData() {
        HttpUriRequest httpRequest = new HttpGet(mobileDeviceApiUrl);
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            return httpclient.execute(httpRequest, new MobileDeviceOfferingResponseHandler());
        } catch (IOException ex) {
            LOG.error("Connection pool timeout exceeded: {}", ex.getMessage());
            LOG.debug("Exception: ", ex);
        }
        return null;
    }
}
