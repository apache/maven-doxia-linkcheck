package org.apache.maven.doxia.linkcheck.validation;

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

import java.io.IOException;

import java.net.URL;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.hc.client5.http.auth.Credentials;
import org.apache.hc.client5.http.auth.NTCredentials;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.classic.HttpClient;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpHead;
import org.apache.hc.client5.http.classic.methods.HttpUriRequest;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.maven.doxia.linkcheck.HttpBean;
import org.apache.maven.doxia.linkcheck.model.LinkcheckFileResult;
import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Checks links which are normal URLs.
 *
 * @author <a href="mailto:bwalding@apache.org">Ben Walding</a>
 * @author <a href="mailto:aheritier@apache.org">Arnaud Heritier</a>
 * @author <a href="mailto:vincent.siveton@gmail.com">Vincent Siveton</a>
 */
public final class OnlineHTTPLinkValidator
    extends HTTPLinkValidator
{
    /** Log for debug output. */
    private static final Logger LOG = LoggerFactory.getLogger( OnlineHTTPLinkValidator.class );

    /** The maximum number of redirections for a link. */
    private static final int MAX_NB_REDIRECT = 10;

    /** The http bean encapsulating all http parameters supported. */
    private HttpBean http;

    /** The base URL for links that start with '/'. */
    private String baseURL;

    /** The HttpClient. */
    private transient HttpClient cl;

    /**
     * Constructor: initialize settings, use HEAD method.
     */
    public OnlineHTTPLinkValidator()
    {
        this( new HttpBean() );
    }

    /**
     * Constructor: initialize settings.
     *
     * @param bean the http bean encapsulating all HTTP parameters supported
     */
    public OnlineHTTPLinkValidator( HttpBean bean )
    {
        if ( bean == null )
        {
            bean = new HttpBean();
        }

        LOG.debug( "Using method : [" + bean.getMethod() + "]" );

        this.http = bean;

        initHttpClient();
    }

    /**
     * The base URL.
     *
     * @return the base URL
     */
    public String getBaseURL()
    {
        return this.baseURL;
    }

    /**
     * Sets the base URL. This is pre-pended to links that start with '/'.
     *
     * @param url the base URL
     */
    public void setBaseURL( String url )
    {
        this.baseURL = url;
    }

    /** {@inheritDoc} */
    @Override
    public LinkValidationResult validateLink( LinkValidationItem lvi )
    {
        if ( this.cl == null )
        {
            initHttpClient();
        }

        if ( this.http.getHttpClientParameters() != null )
        {
            for ( Map.Entry<Object, Object> entry : this.http.getHttpClientParameters().entrySet() )
            {
                if ( entry.getValue() != null )
                {
                    System.setProperty( entry.getKey().toString(), entry.getValue().toString() );
                }
            }
        }

        String link = lvi.getLink();
        String anchor = "";
        int idx = link.indexOf( '#' );
        if ( idx != -1 )
        {
            anchor = link.substring( idx + 1 );
            link = link.substring( 0, idx );
        }

        try
        {
            if ( link.startsWith( "/" ) )
            {
                if ( getBaseURL() == null )
                {
                    LOG.warn( "Cannot check link [" + link + "] in page [" + lvi.getSource()
                            + "], as no base URL has been set!" );

                    return new LinkValidationResult( LinkcheckFileResult.WARNING_LEVEL, false,
                                                     "No base URL specified" );
                }

                link = getBaseURL() + link;
            }

            ClassicHttpResponse response;
            try
            {
                response = checkLink( link, 0 );
            }
            catch ( IOException | HttpException ex )
            {
                LOG.debug( "Received: [" + ex + "] for [" + link + "] in page [" + lvi.getSource() + "]", ex );

                return new LinkValidationResult( LinkcheckFileResult.ERROR_LEVEL, false, ex.getClass().getName()
                    + " : " + ex.getMessage() );
            }

            if ( response == null )
            {
                return new LinkValidationResult( LinkcheckFileResult.ERROR_LEVEL, false,
                                                 "Cannot retrieve HTTP Status" );
            }

            int statusCode = response.getCode();
            if ( statusCode == HttpStatus.SC_OK )
            {
                // check if the anchor is present
                if ( anchor.length() > 0 )
                {
                    String content = EntityUtils.toString( response.getEntity() );

                    if ( !Anchors.matchesAnchor( content, anchor ) )
                    {
                        return new HTTPLinkValidationResult( LinkcheckFileResult.VALID_LEVEL, false,
                            "Missing anchor '" + anchor + "'" );
                    }
                }
                return new HTTPLinkValidationResult( LinkcheckFileResult.VALID_LEVEL, true,
                        statusCode, response.getReasonPhrase() );
            }

            String msg = "Received: [" + statusCode + "] for [" + link + "] in page ["
                    + lvi.getSource() + "]";
            // If there's a redirection, add a warning
            if ( statusCode == HttpStatus.SC_MOVED_PERMANENTLY
                || statusCode == HttpStatus.SC_MOVED_TEMPORARILY
                || statusCode == HttpStatus.SC_TEMPORARY_REDIRECT )
            {
                LOG.warn( msg );

                return new HTTPLinkValidationResult( LinkcheckFileResult.WARNING_LEVEL, true, statusCode,
                        response.getReasonPhrase() );
            }

            LOG.debug( msg );

            return new HTTPLinkValidationResult( LinkcheckFileResult.ERROR_LEVEL, false, statusCode,
                    response.getReasonPhrase() );
        }
        catch ( IOException | ParseException ex )
        {
            String msg = "Received: [" + ex + "] for [" + link + "] in page [" + lvi.getSource() + "]";
            LOG.error( msg, ex );

            return new LinkValidationResult( LinkcheckFileResult.ERROR_LEVEL, false, ex.getMessage() );
        }
        finally
        {
            if ( this.http.getHttpClientParameters() != null )
            {
                for ( Map.Entry<Object, Object> entry : this.http.getHttpClientParameters().entrySet() )
                {
                    if ( entry.getValue() != null )
                    {
                        System.getProperties().remove( entry.getKey().toString() );
                    }
                }
            }
        }
    }

    /** Initialize the HttpClient. */
    private void initHttpClient()
    {
        LOG.debug( "Creating a new HttpClient instance." );

        HttpClientBuilder builder = HttpClients.custom();

        // connection manager
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal( 100 );
        connectionManager.setDefaultMaxPerRoute( 10 );
        builder.setConnectionManager( connectionManager );

        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
        // redirects
        requestConfigBuilder.setRedirectsEnabled( this.http.isFollowRedirects() );

        // timeouts
        SocketConfig.Builder socketConfigBuilder = SocketConfig.custom();
        if ( this.http.getTimeout() != 0 )
        {
            requestConfigBuilder.setConnectTimeout( this.http.getTimeout(), TimeUnit.MILLISECONDS );
//            requestConfigBuilder.setSocketTimeout( this.http.getTimeout() );
            socketConfigBuilder.setSoTimeout( this.http.getTimeout(), TimeUnit.MILLISECONDS );
        }

        builder.setDefaultRequestConfig( requestConfigBuilder.build() );
//        builder.setDefaultSocketConfig( socketConfigBuilder.build() );

        // proxy
        if ( StringUtils.isNotBlank( this.http.getProxyHost() ) )
        {
            HttpHost proxy = new HttpHost( this.http.getProxyHost(), this.http.getProxyPort() );
            requestConfigBuilder.setProxy( proxy );
            LOG.debug( "Proxy Host:" + this.http.getProxyHost() );
            LOG.debug( "Proxy Port:" + this.http.getProxyPort() );

            if ( StringUtils.isNotEmpty( this.http.getProxyUser() ) && this.http.getProxyPassword() != null )
            {
                LOG.debug( "Proxy User:" + this.http.getProxyUser() );

                Credentials credentials;
                if ( StringUtils.isNotEmpty( this.http.getProxyNtlmHost() ) )
                {
                    credentials = new NTCredentials( this.http.getProxyUser(),
                            this.http.getProxyPassword().toCharArray(),
                            this.http.getProxyNtlmHost(), this.http.getProxyNtlmDomain() );
                }
                else
                {
                    credentials =
                        new UsernamePasswordCredentials( this.http.getProxyUser(),
                                this.http.getProxyPassword().toCharArray() );
                }
                BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                credentialsProvider.setCredentials( null, credentials );
                builder.setDefaultCredentialsProvider( credentialsProvider );
            }
        }

        this.cl = builder.build();

        LOG.debug( "New HttpClient instance created." );
    }

    /**
     * Checks the given link.
     *
     * @param url the link to check
     * @param nbRedirect the number of current redirects
     * @throws IOException if something goes wrong
     */
    private ClassicHttpResponse checkLink( String url, int nbRedirect )
            throws IOException, HttpException
    {

        // check if we've redirected too many times
        int max = MAX_NB_REDIRECT;
        if ( this.http.getHttpClientParameters() != null
            && this.http.getHttpClientParameters().get( "http.protocol.max-redirects" ) != null )
        {
            try
            {
                max = Integer.parseInt(
                        this.http.getHttpClientParameters().get( "http.protocol.max-redirects" ).toString() );
            }
            catch ( NumberFormatException e )
            {
                LOG.warn( "HttpClient parameter '" + "http.protocol.max-redirects"
                        + "' is not a number. Ignoring!" );
            }
        }
        if ( nbRedirect > max )
        {
            throw new HttpException( "Maximum number of redirections (" + max + ") exceeded" );
        }

        HttpUriRequest request;
        try
        {
            if ( "HEAD".equalsIgnoreCase( this.http.getMethod() ) )
            {
                request = new HttpHead( url );
            }
            else if ( "GET".equalsIgnoreCase( this.http.getMethod() ) )
            {
                request = new HttpGet( url );
            }
            else
            {
                LOG.error( "Unsupported method: " + this.http.getMethod() + ", using 'get'." );
                request = new HttpGet( url );
            }
        }
        catch ( IllegalArgumentException ex )
        {
            throw new HttpException( "Invalid URL " + url, ex );
        }

        ClassicHttpResponse response = (ClassicHttpResponse) cl.execute( request );

        int statusCode = response.getCode();
        if ( statusCode == HttpStatus.SC_MOVED_PERMANENTLY
                || statusCode == HttpStatus.SC_MOVED_TEMPORARILY
                || statusCode == HttpStatus.SC_TEMPORARY_REDIRECT )
        {
            Header[] locationHeader = response.getHeaders( "location" );

            if ( locationHeader.length == 0 )
            {
                LOG.error( "Site sent redirect, but did not set Location header" );
                return response;
            }

            String newLink = locationHeader[0].getValue();

            // Be careful to absolute/relative links
            if ( !newLink.startsWith( "http://" ) && !newLink.startsWith( "https://" ) )
            {
                if ( newLink.startsWith( "/" ) )
                {
                    URL oldUrl = new URL( url );
                    newLink = oldUrl.getProtocol() + "://" + oldUrl.getHost()
                            + ( oldUrl.getPort() > 0 ? ":" + oldUrl.getPort() : "" ) + newLink;
                }
                else
                {
                    newLink = url + newLink;
                }
            }

            LOG.debug( "[" + url + "] is redirected to [" + newLink + "]" );

            ClassicHttpResponse oldResponse = response;
            response = checkLink( newLink, nbRedirect + 1 );

            // Restore the status to "Moved permanently" | "Moved temporarily" | "Temporary redirect"
            // if the new location is found to allow us to report it
            if ( statusCode == HttpStatus.SC_OK && nbRedirect == 0 )
            {
                return oldResponse;
            }
        }
        return response;
    }
}
