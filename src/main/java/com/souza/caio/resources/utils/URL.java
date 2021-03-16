package com.souza.caio.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {

	public static List<Integer> decodeIntList(String s){
		String [] vet = s.split(",");
		/*List<Integer> ids = new ArrayList<>();
		
		for(String number : vet) {
			ids.add(Integer.parseInt(number));
		}
		
		return ids;*/
		
		return Arrays.asList(
				s.split(",")
		).stream().map(
				x -> Integer.parseInt(x)
		).collect(Collectors.toList());
	}
	
	public static String decodeParam(String param) {
		try {
			return URLDecoder.decode(param, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}
}
