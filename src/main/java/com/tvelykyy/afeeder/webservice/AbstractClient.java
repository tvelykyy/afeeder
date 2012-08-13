package com.tvelykyy.afeeder.webservice;

import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class AbstractClient {
	protected static final String CHOOSE_MESSAGE = "Please choose operation:\n"
			+ "Press 1 - to list all activies;\n"
			+ "Press 2 - to search activies;\n"
			+ "Press 3 - to add activity;\n"
			+ "Press 0 - to quit.";
	protected static final String WELCOME_MESSAGE = "Welcome to afeeder SOAP client.";
	protected static final String INVALID_OPERATION_MESSAGE = "Invalid operation.";
	protected static final String BYE_MESSAGE = "Bye.";
	protected static final String INPUT_PATTERN_MESSAGE = "Input search pattern:";
	protected static final String EMPTY_LIST_MESSAGE = "Empty list";
	protected static final String INPUT_ACTIVITY_TEXT_MESSAGE = "Input Activity text:";
	protected static final String CHOOSE_GROUP_MESSAGE = "Choose group id:";
	protected static final String ACTIVITY_ADDED_MESSAGE = "Activity has been posted";
	protected static final String INVALID_INVOCATION_MESSAGE = "Invalid invocation. One parameter should be passed - Authorization Token.";
	
	protected static Scanner scanner = new Scanner(System.in);
}
