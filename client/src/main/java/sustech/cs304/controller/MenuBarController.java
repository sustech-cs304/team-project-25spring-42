package sustech.cs304.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sustech.cs304.App;
import sustech.cs304.service.CourseApiImpl;
import sustech.cs304.utils.AlterUtils;
import sustech.cs304.utils.FileUtils;
import sustech.cs304.entity.Course;

/**
 * Menu bar controller, responsible for handling menu actions and theme switching in the IDE.
 */
public class MenuBarController {

    @FXML
    private HBox customMenuBar;
    @FXML
    private Button fileMenuButton, colorMenuButton, courseMenuButton, terminalMenuButton, runMenuButton, helpMenuButton;

    private IDEController ideController;

    /**
     * Initializes the menu bar and sets up menu groups for different modes.
     */
    @FXML
    private void initialize() {
        // 文件菜单
        ContextMenu fileMenu = new ContextMenu();
        MenuItem openItem = new MenuItem("Open Folder");
        openItem.setOnAction(e -> openFolder());
        MenuItem saveThisItem = new MenuItem("Save this file");
        saveThisItem.setOnAction(e -> savePage());
        MenuItem saveAllItem = new MenuItem("Save all");
        saveAllItem.setOnAction(e -> saveAll());
        fileMenu.getItems().addAll(openItem, saveThisItem, saveAllItem);
        fileMenuButton.setOnMouseClicked(e -> fileMenu.show(fileMenuButton, javafx.geometry.Side.BOTTOM, 0, 0));

        // 主题菜单
        ContextMenu colorMenu = new ContextMenu();
        MenuItem vsItem = new MenuItem("vs");
        vsItem.setOnAction(e -> setColorVs());
        MenuItem vsDarkItem = new MenuItem("vs-dark");
        vsDarkItem.setOnAction(e -> setColorVsDark());
        MenuItem hcBlackItem = new MenuItem("hc-black");
        hcBlackItem.setOnAction(e -> setColorHcBlack());
        colorMenu.getItems().addAll(vsItem, vsDarkItem, hcBlackItem);
        colorMenuButton.setOnMouseClicked(e -> colorMenu.show(colorMenuButton, javafx.geometry.Side.BOTTOM, 0, 0));

        // 课程菜单
        ContextMenu courseMenu = new ContextMenu();
        MenuItem createCourseItem = new MenuItem("Create Course");
        createCourseItem.setOnAction(e -> createCourse());
        MenuItem invitationListItem = new MenuItem("Course Invitation");
        invitationListItem.setOnAction(e -> invitationList());
        courseMenu.getItems().addAll(createCourseItem, invitationListItem);
        courseMenuButton.setOnMouseClicked(e -> courseMenu.show(courseMenuButton, javafx.geometry.Side.BOTTOM, 0, 0));

        // 终端菜单
        ContextMenu terminalMenu = new ContextMenu();
        MenuItem openTerminalItem = new MenuItem("Open");
        openTerminalItem.setOnAction(e -> openTerminal());
        terminalMenu.getItems().addAll(openTerminalItem);
        terminalMenuButton.setOnMouseClicked(e -> terminalMenu.show(terminalMenuButton, javafx.geometry.Side.BOTTOM, 0, 0));

        // 运行菜单
        ContextMenu runMenu = new ContextMenu();
        MenuItem runItem = new MenuItem("run");
        runItem.setOnAction(e -> run());
        runMenu.getItems().addAll(runItem);
        runMenuButton.setOnMouseClicked(e -> runMenu.show(runMenuButton, javafx.geometry.Side.BOTTOM, 0, 0));

        // 帮助菜单
        ContextMenu helpMenu = new ContextMenu();
        MenuItem aboutItem = new MenuItem("About");
        helpMenu.getItems().addAll(aboutItem);
        helpMenuButton.setOnMouseClicked(e -> helpMenu.show(helpMenuButton, javafx.geometry.Side.BOTTOM, 0, 0));
    }

    /**
     * Opens a folder using the file tree controller.
     */
    @FXML
    public void openFolder() {
        ideController.getFileTreeController().handleSelectFolder();
    }

    /**
     * Saves the current page in the editor.
     */
    @FXML
    public void savePage() {
        ideController.getEditorController().savePage();
    }

    /**
     * Saves all open pages in the editor.
     */
    @FXML
    public void saveAll() {
        ideController.getEditorController().saveAll();
    }

    /**
     * Opens the integrated terminal.
     */
    @FXML
    private void openTerminal() {
        ideController.getJeditermController().open();
    }

    /**
     * Switches the theme to Visual Studio (light).
     */
    @FXML
    private void setColorVs() {
        ideController.getEditorController().setBackground("vs");
        ideController.getEditorController().setTheme("vs");
        ideController.changeImageColor("vs");
    }

    /**
     * Switches the theme to Visual Studio Dark.
     */
    @FXML
    private void setColorVsDark() {
        ideController.getEditorController().setBackground("vs-dark");
        ideController.getEditorController().setTheme("vs-dark");
        ideController.changeImageColor("vs-dark");
    }

    /**
     * Switches the theme to High Contrast Black.
     */
    @FXML
    private void setColorHcBlack() {
        ideController.getEditorController().setBackground("hc-black");
        ideController.getEditorController().setTheme("hc-black");
        ideController.changeImageColor("hc-black");
    }

    /**
     * Runs the current file in the terminal based on its type.
     */
    @FXML
    private void run() {
        File file = ideController.getEditorController().getCurrentFile();
        ideController.getJeditermController().open();
        if (file != null) {
            String filePath = file.getAbsolutePath();
            String extension = FileUtils.getExtension(file);
            ideController.getEditorController().savePage();

            try{
                if (extension.equals("java")) {
                    String javaHome = FileUtils.getEnvValue("JAVA_HOME");
                    if (javaHome == null) javaHome = System.getenv("java.home");
                    String command = javaHome + "/bin/java" + " " + filePath;
                    ideController.getJeditermController().executeCommand(command);
                } else if (extension.equals("py")) {
                    String pythonPath = FileUtils.getEnvValue("PYTHONPATH");
                    if (pythonPath == null) pythonPath = System.getenv("PYTHONPATH");
                    String command = "PYTHONPATH=" + pythonPath;
                    ideController.getJeditermController().executeCommand(command);
                    command = "python" + " " + filePath;
                    ideController.getJeditermController().executeCommand(command);
                } else if (extension.equals("c")) {
                    String command = "gcc" + " " + filePath + " -o " + file.getAbsolutePath().replace(".c", "");
                    ideController.getJeditermController().executeCommand(command);
                    command = file.getAbsolutePath().replace(".c", "");
                    ideController.getJeditermController().executeCommand(command);
                    command = "rm" + " " + file.getAbsolutePath().replace(".c", "");
                    ideController.getJeditermController().executeCommand(command);
                } else if (extension.equals("sh")) {
                    String command = "bash" + " " + filePath;
                    ideController.getJeditermController().executeCommand(command);
                } else if (extension.equals("js")) {
                    String command = "node" + " " + filePath;
                    ideController.getJeditermController().executeCommand(command);
                } else if (extension.equals("cpp")) {
                    String command = "g++" + " " + filePath + " -o " + file.getAbsolutePath().replace(".cpp", "");
                    ideController.getJeditermController().executeCommand(command);
                    command = file.getAbsolutePath().replace(".cpp", "");
                    ideController.getJeditermController().executeCommand(command);
                    command = "rm" + " " + file.getAbsolutePath().replace(".cpp", "");
                    ideController.getJeditermController().executeCommand(command);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Sets the IDE controller reference.
     * @param ideController The IDE controller
     */
    public void setIdeController(IDEController ideController) {
        this.ideController = ideController;
    }

    public void createCourse() {
        Map<String, String> courseInfo = AlterUtils.courseInputForm((Stage) this.customMenuBar.getScene().getWindow());
        CourseApiImpl courseApi = new CourseApiImpl();
        if (courseInfo != null) {
            String courseName = courseInfo.get("courseName");
            String userId = App.user.getUserId();
            courseApi.createCourse(courseName, userId);
        }
        ideController.getClassController().initializeClassChoiceScroll();
    }

    public void invitationList() {
        CourseApiImpl courseApi = new CourseApiImpl();
        String userId = App.user.getUserId();
        List<Course> courseList = courseApi.getCourseInvitationByUserId(userId);
        AlterUtils.showInvitationList((Stage) this.customMenuBar.getScene().getWindow(), courseList);
        ideController.getClassController().initializeClassChoiceScroll();
    }
}
