package com.uugty.app.constant;

/**
 * 
 * @ClassName: HttpHeader
 * @Description: http协议的请求头
 * @author ganliang
 * @date 2015年6月9日 下午3:15:40
 */
public interface HttpHeader {
	/**
	 * See
	 * {@link <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.1">HTTP/1.1 documentation</a>}
	 * .
	 */
	public static final String ACCEPT = "Accept";
	/**
	 * See
	 * {@link <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.2">HTTP/1.1 documentation</a>}
	 * .
	 */
	public static final String ACCEPT_CHARSET = "Accept-Charset";
	/**
	 * See
	 * {@link <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.3">HTTP/1.1 documentation</a>}
	 * .
	 */
	public static final String ACCEPT_ENCODING = "Accept-Encoding";
	/**
	 * See
	 * {@link <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.4">HTTP/1.1 documentation</a>}
	 * .
	 */
	public static final String ACCEPT_LANGUAGE = "Accept-Language";
	/**
	 * See
	 * {@link <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.7">HTTP/1.1 documentation</a>}
	 * .
	 */
	public static final String ALLOW = "Allow";
	/**
	 * See
	 * {@link <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.8">HTTP/1.1 documentation</a>}
	 * .
	 */
	public static final String AUTHORIZATION = "Authorization";
	/**
	 * See
	 * {@link <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9">HTTP/1.1 documentation</a>}
	 * .
	 */
	public static final String CACHE_CONTROL = "Cache-Control";
	/**
	 * See {@link <a href="http://tools.ietf.org/html/rfc2183">IETF RFC-2183</a>}
	 * .
	 */
	public static final String CONTENT_DISPOSITION = "Content-Disposition";
	/**
	 * See
	 * {@link <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.11">HTTP/1.1 documentation</a>}
	 * .
	 */
	public static final String CONTENT_ENCODING = "Content-Encoding";
	/**
	 * See {@link <a href="http://tools.ietf.org/html/rfc2392">IETF RFC-2392</a>}
	 * .
	 */
	public static final String CONTENT_ID = "Content-ID";
	/**
	 * See
	 * {@link <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.12">HTTP/1.1 documentation</a>}
	 * .
	 */
	public static final String CONTENT_LANGUAGE = "Content-Language";
	/**
	 * See
	 * {@link <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.13">HTTP/1.1 documentation</a>}
	 * .
	 */
	public static final String CONTENT_LENGTH = "Content-Length";
	/**
	 * See
	 * {@link <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.14">HTTP/1.1 documentation</a>}
	 * .
	 */
	public static final String CONTENT_LOCATION = "Content-Location";
	/**
	 * See
	 * {@link <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.17">HTTP/1.1 documentation</a>}
	 * .
	 */
	public static final String CONTENT_TYPE = "Content-Type";
	/**
	 * See
	 * {@link <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.18">HTTP/1.1 documentation</a>}
	 * .
	 */
	public static final String DATE = "Date";
	/**
	 * See
	 * {@link <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.19">HTTP/1.1 documentation</a>}
	 * .
	 */
	public static final String ETAG = "ETag";
	/**
	 * See
	 * {@link <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.21">HTTP/1.1 documentation</a>}
	 * .
	 */
	public static final String EXPIRES = "Expires";
	/**
	 * See
	 * {@link <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.23">HTTP/1.1 documentation</a>}
	 * .
	 */
	public static final String HOST = "Host";
	/**
	 * See
	 * {@link <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.24">HTTP/1.1 documentation</a>}
	 * .
	 */
	public static final String IF_MATCH = "If-Match";
	/**
	 * See
	 * {@link <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.25">HTTP/1.1 documentation</a>}
	 * .
	 */
	public static final String IF_MODIFIED_SINCE = "If-Modified-Since";
	/**
	 * See
	 * {@link <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.26">HTTP/1.1 documentation</a>}
	 * .
	 */
	public static final String IF_NONE_MATCH = "If-None-Match";
	/**
	 * See
	 * {@link <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.28">HTTP/1.1 documentation</a>}
	 * .
	 */
	public static final String IF_UNMODIFIED_SINCE = "If-Unmodified-Since";
	/**
	 * See
	 * {@link <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.29">HTTP/1.1 documentation</a>}
	 * .
	 */
	public static final String LAST_MODIFIED = "Last-Modified";
	/**
	 * See
	 * {@link <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.30">HTTP/1.1 documentation</a>}
	 * .
	 */
	public static final String LOCATION = "Location";
	/**
	 * See
	 * {@link <a href="http://tools.ietf.org/html/rfc5988#page-6">Web Linking (IETF RFC-5988) documentation</a>}
	 * .
	 */
	public static final String LINK = "Link";
	/**
	 * See
	 * {@link <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.37">HTTP/1.1 documentation</a>}
	 * .
	 */
	public static final String RETRY_AFTER = "Retry-After";
	/**
	 * See
	 * {@link <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.43">HTTP/1.1 documentation</a>}
	 * .
	 */
	public static final String USER_AGENT = "User-Agent";
	/**
	 * See
	 * {@link <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.44">HTTP/1.1 documentation</a>}
	 * .
	 */
	public static final String VARY = "Vary";
	/**
	 * See
	 * {@link <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.47">HTTP/1.1 documentation</a>}
	 * .
	 */
	public static final String WWW_AUTHENTICATE = "WWW-Authenticate";
	/**
	 * See {@link <a href="http://www.ietf.org/rfc/rfc2109.txt">IETF RFC 2109</a>}
	 * .
	 */
	public static final String COOKIE = "Cookie";
	/**
	 * See {@link <a href="http://www.ietf.org/rfc/rfc2109.txt">IETF RFC 2109</a>}
	 * .
	 */
	public static final String SET_COOKIE = "Set-Cookie";
}
