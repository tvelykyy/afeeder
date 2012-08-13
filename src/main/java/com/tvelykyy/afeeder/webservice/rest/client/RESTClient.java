package com.tvelykyy.afeeder.webservice.rest.client;

import java.util.Collections;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.tvelykyy.afeeder.webservice.AbstractClient;
import com.tvelykyy.afeeder.webservice.soap.client.wsimported.Activity;
import com.tvelykyy.afeeder.webservice.soap.client.wsimported.Group;

public class RESTClient extends AbstractClient {
	private static HttpEntity<?> requestEntity = null;
	private static RestTemplate template = new RestTemplate();
	private static final String BASE_URL = "http://localhost:8080/afeeder/rest";
	private static final String LIST_ALL_ACTIVITIES = "/activities";
	private static final String LIST_ALL_GROUPS = "/groups";
	private static final String FIND_ACTIVITIES = "/activities/search/{pattern}";
	private static final String ADD_ACTIVITY = "/add/activity";

	public static void main(String[] args) {
		// One argument should be passed
		if (args.length != 1) {
			System.err.println(INVALID_INVOCATION_MESSAGE);
		} else {
			String token = args[0];

			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.set("AuthToken", token);
			requestEntity = new HttpEntity<String>(requestHeaders);

			showDialog(WELCOME_MESSAGE);
		}
	}

	protected static void showDialog(String message) {

		if (message != null) {
			System.out.println(message);
		}
		System.out.println(CHOOSE_MESSAGE);

		int operation = 0;
		try {
			operation = scanner.nextInt();
			// Return button handling
			scanner.nextLine();
		} catch (InputMismatchException e) {
			scanner = new Scanner(System.in);
			// This will be handled by default in switch statement
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
		} catch (Exception e) {
			// Invalid Token exception - shutting down
			System.err.println(e.getMessage());
			System.out.println(BYE_MESSAGE);
			System.exit(0);
		}
	}

	protected static void addActivity() {
		System.out.println(INPUT_ACTIVITY_TEXT_MESSAGE);
		String actText = scanner.nextLine();
		System.out.println(CHOOSE_GROUP_MESSAGE);
		
		HttpEntity<String> response = template.exchange(BASE_URL
				+ LIST_ALL_GROUPS, HttpMethod.GET, requestEntity,
				String.class);
		System.out.println(parseGroupsList(response.getBody()));
		
		long groupId = 0;
		
		try {
			groupId = scanner.nextLong(); 
		} catch (Exception e) {
			//Setting default group
			groupId = 1;
		}
		
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		map.add("text", actText);
		map.add("group.id", groupId);
		
		try {
			template.postForObject(ADD_ACTIVITY, map, String.class);
			System.out.println(ACTIVITY_ADDED_MESSAGE);
		} catch (Exception e) {
			System.err.println(e);
		}
		
	}

	protected static void findActivities() {
		System.out.println(INPUT_PATTERN_MESSAGE);
		String pattern = scanner.nextLine();
		HttpEntity<String> response = template.exchange(BASE_URL
				+ FIND_ACTIVITIES, HttpMethod.GET, requestEntity,
				String.class, new Object[] {pattern});
		System.out.println(parseActivitiesList(response.getBody()));
	}

	protected static void listAllActivities() {
		HttpEntity<String> response = template.exchange(BASE_URL
				+ LIST_ALL_ACTIVITIES, HttpMethod.GET, requestEntity,
				String.class);
		System.out.println(parseActivitiesList(response.getBody()));
	}

	private static String parseActivitiesList(String json) {
		JSONObject obj = (JSONObject) JSONValue.parse(json);
		JSONArray resultArray = (JSONArray) obj.get("result");
		
		StringBuilder result = new StringBuilder();
		for (Object tempObj : resultArray) {
			JSONObject o = (JSONObject) tempObj;
			String tempResult = parseActivity(o);
			result.append(tempResult).append("\n");
		}
		return result.toString();
	}

	private static String parseActivity(JSONObject o) {
		String text = (String) o.get("text");
		String userName = (String)((JSONObject)o.get("user")).get("name");
		String groupName = (String)((JSONObject)o.get("group")).get("name");
		
		String tempResult = String.format("User: %s, Group: %s, Text: %s;", userName, groupName, text);
		return tempResult;
	}
	
	private static String parseGroupsList(String json) {
		JSONObject obj = (JSONObject) JSONValue.parse(json);
		JSONArray resultArray = (JSONArray) obj.get("result");
		
		StringBuilder result = new StringBuilder();
		for (Object tempObj : resultArray) {
			JSONObject o = (JSONObject) tempObj;
			String tempResult = parseGroup(o);
			result.append(tempResult).append("\n");
		}
		return result.toString();
	}
	
	private static String parseGroup(JSONObject o) {
		String groupText = (String) o.get("text");
		Long groupId = (Long)((JSONObject)o.get("group")).get("name");
		
		String tempResult = String.format("Group id: %s, Group name: %s;", groupId, groupText);
		return tempResult;
	}
}
