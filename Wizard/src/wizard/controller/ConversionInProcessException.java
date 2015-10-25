/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizard.controller;


import java.lang.Exception;

public class ConversionInProcessException extends Exception
{
	public ConversionInProcessException(String message)
	{
		super(message);
	}
}