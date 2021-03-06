
package com.tvelykyy.afeeder.webservice.soap.client.wsimported;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "ActivityWebService", targetNamespace = "http://soap.webservice.afeeder.tvelykyy.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ActivityWebService {


    /**
     * 
     * @param arg0
     * @return
     *     returns java.util.List<com.tvelykyy.afeeder.webservice.soap.client.wsimported.Activity>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "findActivities", targetNamespace = "http://soap.webservice.afeeder.tvelykyy.com/", className = "com.tvelykyy.afeeder.webservice.soap.client.wsimported.FindActivities")
    @ResponseWrapper(localName = "findActivitiesResponse", targetNamespace = "http://soap.webservice.afeeder.tvelykyy.com/", className = "com.tvelykyy.afeeder.webservice.soap.client.wsimported.FindActivitiesResponse")
    public List<Activity> findActivities(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @return
     *     returns java.util.List<com.tvelykyy.afeeder.webservice.soap.client.wsimported.Group>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "listAllGroups", targetNamespace = "http://soap.webservice.afeeder.tvelykyy.com/", className = "com.tvelykyy.afeeder.webservice.soap.client.wsimported.ListAllGroups")
    @ResponseWrapper(localName = "listAllGroupsResponse", targetNamespace = "http://soap.webservice.afeeder.tvelykyy.com/", className = "com.tvelykyy.afeeder.webservice.soap.client.wsimported.ListAllGroupsResponse")
    public List<Group> listAllGroups();

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.Long
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "addActivity", targetNamespace = "http://soap.webservice.afeeder.tvelykyy.com/", className = "com.tvelykyy.afeeder.webservice.soap.client.wsimported.AddActivity")
    @ResponseWrapper(localName = "addActivityResponse", targetNamespace = "http://soap.webservice.afeeder.tvelykyy.com/", className = "com.tvelykyy.afeeder.webservice.soap.client.wsimported.AddActivityResponse")
    public Long addActivity(
        @WebParam(name = "arg0", targetNamespace = "")
        Activity arg0);

    /**
     * 
     * @return
     *     returns java.util.List<com.tvelykyy.afeeder.webservice.soap.client.wsimported.Activity>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "listAllActivities", targetNamespace = "http://soap.webservice.afeeder.tvelykyy.com/", className = "com.tvelykyy.afeeder.webservice.soap.client.wsimported.ListAllActivities")
    @ResponseWrapper(localName = "listAllActivitiesResponse", targetNamespace = "http://soap.webservice.afeeder.tvelykyy.com/", className = "com.tvelykyy.afeeder.webservice.soap.client.wsimported.ListAllActivitiesResponse")
    public List<Activity> listAllActivities();

}
