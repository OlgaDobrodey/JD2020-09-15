package by.it.lapkovskiy.jd01_06;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskA2 {

    private  static  String[] w ={ };
    private static  int[] count ={ };

    private static int pos(String word){
        for (int i = 0; i < w.length; i++) {
            if(w[i].equals(word)){
                return  i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        StringBuilder text = new StringBuilder(Poem.text);
        Pattern pattern = Pattern.compile("[а-яА-ЯёЁ]+");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String word = matcher.group();
            int position = pos(word);
            if(position>=0){
                count[position]++;
            }
            else {
                int last =w.length;
                w = Arrays.copyOf(w,last+1);
                w[last] = word;
                count =Arrays.copyOf(count,last+1);
                count[last] =1;
            }
        }
        for (int i = 0; i < w.length; i++) {
            System.out.println(w[i]+"="+count[i]);
        }
    }
}
