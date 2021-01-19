package com.apiyoo.anthorization.swy;

import com.apiyoo.anthorization.swy.util.A;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@SpringBootTest
class SwyApplicationTests {

    public static void main(String[] args) {
        List<A> datalist = new ArrayList<A>();
        A a = new A("2019/11/11");
        A b = new A("2023/11/11");
        A c = new A("2005/10/12");
        A d = new A("2005/10/12");
        A e = new A("2005/10/12");
        A f = new A("2089/01/13");
        A g = new A("2110/08/02");
        A h = new A("2068/02/28");
        A j = new A("2014/05/11");
        A k = new A("2005/06/09");
        A l = new A("");




        datalist.add(a);
        datalist.add(b);
        datalist.add(c);
        datalist.add(d);
        datalist.add(e);
        datalist.add(f);
        datalist.add(g);
        datalist.add(h);
        datalist.add(j);
        datalist.add(k);
        datalist.add(l);

        A min=  datalist.stream().min(Comparator.comparing(A::getTime)).get();
        A max=  datalist.stream().max(Comparator.comparing(A::getTime)).get();

        System.out.println(min.getTime());
        System.out.println(max.getTime());


    }

}
