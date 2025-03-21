package netty;



public interface Timer {
    /**添加新定时任务
     * @author yjl
     * @since 2024/6/19
     */
    void add(TimerTask timerTask);

    /**推动指针
     * @author yjl
     * @since 2024/6/19
     */
    void advanceClock(long timeout);

    /**等待执行的任务数
     * @author yjl
     * @since 2024/6/19
     */
    int size();

    /**关闭服务
     * @author yjl
     * @since 2024/6/19
     */
    void shutdown();
}
