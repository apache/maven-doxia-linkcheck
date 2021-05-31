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

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.text.DateFormat;
import org.apache.maven.doxia.linkcheck.model.LinkcheckFile;
import org.apache.maven.doxia.linkcheck.model.LinkcheckFileResult;
import org.apache.maven.doxia.linkcheck.model.LinkcheckModel;
import org.codehaus.plexus.util.ReaderFactory;
import org.codehaus.plexus.util.xml.pull.EntityReplacementMap;
import org.codehaus.plexus.util.xml.pull.MXParser;
import org.codehaus.plexus.util.xml.pull.XmlPullParser;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

/**
 * Class LinkcheckModelXpp3Reader.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings( "all" )
public class LinkcheckModelXpp3Reader
{

      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * If set the parser will be loaded with all single characters
     * from the XHTML specification.
     * The entities used:
     * <ul>
     * <li>http://www.w3.org/TR/xhtml1/DTD/xhtml-lat1.ent</li>
     * <li>http://www.w3.org/TR/xhtml1/DTD/xhtml-special.ent</li>
     * <li>http://www.w3.org/TR/xhtml1/DTD/xhtml-symbol.ent</li>
     * </ul>
     */
    private boolean addDefaultEntities = true;

    /**
     * Field contentTransformer.
     */
    public final ContentTransformer contentTransformer;


      //----------------/
     //- Constructors -/
    //----------------/

    public LinkcheckModelXpp3Reader()
    {
        this( new ContentTransformer()
        {
            public String transform( String source, String fieldName )
            {
                return source;
            }
        } );
    } //-- org.apache.maven.doxia.linkcheck.model.io.xpp3.LinkcheckModelXpp3Reader()

    public LinkcheckModelXpp3Reader(ContentTransformer contentTransformer)
    {
        this.contentTransformer = contentTransformer;
    } //-- org.apache.maven.doxia.linkcheck.model.io.xpp3.LinkcheckModelXpp3Reader(ContentTransformer)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method checkFieldWithDuplicate.
     * 
     * @param parser
     * @param parsed
     * @param alias
     * @param tagName
     * @throws XmlPullParserException
     * @return boolean
     */
    private boolean checkFieldWithDuplicate( XmlPullParser parser, String tagName, String alias, java.util.Set parsed )
        throws XmlPullParserException
    {
        if ( !( parser.getName().equals( tagName ) || parser.getName().equals( alias ) ) )
        {
            return false;
        }
        if ( !parsed.add( tagName ) )
        {
            throw new XmlPullParserException( "Duplicated tag: '" + tagName + "'", parser, null );
        }
        return true;
    } //-- boolean checkFieldWithDuplicate( XmlPullParser, String, String, java.util.Set )

    /**
     * Method checkUnknownAttribute.
     * 
     * @param parser
     * @param strict
     * @param tagName
     * @param attribute
     * @throws XmlPullParserException
     * @throws IOException
     */
    private void checkUnknownAttribute( XmlPullParser parser, String attribute, String tagName, boolean strict )
        throws XmlPullParserException, IOException
    {
        // strictXmlAttributes = true for model: if strict == true, not only elements are checked but attributes too
        if ( strict )
        {
            throw new XmlPullParserException( "Unknown attribute '" + attribute + "' for tag '" + tagName + "'", parser, null );
        }
    } //-- void checkUnknownAttribute( XmlPullParser, String, String, boolean )

    /**
     * Method checkUnknownElement.
     * 
     * @param parser
     * @param strict
     * @throws XmlPullParserException
     * @throws IOException
     */
    private void checkUnknownElement( XmlPullParser parser, boolean strict )
        throws XmlPullParserException, IOException
    {
        if ( strict )
        {
            throw new XmlPullParserException( "Unrecognised tag: '" + parser.getName() + "'", parser, null );
        }

        for ( int unrecognizedTagCount = 1; unrecognizedTagCount > 0; )
        {
            int eventType = parser.next();
            if ( eventType == XmlPullParser.START_TAG )
            {
                unrecognizedTagCount++;
            }
            else if ( eventType == XmlPullParser.END_TAG )
            {
                unrecognizedTagCount--;
            }
        }
    } //-- void checkUnknownElement( XmlPullParser, boolean )

    /**
     * Returns the state of the "add default entities" flag.
     * 
     * @return boolean
     */
    public boolean getAddDefaultEntities()
    {
        return addDefaultEntities;
    } //-- boolean getAddDefaultEntities()

    /**
     * Method getBooleanValue.
     * 
     * @param s
     * @param parser
     * @param attribute
     * @throws XmlPullParserException
     * @return boolean
     */
    private boolean getBooleanValue( String s, String attribute, XmlPullParser parser )
        throws XmlPullParserException
    {
        return getBooleanValue( s, attribute, parser, null );
    } //-- boolean getBooleanValue( String, String, XmlPullParser )

    /**
     * Method getBooleanValue.
     * 
     * @param s
     * @param defaultValue
     * @param parser
     * @param attribute
     * @throws XmlPullParserException
     * @return boolean
     */
    private boolean getBooleanValue( String s, String attribute, XmlPullParser parser, String defaultValue )
        throws XmlPullParserException
    {
        if ( s != null && s.length() != 0 )
        {
            return Boolean.valueOf( s ).booleanValue();
        }
        if ( defaultValue != null )
        {
            return Boolean.valueOf( defaultValue ).booleanValue();
        }
        return false;
    } //-- boolean getBooleanValue( String, String, XmlPullParser, String )

    /**
     * Method getByteValue.
     * 
     * @param s
     * @param strict
     * @param parser
     * @param attribute
     * @throws XmlPullParserException
     * @return byte
     */
    private byte getByteValue( String s, String attribute, XmlPullParser parser, boolean strict )
        throws XmlPullParserException
    {
        if ( s != null )
        {
            try
            {
                return Byte.valueOf( s ).byteValue();
            }
            catch ( NumberFormatException nfe )
            {
                if ( strict )
                {
                    throw new XmlPullParserException( "Unable to parse element '" + attribute + "', must be a byte", parser, nfe );
                }
            }
        }
        return 0;
    } //-- byte getByteValue( String, String, XmlPullParser, boolean )

    /**
     * Method getCharacterValue.
     * 
     * @param s
     * @param parser
     * @param attribute
     * @throws XmlPullParserException
     * @return char
     */
    private char getCharacterValue( String s, String attribute, XmlPullParser parser )
        throws XmlPullParserException
    {
        if ( s != null )
        {
            return s.charAt( 0 );
        }
        return 0;
    } //-- char getCharacterValue( String, String, XmlPullParser )

    /**
     * Method getDateValue.
     * 
     * @param s
     * @param parser
     * @param attribute
     * @throws XmlPullParserException
     * @return Date
     */
    private java.util.Date getDateValue( String s, String attribute, XmlPullParser parser )
        throws XmlPullParserException
    {
        return getDateValue( s, attribute, null, parser );
    } //-- java.util.Date getDateValue( String, String, XmlPullParser )

    /**
     * Method getDateValue.
     * 
     * @param s
     * @param parser
     * @param dateFormat
     * @param attribute
     * @throws XmlPullParserException
     * @return Date
     */
    private java.util.Date getDateValue( String s, String attribute, String dateFormat, XmlPullParser parser )
        throws XmlPullParserException
    {
        if ( s != null )
        {
            String effectiveDateFormat = dateFormat;
            if ( dateFormat == null )
            {
                effectiveDateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS";
            }
            if ( "long".equals( effectiveDateFormat ) )
            {
                try
                {
                    return new java.util.Date( Long.parseLong( s ) );
                }
                catch ( NumberFormatException e )
                {
                    throw new XmlPullParserException( e.getMessage(), parser, e );
                }
            }
            else
            {
                try
                {
                    DateFormat dateParser = new java.text.SimpleDateFormat( effectiveDateFormat, java.util.Locale.US );
                    return dateParser.parse( s );
                }
                catch ( java.text.ParseException e )
                {
                    throw new XmlPullParserException( e.getMessage(), parser, e );
                }
            }
        }
        return null;
    } //-- java.util.Date getDateValue( String, String, String, XmlPullParser )

    /**
     * Method getDoubleValue.
     * 
     * @param s
     * @param strict
     * @param parser
     * @param attribute
     * @throws XmlPullParserException
     * @return double
     */
    private double getDoubleValue( String s, String attribute, XmlPullParser parser, boolean strict )
        throws XmlPullParserException
    {
        if ( s != null )
        {
            try
            {
                return Double.valueOf( s ).doubleValue();
            }
            catch ( NumberFormatException nfe )
            {
                if ( strict )
                {
                    throw new XmlPullParserException( "Unable to parse element '" + attribute + "', must be a floating point number", parser, nfe );
                }
            }
        }
        return 0;
    } //-- double getDoubleValue( String, String, XmlPullParser, boolean )

    /**
     * Method getFloatValue.
     * 
     * @param s
     * @param strict
     * @param parser
     * @param attribute
     * @throws XmlPullParserException
     * @return float
     */
    private float getFloatValue( String s, String attribute, XmlPullParser parser, boolean strict )
        throws XmlPullParserException
    {
        if ( s != null )
        {
            try
            {
                return Float.valueOf( s ).floatValue();
            }
            catch ( NumberFormatException nfe )
            {
                if ( strict )
                {
                    throw new XmlPullParserException( "Unable to parse element '" + attribute + "', must be a floating point number", parser, nfe );
                }
            }
        }
        return 0;
    } //-- float getFloatValue( String, String, XmlPullParser, boolean )

    /**
     * Method getIntegerValue.
     * 
     * @param s
     * @param strict
     * @param parser
     * @param attribute
     * @throws XmlPullParserException
     * @return int
     */
    private int getIntegerValue( String s, String attribute, XmlPullParser parser, boolean strict )
        throws XmlPullParserException
    {
        if ( s != null )
        {
            try
            {
                return Integer.valueOf( s ).intValue();
            }
            catch ( NumberFormatException nfe )
            {
                if ( strict )
                {
                    throw new XmlPullParserException( "Unable to parse element '" + attribute + "', must be an integer", parser, nfe );
                }
            }
        }
        return 0;
    } //-- int getIntegerValue( String, String, XmlPullParser, boolean )

    /**
     * Method getLongValue.
     * 
     * @param s
     * @param strict
     * @param parser
     * @param attribute
     * @throws XmlPullParserException
     * @return long
     */
    private long getLongValue( String s, String attribute, XmlPullParser parser, boolean strict )
        throws XmlPullParserException
    {
        if ( s != null )
        {
            try
            {
                return Long.valueOf( s ).longValue();
            }
            catch ( NumberFormatException nfe )
            {
                if ( strict )
                {
                    throw new XmlPullParserException( "Unable to parse element '" + attribute + "', must be a long integer", parser, nfe );
                }
            }
        }
        return 0;
    } //-- long getLongValue( String, String, XmlPullParser, boolean )

    /**
     * Method getRequiredAttributeValue.
     * 
     * @param s
     * @param strict
     * @param parser
     * @param attribute
     * @throws XmlPullParserException
     * @return String
     */
    private String getRequiredAttributeValue( String s, String attribute, XmlPullParser parser, boolean strict )
        throws XmlPullParserException
    {
        if ( s == null )
        {
            if ( strict )
            {
                throw new XmlPullParserException( "Missing required value for attribute '" + attribute + "'", parser, null );
            }
        }
        return s;
    } //-- String getRequiredAttributeValue( String, String, XmlPullParser, boolean )

    /**
     * Method getShortValue.
     * 
     * @param s
     * @param strict
     * @param parser
     * @param attribute
     * @throws XmlPullParserException
     * @return short
     */
    private short getShortValue( String s, String attribute, XmlPullParser parser, boolean strict )
        throws XmlPullParserException
    {
        if ( s != null )
        {
            try
            {
                return Short.valueOf( s ).shortValue();
            }
            catch ( NumberFormatException nfe )
            {
                if ( strict )
                {
                    throw new XmlPullParserException( "Unable to parse element '" + attribute + "', must be a short integer", parser, nfe );
                }
            }
        }
        return 0;
    } //-- short getShortValue( String, String, XmlPullParser, boolean )

    /**
     * Method getTrimmedValue.
     * 
     * @param s
     * @return String
     */
    private String getTrimmedValue( String s )
    {
        if ( s != null )
        {
            s = s.trim();
        }
        return s;
    } //-- String getTrimmedValue( String )

    /**
     * Method interpolatedTrimmed.
     * 
     * @param value
     * @param context
     * @return String
     */
    private String interpolatedTrimmed( String value, String context )
    {
        return getTrimmedValue( contentTransformer.transform( value, context ) );
    } //-- String interpolatedTrimmed( String, String )

    /**
     * Method nextTag.
     * 
     * @param parser
     * @throws IOException
     * @throws XmlPullParserException
     * @return int
     */
    private int nextTag( XmlPullParser parser )
        throws IOException, XmlPullParserException
    {
        int eventType = parser.next();
        if ( eventType == XmlPullParser.TEXT )
        {
            eventType = parser.next();
        }
        if ( eventType != XmlPullParser.START_TAG && eventType != XmlPullParser.END_TAG )
        {
            throw new XmlPullParserException( "expected START_TAG or END_TAG not " + XmlPullParser.TYPES[eventType], parser, null );
        }
        return eventType;
    } //-- int nextTag( XmlPullParser )

    /**
     * @see ReaderFactory#newXmlReader
     * 
     * @param reader
     * @param strict
     * @throws IOException
     * @throws XmlPullParserException
     * @return LinkcheckModel
     */
    public LinkcheckModel read( Reader reader, boolean strict )
        throws IOException, XmlPullParserException
    {
        XmlPullParser parser = addDefaultEntities ? new MXParser(EntityReplacementMap.defaultEntityReplacementMap) : new MXParser( );

        parser.setInput( reader );


        return read( parser, strict );
    } //-- LinkcheckModel read( Reader, boolean )

    /**
     * @see ReaderFactory#newXmlReader
     * 
     * @param reader
     * @throws IOException
     * @throws XmlPullParserException
     * @return LinkcheckModel
     */
    public LinkcheckModel read( Reader reader )
        throws IOException, XmlPullParserException
    {
        return read( reader, true );
    } //-- LinkcheckModel read( Reader )

    /**
     * Method read.
     * 
     * @param in
     * @param strict
     * @throws IOException
     * @throws XmlPullParserException
     * @return LinkcheckModel
     */
    public LinkcheckModel read( InputStream in, boolean strict )
        throws IOException, XmlPullParserException
    {
        return read( ReaderFactory.newXmlReader( in ), strict );
    } //-- LinkcheckModel read( InputStream, boolean )

    /**
     * Method read.
     * 
     * @param in
     * @throws IOException
     * @throws XmlPullParserException
     * @return LinkcheckModel
     */
    public LinkcheckModel read( InputStream in )
        throws IOException, XmlPullParserException
    {
        return read( ReaderFactory.newXmlReader( in ) );
    } //-- LinkcheckModel read( InputStream )

    /**
     * Method parseLinkcheckFile.
     * 
     * @param parser
     * @param strict
     * @throws IOException
     * @throws XmlPullParserException
     * @return LinkcheckFile
     */
    private LinkcheckFile parseLinkcheckFile( XmlPullParser parser, boolean strict )
        throws IOException, XmlPullParserException
    {
        String tagName = parser.getName();
        LinkcheckFile linkcheckFile = new LinkcheckFile();
        for ( int i = parser.getAttributeCount() - 1; i >= 0; i-- )
        {
            String name = parser.getAttributeName( i );
            String value = parser.getAttributeValue( i );

            if ( name.indexOf( ':' ) >= 0 )
            {
                // just ignore attributes with non-default namespace (for example: xmlns:xsi)
            }
            else
            {
                checkUnknownAttribute( parser, name, tagName, strict );
            }
        }
        java.util.Set parsed = new java.util.HashSet();
        while ( ( strict ? parser.nextTag() : nextTag( parser ) ) == XmlPullParser.START_TAG )
        {
            if ( checkFieldWithDuplicate( parser, "absolutePath", null, parsed ) )
            {
                linkcheckFile.setAbsolutePath( interpolatedTrimmed( parser.nextText(), "absolutePath" ) );
            }
            else if ( checkFieldWithDuplicate( parser, "relativePath", null, parsed ) )
            {
                linkcheckFile.setRelativePath( interpolatedTrimmed( parser.nextText(), "relativePath" ) );
            }
            else if ( checkFieldWithDuplicate( parser, "successful", null, parsed ) )
            {
                linkcheckFile.setSuccessful( getIntegerValue( interpolatedTrimmed( parser.nextText(), "successful" ), "successful", parser, strict ) );
            }
            else if ( checkFieldWithDuplicate( parser, "unsuccessful", null, parsed ) )
            {
                linkcheckFile.setUnsuccessful( getIntegerValue( interpolatedTrimmed( parser.nextText(), "unsuccessful" ), "unsuccessful", parser, strict ) );
            }
            else if ( checkFieldWithDuplicate( parser, "results", null, parsed ) )
            {
                java.util.List<LinkcheckFileResult> results = new java.util.ArrayList<LinkcheckFileResult>();
                linkcheckFile.setResults( results );
                while ( parser.nextTag() == XmlPullParser.START_TAG )
                {
                    if ( "result".equals( parser.getName() ) )
                    {
                        results.add( parseLinkcheckFileResult( parser, strict ) );
                    }
                    else
                    {
                        checkUnknownElement( parser, strict );
                    }
                }
            }
            else
            {
                checkUnknownElement( parser, strict );
            }
        }
        return linkcheckFile;
    } //-- LinkcheckFile parseLinkcheckFile( XmlPullParser, boolean )

    /**
     * Method parseLinkcheckFileResult.
     * 
     * @param parser
     * @param strict
     * @throws IOException
     * @throws XmlPullParserException
     * @return LinkcheckFileResult
     */
    private LinkcheckFileResult parseLinkcheckFileResult( XmlPullParser parser, boolean strict )
        throws IOException, XmlPullParserException
    {
        String tagName = parser.getName();
        LinkcheckFileResult linkcheckFileResult = new LinkcheckFileResult();
        for ( int i = parser.getAttributeCount() - 1; i >= 0; i-- )
        {
            String name = parser.getAttributeName( i );
            String value = parser.getAttributeValue( i );

            if ( name.indexOf( ':' ) >= 0 )
            {
                // just ignore attributes with non-default namespace (for example: xmlns:xsi)
            }
            else
            {
                checkUnknownAttribute( parser, name, tagName, strict );
            }
        }
        java.util.Set parsed = new java.util.HashSet();
        while ( ( strict ? parser.nextTag() : nextTag( parser ) ) == XmlPullParser.START_TAG )
        {
            if ( checkFieldWithDuplicate( parser, "target", null, parsed ) )
            {
                linkcheckFileResult.setTarget( interpolatedTrimmed( parser.nextText(), "target" ) );
            }
            else if ( checkFieldWithDuplicate( parser, "status", null, parsed ) )
            {
                linkcheckFileResult.setStatus( interpolatedTrimmed( parser.nextText(), "status" ) );
            }
            else if ( checkFieldWithDuplicate( parser, "errorMessage", null, parsed ) )
            {
                linkcheckFileResult.setErrorMessage( interpolatedTrimmed( parser.nextText(), "errorMessage" ) );
            }
            else
            {
                checkUnknownElement( parser, strict );
            }
        }
        return linkcheckFileResult;
    } //-- LinkcheckFileResult parseLinkcheckFileResult( XmlPullParser, boolean )

    /**
     * Method parseLinkcheckModel.
     * 
     * @param parser
     * @param strict
     * @throws IOException
     * @throws XmlPullParserException
     * @return LinkcheckModel
     */
    private LinkcheckModel parseLinkcheckModel( XmlPullParser parser, boolean strict )
        throws IOException, XmlPullParserException
    {
        String tagName = parser.getName();
        LinkcheckModel linkcheckModel = new LinkcheckModel();
        for ( int i = parser.getAttributeCount() - 1; i >= 0; i-- )
        {
            String name = parser.getAttributeName( i );
            String value = parser.getAttributeValue( i );

            if ( name.indexOf( ':' ) >= 0 )
            {
                // just ignore attributes with non-default namespace (for example: xmlns:xsi)
            }
            else if ( "xmlns".equals( name ) )
            {
                // ignore xmlns attribute in root class, which is a reserved attribute name
            }
            else
            {
                checkUnknownAttribute( parser, name, tagName, strict );
            }
        }
        java.util.Set parsed = new java.util.HashSet();
        while ( ( strict ? parser.nextTag() : nextTag( parser ) ) == XmlPullParser.START_TAG )
        {
            if ( checkFieldWithDuplicate( parser, "files", null, parsed ) )
            {
                java.util.List<LinkcheckFile> files = new java.util.ArrayList<LinkcheckFile>();
                linkcheckModel.setFiles( files );
                while ( parser.nextTag() == XmlPullParser.START_TAG )
                {
                    if ( "file".equals( parser.getName() ) )
                    {
                        files.add( parseLinkcheckFile( parser, strict ) );
                    }
                    else
                    {
                        checkUnknownElement( parser, strict );
                    }
                }
            }
            else
            {
                checkUnknownElement( parser, strict );
            }
        }
        return linkcheckModel;
    } //-- LinkcheckModel parseLinkcheckModel( XmlPullParser, boolean )

    /**
     * Method read.
     * 
     * @param parser
     * @param strict
     * @throws IOException
     * @throws XmlPullParserException
     * @return LinkcheckModel
     */
    private LinkcheckModel read( XmlPullParser parser, boolean strict )
        throws IOException, XmlPullParserException
    {
        LinkcheckModel linkcheckModel = null;
        int eventType = parser.getEventType();
        boolean parsed = false;
        while ( eventType != XmlPullParser.END_DOCUMENT )
        {
            if ( eventType == XmlPullParser.START_TAG )
            {
                if ( strict && ! "linkcheckModel".equals( parser.getName() ) )
                {
                    throw new XmlPullParserException( "Expected root element 'linkcheckModel' but found '" + parser.getName() + "'", parser, null );
                }
                else if ( parsed )
                {
                    // fallback, already expected a XmlPullParserException due to invalid XML
                    throw new XmlPullParserException( "Duplicated tag: 'linkcheckModel'", parser, null );
                }
                linkcheckModel = parseLinkcheckModel( parser, strict );
                linkcheckModel.setModelEncoding( parser.getInputEncoding() );
                parsed = true;
            }
            eventType = parser.next();
        }
        if ( parsed )
        {
            return linkcheckModel;
        }
        throw new XmlPullParserException( "Expected root element 'linkcheckModel' but found no element at all: invalid XML document", parser, null );
    } //-- LinkcheckModel read( XmlPullParser, boolean )

    /**
     * Sets the state of the "add default entities" flag.
     * 
     * @param addDefaultEntities
     */
    public void setAddDefaultEntities( boolean addDefaultEntities )
    {
        this.addDefaultEntities = addDefaultEntities;
    } //-- void setAddDefaultEntities( boolean )

    public static interface ContentTransformer
{
    /**
     * Interpolate the value read from the xpp3 document
     * @param source The source value
     * @param fieldName A description of the field being interpolated. The implementation may use this to
     *                           log stuff.
     * @return The interpolated value.
     */
    String transform( String source, String fieldName );
}

}
