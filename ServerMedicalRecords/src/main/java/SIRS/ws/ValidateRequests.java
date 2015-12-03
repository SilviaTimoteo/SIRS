package SIRS.ws;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;


public class ValidateRequests {
	static long tolerance = MILLISECONDS.convert(10, MINUTES);
	public static String validateChallenge(byte[] challengeSent, byte[] challengeReceived){
		if(Arrays.equals(challengeSent, challengeReceived)){
			return "Authentication Successful";
		}
		return "Authentication Failed";
	}
	
	/**
	 * Check if timestamp is valid (tolerance of 10 minutes)
	 * @parameter timestamp (HH:mm:SS)
	 * @return void
	 */
	public static boolean validTimestamp(String timestamp){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:SS");
		String now = sdf.format(Calendar.getInstance().getTime());
		Date start= null;
		Date nowTime = null;
		
		try {
			start = sdf.parse(timestamp);
			nowTime = sdf.parse(now);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		long duration = nowTime.getTime() - start.getTime();
		if(duration < tolerance){
			return true;
		}
		else {
			return false;
		}
	}

}
