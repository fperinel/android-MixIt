package com.level42.mixit.webservices.impl;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import roboguice.inject.InjectResource;
import android.content.Context;
import android.util.Log;

import com.google.inject.Inject;
import com.level42.mixit.R;
import com.level42.mixit.exceptions.CommunicationException;
import com.level42.mixit.exceptions.NotFoundException;
import com.level42.mixit.exceptions.TechnicalException;
import com.level42.mixit.models.Favoris;
import com.level42.mixit.models.Interest;
import com.level42.mixit.models.LightningTalk;
import com.level42.mixit.models.Member;
import com.level42.mixit.models.Speaker;
import com.level42.mixit.models.Sponsor;
import com.level42.mixit.models.Staff;
import com.level42.mixit.models.Talk;
import com.level42.mixit.utils.Utils;
import com.level42.mixit.webservices.IWebServices;

/**
 * Service de connexion avec les Webservices MixIt.
 */
public class WebServices extends DefaultHttpClient implements IWebServices {
    
    /**
     * URL du serveur des Webservices.
     */
    @InjectResource(R.string.host)
    private String host;

    /**
     * Délai maximum de connexion.
     */
    @InjectResource(R.string.connectionTimeout)
    private String connectionTimeout;

    /**
     * Contexte de l'application
     */
    @Inject
    private Context context;
    
    /**
     * Délai maximum de communication.
     */
    @InjectResource(R.string.socketTimeout)
    private String socketTimeout;

    /**
     * Mapper Json.
     */
    private ObjectMapper mapper;

    /**
     * Objet de récupération des réponses.
     */
    private ResponseHandler<String> handler;

    /**
     * Objet contenant le cache des appels Webservices.
     */
    private Map<String, String> cache;

    /**
     * Constructeur en charge d'initiliser le client HTTP avec les paramètres.
     */
    public WebServices() {
        HttpParams httpParameters = new BasicHttpParams();
        if (connectionTimeout != null && socketTimeout != null) {
            HttpConnectionParams.setConnectionTimeout(httpParameters,
                    Integer.valueOf(connectionTimeout));
            HttpConnectionParams.setSoTimeout(httpParameters,
                    Integer.valueOf(socketTimeout));
        }
        this.setParams(httpParameters);

        // Initialisation du mapper JSON
        mapper = new ObjectMapper();
        handler = new BasicResponseHandler();

        cache = new HashMap<String, String>();
    }

    /*
     * (non-Javadoc)
     * @see com.level42.mixit.webservices.IWebServices#getTalks()
     */
    @Override
    public List<Talk> getTalks() throws CommunicationException, TechnicalException, NotFoundException {
        try {
            HttpGet request = this.getRequestGET("talks");
            String result = this.executeQuery(request, true);
            List<Talk> talkList = mapper.readValue(result,
                    new TypeReference<List<Talk>>() {});
            return talkList;
        } catch (ClientProtocolException e) {
            throw new CommunicationException(e);
        } catch (JsonMappingException e) {
            throw new TechnicalException(context.getText(R.string.exception_message_JsonMappingException).toString(), e);
        } catch (IOException e) {
            throw new TechnicalException(e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.level42.mixit.webservices.IWebServices#getTalk(java.lang.Integer)
     */
    @Override
    public Talk getTalk(Integer id) throws CommunicationException,
            NotFoundException, TechnicalException {

        try {
            HttpGet request = this.getRequestGET("talks/" + id.toString());
            String result = this.executeQuery(request, true);
            Talk talk = mapper.readValue(result, Talk.class);
            if (talk == null) {
                throw new NotFoundException("getTalk with id " + id.toString());
            }
            return talk;
        } catch (JsonMappingException e) {
            throw new TechnicalException(context.getText(R.string.exception_message_JsonMappingException).toString(), e);
        } catch (ClientProtocolException e) {
            throw new CommunicationException(e);
        } catch (IOException e) {
            throw new CommunicationException(e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.level42.mixit.webservices.IWebServices#getLightningTalks()
     */
    @Override
    public List<LightningTalk> getLightningTalks()
            throws CommunicationException, TechnicalException, NotFoundException {
        try {
            HttpGet request = this.getRequestGET("lightningtalks");
            String result = this.executeQuery(request, true);
            List<LightningTalk> talkList = mapper.readValue(result,
                    new TypeReference<List<LightningTalk>>() {});
            return talkList;
        } catch (JsonMappingException e) {
            throw new TechnicalException(context.getText(R.string.exception_message_JsonMappingException).toString(), e);
        } catch (ClientProtocolException e) {
            throw new CommunicationException(e);
        } catch (IOException e) {
            throw new CommunicationException(e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.level42.mixit.webservices.IWebServices#getLightningTalk(java.lang.Integer)
     */
    @Override
    public LightningTalk getLightningTalk(Integer id)
            throws CommunicationException, NotFoundException, TechnicalException {

        try {
            HttpGet request = this.getRequestGET("lightningtalks/"
                    + id.toString());
            String result = this.executeQuery(request, true);
            LightningTalk talk = mapper.readValue(result, LightningTalk.class);
            if (talk == null) {
                throw new NotFoundException("getLightningTalk with id "
                        + id.toString());
            }
            return talk;
        } catch (JsonMappingException e) {
            throw new TechnicalException(context.getText(R.string.exception_message_JsonMappingException).toString(), e);
        } catch (ClientProtocolException e) {
            throw new CommunicationException(e);
        } catch (IOException e) {
            throw new CommunicationException(e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.level42.mixit.webservices.IWebServices#getMembers()
     */
    @Override
    public List<Member> getMembers() throws CommunicationException, TechnicalException, NotFoundException {
        try {
            HttpGet request = this.getRequestGET("members");
            String result = this.executeQuery(request, true);
            List<Member> list = mapper.readValue(result,
                    new TypeReference<List<Member>>() {});
            return list;
        } catch (JsonMappingException e) {
            throw new TechnicalException(context.getText(R.string.exception_message_JsonMappingException).toString(), e);
        } catch (ClientProtocolException e) {
            throw new CommunicationException(e);
        } catch (IOException e) {
            throw new CommunicationException(e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.level42.mixit.webservices.IWebServices#getStaffs()
     */
    @Override
    public List<Staff> getStaffs() throws CommunicationException, TechnicalException, NotFoundException {
        try {
            HttpGet request = this.getRequestGET("members/staff");
            String result = this.executeQuery(request, true);
            List<Staff> list = mapper.readValue(result,
                    new TypeReference<List<Staff>>() {});
            return list;
        } catch (JsonMappingException e) {
            throw new TechnicalException(context.getText(R.string.exception_message_JsonMappingException).toString(), e);
        } catch (ClientProtocolException e) {
            throw new CommunicationException(e);
        } catch (IOException e) {
            throw new CommunicationException(e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.level42.mixit.webservices.IWebServices#getSpeakers()
     */
    @Override
    public List<Speaker> getSpeakers() throws CommunicationException, TechnicalException, NotFoundException {
        try {
            HttpGet request = this.getRequestGET("members/speakers");
            String result = this.executeQuery(request, true);
            List<Speaker> list = mapper.readValue(result,
                    new TypeReference<List<Speaker>>() {});
            return list;
        } catch (JsonMappingException e) {
            throw new TechnicalException(context.getText(R.string.exception_message_JsonMappingException).toString(), e);
        } catch (ClientProtocolException e) {
            throw new CommunicationException(e);
        } catch (IOException e) {
            throw new CommunicationException(e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.level42.mixit.webservices.IWebServices#getSponsors()
     */
    @Override
    public List<Sponsor> getSponsors() throws CommunicationException, TechnicalException, NotFoundException {
        try {
            HttpGet request = this.getRequestGET("members/sponsors");
            String result = this.executeQuery(request, true);
            List<Sponsor> list = mapper.readValue(result,
                    new TypeReference<List<Sponsor>>() {});
            return list;
        } catch (JsonMappingException e) {
            throw new TechnicalException(context.getText(R.string.exception_message_JsonMappingException).toString(), e);
        } catch (ClientProtocolException e) {
            throw new CommunicationException(e);
        } catch (IOException e) {
            throw new CommunicationException(e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.level42.mixit.webservices.IWebServices#getEntity(java.lang.Integer, java.lang.Object)
     */
    @Override
    public Object getEntity(Integer id, Object entity)
            throws CommunicationException, NotFoundException, TechnicalException {
        try {
            HttpGet request = this.getRequestGET("members/" + id.toString());
            String result = this.executeQuery(request, true);
            return mapper.readValue(result, entity.getClass());
        } catch (JsonMappingException e) {
            throw new TechnicalException(context.getText(R.string.exception_message_JsonMappingException).toString(), e);
        } catch (ClientProtocolException e) {
            throw new CommunicationException(e);
        } catch (IOException e) {
            throw new CommunicationException(e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.level42.mixit.webservices.IWebServices#getInterests()
     */
    @Override
    public List<Interest> getInterests() throws CommunicationException, TechnicalException, NotFoundException {
        try {
            HttpGet request = this.getRequestGET("interests");
            String result = this.executeQuery(request, true);
            List<Interest> list = mapper.readValue(result,
                    new TypeReference<List<Interest>>() {
                    });

            return list;
        } catch (JsonMappingException e) {
            throw new TechnicalException(context.getText(R.string.exception_message_JsonMappingException).toString(), e);
        } catch (ClientProtocolException e) {
            throw new CommunicationException(e);
        } catch (IOException e) {
            throw new CommunicationException(e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.level42.mixit.webservices.IWebServices#getInterest(java.lang.Integer)
     */
    @Override
    public Interest getInterest(Integer id) throws CommunicationException,
            NotFoundException, TechnicalException {
        try {
            HttpGet request = this.getRequestGET("interests/" + id.toString());
            String result = this.executeQuery(request, true);
            Interest interest = mapper.readValue(result, Interest.class);

            return interest;
        } catch (JsonMappingException e) {
            throw new TechnicalException(context.getText(R.string.exception_message_JsonMappingException).toString(), e);
        } catch (ClientProtocolException e) {
            throw new CommunicationException(e);
        } catch (IOException e) {
            throw new CommunicationException(e);
        }
    }

    @Override
    public List<Favoris> getFavorite(String login) throws CommunicationException,
            NotFoundException, TechnicalException {
        try {
            HttpGet request = this.getRequestGET("members/" + URLEncoder.encode(login, "UTF-8") + "/favorites");
            String result = this.executeQuery(request, true);
            List<Favoris> favorisList = mapper.readValue(result,
                    new TypeReference<List<Favoris>>() {});
            return favorisList;
        } catch (JsonMappingException e) {
            throw new TechnicalException(context.getText(R.string.exception_message_JsonMappingException).toString(), e);
        } catch (ClientProtocolException e) {
            throw new CommunicationException(e);
        } catch (IOException e) {
            throw new CommunicationException(e);
        }
    }

    /**
     * Génère une requête GET.
     * @param path
     *            Chemin d'appel
     * @return Requete
     */
    protected HttpGet getRequestGET(String path) {
        return new HttpGet(host + "/" + path);
    }

    /**
     * Execute la requête d'appel au Webservice après vérification du cache.
     * @param request
     * @return Résultat (format jSon)
     * @throws NotFoundException 
     * @throws ClientProtocolException
     * @throws IOException
     */
    protected String executeQuery(HttpUriRequest request, boolean useCache) throws NotFoundException {
        if (request != null) {
            String key = request.getURI().toString();
            String result = null;
            Log.i(Utils.LOGTAG, "Webservice request : " + request.getURI().toString());
            if (!cache.containsKey(key) || !useCache) {
                try {
                    result = this.execute(request, handler);
                    Log.d(Utils.LOGTAG, "Webservice cache set for key : " + key);
                    cache.put(key, result);
                } catch (ClientProtocolException e) {
                    Log.e(Utils.LOGTAG, "Webservice request error : " + e.getMessage());
                    throw new NotFoundException(context.getText(R.string.exception_message_NotFoundException).toString());
                } catch (IOException e) {
                    Log.e(Utils.LOGTAG, "Webservice request error : " + e.getMessage());
                    throw new NotFoundException(context.getText(R.string.exception_message_NotFoundException).toString());
                }
            } else {
                Log.d(Utils.LOGTAG, "Webservice cache hit for key : " + key);
                result = cache.get(key);
            }
    
            Log.d(Utils.LOGTAG, result);
            
            if (result == null) {
                throw new NotFoundException(context.getText(R.string.exception_message_NotFoundException).toString());
            }
            
            return result;
        } else {
            return null;
        }
    }

    @Override
    public void cleanCache() {
        this.cache.clear();
        Log.d(Utils.LOGTAG, "Webservice cache cleared");
    }

    @Override
    public void cleanCache(String key) {
        String cacheKey = this.getRequestGET(key).getURI().toString();
        String result = this.cache.remove(cacheKey);
        if (result != null) {
            Log.d(Utils.LOGTAG, "Webservice cache clear for key : " + cacheKey);
        } else {
            Log.d(Utils.LOGTAG, "Webservice cache not clear for key : " + cacheKey + ", the key is unknown");
        }
    }
}
