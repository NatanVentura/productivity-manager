package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import model.entities.Task;
import model.services.TaskServices;

public class CheckCell extends TableCell<Task, Boolean> {
    private final CheckBox box = new CheckBox();
    private TaskServices service;
    
    public CheckCell(TasksViewController controller){
        box.setOnAction(new EventHandler<ActionEvent>() {
        	
            @Override
            public void handle(ActionEvent e) {
            	Task obj = getTableView().getItems().get(getIndex());
                obj.setDone(box.isSelected());
                service.setDone(obj);
                controller.checkDone();
            }
        });
    }
    
    public void setService(TaskServices service) {
    	this.service = service;
    }

    @Override
    protected void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);

        if(!empty){
            box.setSelected(item);
            //setText(getTableView().getItems().get(getIndex()).toString());
            setGraphic(box);
        }
        else {
            setText(null);
            setGraphic(null);
        }
    }
}
