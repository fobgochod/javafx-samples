package com.fobgochod.api;

import com.fobgochod.util.JdbcUtil;
import com.fobgochod.util.SnowFlake;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * 功能描述
 *
 * @author zhouxiao
 * @date 2019/5/17
 */
public class Controller {

    public TextField url;
    public TextField username;
    public TextField password;
    public Label show;

    @FXML
    public void run(ActionEvent actionEvent) {
        JdbcUtil.data(url.getText(), username.getText(), password.getText());
        show.setText("脚本生成成功！" + SnowFlake.getInstance().newId());
    }

}
