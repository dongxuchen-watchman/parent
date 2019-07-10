package chapter5;

import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by Administrator on 2019/6/27.
 */
public class ExceptionTest {

    public String testException () throws Exception{
        ArrayList list = null;
        try{

            int i = 10;
            double k = i / 0 ;
            System.out.println(list.size());
            System.out.println(k);
            return "2";
        }catch (ArithmeticException e){
//            System.out.println("=====" + e.toString());
        }
        return "3";



    }

    public static void main(String[] args) {
        String logs = "";
        ExceptionTest exceptionTest = new ExceptionTest();
        try {
            String re = exceptionTest.testException();
            System.out.println(re);
        } catch (Exception e) {
//           e.printStackTrace();
//            System.out.println("============");
//            System.out.println(e.toString());
//            System.out.println("============");
           logs = e.getMessage();
//            System.out.println("===" + logs);
        }

        String tt = "";

        System.out.println(tt.split(";").length);


    }

}
