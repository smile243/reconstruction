package base;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;

/**
 * @Description: 
 * @author: yjl
 * @date: 2022年02月11日 10:42
 */
public class WeChat {
    private static void openWechat(Robot robot){
        // 通过robot模拟按键Ctrl+Alt+w
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_W);
        // 松开Ctrl+Alt
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_ALT);
        // 让robot延迟1秒钟，防止页面响应时间长
        robot.delay(1000);
    }

    private static void findFriend(Robot robot,String username){
        // 模拟按键Ctrl+F
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_F);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        // 将好友的昵称先添加到系统剪贴板
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable tText = new StringSelection(username);
        clip.setContents(tText, null);
        // 模拟ctrl+V，完成粘贴功能
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        // 延迟1秒，防止查询慢
        robot.delay(1000);
        // 回车，定位到好友聊天输入框
        robot.keyPress(KeyEvent.VK_ENTER);
    }
    private static void sendMessage(String message) throws AWTException {
        // 将要发送的消息设置到剪贴板
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        Robot robot = new Robot();
        StringSelection text = new StringSelection(message);
        clip.setContents(text, null);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.delay(500);
        // 回车发送
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(500);
    }

    private static void closeWechat(Robot robot) {
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_W);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_ALT);
    }

    public static synchronized void sendMessage(String user, String message) {
        try {
            Robot robot = new Robot();

            openWechat(robot);

            findFriend(robot, user);

            sendMessage(message);

            closeWechat(robot);
        } catch (AWTException e) {
            System.out.println("发消息异常");
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i <3; i++) {
            WeChat.sendMessage("庄志豪", "测试自动脚本！");
        }
    }

}
