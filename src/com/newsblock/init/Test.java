//package com.newsblock.init;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.sql.Timestamp;
//import java.text.ParseException;
//import java.util.List;
//
//import com.google.gson.Gson;
//import com.google.gson.internal.LinkedTreeMap;
//import com.newsblock.dao.ArticlesRepository;
//import com.newsblock.model.Articles;
//
//public class Test {
//
//	public static void main(String[] args) throws IOException, ParseException {
//		// TODO Auto-generated method stub
//
//		ArticlesRepository articlesRepository = new ArticlesRepository();
//		URL obj = null;
//		try {
//			obj = new URL("https://newsapi.org/v2/everything?q=Technology&apiKey=a630e93eb121447da2b77eaa97f1ef3f");
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//		con.setRequestMethod("GET");
//		int responseCode = con.getResponseCode();
//		System.out.println("GET Response Code :: " + responseCode);
//		StringBuffer response = new StringBuffer();
//		if (responseCode == HttpURLConnection.HTTP_OK) { // success
//			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//			String inputLine;
//
//			while ((inputLine = in.readLine()) != null) {
//				response.append(inputLine);
//			}
//			in.close();
//
//			// print result
//			//System.out.println(response.toString());
//			Gson gson = new Gson();
//			LinkedTreeMap commonResponse = gson.fromJson(response.toString(), LinkedTreeMap.class);
//			//System.out.println(commonResponse);
//			
//			List<LinkedTreeMap> articles = (List<LinkedTreeMap>) commonResponse.get("articles");
//			for(LinkedTreeMap article_map : articles) {
//
//				Articles newArticle = new Articles();
//				newArticle.setTitle((String) article_map.get("title"));
//				newArticle.setAuthor((String) article_map.get("author"));
//				newArticle.setDescription((String) article_map.get("description"));
//				newArticle.setImage((String) article_map.get("urlToImage"));
//				
//				String timestamp = (String) article_map.get("publishedAt");
//				timestamp = timestamp.replace("T", " ");
//				timestamp = timestamp.replace("Z", "");
//				newArticle.setSourcepublished(Timestamp.valueOf(timestamp));
//				
//				newArticle.setContent((String) article_map.get("content"));
//				
//				LinkedTreeMap source_map = (LinkedTreeMap) article_map.get("source");
//				newArticle.setSource((String) source_map.get("name"));
//				
//				newArticle.setCreatedOn(new Timestamp(System.currentTimeMillis()));
//				newArticle.setViewcount(0);
//				newArticle.setIsactive(1);
//				
//				articlesRepository.save(newArticle);
//				
//
//			}
//			
//			
//
//		} else {
//			System.out.println("GET request not worked");
//		}
//
//	}
//
//}
