package com.three.assignment.core.handler;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.three.assignment.core.factory.model.MobileDeviceOffering;

public class MobileDeviceOfferingResponseHandler implements ResponseHandler<List<MobileDeviceOffering>> {

    private static final Logger LOG = LoggerFactory.getLogger(MobileDeviceOfferingResponseHandler.class);

    @Override
    public List<MobileDeviceOffering> handleResponse(HttpResponse httpResponse) throws IOException {
        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            try {
                String responseString = EntityUtils.toString(httpResponse.getEntity());
                Type listType = new TypeToken<ArrayList<MobileDeviceOffering>>() {}.getType();
                return new Gson().fromJson(responseString, listType);
            } catch (ParseException ex) {
                LOG.error("Could not parse data: ", ex);
            }
        }
        return Collections.emptyList();
    }
}
