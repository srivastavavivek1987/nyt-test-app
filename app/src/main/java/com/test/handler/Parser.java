package com.test.handler;

import com.test.model.Response;
import com.test.model.Result;
import com.test.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    public Response parseJson(String json) {
        Response response = new Response();
        try {
            JSONObject jsonObject = new JSONObject(json);
            response.setStatus(jsonObject.getString("status"));
            if (jsonObject.has("num_results")) {
                response.setTotal(jsonObject.getInt("num_results"));
            }


            JSONArray jsonArray = jsonObject.getJSONArray("results");
            if (jsonArray != null) {
                List<Result> results = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject resultJson = jsonArray.getJSONObject(i);
                    Result result = new Result();
                    result.setUrl(resultJson.getString("url"));
                    result.setByLine(resultJson.getString("byline"));
                    result.setTitle(resultJson.getString("title"));
                    result.setDesc(resultJson.getString("abstract"));
                    result.setUpdatedDate(Utils.formatDate(resultJson.getString("updated_date")));

                    if (!resultJson.get("multimedia").equals("")) {
                        JSONArray imageJson = resultJson.getJSONArray("multimedia");
                        if (imageJson != null && imageJson.length() > 0) {
                            result.setImageUrl(imageJson.getJSONObject(0).getString("url"));
                        }
                    }
                    results.add(result);
                }
                response.setResults(results);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    public Response parseSearchJson(String json) throws JSONException {
        Response response = new Response();
        try {
            JSONObject jsonObjectMain = new JSONObject(json);
            JSONObject jsonObject = jsonObjectMain.getJSONObject("response");
            response.setStatus(jsonObjectMain.getString("status"));


            JSONArray jsonArray = jsonObject.getJSONArray("docs");
            if (jsonArray != null) {
                response.setTotal(jsonArray.length());
                List<Result> results = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject resultJson = jsonArray.getJSONObject(i);
                    Result result = new Result();

                    JSONObject headLine = resultJson.getJSONObject("headline");
                    result.setTitle(headLine.getString("main"));
                    result.setUpdatedDate(Utils.formatPubDate(resultJson.getString("pub_date")));
                    result.setUrl(resultJson.getString("web_url"));
                    try {
                        JSONObject byline = resultJson.getJSONObject("byline");
                        result.setByLine(byline.getString("original"));
                    } catch (Exception e) {
                        //consume because sometime coming array instead of object
                    }

                    if (!resultJson.get("multimedia").equals("")) {
                        JSONArray imageJson = resultJson.getJSONArray("multimedia");
                        if (imageJson != null && imageJson.length() > 0) {
                            result.setImageUrl("http://nytimes.com/" + imageJson.getJSONObject(0).getString("url"));
                        }
                    }
                    results.add(result);
                }
                response.setResults(results);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }
}
