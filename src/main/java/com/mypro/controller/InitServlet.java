package com.mypro.controller;

import com.mypro.biz.MovieBiz;
import com.mypro.domain.Movie;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;


@Component
public class InitServlet implements InitializingBean {

    public static HashMap<String,HashMap<Integer,List<String>>> indexs;
    public static List<String> dictionary;
    public static Integer movieNum;
    public static List<WordNode> myindex;

    List<WordNode> largeindexs;
    public static String filePath1;
    public static String filePath2;
    /**
     *
     */

    public static Set<String> expectedNature = new HashSet<String>() {{
        add("n");add("v");add("vd");add("vn");add("vf");
        add("vx");add("vi");add("vl");add("vg");
        add("nt");add("nz");add("nw");add("nl");
        add("ng");add("userDefine");add("wh");
    }};
    private static final long serialVersionUID = 1L;

    public void StoreIndex() throws Exception {

        FileInputStream freader = new FileInputStream("d://tempindex.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(freader);


        myindex = (List<WordNode>) objectInputStream.readObject();//从文件中读回块
        freader.close();
        for(int i = 0, j = 0; j< myindex.size(); i++){
            if(i==largeindexs.size() || myindex.get(j).getTermId()<largeindexs.get(i).getTermId()){
                largeindexs.add(i, myindex.get(j));
                j++;
            }
        }
        FileOutputStream outputStream = new FileOutputStream("d://tempindex.txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

        objectOutputStream.writeObject(myindex);

        outputStream.close();
    }

    public void SortIndex() throws Exception {
        for(int i = 0; i< myindex.size(); i++){
            for(int j = i+1; j< myindex.size(); j++){
                WordNode temp1 = myindex.get(i);
                WordNode temp2 = myindex.get(j);
                if(temp1.getTermId()>temp2.getTermId()){
                    myindex.set(i,temp2);
                    myindex.set(j,temp1);
                }
            }
        }
        FileOutputStream outputStream = new FileOutputStream("d://tempindex.txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

        objectOutputStream.writeObject(myindex);

        outputStream.close();
        StoreIndex();
        myindex = new ArrayList<>();
    }

    public void myCreateIndex(String LineStr,int MovieId) throws Exception{
        Result result = ToAnalysis.parse(LineStr);
        List<Term> terms = result.getTerms();

        for(int j=0;j<terms.size();j++){

            String word = terms.get(j).getName();
            if(!dictionary.contains(word)){
                dictionary.add(word);
            }
            Integer termId = dictionary.indexOf(word);
            WordNode tempNode = new WordNode(termId,MovieId);
            myindex.add(tempNode);
            if(myindex.size()>=1000){
                SortIndex();
            }
        }

    }
    public void CreateIndex(String myLine,String LineStr,int MovieId){
        Result result = ToAnalysis.parse(LineStr);
        List<Term> terms = result.getTerms();

        for(int j=0;j<terms.size();j++){

            String word = terms.get(j).getName();
            //                List<WordTextInfoNode> keyNodes  = indexs.get(ord);
            HashMap<Integer, List<String>> keyNodes = indexs.get(word);
            if (keyNodes == null) {
                keyNodes = new HashMap<>();

                List<String> lineList = new ArrayList<>();
                lineList.add(myLine);

                keyNodes.put(MovieId, lineList);
                indexs.put(word, keyNodes);
            } else {
                List<String> lineList = keyNodes.get(MovieId);
                if (lineList == null) {
                    lineList = new ArrayList<>();
                    lineList.add(myLine);
                    keyNodes.put(MovieId, lineList);
                } else {
                    if (!lineList.contains(myLine)) {
                        lineList.add(myLine);
                    }
                }
            }

        }
    }

    List<List<Integer>> tempindex;
    @Autowired
    private MovieBiz movieBiz;
    @Override
    public void afterPropertiesSet() throws Exception {
        myindex = new ArrayList<>();
        movieNum = movieBiz.findTotalNum();
        dictionary = new ArrayList<>();
        largeindexs = new ArrayList<>();
        tempindex = new ArrayList<>();
        for(int i=0;i<movieNum;i++){
            Movie tempMovie = movieBiz.findById(i+1);
            String str = tempMovie.getName()+ " "+ tempMovie.getAuthor()+" "+tempMovie.getType()+ " "
                    +tempMovie.getAreas() +" " + tempMovie.getActor()+" "+tempMovie.getAbst();
            myCreateIndex(str,tempMovie.getId());
        }
        FileInputStream freader1 = new FileInputStream("d://tempindex.txt");
        ObjectInputStream objectInputStream1 = new ObjectInputStream(freader1);

        myindex = (List<WordNode>) objectInputStream1.readObject();
        freader1.close();
        for(int i=0;i<myindex.size();i++){
            List<WordNode> usedindex;
            int termId = myindex.get(i).getTermId();
            if(tempindex.size()==termId){
                List<Integer> mytemp = new ArrayList<>();
                tempindex.add(mytemp);
            }
            if(!tempindex.get(i).contains(myindex.get(i).getDocId())){
                tempindex.get(i).add(myindex.get(i).getDocId());
            }
            if(i%1000==0){

                FileInputStream freader2 = new FileInputStream("d://tempindex.txt");
                ObjectInputStream objectInputStream2 = new ObjectInputStream(freader2);

                usedindex = (List<WordNode>) objectInputStream2.readObject();
                freader1.close();
                myindex = usedindex;
            }
        }
        for(int i=0;i<tempindex.size();i++){
            for(int j=0;j<tempindex.get(i).size();j++){
                System.out.print(tempindex.get(i).get(j)+" ");
            }
            System.out.print("\r\n");
        }
        for(int i=0;i<tempindex.size();i++){
            if((i = freader1.read())==0)
                break;
            List<Integer> wordIndex = tempindex.get(i);
            for(int j=1;j<wordIndex.size();j++){
                wordIndex.set(j,wordIndex.get(j)-wordIndex.get(j-1));
            }
        }

        String mydictionary = new String();
        List<Integer> dicPoint;
        dicPoint = new ArrayList<>();
        for(int i=0;i<dictionary.size();i++){
            dicPoint.add(mydictionary.length());
            mydictionary+=dictionary.get(i).length();
            mydictionary+=dictionary.get(i);
        }
        for(int i=0;i<dicPoint.size();i++){
            int num;
            String numstr = mydictionary.substring(dicPoint.get(i),dicPoint.get(i)+1);
            num = Integer.parseInt(numstr);
            String word = mydictionary.substring(dicPoint.get(i)+1,dicPoint.get(i)+num+1);
            System.out.println(word);
        }
        indexs = new HashMap<>();

        List<Movie> movies = movieBiz.findAll();

        for(int i=0;i<movies.size();i++){

            Movie movie = movies.get(i);

            String str = movie.getName();
            CreateIndex("name",str,movie.getId());

            str = movie.getAuthor();
            CreateIndex("author",str,movie.getId());

            str = movie.getType();
            CreateIndex("type",str,movie.getId());

            str = movie.getAreas();
            CreateIndex("areas",str,movie.getId());

            str = movie.getActor();
            CreateIndex("actor",str,movie.getId());

            str = movie.getAbst();
            CreateIndex("abst",str,movie.getId());

        }

        FileOutputStream outputStream = new FileOutputStream("./WangXiaoMing.txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

        objectOutputStream.writeObject(indexs);

        outputStream.close();

        System.out.println("write success");


        FileInputStream freader = new FileInputStream("./WangXiaoMing.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(freader);

        HashMap<String,HashMap<Integer,List<String>>> IOindex;

        IOindex = (HashMap<String,HashMap<Integer, List<String>>>) objectInputStream.readObject();
        for(Map.Entry<String,HashMap<Integer,List<String>>> me: IOindex.entrySet()){
            if(me.getKey().equals("中国")) {
                String classKey = me.getKey();
                HashMap<Integer, List<String>> numNameMapValue = me.getValue();
                System.out.print(classKey + ":");
                for (Map.Entry<Integer, List<String>> nameMapEntry : numNameMapValue.entrySet()) {
                    System.out.print(nameMapEntry.getKey());
                    System.out.print("  ");
                }
                System.out.println("");
            }
        }

    }

}