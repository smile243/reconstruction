package base;

/**
 * @Description: 滑动窗口
 * @author: scott
 * @date: 2021年05月13日 9:16
 */
public class Window {
    /**
     * 思路:1.source中从左到右依次寻找能包含target的子串r 2.然后从r的左边开始缩减直至不能包含target
     * @param s
     * @param t
     * @return
     */
    public String minWindow(String s, String t) {
        int left = 0, right = 0;
        String res = s;
        StringBuilder window=new StringBuilder();
        while(right < s.length()) {
            window.append(s.charAt(right));
            right++;
            // 如果符合要求，说明窗口构造完成，移动 left 缩小窗口
            while (this.Fit(t,window)) {
                // 如果这个窗口的子串更短，则更新 res
                res = res.length()>window.toString().length()?window.toString():res;
                window.deleteCharAt(0);
                left++;
            }
        }
        if(left==0&&!s.equals(t)){
            return "";
        }
        return res;
    }
    public boolean Fit(String t,StringBuilder window){
        if(window.length()<t.length()){
            return false;
        }
        String win=window.toString();
        for(Character c:t.toCharArray()){
            if(!win.contains(c.toString())){
                return false;
            }else{
                win=win.replaceFirst(c.toString(),"");
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Window w=new Window();
        String s=w.minWindow("bbaa","aba");
        System.out.println(s);
    }
}
