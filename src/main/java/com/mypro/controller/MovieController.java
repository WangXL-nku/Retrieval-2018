package com.mypro.controller;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mypro.biz.MovieBiz;
import com.mypro.domain.Movie;
import com.mypro.domain.TfidfNode;
import com.sun.org.apache.xml.internal.security.Init;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.log;
import static java.lang.Math.pow;
import static java.lang.StrictMath.sqrt;

@Controller
public class MovieController {

    @Autowired
    private MovieBiz movieBiz;

    @RequestMapping(value= "/init-data",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findAll(HttpServletRequest request) {
        List<Movie> movies = movieBiz.findAll();

        return movieBiz.ListToJson(movies);
    }

    @RequestMapping(value = "/tf-idf_search",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String tf_idfSearch(HttpServletRequest request){

        Integer pageNum = 1;
        String question = request.getParameter("question");
        movieBiz.getScore(question);


        List<Movie> highLightMovie = movieBiz.heapSort(pageNum);
        return movieBiz.ListToJson(highLightMovie);
    }

    @RequestMapping(value = "set-page",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String setPage(HttpServletRequest request){
        Integer pageNum = Integer.parseInt(request.getParameter("pageNum"));

        List<Movie> highLightMovie = movieBiz.heapSort(pageNum);
        return movieBiz.ListToJson(highLightMovie);
    }

    @RequestMapping(value = "redo-index",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String redoIndex(HttpServletRequest request) throws Exception {

        InitServlet.tfMap = new HashMap<>();
        InitServlet.wordId = new HashMap<>();
        List<Movie> movies = movieBiz.findAll();
        InitServlet.movieNum = movies.size();
        for(int i=0;i<movies.size();i++) {
            Movie movie = movies.get(i);
            List<TfidfNode> temp = new ArrayList<>();
            InitServlet.tfMap.put(movie.getId(), temp);
            InitServlet.addLine(movie.toString(), movie.getId());
            temp = InitServlet.tfMap.get(movie.getId());
            for(int j=0;j<temp.size();j++){
                temp.get(j).number = log(temp.get(j).number)/log(10)+1;
            }
            InitServlet.tfMap.put(movie.getId(),temp);
        }
        for(Map.Entry<String,TfidfNode> entry: InitServlet.wordId.entrySet()){
            String word = entry.getKey();
            TfidfNode temp = entry.getValue();
            temp.number = log(InitServlet.movieNum/temp.number)/log(10);
            InitServlet.wordId.put(word,temp);
        }

        for(int i=0;i<InitServlet.movieNum;i++){
            List<TfidfNode> temp = InitServlet.tfMap.get(movies.get(i).getId());
            double total=0;
            for(int j=0;j<temp.size();j++){
                total+=pow(temp.get(j).number,2);
            }
            total = sqrt(total);
            for(int j=0;j<temp.size();j++){
                temp.get(j).number/=total;
            }
        }
        FileOutputStream outputStream = new FileOutputStream("./WangXiaoMing.txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

        objectOutputStream.writeObject(InitServlet.tfMap);
        objectOutputStream.writeObject(InitServlet.wordId);
        outputStream.close();

        return "redo Index";
    }
//    @RequestMapping(value="/common-search",produces = "text/html;charset=UTF-8")
//    @ResponseBody
//    public String commonSearch(HttpServletRequest request){
//        String question = request.getParameter("question");
//
//        List<Integer> idList = movieBiz.findByString(question,"");
//
//        List<Movie> resultList = movieBiz.findByIdList(idList);
//
//        Result result = ToAnalysis.parse(question);
//        List<Term> terms = result.getTerms();
//
//        List<Movie> highLightMovie = movieBiz.highLight(resultList,terms,"");
//        return movieBiz.ListToJson(highLightMovie);
//    }
//
//    @RequestMapping(value="/key-search",produces = "text/html;charset=UTF-8")
//    @ResponseBody
//    public String keySearch(HttpServletRequest request){
//
//        String Line1 = request.getParameter("Line1");
//        String KeyWord1 = request.getParameter("KeyWord1");
//        String Option1 = request.getParameter("Option1");
//        String Line2 = request.getParameter("Line2");
//        String KeyWord2 = request.getParameter("KeyWord2");
//        String Option2 = request.getParameter("Option2");
//        String Line3 = request.getParameter("Line3");
//        String KeyWord3 = request.getParameter("KeyWord3");
//
//        List<Integer> idList1 = movieBiz.findByString(KeyWord1,Line1);
//        List<Integer> idList2 = movieBiz.findByString(KeyWord2,Line2);
//        List<Integer> idList3 = movieBiz.findByString(KeyWord3,Line3);
//
//        List<Integer> idList = new ArrayList<>();
//
//        idList = movieBiz.boolOption(idList1,idList2,Option1);
//
//        idList = movieBiz.boolOption(idList,idList3,Option2);
//
//        List<Movie> resultList = movieBiz.findByIdList(idList);
//
//        Result result = ToAnalysis.parse(KeyWord1);
//        List<Term> terms = result.getTerms();
//
//        List<Movie> highLightMovie = movieBiz.highLight(resultList,terms,Line1);
//
//        if(!KeyWord2.equals("")) {
//            result = ToAnalysis.parse(KeyWord2);
//            terms = result.getTerms();
//
//            highLightMovie = movieBiz.highLight(highLightMovie, terms, Line1);
//        }
//        if(!KeyWord3.equals("")) {
//            result = ToAnalysis.parse(KeyWord3);
//            terms = result.getTerms();
//
//            highLightMovie = movieBiz.highLight(highLightMovie, terms, Line1);
//        }
//        return movieBiz.ListToJson(highLightMovie);
//    }
}
