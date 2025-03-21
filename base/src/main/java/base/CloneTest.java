package base;

/** not suitable for array
 * @author yjl
 * @since 2024/5/31
 */
public class CloneTest implements Cloneable {
    private String name;
    private int age;
    private Student stu;

    public CloneTest(String name, int age, Student stu) {
        super();
        this.name = name;
        this.age = age;
        this.stu = stu;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", stu=" + stu +
                '}';
    }

    public static class Student implements Cloneable {
        private String name;
        private int age;

        public Student(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

    @Override
    protected Object clone()throws CloneNotSupportedException{
        CloneTest tea=(CloneTest) super.clone();
        tea.stu=(Student) tea.stu.clone();
        return tea;
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        Student stu = new Student("ls", 24);
        CloneTest tea = new CloneTest("zs", 30, stu);
        CloneTest teaClone = (CloneTest) tea.clone();
        stu.name = "lsV2";
        tea.name = "zsV2";
        System.out.println(teaClone);
        System.out.println(tea);
    }

}
