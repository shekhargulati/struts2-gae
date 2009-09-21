package com.struts2.gae.dispatcher.multipart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.StrutsRequestWrapper;

import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

public class GAEMultiPartRequestWrapper extends StrutsRequestWrapper {
	
	protected static final Logger LOG = LoggerFactory.getLogger(GAEMultiPartRequestWrapper.class);

    Collection<String> errors;
    GAEMultiPartRequest multi;

	public GAEMultiPartRequestWrapper(GAEMultiPartRequest multiPartRequest, HttpServletRequest request) {
		super(request);
		multi = multiPartRequest;
        try {
            multi.parse(request);
            for (Object o : multi.getErrors()) {
                String error = (String) o;
                addError(error);
            }
        } catch (IOException e) {
            addError("Cannot parse request: "+e.toString());
        }
		
	}
	
	/**
     * Adds an error message.
     *
     * @param anErrorMessage the error message to report.
     */
    protected void addError(String anErrorMessage) {
        if (errors == null) {
            errors = new ArrayList<String>();
        }

        errors.add(anErrorMessage);
    }

    /**
     * Returns <tt>true</tt> if any errors occured when parsing the HTTP multipart request, <tt>false</tt> otherwise.
     *
     * @return <tt>true</tt> if any errors occured when parsing the HTTP multipart request, <tt>false</tt> otherwise.
     */
    public boolean hasErrors() {
        return !((errors == null) || errors.isEmpty());
    }
    
    /**
     * Returns a collection of any errors generated when parsing the multipart request.
     *
     * @return the error Collection.
     */
    public Collection<String> getErrors() {
        return errors;
    }
    
    /**
     * Get an enumeration of the parameter names for uploaded files
     *
     * @return enumeration of parameter names for uploaded files
     */
    public Enumeration<String> getFileParameterNames() {
        if (multi == null) {
            return null;
        }

        return multi.getFileParameterNames();
    }

    /**
     * Get an array of content encoding for the specified input field name or <tt>null</tt> if
     * no content type was specified.
     *
     * @param name input field name
     * @return an array of content encoding for the specified input field name
     */
    public String[] getContentTypes(String name) {
        if (multi == null) {
            return null;
        }

        return multi.getContentType(name);
    }
    
    public String[] getFileContents(String fieldName) {
    	if (multi == null) {
            return null;
        }

        return multi.getFileContents(fieldName);
    }

    /**
     * Get a String array of the file names for uploaded files
     *
     * @param fieldName Field to check for file names.
     * @return a String[] of file names for uploaded files
     */
    public String[] getFileNames(String fieldName) {
        if (multi == null) {
            return null;
        }

        return multi.getFileNames(fieldName);
    }
}
