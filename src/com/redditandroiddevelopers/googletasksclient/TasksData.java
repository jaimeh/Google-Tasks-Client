package com.redditandroiddevelopers.googletasksclient;

import java.util.Date;

import com.google.api.client.util.DateTime;

public class TasksData {
	
	 

	private String etag;

	private String id;
     private String kind;
     private long position;
     private String selfLink;
     private String status;
     private String title;
     private DateTime updated;
     
     public TasksData(String etag, 
    		 	  String id,
    		 	  String kind,
    		 	  long position, 
    		 	  String selfLink,
    		 	  String status,
    		 	  String title,
    		 	  DateTime updated){
    	 
         this.etag = etag;

         this.id= id;
         
         this.kind = kind;
         
         this.position = position;
         
         this.selfLink = selfLink;
         
         this.status = status;
         
         this.title = title;
         
         this.updated = updated;

     		}
     
    
     public String getEtag() {
 		return etag;
 	}

 	public void setEtag(String etag) {
 		this.etag = etag;
 	}

 	public String getId() {
 		return id;
 	}

 	public void setId(String id) {
 		this.id = id;
 	}

 	public String getKind() {
 		return kind;
 	}

 	public void setKind(String kind) {
 		this.kind = kind;
 	}

 	public long getPosition() {
 		return position;
 	}

 	public void setPosition(long position) {
 		this.position = position;
 	}

 	public String getSelfLink() {
 		return selfLink;
 	}

 	public void setSelfLink(String selfLink) {
 		this.selfLink = selfLink;
 	}

 	public String getStatus() {
 		return status;
 	}

 	public void setStatus(String status) {
 		this.status = status;
 	}

 	public String getTitle() {
 		return title;
 	}

 	public void setTitle(String title) {
 		this.title = title;
 	}

 	public DateTime getUpdated() {
 		return updated;
 	}

 	public void setUpdated(DateTime updated) {
 		this.updated = updated;
 	}
 	
 	

}
