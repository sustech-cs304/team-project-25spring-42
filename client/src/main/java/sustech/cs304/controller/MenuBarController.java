package sustech.cs304.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
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
    private MenuBar menuBar;

    @FXML
    private Menu fileMenu, colorMenu, terminalMenu, courseMenu, runMenu, helpMenu;

    private Menu[] IDEMenus;
    private Menu[] classMenus;
    private Menu[] userHomeMenus;
    private Menu[] chatMenus;
    private Menu[] settingMenus;
    private String css;

    private IDEController ideController;

    public MenuBarController() {}

    // Mock
    public MenuBarController(MenuBar menuBar, Menu fileMenu, Menu colorMenu, Menu terminalMenu, Menu courseMenu, Menu runMenu, Menu helpMenu, Menu[] IDEMenus, Menu[] classMenus, Menu[] userHomeMenus, Menu[] chatMenus, Menu[] settingMenus) {
        this.menuBar = menuBar;
        this.fileMenu = fileMenu;
        this.colorMenu = colorMenu;
        this.terminalMenu = terminalMenu;
        this.courseMenu = courseMenu;
        this.runMenu = runMenu;
        this.helpMenu = helpMenu;
        this.IDEMenus = IDEMenus;
        this.classMenus = classMenus;
        this.userHomeMenus = userHomeMenus;
        this.chatMenus = chatMenus;
        this.settingMenus = settingMenus;
    }

    /**
     * Initializes the menu bar and sets up menu groups for different modes.
     */
    @FXML
    private void initialize() {
        IDEMenus = new Menu[]{fileMenu, colorMenu, terminalMenu, runMenu, helpMenu};
        chatMenus = new Menu[]{colorMenu, helpMenu};
        classMenus = new Menu[]{colorMenu, courseMenu, helpMenu};
        userHomeMenus = new Menu[]{colorMenu, helpMenu};
        settingMenus = new Menu[]{colorMenu, helpMenu};
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            menuBar.setUseSystemMenuBar(true);
        }
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
        changeTheme("vs");
    }

    /**
     * Switches the theme to Visual Studio Dark.
     */
    @FXML
    private void setColorVsDark() {
        changeTheme("vs-dark");
    }

    /**
     * Switches the theme to High Contrast Black.
     */
    @FXML
    private void setColorHcBlack() {
        changeTheme("hc-black");
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
     * Changes the theme for the IDE and updates all relevant components.
     * @param theme The theme name
     */
    private void changeTheme(String theme) {
        ideController.getJeditermController().changeTheme(theme);
        ideController.getEditorController().setBackground(theme);
        ideController.getEditorController().setTheme(theme);
        ideController.changeImageColor(theme);
        Scene scene = menuBar.getScene();
        if (scene != null) {
            scene.getStylesheets().remove(this.css);
            this.css = this.getClass().getResource("/css/style-" + theme + ".css").toExternalForm();
            scene.getStylesheets().add(this.css);
        }
    }

    /**
     * Sets the IDE controller reference.
     * @param ideController The IDE controller
     */
    public void setIdeController(IDEController ideController) {
        this.ideController = ideController;
    }

    /**
     * Changes the visible menu group based on the current mode.
     * @param mode The mode name (editor, class, userHome, etc.)
     */
    public void changeMode(String mode) {
        Scene scene = menuBar.getScene();
        if (scene != null) {
            if (mode.equals("editor")) {
                for (Menu menu : this.chatMenus) {
                    menu.setVisible(false);
                }
                for (Menu menu : this.classMenus) {
                    menu.setVisible(false);
                }
                for (Menu menu : this.userHomeMenus) {
                    menu.setVisible(false);
                }
                for (Menu menu : this.settingMenus) {
                    menu.setVisible(false);
                }
                for (Menu menu : this.IDEMenus) {
                    menu.setVisible(true);
                }
            } else if (mode.equals("class")) {
                for (Menu menu : this.IDEMenus) {
                    menu.setVisible(false);
                }
                for (Menu menu : this.chatMenus) {
                    menu.setVisible(false);
                }
                for (Menu menu : this.userHomeMenus) {
                    menu.setVisible(false);
                }
                for (Menu menu : this.settingMenus) {
                    menu.setVisible(false);
                }
                for (Menu menu : this.classMenus) {
                    menu.setVisible(true);
                }
           } else if (mode.equals("userHome")) {
                for (Menu menu : IDEMenus) {
                    menu.setVisible(false);
                }
                for (Menu menu : this.chatMenus) {
                    menu.setVisible(false);
                }
                for (Menu menu : this.classMenus) {
                    menu.setVisible(false);
                }
                for (Menu menu : this.settingMenus) {
                    menu.setVisible(false);
                }
                for (Menu menu : this.userHomeMenus) {
                    menu.setVisible(true);
                }
            } else if (mode.equals("chat")) {
                for (Menu menu : IDEMenus) {
                    menu.setVisible(false);
                }
                for (Menu menu : this.classMenus) {
                    menu.setVisible(false);
                }
                for (Menu menu : this.userHomeMenus) {
                    menu.setVisible(false);
                }
                for (Menu menu : this.settingMenus) {
                    menu.setVisible(false);
                }
                 for (Menu menu : this.chatMenus) {
                    menu.setVisible(true);
                }
            } else if (mode.equals("setting")) {
                for (Menu menu : IDEMenus) {
                    menu.setVisible(false);
                }
                for (Menu menu : this.classMenus) {
                    menu.setVisible(false);
                }
                for (Menu menu : this.userHomeMenus) {
                    menu.setVisible(false);
                }
                for (Menu menu : this.chatMenus) {
                    menu.setVisible(false);
                }
                for (Menu menu : this.settingMenus) {
                    menu.setVisible(true);
                }
            }
        }
    }

    public void createCourse() {
        Map<String, String> courseInfo = AlterUtils.courseInputForm((Stage) this.menuBar.getScene().getWindow());
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
        AlterUtils.showInvitationList((Stage) this.menuBar.getScene().getWindow(), courseList);
        ideController.getClassController().initializeClassChoiceScroll();
    }
}
