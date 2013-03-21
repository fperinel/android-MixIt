package com.level42.mixtit.webservices.impl;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import roboguice.inject.InjectResource;
import android.util.Log;

import com.level42.mixtit.R;
import com.level42.mixtit.exceptions.CommunicationException;
import com.level42.mixtit.models.LightningTalk;
import com.level42.mixtit.models.Member;
import com.level42.mixtit.models.Speaker;
import com.level42.mixtit.models.Sponsor;
import com.level42.mixtit.models.Staff;
import com.level42.mixtit.models.Talk;
import com.level42.mixtit.utils.Utils;
import com.level42.mixtit.webservices.IWebServices;

public class WebServices extends DefaultHttpClient implements IWebServices {

	@InjectResource(R.string.host)
	private String host;
	
	@InjectResource(R.string.connectionTimeout)
	private String connectionTimeout;

	@InjectResource(R.string.socketTimeout)
	private String socketTimeout;

	private ObjectMapper mapper;
	
	private ResponseHandler<String> handler;
	
	/**
	 * Constructeur en charge d'initiliser le client HTTP avec les paramètres
	 */
	public WebServices() {	
		HttpParams httpParameters = new BasicHttpParams();
		if (connectionTimeout != null && socketTimeout != null) {
			HttpConnectionParams.setConnectionTimeout(httpParameters, Integer.valueOf(connectionTimeout));
			HttpConnectionParams.setSoTimeout(httpParameters, Integer.valueOf(socketTimeout));
		}
		this.setParams(httpParameters);
		
		// Initialisation du mapper JSON
		mapper = new ObjectMapper();		
		handler = new BasicResponseHandler();
	}
	
	public List<Talk> getTalks() throws CommunicationException {
		try {
			HttpGet request = this.getRequestGET("talks");
			String result = this.execute(request, handler);
			
	        Log.d(Utils.LOGTAG, result);
			List<Talk> talkList = mapper.readValue(result, new TypeReference<List<Talk>>() {});

			return talkList;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			throw new CommunicationException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new CommunicationException(e);
		}
	}

	public Talk getTalk(Integer id) throws CommunicationException {

		try {
			HttpGet request = this.getRequestGET("talks/" + id.toString());
			String result = this.execute(request, handler);
			
	        Log.d(Utils.LOGTAG, result);
			Talk talk = mapper.readValue(result, Talk.class);

			return talk;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			throw new CommunicationException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new CommunicationException(e);
		}
	}

	public List<LightningTalk> getLightningTalks() throws CommunicationException {
		try {
			HttpGet request = this.getRequestGET("lightningtalks");
			String result = this.execute(request, handler);
			
	        Log.d(Utils.LOGTAG, result);
			List<LightningTalk> talkList = mapper.readValue(result, new TypeReference<List<LightningTalk>>() {});

			return talkList;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			throw new CommunicationException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new CommunicationException(e);
		}
	}

	public LightningTalk getLightningTalk(Integer id) throws CommunicationException {

		try {
			HttpGet request = this.getRequestGET("lightningtalks/" + id.toString());
			String result = this.execute(request, handler);
			
	        Log.d(Utils.LOGTAG, result);
	        LightningTalk talk = mapper.readValue(result, LightningTalk.class);

			return talk;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			throw new CommunicationException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new CommunicationException(e);
		}
	}

	public List<Member> getMembers() throws CommunicationException {
		try {
			HttpGet request = this.getRequestGET("members");
			String result = this.execute(request, handler);
			
	        Log.d(Utils.LOGTAG, result);
			List<Member> list = mapper.readValue(result, new TypeReference<List<Member>>() {});

			return list;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			throw new CommunicationException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new CommunicationException(e);
		}
	}

	public List<Staff> getStaff() throws CommunicationException {
		try {
			HttpGet request = this.getRequestGET("members/staff");
			String result = this.execute(request, handler);
			
	        Log.d(Utils.LOGTAG, result);
			List<Staff> list = mapper.readValue(result, new TypeReference<List<Staff>>() {});

			return list;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			throw new CommunicationException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new CommunicationException(e);
		}
	}

	public List<Speaker> getSpeakers() throws CommunicationException {
		try {
			HttpGet request = this.getRequestGET("members/speakers");
			String result = this.execute(request, handler);
			
	        Log.d(Utils.LOGTAG, result);
			List<Speaker> list = mapper.readValue(result, new TypeReference<List<Speaker>>() {});

			return list;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			throw new CommunicationException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new CommunicationException(e);
		}
	}

	public List<Sponsor> getSponsors() throws CommunicationException {
		try {
			HttpGet request = this.getRequestGET("members/sponsors");
			String result = this.execute(request, handler);
			
	        Log.d(Utils.LOGTAG, result);
			List<Sponsor> list = mapper.readValue(result, new TypeReference<List<Sponsor>>() {});

			return list;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			throw new CommunicationException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new CommunicationException(e);
		}
	}

	public Member getMember(Integer id) throws CommunicationException {
		try {
			HttpGet request = this.getRequestGET("members/" + id.toString());
			String result = this.execute(request, handler);
			
	        Log.d(Utils.LOGTAG, result);
	        Member member = mapper.readValue(result, new TypeReference<Member>() {});

			return member;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			throw new CommunicationException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new CommunicationException(e);
		}
	}

	/**
	 * Génère une requête GET
	 * 
	 * @param path Chemin d'appel
	 * 
	 * @return Requete
	 */
	protected HttpGet getRequestGET(String path) {
		return new HttpGet(host + "/" + path);
	}
	
}
