
package com.tvelykyy.afeeder.webservice.soap.client.wsimported;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.tvelykyy.afeeder.webservice.soap.client.wsimported package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ListAllActivities_QNAME = new QName("http://soap.webservice.afeeder.tvelykyy.com/", "listAllActivities");
    private final static QName _ListAllGroupsResponse_QNAME = new QName("http://soap.webservice.afeeder.tvelykyy.com/", "listAllGroupsResponse");
    private final static QName _AddActivityResponse_QNAME = new QName("http://soap.webservice.afeeder.tvelykyy.com/", "addActivityResponse");
    private final static QName _FindActivities_QNAME = new QName("http://soap.webservice.afeeder.tvelykyy.com/", "findActivities");
    private final static QName _FindActivitiesResponse_QNAME = new QName("http://soap.webservice.afeeder.tvelykyy.com/", "findActivitiesResponse");
    private final static QName _ListAllGroups_QNAME = new QName("http://soap.webservice.afeeder.tvelykyy.com/", "listAllGroups");
    private final static QName _AddActivity_QNAME = new QName("http://soap.webservice.afeeder.tvelykyy.com/", "addActivity");
    private final static QName _ListAllActivitiesResponse_QNAME = new QName("http://soap.webservice.afeeder.tvelykyy.com/", "listAllActivitiesResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.tvelykyy.afeeder.webservice.soap.client.wsimported
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ListAllGroupsResponse }
     * 
     */
    public ListAllGroupsResponse createListAllGroupsResponse() {
        return new ListAllGroupsResponse();
    }

    /**
     * Create an instance of {@link ListAllActivities }
     * 
     */
    public ListAllActivities createListAllActivities() {
        return new ListAllActivities();
    }

    /**
     * Create an instance of {@link FindActivities }
     * 
     */
    public FindActivities createFindActivities() {
        return new FindActivities();
    }

    /**
     * Create an instance of {@link AddActivityResponse }
     * 
     */
    public AddActivityResponse createAddActivityResponse() {
        return new AddActivityResponse();
    }

    /**
     * Create an instance of {@link ListAllGroups }
     * 
     */
    public ListAllGroups createListAllGroups() {
        return new ListAllGroups();
    }

    /**
     * Create an instance of {@link ListAllActivitiesResponse }
     * 
     */
    public ListAllActivitiesResponse createListAllActivitiesResponse() {
        return new ListAllActivitiesResponse();
    }

    /**
     * Create an instance of {@link AddActivity }
     * 
     */
    public AddActivity createAddActivity() {
        return new AddActivity();
    }

    /**
     * Create an instance of {@link FindActivitiesResponse }
     * 
     */
    public FindActivitiesResponse createFindActivitiesResponse() {
        return new FindActivitiesResponse();
    }

    /**
     * Create an instance of {@link Timestamp }
     * 
     */
    public Timestamp createTimestamp() {
        return new Timestamp();
    }

    /**
     * Create an instance of {@link Role }
     * 
     */
    public Role createRole() {
        return new Role();
    }

    /**
     * Create an instance of {@link Group }
     * 
     */
    public Group createGroup() {
        return new Group();
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link Activity }
     * 
     */
    public Activity createActivity() {
        return new Activity();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListAllActivities }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.webservice.afeeder.tvelykyy.com/", name = "listAllActivities")
    public JAXBElement<ListAllActivities> createListAllActivities(ListAllActivities value) {
        return new JAXBElement<ListAllActivities>(_ListAllActivities_QNAME, ListAllActivities.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListAllGroupsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.webservice.afeeder.tvelykyy.com/", name = "listAllGroupsResponse")
    public JAXBElement<ListAllGroupsResponse> createListAllGroupsResponse(ListAllGroupsResponse value) {
        return new JAXBElement<ListAllGroupsResponse>(_ListAllGroupsResponse_QNAME, ListAllGroupsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddActivityResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.webservice.afeeder.tvelykyy.com/", name = "addActivityResponse")
    public JAXBElement<AddActivityResponse> createAddActivityResponse(AddActivityResponse value) {
        return new JAXBElement<AddActivityResponse>(_AddActivityResponse_QNAME, AddActivityResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindActivities }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.webservice.afeeder.tvelykyy.com/", name = "findActivities")
    public JAXBElement<FindActivities> createFindActivities(FindActivities value) {
        return new JAXBElement<FindActivities>(_FindActivities_QNAME, FindActivities.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindActivitiesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.webservice.afeeder.tvelykyy.com/", name = "findActivitiesResponse")
    public JAXBElement<FindActivitiesResponse> createFindActivitiesResponse(FindActivitiesResponse value) {
        return new JAXBElement<FindActivitiesResponse>(_FindActivitiesResponse_QNAME, FindActivitiesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListAllGroups }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.webservice.afeeder.tvelykyy.com/", name = "listAllGroups")
    public JAXBElement<ListAllGroups> createListAllGroups(ListAllGroups value) {
        return new JAXBElement<ListAllGroups>(_ListAllGroups_QNAME, ListAllGroups.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddActivity }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.webservice.afeeder.tvelykyy.com/", name = "addActivity")
    public JAXBElement<AddActivity> createAddActivity(AddActivity value) {
        return new JAXBElement<AddActivity>(_AddActivity_QNAME, AddActivity.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListAllActivitiesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.webservice.afeeder.tvelykyy.com/", name = "listAllActivitiesResponse")
    public JAXBElement<ListAllActivitiesResponse> createListAllActivitiesResponse(ListAllActivitiesResponse value) {
        return new JAXBElement<ListAllActivitiesResponse>(_ListAllActivitiesResponse_QNAME, ListAllActivitiesResponse.class, null, value);
    }

}
