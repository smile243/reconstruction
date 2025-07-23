# SoftReference 不被回收的原因分析

## 问题描述
设置了 JVM 的 `-Xmx10m` 参数，但是 SoftReference 没有被回收。

## 原因分析

### 1. **内存压力不够大**
```java
byte[] b = new byte[1024*925*7]; // 约 6.4MB
```
- 您的代码分配了约 6.4MB 的数组
- 在 10MB 的堆内存中，6.4MB 可能还不足以触发 SoftReference 的回收
- JVM 会尽量保留 SoftReference，只有在真正内存不足时才回收

### 2. **SoftReference 回收策略**
SoftReference 的回收遵循以下规则：
- **不是在每次 GC 时都回收**
- **只有在内存真正不足时才回收**
- **回收时机由 JVM 的内存管理策略决定**

### 3. **JVM 内存分配特点**
- 堆内存不是立即分配到最大值
- 初始堆内存较小，会逐步增长
- 只有当堆内存增长到接近最大值时，才会触发更激进的 GC

## 解决方案

### 方案1：增加内存压力
```java
// 持续分配内存直到真正的内存不足
List<byte[]> memoryConsumers = new ArrayList<>();
try {
    while (true) {
        memoryConsumers.add(new byte[1024 * 1024]); // 持续分配 1MB
        if (softReference.get() == null) {
            System.out.println("SoftReference 被回收了！");
            break;
        }
    }
} catch (OutOfMemoryError e) {
    System.out.println("OOM: " + softReference.get());
}
```

### 方案2：使用更小的堆内存
```bash
# 使用更小的堆内存，比如 5MB
java -Xmx5m -Xms5m YourClass
```

### 方案3：添加 GC 参数观察
```bash
java -Xmx10m -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps YourClass
```

### 方案4：调整 SoftReference 回收策略
```bash
# 调整 SoftRefLRUPolicyMSPerMB 参数（默认 1000ms）
java -Xmx10m -XX:SoftRefLRUPolicyMSPerMB=0 YourClass
```

## 测试代码改进

### 原始代码问题
```java
public class ReferenceTest {
    public static void main(String[] args) {
        Student student = new Student();
        SoftReference<Student> softReference = new SoftReference<>(student);
        student = null;
        System.out.println(softReference.get());
        System.gc();
        System.out.println("after gc:");
        System.out.println(softReference.get());
        byte[] b = new byte[1024*925*7]; // 问题：内存压力不够
        System.gc();
        System.out.println(softReference.get());
    }
}
```

### 改进后的代码
```java
public class ImprovedReferenceTest {
    public static void main(String[] args) {
        Student student = new Student();
        SoftReference<Student> softReference = new SoftReference<>(student);
        student = null;
        
        System.out.println("初始: " + softReference.get());
        
        List<byte[]> memoryConsumers = new ArrayList<>();
        
        try {
            // 持续分配内存
            for (int i = 1; i <= 20; i++) {
                byte[] array = new byte[1024 * 1024]; // 1MB
                memoryConsumers.add(array);
                
                System.out.printf("分配第 %d MB: %s\n", 
                    i, softReference.get() != null ? "存在" : "已回收");
                
                if (softReference.get() == null) {
                    System.out.println("SoftReference 在第 " + i + " MB 时被回收");
                    break;
                }
                
                if (i % 2 == 0) {
                    System.gc();
                }
            }
        } catch (OutOfMemoryError e) {
            System.out.println("OOM: " + softReference.get());
        }
    }
}
```

## 关键要点

### 1. **SoftReference 回收条件**
- 内存真正不足
- 即将发生 OutOfMemoryError
- JVM 认为需要回收软引用来释放内存

### 2. **内存压力的判断**
- 不是基于绝对内存大小
- 而是基于可用内存和内存分配请求的关系
- 需要持续的内存分配压力

### 3. **测试建议**
- 使用更小的堆内存（如 `-Xmx5m`）
- 持续分配内存而不是一次性分配
- 监控 GC 日志观察回收行为
- 使用 JVM 参数调整 SoftReference 策略

### 4. **实际应用中的考虑**
- SoftReference 适合做缓存
- 不要依赖 SoftReference 的精确回收时机
- 结合 WeakReference 和手动清理机制

## 运行建议

```bash
# 编译
javac -cp . jvm/SoftReferenceAnalysisTest.java

# 运行（观察详细的回收过程）
java -Xmx10m -XX:+PrintGC -XX:+PrintGCDetails -XX:SoftRefLRUPolicyMSPerMB=0 jvm.SoftReferenceAnalysisTest

# 或者使用更小的堆内存
java -Xmx5m -XX:+PrintGC jvm.SoftReferenceAnalysisTest
```

这样您就能观察到 SoftReference 的实际回收行为了。
