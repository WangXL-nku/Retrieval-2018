package com.mypro.biz;

import com.mypro.domain.Movie;
import org.ansj.domain.Term;

import java.util.HashMap;
import java.util.List;

public interface MovieBiz {
    List<Movie> findAll();

    Movie findById(Integer id);

    String ListToJson(List<Movie> movies);

    List<Integer> boolOption(List<Integer> list1,List<Integer> list2,String option);

    List<Movie> highLight(Integer pageNum);

    String highLightStr(String movieStr,String keyWord);

    void getScore(String qst);

    void queryScore(String qst);

    void initScore(String qst);

    Integer swapNode(int head,int father);

    void makeHeap();

    List<Movie> heapSort(int pageNum);
}
