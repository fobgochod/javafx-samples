package com.fobgochod.api;

import com.fobgochod.util.JdbcUtil;
import com.fobgochod.util.SnowFlake;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * 功能描述
 *
 * @author zhouxiao
 * @date 2019/5/17
 */
public class Controller {

    public GridPane gridPane;
    public TextField url;
    public TextField username;
    public TextField password;
    public TextField filePath;
    public Label show;


    @FXML
    public void run(ActionEvent actionEvent) {
        show.setText("");
        JdbcUtil.initSql2(url.getText(), username.getText(), password.getText(), filePath.getText());
        show.setText("脚本生成成功！" + SnowFlake.getInstance().newId());
    }

    @FXML
    public void chooseFile(ActionEvent actionEvent) {
        Stage stage = (Stage) gridPane.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        File file = fileChooser.showOpenDialog(stage);

        filePath.setText(file.getAbsolutePath());
    }
}
