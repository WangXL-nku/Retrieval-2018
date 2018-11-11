package com.mypro.controller;

import com.mypro.biz.MovieBiz;
import com.mypro.domain.Movie;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.image.Kernel;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;


@Component
public class InitServlet implements InitializingBean {

    public static HashMap<String,HashMap<Integer,List<String>>> indexs;
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

    @Autowired
    private MovieBiz movieBiz;
    @Override
    public void afterPropertiesSet() throws Exception {



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