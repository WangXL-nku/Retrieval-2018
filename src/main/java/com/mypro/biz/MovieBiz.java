package com.mypro.biz;

import com.mypro.domain.Movie;
import org.ansj.domain.Term;

import java.util.HashMap;
import java.util.List;

public interface MovieBiz {
    List<Movie> findAll();

    Movie findById(Integer id);

    Integer findTotalNum();

    List<Integer> findByString(String qst,String Line);

    List<Integer> wordToList(String word, String Line);

    List<Movie> findByIdList(List<Integer> idList);

    String ListToJson(List<Movie> movies);

    List<Integer> boolOption(List<Integer> list1,List<Integer> list2,String option);

    List<Movie> highLight(List<Movie> movies,List<Term> keyWords,String Line);

    String highLightStr(String movieStr,String keyWord);
}
