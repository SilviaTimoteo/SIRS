package SIRS.ws;

import java.util.Arrays;

public class ValidateRequests {
	public static String validateChallenge(byte[] challengeSent, byte[] challengeReceived){
		if(Arrays.equals(challengeSent, challengeReceived)){
			return "Authentication Successful";
		}
		return "Authentication Failed";
	}
}
