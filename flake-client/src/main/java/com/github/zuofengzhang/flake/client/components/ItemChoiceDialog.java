package com.github.zuofengzhang.flake.client.components;

import com.sun.javafx.scene.control.skin.resources.ControlResources;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ItemChoiceDialog extends Dialog<ItemChoiceData> {

    private final CheckBox sitCheckBox;
    private final GridPane grid;
    private final Label label;
    private final List<RadioButton> radioButtons;
    private ItemChoiceData selectedItem;
    private final SimpleBooleanProperty sitProperty = new SimpleBooleanProperty();

    public ItemChoiceDialog(String title, String header, Collection<ItemChoiceData> choices) {
        DialogPane dialogPane = getDialogPane();

        // -- grid
        this.grid = new GridPane();
        this.grid.setHgap(10);
        this.grid.setMaxWidth(Double.MAX_VALUE);
        this.grid.setAlignment(Pos.CENTER_LEFT);

        // -- label
        label = new Label(dialogPane.getContentText());
        label.setMaxWidth(Double.MAX_VALUE);
        label.setMaxHeight(Double.MAX_VALUE);
        label.getStyleClass().add("content");
        label.setWrapText(true);
        label.setPrefWidth(360);
        label.setPrefWidth(Region.USE_COMPUTED_SIZE);
        label.textProperty().bind(dialogPane.contentTextProperty());

//        setTitle(ControlResources.getString("Dialog.confirm.title"));
        setTitle(title);
//        dialogPane.setHeaderText(ControlResources.getString("Dialog.confirm.header"));
        dialogPane.setHeaderText(header);
        dialogPane.getStyleClass().add("choice-dialog");
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        sitCheckBox = new CheckBox("is sit");
        ToggleGroup toggleGroup = new ToggleGroup();
        radioButtons = choices.stream().map(choice -> {

            RadioButton radioButton = new RadioButton(choice.getText());
            radioButton.setToggleGroup(toggleGroup);
            radioButton.selectedProperty().addListener((v, t1, t2) -> {
                if (t2) {
                    selectedItem = choice;
                    sitCheckBox.setSelected(choice.isSit());
//                        sitProperty.setValue(choice.isSit());
                }
            });
            return radioButton;
        }).collect(Collectors.toList());
        sitProperty.bind(sitCheckBox.selectedProperty());
        updateGrid();
        setResultConverter((dialogButton) -> {
            ButtonBar.ButtonData data = dialogButton == null ? null : dialogButton.getButtonData();
            return data == ButtonBar.ButtonData.OK_DONE ? getSelectedItem() : null;
        });
    }

    private void updateGrid() {
        grid.getChildren().clear();

        grid.add(label, 0, 0);
        for (int i = 0; i < radioButtons.size(); i++) {
            RadioButton radioButton = radioButtons.get(i);
            grid.add(radioButton, 0, i + 1);
        }
        grid.add(new Separator(), 0, radioButtons.size() + 1);
        grid.add(sitCheckBox, 0, radioButtons.size() + 2);
        getDialogPane().setContent(grid);

        Platform.runLater(sitCheckBox::requestFocus);
    }

    private ItemChoiceData getSelectedItem() {
        if (selectedItem == null) {
            return null;
        }
        ItemChoiceData choiceData = this.selectedItem.clone();
        choiceData.setSit(sitProperty.get());
        return choiceData;
    }


}

