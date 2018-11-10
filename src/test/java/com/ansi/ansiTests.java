package com.ansi;

import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ansiTests {

    @Test
    public void testansi(){
        //只关注这些词性的词
        Set<String> expectedNature = new HashSet<String>() {{
            add("n");add("v");add("vd");add("vn");add("vf");
            add("vx");add("vi");add("vl");add("vg");
            add("nt");add("nz");add("nw");add("nl");
            add("ng");add("userDefine");add("wh");
        }};
        String str = "在这部里程碑式的黑帮史诗巨片里真实上演。在这部里程碑式的黑帮史诗巨片里真实上演。在这部里程碑式的黑帮史诗巨片里真实上演。在这部里程碑式的黑帮史诗巨片里真实上演。" ;
        Result result = ToAnalysis.parse(str); //分词结果的一个封装，主要是一个List<Term>的terms
        System.out.println(result.getTerms());

        List<Term> terms = result.getTerms(); //拿到terms
        System.out.println(terms.size());

        for(int i=0; i<terms.size(); i++) {
            String word = terms.get(i).getName(); //拿到词
            String natureStr = terms.get(i).getNatureStr(); //拿到词性
            if(expectedNature.contains(natureStr)) {
                System.out.println(word + ":" + natureStr);
            }
        }
    }
}