package org.apache.maven.doxia.linkcheck.model;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

/**
 * 
 * A class containing the results of a single check of a link.      
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings( "all" )
public class LinkcheckFileResult
    implements java.io.Serializable
{

      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * 
     *             The target URL.
     *           
     */
    private String target;

    /**
     * 
     *             The status.
     *           
     */
    private String status;

    /**
     * 
     *             The error message.
     *           
     */
    private String errorMessage;


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method equals.
     * 
     * @param other
     * @return boolean
     */
    public boolean equals( Object other )
    {
        if ( this == other )
        {
            return true;
        }

        if ( !( other instanceof LinkcheckFileResult ) )
        {
            return false;
        }

        LinkcheckFileResult that = (LinkcheckFileResult) other;
        boolean result = true;

        result = result && ( getTarget() == null ? that.getTarget() == null : getTarget().equals( that.getTarget() ) );
        result = result && ( getStatus() == null ? that.getStatus() == null : getStatus().equals( that.getStatus() ) );
        result = result && ( getErrorMessage() == null ? that.getErrorMessage() == null : getErrorMessage().equals( that.getErrorMessage() ) );

        return result;
    } //-- boolean equals( Object )

    /**
     * Get the error message.
     * 
     * @return String
     */
    public String getErrorMessage()
    {
        return this.errorMessage;
    } //-- String getErrorMessage()

    /**
     * Get the status.
     * 
     * @return String
     */
    public String getStatus()
    {
        return this.status;
    } //-- String getStatus()

    /**
     * Get the target URL.
     * 
     * @return String
     */
    public String getTarget()
    {
        return this.target;
    } //-- String getTarget()

    /**
     * Method hashCode.
     * 
     * @return int
     */
    public int hashCode()
    {
        int result = 17;

        result = 37 * result + ( target != null ? target.hashCode() : 0 );
        result = 37 * result + ( status != null ? status.hashCode() : 0 );
        result = 37 * result + ( errorMessage != null ? errorMessage.hashCode() : 0 );

        return result;
    } //-- int hashCode()

    /**
     * Set the error message.
     * 
     * @param errorMessage
     */
    public void setErrorMessage( String errorMessage )
    {
        this.errorMessage = errorMessage;
    } //-- void setErrorMessage( String )

    /**
     * Set the status.
     * 
     * @param status
     */
    public void setStatus( String status )
    {
        this.status = status;
    } //-- void setStatus( String )

    /**
     * Set the target URL.
     * 
     * @param target
     */
    public void setTarget( String target )
    {
        this.target = target;
    } //-- void setTarget( String )

    /**
     * Method toString.
     * 
     * @return String
     */
    public java.lang.String toString()
    {
        StringBuilder buf = new StringBuilder( 128 );

        buf.append( "target = '" );
        buf.append( getTarget() );
        buf.append( "'" );
        buf.append( "\n" ); 
        buf.append( "status = '" );
        buf.append( getStatus() );
        buf.append( "'" );
        buf.append( "\n" ); 
        buf.append( "errorMessage = '" );
        buf.append( getErrorMessage() );
        buf.append( "'" );

        return buf.toString();
    } //-- java.lang.String toString()

    
            
    /** The vm line separator. */
    private static final String EOL = System.getProperty( "line.separator" );

    /** Validation result level: error. */
    public static final int ERROR_LEVEL = 1;

    /** Validation result level: warning. */
    public static final int WARNING_LEVEL = 2;

    /** Validation result level: valid. */
    public static final int VALID_LEVEL = 3;

    /** Validation result level: unknown. */
    public static final int UNKNOWN_LEVEL = 4;

    /** Validation result: error. */
    public static final String ERROR = "error";

    /** Validation result: warning. */
    public static final String WARNING = "warning";

    /** Validation result: valid. */
    public static final String VALID = "valid";

    /** Validation result: unknown. */
    public static final String UNKNOWN = "unknown";

    /**
     * Returns the status as an integer.
     *
     * @return One of ERROR, WARNING, VALID or UNKNOWN.
     */
    public int getStatusLevel()
    {
        int level = UNKNOWN_LEVEL;

        if ( VALID.equals( getStatus() ) )
        {
            level = VALID_LEVEL;
        }
        else if ( WARNING.equals( getStatus() ) )
        {
            level = WARNING_LEVEL;
        }
        else if ( ERROR.equals( getStatus() ) )
        {
            level = ERROR_LEVEL;
        }

        return level;
    }
            
          
}
