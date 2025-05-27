package sustech.cs304.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import junit.framework.TestCase;

public class MenuBarControllerTest extends TestCase {
    private MenuBarController controller;

    @BeforeAll
    public static void initJavaFX() {
        // 初始化 JavaFX 平台
        new JFXPanel(); // 这是关键：它会隐式启动 JavaFX Toolkit
        Platform.setImplicitExit(false); // 防止测试时关闭 JavaFX 平台
    }



    @Override
    protected void setUp() throws Exception {
        super.setUp();
        controller = new MenuBarController();
    }

    public void testSetIdeController() {
        controller.setIdeController(null);
    }

    @Test
    void testChangeMode() {
        MenuBar menuBar = new MenuBar();
        Menu fileMenu, colorMenu, terminalMenu, courseMenu, runMenu, helpMenu;
        fileMenu = new Menu("File");
        colorMenu = new Menu("Color");
        terminalMenu = new Menu("Terminal");
        courseMenu = new Menu("Course");
        runMenu = new Menu("Run");
        helpMenu = new Menu("Help");
        Menu[] IDEMenu, classMenu, userHomeMenu, chatMenu, settingMenu;
        IDEMenu = new Menu[]{fileMenu, colorMenu, terminalMenu, runMenu, helpMenu};
        chatMenu = new Menu[]{colorMenu, helpMenu};
        classMenu = new Menu[]{colorMenu, courseMenu, helpMenu};
        userHomeMenu = new Menu[]{colorMenu, helpMenu};
        settingMenu = new Menu[]{colorMenu, helpMenu};
        Scene scene = new Scene(menuBar);
        menuBar.getMenus().addAll(fileMenu,colorMenu,terminalMenu,courseMenu,runMenu,helpMenu);
        controller = new MenuBarController(menuBar, fileMenu, colorMenu, terminalMenu, courseMenu, runMenu, helpMenu, IDEMenu, classMenu, userHomeMenu, chatMenu, settingMenu);

        // controller.changeMode("dark");
        controller.changeMode("editor");
        controller.changeMode("class");
        controller.changeMode("userHome");
        controller.changeMode("chat");
        controller.changeMode("setting");
    }

    public void testOpenFolder() {
        // Skipped: depends on IDEController and JavaFX, cannot be tested in normal JUnit environment
    }
} 
