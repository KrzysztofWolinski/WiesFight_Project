package com.wiesfight.dataaccesslayer;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@ParseClassName("Posts")
public class AnywallPost extends ParseObject {
	
	public String getText() {
		return getString("text");
	}
	 
	public void setText(String value) {
	    put("text", value);
	}
	
	public void add() {
		this.saveInBackground();
	}
	 
	public static ParseQuery<AnywallPost> getQuery() {
	    return ParseQuery.getQuery(AnywallPost.class);
	}
}