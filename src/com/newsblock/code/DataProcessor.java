/**
 * 
 */
package com.newsblock.code;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.newsblock.dao.ArticlesRepository;
import com.newsblock.dao.ArticlesToCategoryRepository;
import com.newsblock.dao.CategoryRepository;
import com.newsblock.dao.ConfigPropertiesRepository;
import com.newsblock.model.Articles;
import com.newsblock.model.ArticletoCategory;
import com.newsblock.model.Categories;
import com.newsblock.model.ConfigProperties;

/**
 * @author lnb
 *
 */
public class DataProcessor {

	public void extractdata() {

		CategoryRepository categoryRepository = new CategoryRepository();
		ArticlesToCategoryRepository articlesToCategoryRepository = new ArticlesToCategoryRepository();
		ArticlesRepository articlesRepository = new ArticlesRepository();
		ConfigPropertiesRepository configPropertiesRepository = new ConfigPropertiesRepository();

		List<Categories> categories = categoryRepository.findAllActive();

		ConfigProperties configPropertieURL = configPropertiesRepository.getMapByName().get("NEWSAPI_ENDPOINT");
		ConfigProperties configPropertieAPIKey = configPropertiesRepository.getMapByName().get("NEWSAPI_KEY");

		for (Categories category : categories) {

			List<ArticletoCategory> listOfArticleToCategories = articlesToCategoryRepository
					.findByCategory(category.getId());
			List<Integer> ArticleIds = new ArrayList<>();
			for (ArticletoCategory articletoCategory : listOfArticleToCategories) {
				ArticleIds.add(articletoCategory.getArticleid());
			}

//			List<Articles> recentList = articlesRepository.findByList(ArticleIds,
//					new Timestamp(System.currentTimeMillis()));
			List<Articles> recentList = new ArrayList<>();
			if (recentList.size() == 0) {

				LinkedTreeMap jsonObj = null;
				try {

					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
					Date date = new Date(System.currentTimeMillis());

					System.out.println(dateFormat.format(date));

					jsonObj = getResponseNewsAPI(configPropertieURL.getValue(), category.getCategoryname(),
							configPropertieAPIKey.getValue(), dateFormat.format(date));
				} catch (Exception e) {
					e.printStackTrace();
				}
				// System.out.println("jsonObj" + jsonObj);

				List<LinkedTreeMap> articles = (List<LinkedTreeMap>) jsonObj.get("articles");
				for (LinkedTreeMap article_map : articles) {

					Articles newArticle = new Articles();
					newArticle.setTitle((String) article_map.get("title"));
					newArticle.setAuthor((String) article_map.get("author"));
					newArticle.setDescription((String) article_map.get("summary"));
					newArticle.setImage((String) article_map.get("media"));

					String timestamp = (String) article_map.get("published_date");
					newArticle.setSourcepublished(Timestamp.valueOf(timestamp));

					newArticle.setContent((String) article_map.get("excerpt"));

					newArticle.setSource((String) article_map.get("link"));

					newArticle.setViewcount(0);
					newArticle.setIsactive(1);

					Timestamp ts = new Timestamp(System.currentTimeMillis());
					newArticle.setCreatedon(ts);
					newArticle.setUpdatedon(ts);

					articlesRepository.save(newArticle);

					ArticletoCategory articletoCategory = new ArticletoCategory();
					articletoCategory.setArticleid(newArticle.getId());
					articletoCategory.setCategoryid(category.getId());
					articletoCategory.setCreatedon(ts);
					articletoCategory.setUpdatedon(ts);
					articletoCategory.setIsactive(1);

					articlesToCategoryRepository.save(articletoCategory);

				}

			}

		}

	}

	private LinkedTreeMap getResponseNewsAPI(String configPropertieURL, String category, String configPropertieAPIKey,
			String date) throws IOException {

		String url = configPropertieURL + "?q=" + category + "&from=" + date
				+ "&lang=en&ranked_only=True&page_size=100";
		System.out.println(url);
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("x-api-key", configPropertieAPIKey);
		int responseCode = con.getResponseCode();
		System.out.println("GET Response Code :: " + responseCode);
		StringBuffer response = new StringBuffer();
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			Gson gson = new Gson();
			LinkedTreeMap jsonObject = gson.fromJson(response.toString(), LinkedTreeMap.class);
			return jsonObject;
		} else {
			System.out.println("GET request not worked");
		}
		return null;

	}

}