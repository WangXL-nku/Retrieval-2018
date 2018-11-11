package com.str;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class strTest {

    public void Stropt(){
        String head = "<span style=\"color:#F00;\">";
        String foot = "</span>";
        String string = "我喜欢霸王别姬霸王霸王";
        System.out.println(head.length());
        System.out.println(foot.length());
    }

    @Test
    public void teststr(){
        String head = "<span style=\"color:#F00;\">";
        String foot = "</span>";
        String string = "我喜欢霸王别姬霸王he霸王以及霸王霸王";
        String key = "霸王";
        StringBuilder sb = new StringBuilder(string);
        List<Integer> indexList = new ArrayList<>();
        for(int i=0;i<string.length();i++){
            Integer index = string.indexOf(key,i);
            if(index!=-1){
                indexList.add(index);
                i=index;
            }
        }
        if(indexList.size()>0){
            for(int j = indexList.size() -1;j >= 0 ; j--){
                sb.insert(indexList.get(j)+key.length(), foot);
                sb.insert(indexList.get(j),head);
            }
        }

        System.out.println(sb.toString());
    }
}
