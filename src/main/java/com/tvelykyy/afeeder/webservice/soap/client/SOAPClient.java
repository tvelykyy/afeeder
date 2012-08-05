package com.tvelykyy.afeeder.webservice.soap.client;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.tvelykyy.afeeder.webservice.soap.client.wsimported.Activity;
import com.tvelykyy.afeeder.webservice.soap.client.wsimported.ActivityWebService;
import com.tvelykyy.afeeder.webservice.soap.client.wsimported.ActivityWebServiceEndpointService;

public class SOAPClient {
	private static final String chooseMessage = "Please choose operation:\n"
			+ "Press 1 - to list all activies;\n"
			+ "Press 2 - to search activies;\n"
			+ "Press 3 - to add activity;\n"
			+ "Press 0 - to quit.";
	private static final String welcomeMessage = "Welcome to afeeder SOAP client.";
	private static final String invalidOperationMessage = "Invalid operation.";
	private static final String byeMessage = "Bye.";
	private static Scanner scanner = new Scanner(System.in);
	public static void main(String[] args) {
		ActivityWebServiceEndpointService endPointService = new ActivityWebServiceEndpointService();
		ActivityWebService servicePort = endPointService.getActivityWebServicePort();
		
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
				System.out.println(1);
				showDialog(null);
				break;
			case 2:
				System.out.println(2);
				showDialog(null);
				break;
			case 3:
				System.out.println(3);
				showDialog(null);
				break;
			default:
				showDialog(invalidOperationMessage);
				break;
		}
	}
}
