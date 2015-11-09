package com.skylka.ensieg.constants;

import android.graphics.Bitmap;

public class Ensieg_AppConstants {
	/*
	 * This class consists of all constants of app
	 */
	public static String SenderID = "655359827112";// 192.168.0.15/ENSIEG-Backend/
	public static String appService = "http://52.74.177.78/api/index.php/registeruser";
	public static String appService_login = "http://52.74.177.78/api/index.php/userlogin";
	public static String appService_profile = "http://52.74.177.78/api/index.php/createprofile";
	public static String appService_update = "http://52.74.177.78/api/index.php/updateprofile";
	public static String appService_host = "http://52.74.177.78/api/index.php/createevent";
	public static String appService_getCards = "http://52.74.177.78/api/index.php/getcards";
	public static String appService_userchecking = "http://52.74.177.78/api/index.php/validateuser";
	public static String appService_match = "http://52.74.177.78/api/index.php/getteams";
	public static String appService_join = "http://52.74.177.78/api/index.php/joinevent";
	public static String appService_comment = "http://52.74.177.78/api/index.php/addcomment";
	public static String appService_getcomment = "http://52.74.177.78/api/index.php/getcomments";
	public static String appService_getNotifications = "http://52.74.177.78/api/index.php/getnotification";
	public static String appService_getEnsiegContacts = "http://52.74.177.78/api/index.php/getensiegcontact";
	public static String appService_getTimeline = "http://52.74.177.78/api/index.php/gettimelinedata";
	public static String appService_notify = "http://52.74.177.78/api/index.php/addemail";
	public static String appService_createGroup = "http://52.74.177.78/api/index.php/creategroup";
	public static String appService_logout = "http://52.74.177.78/api/index.php/destroysession";// 
	public static String appService_requesttojoin = "http://52.74.177.78/api/index.php/requesttojoin";
	public static String appService_verifypassword = "http://52.74.177.78/api/index.php/verifypassword";
	public static String appService_getgroups = "http://52.74.177.78/api/index.php/getgroup";
	public static String appService_sendinvite = "http://52.74.177.78/api/index.php/sendinvite";
	public static String GENERATEOTP = "http://sendotp.msg91.com/api/generateOTP";
	public static String VERIFYOTP = "http://sendotp.msg91.com/api/verifyOTP";//
	public static boolean isUserForgetpassword = false;
	public static String sessionId = "";
	public static String macAddress = "";
	public static Bitmap profile;
	public static String otp_application_key = "RyI58WoTOjycIr_hKfXe1YfXILcXExt-khZOtHj5tz-4rkj70KTfi6mGrTgfF9K5saMUo9_vHaxnqIfN30WaeHXtwXODdH5OfQx2xzELhrTmWpXvYfLc6Aew4ZyZRK_HRDpIWfOjwVkJnAQbhIV2KQ==";
	public static boolean loginSuccess = false;
	public static boolean userPressedBack = false;
	// Palakol lat&long

	// public static String latitude = "16.5333";
	// public static String longitude = "81.7333";
	// public static String radius = "20";

	// banjara hills lattitude and longitude

	public static String latitude = "17.4165";
	public static String longitude = "78.4382";
	public static String radius = "20";

	//

	/*
	 * public static String latitude = "17.4165"; public static String longitude
	 * = "78.4382"; public static String radius = "10";
	 */

	public static boolean loginFromTheDifferentDevice = false;

}
