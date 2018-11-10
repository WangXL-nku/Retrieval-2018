package com.mypro.biz.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mypro.biz.MovieBiz;
import com.mypro.controller.InitServlet;
import com.mypro.dao.MovieDao;
import com.mypro.domain.Movie;
import com.mypro.domain.TfidfNode;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.lang.Math.log;
import static java.lang.Math.scalb;

@Service
public class MovieBizImpl implements MovieBiz {

    public static String query;
    public static List<TfidfNode> score;    //query score
    public static HashMap<Integer,Double> docScore;
    public static List<Integer> docScoreRank;
    public static Integer rankedNum;
    public static List<Movie> movieList;
    public static List<Integer> highLightList;
    public static Integer pageSize;

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
        for(int i=0;i<movieList.size();i++){
            if(movieList.get(i).getId()==id)
                return movieList.get(i);
        }
        return null;
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
            movieOb.addProperty("comment1",movie.getComment1());
            movieOb.addProperty("comment2",movie.getComment2());
            movieOb.addProperty("comment3",movie.getComment3());
            movieList.add(movieOb);
        }
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("movies", movieList);
        data.put("query",query);
        data.put("movieNum",movieNum);
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
    public List<Movie> highLight(Integer pageNum) {
        List<Movie> highLightMovie = new ArrayList<>();
        int end = pageNum*pageSize;
        end = end>movieNum?movieNum:end;
        int start = (pageNum-1)*pageSize;
        if(!highLightList.contains(pageNum)) {
            Result result = ToAnalysis.parse(query);
            List<Term> terms = result.getTerms();

            for (int i = 0; i < terms.size(); i++) {
//                if (InitServlet.expectedNature.contains(terms.get(i).getNatureStr())) {
                String keyword = terms.get(i).getName();
                for (int j = start; j < end; j++) {
                    Movie movie = findById(docScoreRank.get(j));
                    movie.setName(highLightStr(movie.getName(), keyword));
                    movie.setAuthor(highLightStr(movie.getAuthor(), keyword));
                    movie.setAbst(highLightStr(movie.getAbst(), keyword));
                    movie.setType(highLightStr(movie.getType(), keyword));
                    movie.setAreas(highLightStr(movie.getAreas(), keyword));
                    movie.setComment1(highLightStr(movie.getComment1(), keyword));
                    movie.setComment2(highLightStr(movie.getComment2(), keyword));
                    movie.setComment3(highLightStr(movie.getComment3(), keyword));
                }
//                }
            }
            highLightList.add(pageNum);
        }
        for (int i = start; i < end; i++) {
            highLightMovie.add(findById(docScoreRank.get(i)));
        }
        return highLightMovie;
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

    @Override
    public void getScore(String qst) {
        initScore(qst);
        queryScore(qst);

        for(int i=0;i<movieList.size();i++){
            double movieScore = 0.0;
            List<TfidfNode> temp = InitServlet.tfMap.get(movieList.get(i).getId());
            int x=0,y=0;
            for(;x<score.size()&&y<temp.size();){
                if(score.get(x).wordId.equals(temp.get(y).wordId)){
                    movieScore += score.get(x++).number*temp.get(y++).number;
                    continue;
                }
                if(score.get(x).wordId>temp.get(y).wordId) {
                    y++;
                } else{
                    x++;
                }
            }
            docScore.put(movieList.get(i).getId(),movieScore);
        }
        makeHeap();
    }

    @Override
    public void queryScore(String qst) {

        Result result = ToAnalysis.parse(qst);
        List<Term> terms = result.getTerms();

        HashMap<Integer,Double> queryIdf = new HashMap<>();

        for(int i=0;i<terms.size();i++) {

            String word = terms.get(i).getName();
            TfidfNode temp = InitServlet.wordId.get(word);
            queryIdf.put(temp.wordId,temp.number);
            int j,cot=0;
            for(j=0;j<score.size();j++){
                if(score.get(j).wordId.equals(temp.wordId)){
                    score.get(j).number+=1;
                    cot=1;
                    break;
                }
                if(score.get(j).wordId>temp.wordId){
                    break;
                }
            }
            if(cot==0){
                TfidfNode tempNode = new TfidfNode(temp.wordId,1.0);
                score.add(j,tempNode);
            }
        }
        for(int i=0;i<score.size();i++){
            score.get(i).number = log(score.get(i).number)/log(10)+1;
            score.get(i).number *=queryIdf.get(score.get(i).wordId);
        }
    }

    @Override
    public void initScore(String qst) {
        query = qst;
        score = new ArrayList<>();
        movieNum = InitServlet.movieNum;
        docScore = new HashMap<>();
        docScoreRank = new ArrayList<>();
        movieList = findAll();
        highLightList = new ArrayList<>();
        pageSize = 5;
    }

    @Override
    public Integer swapNode(int head,int father) {
        father-=head;
        int size = docScoreRank.size()-head;
        double a =docScore.get(docScoreRank.get(father)),b =docScore.get(docScoreRank.get(2*father+1));
        double c = size==2*father+2?-1:docScore.get(docScoreRank.get(father*2+2));
        if(b>=c && b>a){
            Collections.swap(docScoreRank,father,father*2+1);
            return father*2+1;
        }else if(c>b && c>a){
            Collections.swap(docScoreRank,father,father*2+2);
            return father*2+2;
        }
        return -1;
    }

    @Override
    public void makeHeap() {
        rankedNum = 0;
        for(Map.Entry<Integer,Double> entry:docScore.entrySet()){
            docScoreRank.add(entry.getKey());
        }
        for(int i=docScoreRank.size()/2-1;i>=0;i--) {
            int j = i;
            do {
                j = swapNode(0,j);
            } while (j != -1 && j * 2 + 2 <= docScoreRank.size());
        }
    }

    @Override
    public List<Movie> heapSort(int pageNum) {
        int end = pageNum * pageSize;
        end = end>movieNum?movieNum:end;
        if(rankedNum<end){
            for(;rankedNum<end;rankedNum++){
                int j = rankedNum;
                do{
                    j = swapNode(rankedNum,j);
                }while (j != -1 && j*2+2<=docScoreRank.size()+rankedNum);
            }
        }
        return highLight(pageNum);
    }

}
