package com.yedamFinal.aco.common;

import java.util.Random;

public class RandomString {
	private static String randStrArr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	
	public static String generateRandomString(int len) {
		if(len <= 0) {
			return null;
		}
		
		StringBuilder randomString = new StringBuilder(len);
        Random random = new Random();

        for (int i = 0; i < len; i++) {
            int randomIndex = random.nextInt(randStrArr.length());
            char randomChar = randStrArr.charAt(randomIndex);
            randomString.append(randomChar);
        }

        return randomString.toString();
	}
}
