package com.mypro.biz.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mypro.biz.MovieBiz;
import com.mypro.controller.InitServlet;
import com.mypro.dao.MovieDao;
import com.mypro.domain.Movie;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MovieBizImpl implements MovieBiz {

    @Autowired
    private MovieDao movieDao;

    private static Integer movieNum;

    @Override
    public List<Movie> findAll(){
        List<Movie> movieList = movieDao.findAll();
        movieNum = movieList.size();
        return movieList;
    }


    @Override
    public Movie findById(Integer id) {
        return movieDao.findById(id);
    }

    @Override
    public List<Integer> findByString(String qst,String Line) {
        Result result = ToAnalysis.parse(qst);
        List<Term> terms = result.getTerms();

        List<Integer> resultList = new ArrayList<>();
        for(int i=0;i<terms.size();i++) {
                List<Integer> wordList = wordToList(terms.get(i).getName(), Line);

//            wordList.removeAll(resultList);
//            resultList.addAll(wordList);
                if (i == 0) {
                    if (wordList != null)
                        resultList.addAll(wordList);
                    else return null;
                    continue;
                }
                if (wordList != null)
                    resultList.retainAll(wordList);
                else return null;
        }
        return resultList;
    }

    @Override
    public List<Integer> wordToList(String word, String Line) {
        HashMap<Integer,List<String>> temp = InitServlet.indexs.get(word);
        if(temp==null)return null;
        if(Line.equals("")){
            List<Integer> wordList = new ArrayList<>(temp.keySet());
            return wordList;
        }else{
            List<Integer> wordList = new ArrayList<>();
            for(Map.Entry<Integer,List<String>> me:temp.entrySet()){
                if(me.getValue().contains(Line)){
                    wordList.add(me.getKey());
                }
            }
            return wordList;
        }
    }

    @Override
    public List<Movie> findByIdList(List<Integer> idList) {
        if(idList==null)return  null;
        List<Movie> resultList = new ArrayList<>();
        for(int i=0;i<idList.size();i++){
            Movie movie = findById(idList.get(i));
            if(movie!=null)
                resultList.add(movie);
        }
        return resultList;
    }

    @Override
    public String ListToJson(List<Movie> movies) {
        boolean success = true;
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

        Map<String, Object> res = new HashMap<String, Object>();

        JsonArray movieList = new JsonArray();


        for (Movie movie : movies) {
            JsonObject movieOb = new JsonObject();

            movieOb.addProperty("name", movie.getName());
            movieOb.addProperty("author", movie.getAuthor());
            movieOb.addProperty("id", movie.getId());
            movieOb.addProperty("abst", movie.getAbst());
            movieOb.addProperty("type",movie.getType());
            movieOb.addProperty("areas",movie.getAreas());
            movieOb.addProperty("grade",movie.getGrade());
            movieOb.addProperty("actor",movie.getActor());
            movieOb.addProperty("picture",movie.getPicture());
            movieList.add(movieOb);
        }
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("movies", movieList);
        if (movieList.size() < 1)
            success = false;
        res.put("data", data);
        res.put("success",success);

        return gson.toJson(res);

    }

    @Override
    public List<Integer> boolOption(List<Integer> list1, List<Integer> list2, String option) {
        List<Integer> allList = new ArrayList<>();
        for(int i=0;i<movieNum;i++) allList.add(i);
        List<Integer> resultList = new ArrayList<>();
        if(option.equals("1")){
            if(list1==null||list2==null) return null;

            resultList.addAll(list1);
            resultList.retainAll(list2);

        }else if(option.equals("2")){
            if(list1==null && list2==null) return null;
            else if(list1==null) return list2;
            else if(list2==null) return list1;
            resultList.addAll(list1);
            resultList.removeAll(list2);
            resultList.addAll(list2);
        }else if(option.equals("3")){
            if(list1==null)return null;
            else if(list2==null) return list1;
            else if(list2.size()>=allList.size())return null;
            resultList.addAll(allList);
            resultList.removeAll(list2);
            resultList.retainAll(list1);
        }else if(option.equals("4")){
            if(list2==null) return allList;
            else{
                resultList.addAll(allList);
                resultList.removeAll(list2);
                resultList = boolOption(list1,resultList,"2");
            }
        }else if(option.equals("5")){
            resultList = boolOption(boolOption(list1,list2,"3"),boolOption(list2,list1,"3"),"2");
        }
        return resultList;
    }

    @Override
    public List<Movie> highLight(List<Movie> movies,List<Term> keyWords,String Line) {

        for(int i=0;i<keyWords.size();i++){
            if (InitServlet.expectedNature.contains(keyWords.get(i).getNatureStr())) {
                String keyword = keyWords.get(i).getName();
                for(int j=0;j<movies.size();j++){
                    Movie movie = movies.get(j);
                    if(Line.equals("")){
                        movie.setName(highLightStr(movie.getName(),keyword));
                        movie.setAuthor(highLightStr(movie.getAuthor(),keyword));
                        movie.setAbst(highLightStr(movie.getAbst(),keyword));
                        movie.setType(highLightStr(movie.getType(),keyword));
                        movie.setAreas(highLightStr(movie.getAreas(),keyword));
                        movie.setActor(highLightStr(movie.getActor(),keyword));
                    }else if(Line.equals("name")){
                        movie.setName(highLightStr(movie.getName(),keyword));
                    }else if(Line.equals("author")){
                        movie.setAuthor(highLightStr(movie.getAuthor(),keyword));
                    }else if(Line.equals("type")){
                        movie.setType(highLightStr(movie.getType(),keyword));
                    }else if(Line.equals("areas")){
                        movie.setAreas(highLightStr(movie.getAreas(),keyword));
                    }else if(Line.equals("actor")){
                        movie.setActor(highLightStr(movie.getActor(),keyword));
                    }else if(Line.equals("abst")){
                        movie.setAbst(highLightStr(movie.getAbst(),keyword));
                    }
                }
//                StringBuilder sb = new StringBuilder(realStr);
//                List<Integer> indexList = new ArrayList<>();
//                for(int j=0;j<realStr.length();j++){
//                    Integer index = realStr.indexOf(keyword,i);
//                    if(index!=-1){
//                        indexList.add(index);
//                        i=index;
//                    }else {
//                        break;
//                    }
//                }
//                if(indexList.size()>0){
//                    for (int j=indexList.size()-1;j>=0;j--){
//                        sb.insert(indexList.get(j)+keyword.length(),foot);
//                        sb.insert(indexList.get(j),head);
//                    }
//                }
//                realStr = sb.toString();
            }
        }
        return movies;
    }

    @Override
    public String highLightStr(String movieStr, String keyWord) {
        String head = "<span style=\"color:#F00;\">";
        String foot = "</span>";
        StringBuilder sb = new StringBuilder(movieStr);
        List<Integer> indexList = new ArrayList<>();
        for(int i=0;i<movieStr.length();i++){
            Integer index = movieStr.indexOf(keyWord,i);
            if(index!=-1){
                indexList.add(index);
                i=index;
            }else {
                break;
            }
        }
        if(indexList.size()>0){
            for (int j=indexList.size()-1;j>=0;j--){
                sb.insert(indexList.get(j)+keyWord.length(),foot);
                sb.insert(indexList.get(j),head);
            }
        }
        movieStr = sb.toString();
        return movieStr;
    }


}
