package com.struts2.gae.dispatcher;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterConfig;

import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.dispatcher.FilterDispatcher;

/**
 * GAEFilterDispatcher is an {@link FilterDispatcher} which is specific to Google App Engine.
 * Google App Engine imposes lots of restriction such as you cannot write to the FileSystem. To avoid those problems you need to use GAEFilterDispatcher.
 * It overrides createDispatcher method to provide Google App Engine Specific Dispatcher {@link GAEDispatcher}.
 * To use this you need to configure this in your web.xml file instead of {@link FilterDispatcher}.
 * 
 * @author whyjava7@gmail.com
 * @version 0.1
 */
@SuppressWarnings("deprecation")
public class GAEFilterDispatcher extends FilterDispatcher {
	
	/**
	 * Creates a Google App Engine specific dispatcher {@link GAEDispatcher}.
	 * 
	 * @param filterConfig
	 * @return {@link GAEDispatcher}
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Dispatcher createDispatcher(FilterConfig filterConfig) {
		Map<String, String> params = new HashMap<String, String>();
        for (Enumeration e = filterConfig.getInitParameterNames(); e.hasMoreElements();) {
            String name =  (String)e.nextElement();
            String value = filterConfig.getInitParameter(name);
            params.put(name, value);
        }
        return new GAEDispatcher(filterConfig.getServletContext(), params);
	}
}
