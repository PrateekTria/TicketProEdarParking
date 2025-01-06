package com.ticketpro.vendors;

import android.util.Log;

import com.ticketpro.util.TLSSocketFactory;
import com.ticketpro.util.TPUtility;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import static com.ticketpro.util.TPUtility.getNetworkTimeout;

public class IPSQuery {
	private static Logger log;

	public static String getMeterStatus(String URL, String token, String meterNumber) {
		String meterReqeust = "<Request xmlns=\"http://www2.ipsmetersystems.com/meter\"><MeterNumber>" + meterNumber
				+ "</MeterNumber>" + "</Request>";

		return callService(meterReqeust, token, URL);
	}

	public static String getSpaceStatus(String URL, String token, String SpaceGroup, String spaceNumber) {
		String spaceRequest = "<Request xmlns=\"http://www2.ipsmetersystems.com/meter\"><Space>" + spaceNumber
				+ "</Space><SpaceGroup>" + SpaceGroup + "</SpaceGroup></Request>";
		return callService(spaceRequest, token, URL);
	}

	public static String getMultiSpaceStatus(String URL, String token, String SubAreaName) {
		String spaceRequest = "<Request xmlns=\"http://www2.ipsmetersystems.com/meter\"><SubAreaName>" + SubAreaName
				+ "</SubAreaName></Request>";

		return callServiceWithSSL(spaceRequest, token, URL);
	}

	public static String getSpaceGroupStatus(String URL, String token, String SpaceGroup) {
		String spaceRequest = "<Request xmlns=\"http://www2.ipsmetersystems.com/meter\"><SpaceGroup>" + SpaceGroup
				+ "</SpaceGroup></Request>";

		return callServiceWithSSL(spaceRequest, token, URL);
	}

	public static String getPlatesBySubArea(String URL, String token, String subArea) {
		String spaceRequest = "<Request xmlns=\"http://www2.ipsmetersystems.com/meter\"><SubAreaName>" + subArea
				+ "</SubAreaName></Request>";

		return callServiceWithSSL(spaceRequest, token, URL);
	}

	public static String getPlateStatus(String URL, String token, String plateNumber) {
		String serverRequest = "<Request xmlns=\"http://www2.ipsmetersystems.com/meter\"><LicensePlateNumber>"
				+ plateNumber + "</LicensePlateNumber>" + "</Request> ";

		return callService(serverRequest, token, URL);
	}

	private static String callService(String request, String token, String URL) {
		String response;
		StringEntity se;
		try {
			se = new StringEntity(request, HTTP.UTF_8);
			se.setContentType("text/xml");

			HttpClient httpClient = new DefaultHttpClient();

			HttpPost httpPost = new HttpPost(URL);
			httpPost.addHeader("IPSToken", token);
			httpPost.setEntity(se);

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity resEntity = httpResponse.getEntity();
			response = EntityUtils.toString(resEntity);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return response;
	}

	private static String callServiceWithSSL(String request, String token, String urlString,boolean isRequired) {
		String result = "";
		StringEntity se;
		HttpsURLConnection urlConnection = null;
		try {
			// create connection
			URL urlToRequest = new URL(urlString);
			urlConnection = (HttpsURLConnection) urlToRequest.openConnection();
			urlConnection.setSSLSocketFactory(new TLSSocketFactory());
			urlConnection.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			urlConnection.setReadTimeout(getNetworkTimeout());
			urlConnection.setConnectTimeout(getNetworkTimeout());
			urlConnection.setRequestProperty("IPSToken",token);
			// handle POST parameters
			if (request != null) {
				Log.i("TAG", "POST parameters: " + request);
				urlConnection.setDoOutput(true);
				urlConnection.setRequestMethod("POST");
				urlConnection.setFixedLengthStreamingMode(
						request.getBytes().length);
				urlConnection.setRequestProperty("Content-Type",
						"text/xml");

				//send the POST out
				PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
				out.print(request);
				out.close();
			}

			// handle issues
			int statusCode = urlConnection.getResponseCode();
			if (statusCode != HttpURLConnection.HTTP_OK) {
				// throw some exception
			}

			// read output (only for GET)
			if (request == null) {
				return null;
			} else {
				InputStream in =
						new BufferedInputStream(urlConnection.getInputStream());

				BufferedReader bfr = new BufferedReader(new InputStreamReader(in));
				String inputLine;
				while ((inputLine = bfr.readLine()) != null) {
					result += inputLine;
				}


				return result ;
			}


		} catch (MalformedURLException e) {
			e.printStackTrace();
			// handle invalid URL
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			// hadle timeout
		} catch (IOException e) {
			e.printStackTrace();
			// handle I/0
		} catch (Exception e) {
			e.printStackTrace();// handle I/0
		}
		finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
		}

		return result;
	}

	public static String  callServiceWithSSL(String request, String token, String url)
	{
		StringBuilder result = new StringBuilder();
		HttpsURLConnection urlConnection = null;
		try {
			// create connection
			URL urlToRequest = new URL(url);
			urlConnection = (HttpsURLConnection) urlToRequest.openConnection();
			urlConnection.setSSLSocketFactory(new TLSSocketFactory());
			urlConnection.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			urlConnection.setReadTimeout(getNetworkTimeout());
			urlConnection.setConnectTimeout(getNetworkTimeout());
			String userCredentials = "IPSToken:"+token;
			String basicAuth = "Basic " + new String(new Base64().encode(userCredentials.getBytes()));

			// handle POST parameters
			if (request != null) {
				Log.i("TAG", "POST parameters: " + request);
				urlConnection.setDoOutput(true);
				urlConnection.setRequestMethod("POST");
				urlConnection.setFixedLengthStreamingMode(
						request.getBytes().length);
				urlConnection.setRequestProperty("Content-Type",
						"text/xml");
				urlConnection.setRequestProperty ("IPSToken", token);
				//send the POST out
				PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
				out.print(request);
				out.close();
			}

			// handle issues
			int statusCode = urlConnection.getResponseCode();
			// throw some exception

			// read output (only for GET)
			if (request == null) {
				return null;
			} else {
				InputStream in =
						new BufferedInputStream(urlConnection.getInputStream());

				BufferedReader bfr = new BufferedReader(new InputStreamReader(in));
				String inputLine;
				while ((inputLine = bfr.readLine()) != null) {
					result.append(inputLine);
				}

				return result.toString();
			}
		}  // hadle timeout
		// handle I/0
		catch (Exception e) {
			log.error(TPUtility.getPrintStackTrace(e));
			e.printStackTrace();
			// handle invalid URL
		}// handle I/0
		finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
		}

		return null;
	}
}
