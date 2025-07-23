package jvm;

import real.Student;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * SoftReference 回收分析测试
 * 
 * 运行参数：-Xmx10m -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps
 * 
 * SoftReference 不被回收的原因分析：
 * 1. 内存压力不够大
 * 2. JVM 的 SoftReference 回收策略
 * 3. 堆内存分配情况
 */
public class SoftReferenceAnalysisTest {
    
    public static void main(String[] args) {
        System.out.println("=== SoftReference 回收分析测试 ===");
        
        // 打印初始内存状态
        printMemoryInfo("初始状态");
        
        // 测试1：基本的 SoftReference 测试
        testBasicSoftReference();
        
        // 测试2：逐步增加内存压力
        testWithIncreasingMemoryPressure();
        
        // 测试3：强制内存不足
        testWithMemoryExhaustion();
    }
    
    /**
     * 基本的 SoftReference 测试（您原来的代码逻辑）
     */
    public static void testBasicSoftReference() {
        System.out.println("\n=== 测试1：基本 SoftReference 测试 ===");
        
        Student student = new Student();
        student.setName("TestStudent");
        SoftReference<Student> softReference = new SoftReference<>(student);
        student = null; // 移除强引用
        
        System.out.println("创建 SoftReference 后: " + softReference.get());
        printMemoryInfo("创建 SoftReference 后");
        
        // 手动触发 GC
        System.gc();
        System.out.println("第一次 GC 后: " + softReference.get());
        printMemoryInfo("第一次 GC 后");
        
        // 分配一些内存（您原来的 7MB 数组）
        try {
            byte[] b = new byte[1024 * 925 * 7]; // 约 6.4MB
            System.out.println("分配 6.4MB 数组后: " + softReference.get());
            printMemoryInfo("分配大数组后");
            
            System.gc();
            System.out.println("分配数组后 GC: " + softReference.get());
            printMemoryInfo("分配数组后 GC");
            
        } catch (OutOfMemoryError e) {
            System.out.println("OutOfMemoryError: " + e.getMessage());
            System.out.println("OOM 后 SoftReference: " + softReference.get());
        }
    }
    
    /**
     * 逐步增加内存压力的测试
     */
    public static void testWithIncreasingMemoryPressure() {
        System.out.println("\n=== 测试2：逐步增加内存压力 ===");
        
        Student student = new Student();
        student.setName("PressureTestStudent");
        SoftReference<Student> softReference = new SoftReference<>(student);
        student = null;
        
        System.out.println("初始 SoftReference: " + softReference.get());
        
        List<byte[]> memoryConsumers = new ArrayList<>();
        
        try {
            // 逐步分配内存，每次 1MB
            for (int i = 1; i <= 15; i++) {
                byte[] array = new byte[1024 * 1024]; // 1MB
                memoryConsumers.add(array);
                
                System.out.printf("分配第 %d MB 后: SoftReference = %s\n", 
                    i, softReference.get() != null ? "存在" : "已回收");
                
                if (softReference.get() == null) {
                    System.out.println("*** SoftReference 在分配第 " + i + " MB 时被回收 ***");
                    break;
                }
                
                // 每分配几MB就触发一次GC
                if (i % 2 == 0) {
                    System.gc();
                    System.out.printf("第 %d MB 后 GC: SoftReference = %s\n", 
                        i, softReference.get() != null ? "存在" : "已回收");
                    
                    if (softReference.get() == null) {
                        System.out.println("*** SoftReference 在第 " + i + " MB 后的 GC 中被回收 ***");
                        break;
                    }
                }
                
                printMemoryInfo("分配第 " + i + " MB 后");
            }
        } catch (OutOfMemoryError e) {
            System.out.println("OutOfMemoryError 在分配内存时发生: " + e.getMessage());
            System.out.println("OOM 后 SoftReference: " + 
                (softReference.get() != null ? "仍然存在" : "已被回收"));
        }
    }
    
    /**
     * 强制内存耗尽的测试
     */
    public static void testWithMemoryExhaustion() {
        System.out.println("\n=== 测试3：强制内存耗尽测试 ===");
        
        // 创建多个 SoftReference
        List<SoftReference<byte[]>> softReferences = new ArrayList<>();
        
        // 创建一些 SoftReference 对象
        for (int i = 0; i < 5; i++) {
            byte[] data = new byte[1024 * 1024]; // 1MB
            SoftReference<byte[]> softRef = new SoftReference<>(data);
            softReferences.add(softRef);
            System.out.println("创建 SoftReference " + i + ": " + (softRef.get() != null));
        }
        
        System.out.println("创建完所有 SoftReference，开始内存压力测试...");
        
        List<byte[]> hardReferences = new ArrayList<>();
        
        try {
            // 持续分配内存直到 OOM
            for (int i = 0; i < 20; i++) {
                byte[] array = new byte[1024 * 1024]; // 1MB
                hardReferences.add(array);
                
                // 检查 SoftReference 状态
                int aliveCount = 0;
                for (int j = 0; j < softReferences.size(); j++) {
                    if (softReferences.get(j).get() != null) {
                        aliveCount++;
                    }
                }
                
                System.out.printf("分配第 %d MB，存活的 SoftReference: %d/%d\n", 
                    i + 1, aliveCount, softReferences.size());
                
                if (aliveCount == 0) {
                    System.out.println("*** 所有 SoftReference 都被回收了 ***");
                    break;
                }
                
                // 定期触发 GC
                if (i % 3 == 0) {
                    System.gc();
                    Thread.sleep(100); // 给 GC 一些时间
                }
            }
        } catch (OutOfMemoryError e) {
            System.out.println("OutOfMemoryError: " + e.getMessage());
            
            // 检查最终状态
            int finalAliveCount = 0;
            for (SoftReference<byte[]> softRef : softReferences) {
                if (softRef.get() != null) {
                    finalAliveCount++;
                }
            }
            System.out.println("OOM 后存活的 SoftReference: " + finalAliveCount + "/" + softReferences.size());
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * 打印内存信息
     */
    public static void printMemoryInfo(String stage) {
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        
        System.out.printf("[%s] 内存状态 - 最大: %dMB, 总计: %dMB, 已用: %dMB, 空闲: %dMB, 使用率: %.1f%%\n",
            stage,
            maxMemory / 1024 / 1024,
            totalMemory / 1024 / 1024,
            usedMemory / 1024 / 1024,
            freeMemory / 1024 / 1024,
            (double) usedMemory / maxMemory * 100
        );
    }
}
