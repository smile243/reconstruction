/**
 * @author yjl
 * @since 2025/5/7
 */
public class test {
    public static void main(String[] args) {
        String haystack="aaaaa",needle="adcadde";
        int hl = haystack.length();
        int nl = needle.length();
        int hs=0,ns=0;
        int[] over = getOverlapArr(needle);
        while(hs<hl){
            if(haystack.charAt(hs)==needle.charAt(ns)){
                if(ns==nl-1){
                    System.out.println( hs-nl+1);
                    return;
                }else{
                    hs++;
                    ns++;
                }
            }else{
                if(ns==0){
                    hs++;
                }else{
                    ns = over[ns-1];
                }
            }
        }
        System.out.println(-1);
    }

    public static int[] getOverlapArr(String s) {
        int l = s.length();
        int[] r = new int[l];
        for (int i = 1; i < l; i++) {
            r[i]=0;
            int index = i-1;
            int k = r[i-1];
            char c = s.charAt(i);
            while(k!=0&&c!=s.charAt(k)){
                k=r[r[--index]];
            }
            if( s.charAt(k)==c){
                r[i]=k+1;
            }
        }
        return r;
    }
}
