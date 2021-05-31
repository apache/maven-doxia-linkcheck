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
 *         The <code>&lt;file&gt;</code> to be checked.
 *       
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings( "all" )
public class LinkcheckFile
    implements java.io.Serializable
{

      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * 
     *             The file to check as String Object.
     *           
     */
    private String absolutePath;

    /**
     * 
     *             The relative path of the file.
     *           
     */
    private String relativePath;

    /**
     * 
     *             The number of successful links in this file.
     *           
     */
    private int successful = -1;

    /**
     * 
     *             The number of unsuccessful links in this file.
     *           
     */
    private int unsuccessful = -1;

    /**
     * Field results.
     */
    private java.util.List<LinkcheckFileResult> results;


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addResult.
     * 
     * @param linkcheckFileResult
     */
    public void addResult( LinkcheckFileResult linkcheckFileResult )
    {
        getResults().add( linkcheckFileResult );
    } //-- void addResult( LinkcheckFileResult )

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

        if ( !( other instanceof LinkcheckFile ) )
        {
            return false;
        }

        LinkcheckFile that = (LinkcheckFile) other;
        boolean result = true;

        result = result && ( getAbsolutePath() == null ? that.getAbsolutePath() == null : getAbsolutePath().equals( that.getAbsolutePath() ) );
        result = result && ( getRelativePath() == null ? that.getRelativePath() == null : getRelativePath().equals( that.getRelativePath() ) );
        result = result && successful == that.successful;
        result = result && unsuccessful == that.unsuccessful;
        result = result && ( getResults() == null ? that.getResults() == null : getResults().equals( that.getResults() ) );

        return result;
    } //-- boolean equals( Object )

    /**
     * Get the file to check as String Object.
     * 
     * @return String
     */
    public String getAbsolutePath()
    {
        return this.absolutePath;
    } //-- String getAbsolutePath()

    /**
     * Get the relative path of the file.
     * 
     * @return String
     */
    public String getRelativePath()
    {
        return this.relativePath;
    } //-- String getRelativePath()

    /**
     * Method getResults.
     * 
     * @return List
     */
    public java.util.List<LinkcheckFileResult> getResults()
    {
        if ( this.results == null )
        {
            this.results = new java.util.ArrayList<LinkcheckFileResult>();
        }

        return this.results;
    } //-- java.util.List<LinkcheckFileResult> getResults()

    /**
     * Get the number of successful links in this file.
     * 
     * @return int
     */
    public int getSuccessful()
    {
        return this.successful;
    } //-- int getSuccessful()

    /**
     * Get the number of unsuccessful links in this file.
     * 
     * @return int
     */
    public int getUnsuccessful()
    {
        return this.unsuccessful;
    } //-- int getUnsuccessful()

    /**
     * Method hashCode.
     * 
     * @return int
     */
    public int hashCode()
    {
        int result = 17;

        result = 37 * result + ( absolutePath != null ? absolutePath.hashCode() : 0 );
        result = 37 * result + ( relativePath != null ? relativePath.hashCode() : 0 );
        result = 37 * result + (int) successful;
        result = 37 * result + (int) unsuccessful;
        result = 37 * result + ( results != null ? results.hashCode() : 0 );

        return result;
    } //-- int hashCode()

    /**
     * Method removeResult.
     * 
     * @param linkcheckFileResult
     */
    public void removeResult( LinkcheckFileResult linkcheckFileResult )
    {
        getResults().remove( linkcheckFileResult );
    } //-- void removeResult( LinkcheckFileResult )

    /**
     * Set the file to check as String Object.
     * 
     * @param absolutePath
     */
    public void setAbsolutePath( String absolutePath )
    {
        this.absolutePath = absolutePath;
    } //-- void setAbsolutePath( String )

    /**
     * Set the relative path of the file.
     * 
     * @param relativePath
     */
    public void setRelativePath( String relativePath )
    {
        this.relativePath = relativePath;
    } //-- void setRelativePath( String )

    /**
     * Set all error details in this file.
     * 
     * @param results
     */
    public void setResults( java.util.List<LinkcheckFileResult> results )
    {
        this.results = results;
    } //-- void setResults( java.util.List )

    /**
     * Set the number of successful links in this file.
     * 
     * @param successful
     */
    public void setSuccessful( int successful )
    {
        this.successful = successful;
    } //-- void setSuccessful( int )

    /**
     * Set the number of unsuccessful links in this file.
     * 
     * @param unsuccessful
     */
    public void setUnsuccessful( int unsuccessful )
    {
        this.unsuccessful = unsuccessful;
    } //-- void setUnsuccessful( int )

    /**
     * Method toString.
     * 
     * @return String
     */
    public java.lang.String toString()
    {
        StringBuilder buf = new StringBuilder( 128 );

        buf.append( "absolutePath = '" );
        buf.append( getAbsolutePath() );
        buf.append( "'" );
        buf.append( "\n" ); 
        buf.append( "relativePath = '" );
        buf.append( getRelativePath() );
        buf.append( "'" );
        buf.append( "\n" ); 
        buf.append( "successful = '" );
        buf.append( getSuccessful() );
        buf.append( "'" );
        buf.append( "\n" ); 
        buf.append( "unsuccessful = '" );
        buf.append( getUnsuccessful() );
        buf.append( "'" );
        buf.append( "\n" ); 
        buf.append( "results = '" );
        buf.append( getResults() );
        buf.append( "'" );

        return buf.toString();
    } //-- java.lang.String toString()

    
            
    /**
     * Get the number of links for this file depending the level wanted.
     *
     * {@link LinkcheckFileResult#ERROR_LEVEL}
     * {@link LinkcheckFileResult#UNKNOWN_LEVEL}
     * {@link LinkcheckFileResult#VALID_LEVEL}
     * {@link LinkcheckFileResult#WARNING_LEVEL}
     *
     * @param level the restricted level
     * @return the number of links for the restrict level, -1 if the level is not a valid one
     * or no results was found.
     * @throws UnsupportedOperationException if the level is unsupported.
     */
    public int getNumberOfLinks( int level )
    {
        if ( results == null )
        {
            return -1;
        }

        if ( !( level == LinkcheckFileResult.ERROR_LEVEL || level == LinkcheckFileResult.WARNING_LEVEL
            || level == LinkcheckFileResult.VALID_LEVEL || level == LinkcheckFileResult.UNKNOWN_LEVEL ) )
        {
            throw new UnsupportedOperationException( "This level [" + level + "] is unsupported." );
        }

        int number = 0;
        for ( java.util.Iterator it = results.iterator(); it.hasNext(); )
        {
            LinkcheckFileResult linkcheckFileResult = (LinkcheckFileResult) it.next();

            if ( linkcheckFileResult.getStatusLevel() == level )
            {
                number++;
            }
        }

        return number;
    }

    /**
     * Get the number of links for this file.
     *
     * @param level
     * @return
     */
    public int getNumberOfLinks()
    {
        if ( results == null )
        {
            return -1;
        }

        return results.size();
    }
            
          
}
