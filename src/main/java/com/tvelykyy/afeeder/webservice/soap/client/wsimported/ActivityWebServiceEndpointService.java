
package com.tvelykyy.afeeder.webservice.soap.client.wsimported;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "ActivityWebServiceEndpointService", targetNamespace = "http://soap.webservice.afeeder.tvelykyy.com/", wsdlLocation = "http://localhost:8080/afeeder/ws?wsdl")
public class ActivityWebServiceEndpointService
    extends Service
{

    private final static URL ACTIVITYWEBSERVICEENDPOINTSERVICE_WSDL_LOCATION;
    private final static WebServiceException ACTIVITYWEBSERVICEENDPOINTSERVICE_EXCEPTION;
    private final static QName ACTIVITYWEBSERVICEENDPOINTSERVICE_QNAME = new QName("http://soap.webservice.afeeder.tvelykyy.com/", "ActivityWebServiceEndpointService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8080/afeeder/ws?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        ACTIVITYWEBSERVICEENDPOINTSERVICE_WSDL_LOCATION = url;
        ACTIVITYWEBSERVICEENDPOINTSERVICE_EXCEPTION = e;
    }

    public ActivityWebServiceEndpointService() {
        super(__getWsdlLocation(), ACTIVITYWEBSERVICEENDPOINTSERVICE_QNAME);
    }

    public ActivityWebServiceEndpointService(WebServiceFeature... features) {
        super(__getWsdlLocation(), ACTIVITYWEBSERVICEENDPOINTSERVICE_QNAME, features);
    }

    public ActivityWebServiceEndpointService(URL wsdlLocation) {
        super(wsdlLocation, ACTIVITYWEBSERVICEENDPOINTSERVICE_QNAME);
    }

    public ActivityWebServiceEndpointService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, ACTIVITYWEBSERVICEENDPOINTSERVICE_QNAME, features);
    }

    public ActivityWebServiceEndpointService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ActivityWebServiceEndpointService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns ActivityWebService
     */
    @WebEndpoint(name = "ActivityWebServicePort")
    public ActivityWebService getActivityWebServicePort() {
        return super.getPort(new QName("http://soap.webservice.afeeder.tvelykyy.com/", "ActivityWebServicePort"), ActivityWebService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ActivityWebService
     */
    @WebEndpoint(name = "ActivityWebServicePort")
    public ActivityWebService getActivityWebServicePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://soap.webservice.afeeder.tvelykyy.com/", "ActivityWebServicePort"), ActivityWebService.class, features);
    }

    private static URL __getWsdlLocation() {
        if (ACTIVITYWEBSERVICEENDPOINTSERVICE_EXCEPTION!= null) {
            throw ACTIVITYWEBSERVICEENDPOINTSERVICE_EXCEPTION;
        }
        return ACTIVITYWEBSERVICEENDPOINTSERVICE_WSDL_LOCATION;
    }

}
