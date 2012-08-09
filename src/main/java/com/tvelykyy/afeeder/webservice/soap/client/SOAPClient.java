package com.tvelykyy.afeeder.webservice.soap.client;

import java.util.Collections;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.soap.SOAPFaultException;

import com.tvelykyy.afeeder.webservice.soap.client.wsimported.Activity;
import com.tvelykyy.afeeder.webservice.soap.client.wsimported.ActivityWebService;
import com.tvelykyy.afeeder.webservice.soap.client.wsimported.ActivityWebServiceEndpointService;
import com.tvelykyy.afeeder.webservice.soap.client.wsimported.Group;

public class SOAPClient {
	private static final String CHOOSE_MESSAGE = "Please choose operation:\n"
			+ "Press 1 - to list all activies;\n"
			+ "Press 2 - to search activies;\n"
			+ "Press 3 - to add activity;\n"
			+ "Press 0 - to quit.";
	private static final String WELCOME_MESSAGE = "Welcome to afeeder SOAP client.";
	private static final String INVALID_OPERATION_MESSAGE = "Invalid operation.";
	private static final String BYE_MESSAGE = "Bye.";
	private static final String INPUT_PATTERN_MESSAGE = "Input search pattern:";
	private static final String EMPTY_LIST_MESSAGE = "Empty list";
	private static final String INPUT_ACTIVITY_TEXT_MESSAGE = "Input Activity text:";
	private static final String CHOOSE_GROUP_MESSAGE = "Choose group id:";
	private static final String ACTIVITY_ADDED_MESSAGE = "Activity has been posted";
	
	private static Scanner scanner = new Scanner(System.in);
	private static ActivityWebService servicePort = new ActivityWebServiceEndpointService()
														.getActivityWebServicePort();
	public static void main(String[] args) {
		String token = args[0];
		Map<String, Object> req_ctx = ((BindingProvider)servicePort).getRequestContext();
		
		Map<String, List<String>> headers = new HashMap<String, List<String>>();
        headers.put("AuthToken", Collections.singletonList(token));
        req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
		
		showDialog(WELCOME_MESSAGE);
	}

	private static void showDialog(String message) {
		
		if (message != null) {
			System.out.println(message);
		}
		System.out.println(CHOOSE_MESSAGE);
		
		int operation = 0;
		try {
			operation = scanner.nextInt();
			//Return button handling
			scanner.nextLine();
		} catch (InputMismatchException e) {
			scanner = new Scanner(System.in);
			//This will be handled by default in switch statement
			operation = -1;
		}
		try {
			switch (operation) {
				case 0:
					System.out.println(BYE_MESSAGE);
					System.exit(0);
				case 1:
					listAllActivities();
					showDialog(null);
					break;
				case 2:
					findActivities();
					showDialog(null);
					break;
				case 3:
					addActivity();
					showDialog(null);
					break;
				default:
					showDialog(INVALID_OPERATION_MESSAGE);
					break;
			}
		} catch (SOAPFaultException e) {
			//Invalid Token exception - shutting down
			System.out.println(e.getMessage());
			System.out.println(BYE_MESSAGE);
			System.exit(0);
		}
	}

	private static void addActivity() {
		System.out.println(INPUT_ACTIVITY_TEXT_MESSAGE);
		String actText = scanner.nextLine();
		System.out.println(CHOOSE_GROUP_MESSAGE);
		List<Group> groups = servicePort.listAllGroups();
		System.out.println(groupsListToString(groups));
		long groupId = 0;
		
		try {
			groupId = scanner.nextLong(); 
		} catch (Exception e) {
			//Setting default group
			groupId = 1;
		}
		
		Group group = new Group();
		group.setId(groupId);
		
		Activity activity = new Activity();
		activity.setText(actText);
		activity.setGroup(group);
		
		try {
			servicePort.addActivity(activity);
			System.out.println(ACTIVITY_ADDED_MESSAGE);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private static void findActivities() {
		System.out.println(INPUT_PATTERN_MESSAGE);
		String pattern = scanner.nextLine();
		List<Activity> searchResult = servicePort.findActivities(pattern);
		System.out.println(activitiesListToString(searchResult));
	}

	private static void listAllActivities() {
		List<Activity> result = servicePort.listAllActivities();
		System.out.println(activitiesListToString(result));
	}
	
	private static String activityToString(Activity activity) {
		return String.format("%s %s: %s --- %s", activity.getId(), activity.getUser().getName(),
				activity.getText(), activity.getGroup().getName());
	}
	
	private static String activitiesListToString(List<Activity> activities) {
		StringBuffer result = new StringBuffer();
		
		if (activities.size() == 0) {
			result.append(EMPTY_LIST_MESSAGE);
		} else {
			for (Activity activity : activities) {
				result.append(activityToString(activity)).append("\n");
			}
		}
		
		return result.toString();
	}
	
	private static String groupToString(Group group) {
		return String.format("%s - %s", group.getId(), group.getName());
	}
	
	private static String groupsListToString(List<Group> groups) {
		StringBuffer result = new StringBuffer();
		
		if (groups.size() == 0) {
			result.append(EMPTY_LIST_MESSAGE);
		} else {
			for (Group group : groups) {
				result.append(groupToString(group)).append("\n");
			}
		}
		
		return result.toString();
	}
}
