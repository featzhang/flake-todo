package com.github.zuofengzhang.flake.client.controller;

import com.github.zuofengzhang.flake.client.constraints.FlakeLabel;
import com.github.zuofengzhang.flake.client.entity.TaskDto;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import net.rgielen.fxweaver.core.FxmlView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author averyzhang
 * @date 2021/2/20
 */
@Component
@FxmlView("task-detail.fxml")
public class TaskDetailController implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(TaskDetailController.class);
    @FXML
    public TextField titleTextField;
    @FXML
    public TitledPane contentTitlePane;
    @FXML
    public TitledPane attachmentTitlePane;
    @FXML
    public TitledPane historyTitlePane;
    @FXML
    public ListView<String> attachmentListView;
    @FXML
    public ListView<String> historyListView;
    public TextArea contentTextArea;
    public Accordion accordion;


    public void onDeleteAttachmentMenu(ActionEvent actionEvent) {
        String item = attachmentListView.getSelectionModel().getSelectedItem();
        attachmentListView.getItems().remove(item);
        taskDto.attachmentProperty().set(attachmentListView.getItems().stream().sorted().collect(Collectors.joining(";")));
    }

    public void onAddAttachmentMenu(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getTarget();
        ContextMenu cm = menuItem.getParentPopup();
        Scene scene = cm.getScene();
        Window window = scene.getWindow();
        logger.info("open file chooser");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file1 = fileChooser.showOpenDialog(new Stage());
        if (file1 != null) {
            attachmentListView.getItems().add(file1.getAbsolutePath());
        }
        taskDto.attachmentProperty().set(attachmentListView.getItems().stream().sorted().collect(Collectors.joining(";")));
    }

    public void onOpenAttachmentMenu(ActionEvent actionEvent) {
        String selectedItem = attachmentListView.getSelectionModel().getSelectedItem();
        doOpenFile(selectedItem);
    }


    /**
     * open attachments
     */
    public void onAttachmentMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            String selectedItem = attachmentListView.getSelectionModel().getSelectedItem();

            doOpenFile(selectedItem);
        }
    }

    private void doOpenFile(String selectedItem) {
        try {
            Desktop.getDesktop().open(new File(selectedItem));
        } catch (IOException | RuntimeException e) {
            logger.error("", e);
            try {
                ProcessBuilder processBuilder = new ProcessBuilder();
                processBuilder.command("open", selectedItem);
                Process process = processBuilder.start();
                int waitFor = process.waitFor();
                logger.info("waitFor: {}", waitFor);
            } catch (IOException | InterruptedException ioException) {
                logger.error("", ioException);
            }
        }
    }

    private TaskDto taskDto;

    public void setData(TaskDto taskDto) {
        this.taskDto = taskDto;
        titleTextField.setText(taskDto.getTitle());
        contentTextArea.setText(taskDto.getContent());
        {
            List<String> historyList = new ArrayList<>();
            String createTimeFormatted = LocalDateTime.ofInstant(Instant.ofEpochMilli(taskDto.getCreatedTime()), ZoneId.systemDefault()).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            historyList.add(FlakeLabel.CREATE + ":\t" + createTimeFormatted);
            String lastUpdatedTimeFormatted = LocalDateTime.ofInstant(Instant.ofEpochMilli(taskDto.getUpdateTime()), ZoneId.systemDefault()).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            historyList.add(FlakeLabel.LAST_UPDATE + ":\t" + lastUpdatedTimeFormatted);

            if (taskDto.isFinished()) {
                String endTimeFormatted = LocalDateTime.ofInstant(Instant.ofEpochMilli(taskDto.getEndTime()), ZoneId.systemDefault()).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                historyList.add(FlakeLabel.FINISH + ":\t" + endTimeFormatted);
            }
            historyListView.getItems().clear();
            historyListView.getItems().addAll(historyList);
        }
        // attachments
        ObservableList<String> items = attachmentListView.getItems();
        items.clear();
        String attachment = taskDto.getAttachment();
        if (attachment != null) {
            List<String> list = Arrays.stream(attachment.split(";")).collect(Collectors.toList());
            items.addAll(list);
        }

        taskDto.titleProperty().bind(titleTextField.textProperty());

//        taskDto.contentProperty().bind(contentTextArea.textProperty());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        accordion.setExpandedPane(contentTitlePane);
    }

    public void onContentMouseExited(MouseEvent mouseEvent) {
        saveChangedContentOnExisted();
    }

    private void saveChangedContentOnExisted() {
        String text = contentTextArea.getText();
        String content = taskDto.getContent();
        if (!Objects.equals(text, content)) {
            taskDto.setContent(text);
        }
    }

    public void onRootPaneKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.isMetaDown()) {
            if (keyEvent.getCode() == KeyCode.W) {
                // save changed
                saveChangedContentOnExisted();

                // close
                Object source = keyEvent.getSource();
                GridPane sourceNode = (GridPane) source;
                Scene scene = sourceNode.getScene();
                Window window = scene.getWindow();
                window.hide();
            }
        }
    }

    public void onPasteAttachmentMenu(ActionEvent actionEvent) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        if (clipboard.hasUrl() || clipboard.hasString()) {
            String url = clipboard.getString();
            attachmentListView.getItems().add(url);
            taskDto.attachmentProperty().set(attachmentListView.getItems().stream().sorted().collect(Collectors.joining(";")));
        } else if (clipboard.hasFiles()) {
            for (File file : clipboard.getFiles()) {
                attachmentListView.getItems().add(file.getAbsolutePath());
            }
            taskDto.attachmentProperty().set(attachmentListView.getItems().stream().sorted().collect(Collectors.joining(";")));
        } else if (clipboard.hasImage()) {
            Image image = clipboard.getImage();
            String url = image.getUrl();
            attachmentListView.getItems().add(url);
            taskDto.attachmentProperty().set(attachmentListView.getItems().stream().sorted().collect(Collectors.joining(";")));
        }
    }
}
