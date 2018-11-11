package com.mypro.controller;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mypro.biz.MovieBiz;
import com.mypro.domain.Movie;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MovieController {

    @Autowired
    private MovieBiz movieBiz;

    @RequestMapping(value= "/datatest",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findAll(HttpServletRequest request) {
        List<Movie> movies = movieBiz.findAll();

        return movieBiz.ListToJson(movies);
    }

    @RequestMapping(value="/common-search",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String commonSearch(HttpServletRequest request){
        String question = request.getParameter("question");

        List<Integer> idList = movieBiz.findByString(question,"");

        List<Movie> resultList = movieBiz.findByIdList(idList);

        Result result = ToAnalysis.parse(question);
        List<Term> terms = result.getTerms();

        List<Movie> highLightMovie = movieBiz.highLight(resultList,terms,"");
        return movieBiz.ListToJson(highLightMovie);
    }

    @RequestMapping(value="/key-search",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String keySearch(HttpServletRequest request){

        String Line1 = request.getParameter("Line1");
        String KeyWord1 = request.getParameter("KeyWord1");
        String Option1 = request.getParameter("Option1");
        String Line2 = request.getParameter("Line2");
        String KeyWord2 = request.getParameter("KeyWord2");
        String Option2 = request.getParameter("Option2");
        String Line3 = request.getParameter("Line3");
        String KeyWord3 = request.getParameter("KeyWord3");

        List<Integer> idList1 = movieBiz.findByString(KeyWord1,Line1);
        List<Integer> idList2 = movieBiz.findByString(KeyWord2,Line2);
        List<Integer> idList3 = movieBiz.findByString(KeyWord3,Line3);

        List<Integer> idList = new ArrayList<>();

        idList = movieBiz.boolOption(idList1,idList2,Option1);

        idList = movieBiz.boolOption(idList,idList3,Option2);

        List<Movie> resultList = movieBiz.findByIdList(idList);

        Result result = ToAnalysis.parse(KeyWord1);
        List<Term> terms = result.getTerms();

        List<Movie> highLightMovie = movieBiz.highLight(resultList,terms,Line1);

        if(!KeyWord2.equals("")) {
            result = ToAnalysis.parse(KeyWord2);
            terms = result.getTerms();

            highLightMovie = movieBiz.highLight(highLightMovie, terms, Line1);
        }
        if(!KeyWord3.equals("")) {
            result = ToAnalysis.parse(KeyWord3);
            terms = result.getTerms();

            highLightMovie = movieBiz.highLight(highLightMovie, terms, Line1);
        }
        return movieBiz.ListToJson(highLightMovie);
    }
}
