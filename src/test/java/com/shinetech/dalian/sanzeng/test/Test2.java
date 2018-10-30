package com.shinetech.dalian.sanzeng.test;


import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;




public class Test2 {


	 public static void main(String[] args) {
	        String userName = "ext-adm-mia.wan";
	        String password = "2018Limagrain$";
	        String host = "127.0.0.1";
	        String domain = "FromEarthToLife.net";
	        String port = "389"; 
	        String url = new String("ldap://" + host + ":" + port);
	        String user = userName.indexOf(domain) > 0 ? userName : userName
	                + domain;
	        Hashtable env = new Hashtable();
	        DirContext ctx = null;
	        env.put(Context.SECURITY_AUTHENTICATION, "simple");
	        env.put(Context.SECURITY_PRINCIPAL, user); 
	        env.put(Context.SECURITY_CREDENTIALS, password);
	        env.put(Context.INITIAL_CONTEXT_FACTORY,
	                "com.sun.jndi.ldap.LdapCtxFactory");
	        env.put(Context.PROVIDER_URL, url);
	        try {
	            ctx = new InitialDirContext(env);
	            System.out.println("success");
	        } catch (AuthenticationException e) {
	            System.out.println("failed");
	            e.printStackTrace();
	        } catch (javax.naming.CommunicationException e) {
	            System.out.println("AD faid");
	            e.printStackTrace();
	        } catch (Exception e) {
	            System.out.println("ID Exception");
	            e.printStackTrace();
	        } finally{
	            if(null!=ctx){
	                try {
	                    ctx.close();
	                    ctx=null;
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	    }
 

}
