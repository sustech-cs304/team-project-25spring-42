# How to run program with javafx package (not recommend)



If you meet the question that `错误: 缺少 JavaFX 运行时组件, 需要使用该组件来运行此应用程序`, you need to config something in your idea.



Firstly, find **Project Structure** in **file** menu.![run_javafx_picture1](.\picture\run_javafx_picture1.png)







Secondly, find **Librabries** and click the **+** icon, then click **java**. 

![run_javafx_picture1](.\picture\run_javafx_picture2.png)





Then, you need to add all of the jar package in this project's lib directory.

![run_javafx_picture1](.\picture\run_javafx_picture3.png)









When execute the program, don't click the icon directly. Instead, you need click **Current File**, and then find the **Run with parameters... ** .

![run_javafx_picture1](.\picture\run_javafx_picture4.png)



Then you need to find the **Modify options** and find **Add VM options**.

![run_javafx_picture1](.\picture\run_javafx_picture5.png)



And next you need to type this string.

```
--module-path ${PATH_TO_FX} --add-modules javafx.controls,javafx.fxml
```

![run_javafx_picture1](.\picture\run_javafx_picture6.png)



Finally you can successfully execute the program.







# Using maven to run (recommend)



change the file that you want to run in

![run_javafx_picture1](.\picture\run_javafx_picture7.png)







in the main directiory.

```bash
mvn clean install
mvn javafx:run
```





