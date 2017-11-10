/****************************************************************************************************
* Project: comp3095assignment2
* Assignment: Assignment 2 
* Author(s): Gon Hu, Elis Shukullari, Leba Rubinoff
* Student Number: 100936779, 100823478, 100831385 
* Date: December 5, 2016
* Description: XML exception class
****************************************************************************************************/
package system.xmls;

public class XMLException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public XMLException() {
    }
    public XMLException(String message) {
        super(message);
    }
    public XMLException(Throwable cause) {
        super(cause);
    }
    public XMLException(String message, Throwable cause) {
        super(message, cause);
    }
    public XMLException(String message, Throwable cause,boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
