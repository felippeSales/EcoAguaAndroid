package ecoagua.controller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
 
import android.os.AsyncTask;
import android.util.Log;
 
public class Consulta extends AsyncTask<String, Void, String> {
	 
    @Override
    protected String doInBackground(String... params) {
 
       String resultado = ""; 
        if (params.length > 0){
            // faço qualquer coisa com os parâmetros
 
            try {                
            	resultado = GET(params[0]);
            	
                 
            } catch (Exception e) {
                
            }
        }
        
        return resultado;
    }
    
	/** 
    *
    * Recebe uma url e gera uma requiciao HTTP GET, retorna o resultado dessa requisicao se ela for bem sucessida se naa retorna: Did not Work!
    * 
    * @param url       A url a qual a requisicao sera feita.
    * 
    * @return String   A resposta obitida apos a requisicao
    * 
    */
	@SuppressWarnings("deprecation")
	private static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        
        HttpClient httpclient;
        HttpResponse httpResponse;
        HttpEntity enty;
        
        try {
        	
            // create HttpClient
            httpclient = new DefaultHttpClient();
 
            // make GET request to the given URL
            httpResponse = httpclient.execute(new HttpGet(url));
            enty = httpResponse.getEntity();
            
            
            // receive response as inputStream
            inputStream = enty.getContent();
            
            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
 
            if (enty != null)
                enty.consumeContent();
            
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
 
        return result;
    }
	
	/** 
    *
    * Recebe uma stream e a converte em uma unica string.
    * 
    * @return String   A stream convertida em string.
    * 
    */
    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
 
        inputStream.close();
        return result;
 
    }
}