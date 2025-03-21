package jvm;

/**
 * @Description: 解析调用中非虚方法、虚方法的测试
 * invokespecial invokestatic为非虚方法 编译器确定
 * invokevirtual 虚方法
 * @author: yjl
 * @date: 2022年02月11日 9:38
 */
class Father{
    public Father(){
        System.out.println("fahter构造器");
    }

    public static void showStatic(String str) {
        System.out.println("father "+str);
    }
    public final void showFinal(){
        System.out.println("father show final");
    }
    public void showCommon(){
        System.out.println("father普通方法");
    }

}

public class Son extends Father{
    public Son(){
        //invokespecial
        super();
    }

    public Son(int age){
        //invokespecial
        this();
    }
    //不是重写的父类的静态方法，因为静态方法不可重写
    public static void showStatic(String str) {
        System.out.println("son "+str);
    }

    private  void showPrivate(String str) {
        System.out.println("son private "+str);
    }
    public void show(){
        //invokestatic
        showStatic("yjl");
        //invokestatic
        super.showStatic("good");
        //invokespecial
        showPrivate("hello");
        //invokespecial
        super.showCommon();
        //invokevirtual  此方法为final不可被子类重写
        showFinal();
        //invokevirtual
        showCommon();
    }

    public static void main(String[] args) {
        Son so=new Son();
        so.show();
    }
}
