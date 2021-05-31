package org.apache.maven.doxia.linkcheck;

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
 * Http bean to encapsulate the supported HTTP parameters.
 *          @see org.apache.commons.httpclient.HttpMethod.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings( "all" )
public class HttpBean
    implements java.io.Serializable
{

      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * 
     *      The HTTP method to use. Currently supported are "GET"
     * and "HEAD".
     *      <dl>
     *      <dt>HTTP GET</dt>
     *      <dd>
     *      The HTTP GET method is defined in section 9.3 of
     *      <a
     * HREF="http://www.ietf.org/rfc/rfc2616.txt">RFC2616</a>:
     *      <blockquote>
     *      The GET method means retrieve whatever information (in
     * the form of an
     *      entity) is identified by the Request-URI.
     *      </blockquote>
     *      </dd>
     *      <dt>HTTP HEAD</dt>
     *      <dd>
     *      The HTTP HEAD method is defined in section 9.4 of
     *      <a
     * HREF="http://www.ietf.org/rfc/rfc2616.txt">RFC2616</a>:
     *      <blockquote>
     *      The HEAD method is identical to GET except that the
     * server MUST NOT
     *      return a message-body in the response.
     *      </blockquote>
     *      </dd>
     *      </dl>
     */
    private String method = "head";

    /**
     * 
     *      if the HTTP method should automatically follow HTTP
     * redirects
     *      (status code 302, etc.), <tt>false</tt> otherwise.
     */
    private boolean followRedirects = false;

    /**
     * The proxy host.
     */
    private String proxyHost;

    /**
     * The proxy port.
     */
    private int proxyPort = 0;

    /**
     * The proxy user.
     */
    private String proxyUser;

    /**
     * The proxy password.
     */
    private String proxyPassword;

    /**
     * The proxy NTLM (NT Lan Manager) host.
     */
    private String proxyNtlmHost;

    /**
     * The proxy NTLM (NT Lan Manager) domain.
     */
    private String proxyNtlmDomain;

    /**
     * The timeout to be used. A value of zero means the timeout is
     * not used.
     *             Default value is 2000.
     */
    private int timeout = 2000;


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

        if ( !( other instanceof HttpBean ) )
        {
            return false;
        }

        HttpBean that = (HttpBean) other;
        boolean result = true;

        result = result && ( getMethod() == null ? that.getMethod() == null : getMethod().equals( that.getMethod() ) );
        result = result && followRedirects == that.followRedirects;
        result = result && ( getProxyHost() == null ? that.getProxyHost() == null : getProxyHost().equals( that.getProxyHost() ) );
        result = result && proxyPort == that.proxyPort;
        result = result && ( getProxyUser() == null ? that.getProxyUser() == null : getProxyUser().equals( that.getProxyUser() ) );
        result = result && ( getProxyPassword() == null ? that.getProxyPassword() == null : getProxyPassword().equals( that.getProxyPassword() ) );
        result = result && ( getProxyNtlmHost() == null ? that.getProxyNtlmHost() == null : getProxyNtlmHost().equals( that.getProxyNtlmHost() ) );
        result = result && ( getProxyNtlmDomain() == null ? that.getProxyNtlmDomain() == null : getProxyNtlmDomain().equals( that.getProxyNtlmDomain() ) );
        result = result && timeout == that.timeout;

        return result;
    } //-- boolean equals( Object )

    /**
     * Get the HTTP method to use. Currently supported are "GET"
     * and "HEAD".
     *      <dl>
     *      <dt>HTTP GET</dt>
     *      <dd>
     *      The HTTP GET method is defined in section 9.3 of
     *      <a
     * HREF="http://www.ietf.org/rfc/rfc2616.txt">RFC2616</a>:
     *      <blockquote>
     *      The GET method means retrieve whatever information (in
     * the form of an
     *      entity) is identified by the Request-URI.
     *      </blockquote>
     *      </dd>
     *      <dt>HTTP HEAD</dt>
     *      <dd>
     *      The HTTP HEAD method is defined in section 9.4 of
     *      <a
     * HREF="http://www.ietf.org/rfc/rfc2616.txt">RFC2616</a>:
     *      <blockquote>
     *      The HEAD method is identical to GET except that the
     * server MUST NOT
     *      return a message-body in the response.
     *      </blockquote>
     *      </dd>
     *      </dl>
     * 
     * @return String
     */
    public String getMethod()
    {
        return this.method;
    } //-- String getMethod()

    /**
     * Get the proxy host.
     * 
     * @return String
     */
    public String getProxyHost()
    {
        return this.proxyHost;
    } //-- String getProxyHost()

    /**
     * Get the proxy NTLM (NT Lan Manager) domain.
     * 
     * @return String
     */
    public String getProxyNtlmDomain()
    {
        return this.proxyNtlmDomain;
    } //-- String getProxyNtlmDomain()

    /**
     * Get the proxy NTLM (NT Lan Manager) host.
     * 
     * @return String
     */
    public String getProxyNtlmHost()
    {
        return this.proxyNtlmHost;
    } //-- String getProxyNtlmHost()

    /**
     * Get the proxy password.
     * 
     * @return String
     */
    public String getProxyPassword()
    {
        return this.proxyPassword;
    } //-- String getProxyPassword()

    /**
     * Get the proxy port.
     * 
     * @return int
     */
    public int getProxyPort()
    {
        return this.proxyPort;
    } //-- int getProxyPort()

    /**
     * Get the proxy user.
     * 
     * @return String
     */
    public String getProxyUser()
    {
        return this.proxyUser;
    } //-- String getProxyUser()

    /**
     * Get the timeout to be used. A value of zero means the
     * timeout is not used.
     *             Default value is 2000.
     * 
     * @return int
     */
    public int getTimeout()
    {
        return this.timeout;
    } //-- int getTimeout()

    /**
     * Method hashCode.
     * 
     * @return int
     */
    public int hashCode()
    {
        int result = 17;

        result = 37 * result + ( method != null ? method.hashCode() : 0 );
        result = 37 * result + ( followRedirects ? 0 : 1 );
        result = 37 * result + ( proxyHost != null ? proxyHost.hashCode() : 0 );
        result = 37 * result + (int) proxyPort;
        result = 37 * result + ( proxyUser != null ? proxyUser.hashCode() : 0 );
        result = 37 * result + ( proxyPassword != null ? proxyPassword.hashCode() : 0 );
        result = 37 * result + ( proxyNtlmHost != null ? proxyNtlmHost.hashCode() : 0 );
        result = 37 * result + ( proxyNtlmDomain != null ? proxyNtlmDomain.hashCode() : 0 );
        result = 37 * result + (int) timeout;

        return result;
    } //-- int hashCode()

    /**
     * Get if the HTTP method should automatically follow HTTP
     * redirects
     *      (status code 302, etc.), <tt>false</tt> otherwise.
     * 
     * @return boolean
     */
    public boolean isFollowRedirects()
    {
        return this.followRedirects;
    } //-- boolean isFollowRedirects()

    /**
     * Set if the HTTP method should automatically follow HTTP
     * redirects
     *      (status code 302, etc.), <tt>false</tt> otherwise.
     * 
     * @param followRedirects
     */
    public void setFollowRedirects( boolean followRedirects )
    {
        this.followRedirects = followRedirects;
    } //-- void setFollowRedirects( boolean )

    /**
     * Set the HTTP method to use. Currently supported are "GET"
     * and "HEAD".
     *      <dl>
     *      <dt>HTTP GET</dt>
     *      <dd>
     *      The HTTP GET method is defined in section 9.3 of
     *      <a
     * HREF="http://www.ietf.org/rfc/rfc2616.txt">RFC2616</a>:
     *      <blockquote>
     *      The GET method means retrieve whatever information (in
     * the form of an
     *      entity) is identified by the Request-URI.
     *      </blockquote>
     *      </dd>
     *      <dt>HTTP HEAD</dt>
     *      <dd>
     *      The HTTP HEAD method is defined in section 9.4 of
     *      <a
     * HREF="http://www.ietf.org/rfc/rfc2616.txt">RFC2616</a>:
     *      <blockquote>
     *      The HEAD method is identical to GET except that the
     * server MUST NOT
     *      return a message-body in the response.
     *      </blockquote>
     *      </dd>
     *      </dl>
     * 
     * @param method
     */
    public void setMethod( String method )
    {
        this.method = method;
    } //-- void setMethod( String )

    /**
     * Set the proxy host.
     * 
     * @param proxyHost
     */
    public void setProxyHost( String proxyHost )
    {
        this.proxyHost = proxyHost;
    } //-- void setProxyHost( String )

    /**
     * Set the proxy NTLM (NT Lan Manager) domain.
     * 
     * @param proxyNtlmDomain
     */
    public void setProxyNtlmDomain( String proxyNtlmDomain )
    {
        this.proxyNtlmDomain = proxyNtlmDomain;
    } //-- void setProxyNtlmDomain( String )

    /**
     * Set the proxy NTLM (NT Lan Manager) host.
     * 
     * @param proxyNtlmHost
     */
    public void setProxyNtlmHost( String proxyNtlmHost )
    {
        this.proxyNtlmHost = proxyNtlmHost;
    } //-- void setProxyNtlmHost( String )

    /**
     * Set the proxy password.
     * 
     * @param proxyPassword
     */
    public void setProxyPassword( String proxyPassword )
    {
        this.proxyPassword = proxyPassword;
    } //-- void setProxyPassword( String )

    /**
     * Set the proxy port.
     * 
     * @param proxyPort
     */
    public void setProxyPort( int proxyPort )
    {
        this.proxyPort = proxyPort;
    } //-- void setProxyPort( int )

    /**
     * Set the proxy user.
     * 
     * @param proxyUser
     */
    public void setProxyUser( String proxyUser )
    {
        this.proxyUser = proxyUser;
    } //-- void setProxyUser( String )

    /**
     * Method toString.
     * 
     * @return String
     */
    public java.lang.String toString()
    {
        StringBuilder buf = new StringBuilder( 128 );

        buf.append( "method = '" );
        buf.append( getMethod() );
        buf.append( "'" );
        buf.append( "\n" ); 
        buf.append( "followRedirects = '" );
        buf.append( isFollowRedirects() );
        buf.append( "'" );
        buf.append( "\n" ); 
        buf.append( "proxyHost = '" );
        buf.append( getProxyHost() );
        buf.append( "'" );
        buf.append( "\n" ); 
        buf.append( "proxyPort = '" );
        buf.append( getProxyPort() );
        buf.append( "'" );
        buf.append( "\n" ); 
        buf.append( "proxyUser = '" );
        buf.append( getProxyUser() );
        buf.append( "'" );
        buf.append( "\n" ); 
        buf.append( "proxyPassword = '" );
        buf.append( getProxyPassword() );
        buf.append( "'" );
        buf.append( "\n" ); 
        buf.append( "proxyNtlmHost = '" );
        buf.append( getProxyNtlmHost() );
        buf.append( "'" );
        buf.append( "\n" ); 
        buf.append( "proxyNtlmDomain = '" );
        buf.append( getProxyNtlmDomain() );
        buf.append( "'" );
        buf.append( "\n" ); 
        buf.append( "timeout = '" );
        buf.append( getTimeout() );
        buf.append( "'" );

        return buf.toString();
    } //-- java.lang.String toString()

    
          
    /**
     * Set the timeout to be used. A value of zero means the timeout is not used.
     *
     * @param timeout positive int
     */
    public void setTimeout( int timeout )
    {
        if ( timeout < 0 )
        {
            throw new IllegalArgumentException( timeout + " should be 0 or positive." );
        }
        this.timeout = timeout;
    }

    private java.util.Properties httpClientParameters;

    /**
     * @return the extra HttpClient parameters
     * @see http://hc.apache.org/httpclient-3.x/preference-api.html
     */
    public java.util.Properties getHttpClientParameters()
    {
        return httpClientParameters;
    }

    /**
     * @param httpClientParameters the extra HttpClient parameters to set
     * @see http://hc.apache.org/httpclient-3.x/preference-api.html
     */
    public void setHttpClientParameters( java.util.Properties httpClientParameters )
    {
        this.httpClientParameters = httpClientParameters;
    }
          
}
