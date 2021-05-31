package org.apache.maven.doxia.linkcheck.model.io.xpp3;

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

import java.io.OutputStream;
import java.io.Writer;
import java.util.Iterator;
import org.apache.maven.doxia.linkcheck.model.LinkcheckFile;
import org.apache.maven.doxia.linkcheck.model.LinkcheckFileResult;
import org.apache.maven.doxia.linkcheck.model.LinkcheckModel;
import org.codehaus.plexus.util.xml.pull.MXSerializer;
import org.codehaus.plexus.util.xml.pull.XmlSerializer;

/**
 * Class LinkcheckModelXpp3Writer.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings( "all" )
public class LinkcheckModelXpp3Writer
{

      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field NAMESPACE.
     */
    private static final String NAMESPACE = null;

    /**
     * Field fileComment.
     */
    private String fileComment = null;


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method setFileComment.
     * 
     * @param fileComment
     */
    public void setFileComment( String fileComment )
    {
        this.fileComment = fileComment;
    } //-- void setFileComment( String )

    /**
     * Method write.
     * 
     * @param writer
     * @param linkcheckModel
     * @throws java.io.IOException
     */
    public void write( Writer writer, LinkcheckModel linkcheckModel )
        throws java.io.IOException
    {
        XmlSerializer serializer = new MXSerializer();
        serializer.setProperty( "http://xmlpull.org/v1/doc/properties.html#serializer-indentation", "  " );
        serializer.setProperty( "http://xmlpull.org/v1/doc/properties.html#serializer-line-separator", "\n" );
        serializer.setOutput( writer );
        serializer.startDocument( linkcheckModel.getModelEncoding(), null );
        writeLinkcheckModel( linkcheckModel, "linkcheckModel", serializer );
        serializer.endDocument();
    } //-- void write( Writer, LinkcheckModel )

    /**
     * Method write.
     * 
     * @param stream
     * @param linkcheckModel
     * @throws java.io.IOException
     */
    public void write( OutputStream stream, LinkcheckModel linkcheckModel )
        throws java.io.IOException
    {
        XmlSerializer serializer = new MXSerializer();
        serializer.setProperty( "http://xmlpull.org/v1/doc/properties.html#serializer-indentation", "  " );
        serializer.setProperty( "http://xmlpull.org/v1/doc/properties.html#serializer-line-separator", "\n" );
        serializer.setOutput( stream, linkcheckModel.getModelEncoding() );
        serializer.startDocument( linkcheckModel.getModelEncoding(), null );
        writeLinkcheckModel( linkcheckModel, "linkcheckModel", serializer );
        serializer.endDocument();
    } //-- void write( OutputStream, LinkcheckModel )

    /**
     * Method writeLinkcheckFile.
     * 
     * @param linkcheckFile
     * @param serializer
     * @param tagName
     * @throws java.io.IOException
     */
    private void writeLinkcheckFile( LinkcheckFile linkcheckFile, String tagName, XmlSerializer serializer )
        throws java.io.IOException
    {
        serializer.startTag( NAMESPACE, tagName );
        if ( linkcheckFile.getAbsolutePath() != null )
        {
            serializer.startTag( NAMESPACE, "absolutePath" ).text( linkcheckFile.getAbsolutePath() ).endTag( NAMESPACE, "absolutePath" );
        }
        if ( linkcheckFile.getRelativePath() != null )
        {
            serializer.startTag( NAMESPACE, "relativePath" ).text( linkcheckFile.getRelativePath() ).endTag( NAMESPACE, "relativePath" );
        }
        if ( linkcheckFile.getSuccessful() != -1 )
        {
            serializer.startTag( NAMESPACE, "successful" ).text( String.valueOf( linkcheckFile.getSuccessful() ) ).endTag( NAMESPACE, "successful" );
        }
        if ( linkcheckFile.getUnsuccessful() != -1 )
        {
            serializer.startTag( NAMESPACE, "unsuccessful" ).text( String.valueOf( linkcheckFile.getUnsuccessful() ) ).endTag( NAMESPACE, "unsuccessful" );
        }
        if ( ( linkcheckFile.getResults() != null ) && ( linkcheckFile.getResults().size() > 0 ) )
        {
            serializer.startTag( NAMESPACE, "results" );
            for ( Iterator iter = linkcheckFile.getResults().iterator(); iter.hasNext(); )
            {
                LinkcheckFileResult o = (LinkcheckFileResult) iter.next();
                writeLinkcheckFileResult( o, "result", serializer );
            }
            serializer.endTag( NAMESPACE, "results" );
        }
        serializer.endTag( NAMESPACE, tagName );
    } //-- void writeLinkcheckFile( LinkcheckFile, String, XmlSerializer )

    /**
     * Method writeLinkcheckFileResult.
     * 
     * @param linkcheckFileResult
     * @param serializer
     * @param tagName
     * @throws java.io.IOException
     */
    private void writeLinkcheckFileResult( LinkcheckFileResult linkcheckFileResult, String tagName, XmlSerializer serializer )
        throws java.io.IOException
    {
        serializer.startTag( NAMESPACE, tagName );
        if ( linkcheckFileResult.getTarget() != null )
        {
            serializer.startTag( NAMESPACE, "target" ).text( linkcheckFileResult.getTarget() ).endTag( NAMESPACE, "target" );
        }
        if ( linkcheckFileResult.getStatus() != null )
        {
            serializer.startTag( NAMESPACE, "status" ).text( linkcheckFileResult.getStatus() ).endTag( NAMESPACE, "status" );
        }
        if ( linkcheckFileResult.getErrorMessage() != null )
        {
            serializer.startTag( NAMESPACE, "errorMessage" ).text( linkcheckFileResult.getErrorMessage() ).endTag( NAMESPACE, "errorMessage" );
        }
        serializer.endTag( NAMESPACE, tagName );
    } //-- void writeLinkcheckFileResult( LinkcheckFileResult, String, XmlSerializer )

    /**
     * Method writeLinkcheckModel.
     * 
     * @param linkcheckModel
     * @param serializer
     * @param tagName
     * @throws java.io.IOException
     */
    private void writeLinkcheckModel( LinkcheckModel linkcheckModel, String tagName, XmlSerializer serializer )
        throws java.io.IOException
    {
        serializer.startTag( NAMESPACE, tagName );
        if ( ( linkcheckModel.getFiles() != null ) && ( linkcheckModel.getFiles().size() > 0 ) )
        {
            serializer.startTag( NAMESPACE, "files" );
            for ( Iterator iter = linkcheckModel.getFiles().iterator(); iter.hasNext(); )
            {
                LinkcheckFile o = (LinkcheckFile) iter.next();
                writeLinkcheckFile( o, "file", serializer );
            }
            serializer.endTag( NAMESPACE, "files" );
        }
        serializer.endTag( NAMESPACE, tagName );
    } //-- void writeLinkcheckModel( LinkcheckModel, String, XmlSerializer )

}
