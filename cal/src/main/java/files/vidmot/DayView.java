/**
 * @ Author: Þorvaldur Tumi Baldursson
 * @ Create Time: 2022-02-21 11:25:10
 * @ Modified by: Tumi
 * @ Modified time: 2022-02-21 12:04:19
 * @ Description: Sér um viðmótið fyrir einstakan dag
 */

package files.vidmot;

import javafx.scene.layout.AnchorPane;

public class DayView extends AnchorPane{
    public void showEvent(EventView e) {
        getChildren().add(e);
        getScene().getRoot().layout();
    }
}
