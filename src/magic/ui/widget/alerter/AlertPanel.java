package magic.ui.widget.alerter;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class AlertPanel extends JPanel {
    
    private final MigLayout miglayout = new MigLayout();
    private final NewVersionAlertButton newVersionAlertButton;
    private final MissingImagesAlertButton missingImagesAlertButton;

    public AlertPanel() {
        System.out.println("AlertPanel.ctr");
        setOpaque(false);
        newVersionAlertButton = new NewVersionAlertButton();
        missingImagesAlertButton = new MissingImagesAlertButton();
        setLayout(miglayout);
        refreshLayout();
    }

    private void refreshLayout() {
        miglayout.setLayoutConstraints("insets 1 2 0 4, hidemode 3");
        add(newVersionAlertButton);
        add(missingImagesAlertButton);
    }

    public void refreshAlerts() {
        missingImagesAlertButton.checkForMissingFiles();
        newVersionAlertButton.checkForNewVersion();
    }

}
