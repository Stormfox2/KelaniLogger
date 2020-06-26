/*
 *
 *  Copyright (c) 2020.
 *  This system ist developed by Jes Müller and Quirin Brändli!
 *  All rights reserved!
 *
 *  Please read the licence for more information.
 *
 */

package de.kelanisystem.kelanilogger.exceptions;

public class NoFileSpecifiedException extends Exception {

    public NoFileSpecifiedException() {
        super("Could not activate file addition. No filename or directory specified.");
    }

}
