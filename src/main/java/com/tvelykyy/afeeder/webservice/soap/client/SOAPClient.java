package com.tvelykyy.afeeder.webservice.soap.client;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.tvelykyy.afeeder.webservice.soap.client.wsimported.Activity;
import com.tvelykyy.afeeder.webservice.soap.client.wsimported.ActivityWebService;
import com.tvelykyy.afeeder.webservice.soap.client.wsimported.ActivityWebServiceEndpointService;
import com.tvelykyy.afeeder.webservice.soap.client.wsimported.Group;

public class SOAPClient {
	private static final String chooseMessage = "Please choose operation:\n"
			+ "Press 1 - to list all activies;\n"
			+ "Press 2 - to search activies;\n"
			+ "Press 3 - to add activity;\n"
			+ "Press 0 - to quit.";
	private static final String welcomeMessage = "Welcome to afeeder SOAP client.";
	private static final String invalidOperationMessage = "Invalid operation.";
	private static final String byeMessage = "Bye.";
	private static final String inputPatternMessage = "Input search pattern:";
	private static final String emptyListMessage = "Empty list";
	private static final String enterActivityTextMessage = "Enter Activity text:";
	private static final String chooseGroupMessage = "Choose group id:";
	private static final String activityAddedMessage = "Activity has been posted";
	private static Scanner scanner = new Scanner(System.in);
	private static ActivityWebService servicePort = new ActivityWebServiceEndpointService()
														.getActivityWebServicePort();
	public static void main(String[] args) {		
		showDialog(welcomeMessage);
	}

	private static void showDialog(String message) {
		
		if (message != null) {
			System.out.println(message);
		}
		System.out.println(chooseMessage);
		
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
		switch (operation) {
			case 0:
				System.out.println(byeMessage);
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
				showDialog(invalidOperationMessage);
				break;
		}
	}

	private static void addActivity() {
		System.out.println(enterActivityTextMessage);
		String actText = scanner.nextLine();
		System.out.println(chooseGroupMessage);
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
			System.out.println(activityAddedMessage);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private static void findActivities() {
		System.out.println(inputPatternMessage);
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
			result.append(emptyListMessage);
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
			result.append(emptyListMessage);
		} else {
			for (Group group : groups) {
				result.append(groupToString(group)).append("\n");
			}
		}
		
		return result.toString();
	}
}
