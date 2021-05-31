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
 * The <code>&lt;linkcheck&gt;</code> element is the root of the linkcheck descriptor.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings( "all" )
public class LinkcheckModel
    implements java.io.Serializable
{

      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field files.
     */
    private java.util.List<LinkcheckFile> files;

    /**
     * Field modelEncoding.
     */
    private String modelEncoding = "UTF-8";


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addFile.
     * 
     * @param linkcheckFile
     */
    public void addFile( LinkcheckFile linkcheckFile )
    {
        getFiles().add( linkcheckFile );
    } //-- void addFile( LinkcheckFile )

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

        if ( !( other instanceof LinkcheckModel ) )
        {
            return false;
        }

        LinkcheckModel that = (LinkcheckModel) other;
        boolean result = true;

        result = result && ( getFiles() == null ? that.getFiles() == null : getFiles().equals( that.getFiles() ) );

        return result;
    } //-- boolean equals( Object )

    /**
     * Method getFiles.
     * 
     * @return List
     */
    public java.util.List<LinkcheckFile> getFiles()
    {
        if ( this.files == null )
        {
            this.files = new java.util.ArrayList<LinkcheckFile>();
        }

        return this.files;
    } //-- java.util.List<LinkcheckFile> getFiles()

    /**
     * Get the modelEncoding field.
     * 
     * @return String
     */
    public String getModelEncoding()
    {
        return this.modelEncoding;
    } //-- String getModelEncoding()

    /**
     * Method hashCode.
     * 
     * @return int
     */
    public int hashCode()
    {
        int result = 17;

        result = 37 * result + ( files != null ? files.hashCode() : 0 );

        return result;
    } //-- int hashCode()

    /**
     * Method removeFile.
     * 
     * @param linkcheckFile
     */
    public void removeFile( LinkcheckFile linkcheckFile )
    {
        getFiles().remove( linkcheckFile );
    } //-- void removeFile( LinkcheckFile )

    /**
     * Set list of <code>&lt;file&gt;</code> elements.
     * 
     * @param files
     */
    public void setFiles( java.util.List<LinkcheckFile> files )
    {
        this.files = files;
    } //-- void setFiles( java.util.List )

    /**
     * Set the modelEncoding field.
     * 
     * @param modelEncoding
     */
    public void setModelEncoding( String modelEncoding )
    {
        this.modelEncoding = modelEncoding;
    } //-- void setModelEncoding( String )

    /**
     * Method toString.
     * 
     * @return String
     */
    public java.lang.String toString()
    {
        StringBuilder buf = new StringBuilder( 128 );

        buf.append( "files = '" );
        buf.append( getFiles() );
        buf.append( "'" );

        return buf.toString();
    } //-- java.lang.String toString()

}
