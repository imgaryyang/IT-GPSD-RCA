package com.gps.util;

import java.util.ArrayList;
import java.util.Hashtable;

public class WildCard {
	
	public Hashtable<String,Integer> wildCardCounts;
	public ArrayList<String> wildKeys;
	public int wildKeyIndex;
		
	public WildCard(){
		this.wildKeys = new ArrayList<String>();
		wildCardCounts = new Hashtable<String, Integer>();
	}
	
	
}

