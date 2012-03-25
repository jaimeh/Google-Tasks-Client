package com.redditandroiddevelopers.googletasksclient;

public class ClientCredentials {

	  /** Value of the "API key" shown under "Simple API Access". */
	  public static final String KEY = "AIzaSyDQQw3jm86Qpn9knnNAVlJ0IncVp5kDNJo";

	  public static void errorIfNotSpecified() {
	    if (KEY == null) {
	      System.err.println("Please enter your API key in " + ClientCredentials.class);
	      System.exit(1);
	    }
	  }
	}
