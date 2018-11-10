package com.mypro.controller;

import com.mypro.biz.MovieBiz;
import com.mypro.domain.Movie;
import com.mypro.domain.TfidfNode;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

import static java.lang.Math.log;
import static java.lang.Math.pow;
import static java.lang.StrictMath.sqrt;


@Component
public class InitServlet implements InitializingBean {

    public static Integer movieNum;
    public static HashMap<Integer,List<TfidfNode>> tfMap;
    public static HashMap<String,TfidfNode> wordId;
    /**
     *
     */
    public static Set<String> expectedNature = new HashSet<String>() {{
        add("n");add("v");add("vd");add("vn");add("vf");
        add("vx");add("vi");add("vl");add("vg");
        add("nt");add("nz");add("nw");add("nl");
        add("ng");add("userDefine");add("wh");
    }};

    public static void addLine(String content,int docId){
        Result result = ToAnalysis.parse(content);
        List<Term> terms = result.getTerms();

        List<String> tempWordList = new ArrayList<>();
        for(int i=0;i<terms.size();i++) {
            String word = terms.get(i).getName();
            if (!tempWordList.contains(word)) {
                tempWordList.add(word);
            }
        }
        for(int i=0;i<tempWordList.size();i++){
            if(!wordId.containsKey(tempWordList.get(i))){
                TfidfNode temp = new TfidfNode(wordId.size(),1.0);
                wordId.put(tempWordList.get(i),temp);
            }else{
                wordId.get(tempWordList.get(i)).number+=1;
            }
        }
        for(int i=0;i<terms.size();i++){
            String word = terms.get(i).getName();
            List<TfidfNode> temp = tfMap.get(docId);
            int tempId = wordId.get(word).wordId;
            int cot = 0,j;
            for(j=0;j<temp.size();j++){
                if(temp.get(j).wordId==tempId){
                    temp.get(j).number+=1;
                    cot = 1;
                    break;
                }
                if(temp.get(j).wordId>tempId){
                    break;
                }
            }
            if(cot == 0){
                TfidfNode tempNode = new TfidfNode(tempId,1.0);
                tfMap.get(docId).add(j,tempNode);
            }
        }
    }

    @Autowired
    private MovieBiz movieBiz;


    @Override
    public void afterPropertiesSet() throws Exception {
        tfMap = new HashMap<>();
        wordId = new HashMap<>();
        List<Movie> movies = movieBiz.findAll();
        movieNum = movies.size();
        for(int i=0;i<movies.size();i++) {
            Movie movie = movies.get(i);
            List<TfidfNode> temp = new ArrayList<>();
            tfMap.put(movie.getId(), temp);
            addLine(movie.toString(), movie.getId());
            temp = tfMap.get(movie.getId());
            for(int j=0;j<temp.size();j++){
                temp.get(j).number = log(temp.get(j).number)/log(10)+1;
            }
            tfMap.put(movie.getId(),temp);
        }
        for(Map.Entry<String,TfidfNode> entry: wordId.entrySet()){
            String word = entry.getKey();
            TfidfNode temp = entry.getValue();
            temp.number = log(movieNum/temp.number)/log(10);
            wordId.put(word,temp);
        }

        for(int i=0;i<movieNum;i++){
            List<TfidfNode> temp = tfMap.get(movies.get(i).getId());
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

        objectOutputStream.writeObject(tfMap);
        objectOutputStream.writeObject(wordId);
        outputStream.close();

        FileInputStream freader = new FileInputStream("./WangXiaoMing.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(freader);

        tfMap = (HashMap<Integer,List<TfidfNode>>)objectInputStream.readObject();
        wordId = (HashMap<String,TfidfNode>)objectInputStream.readObject();
    }

}