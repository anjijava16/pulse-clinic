
package com.pulse.desktop.view.frame.user;

/**
 *
 * @author befast
 */
import java.awt.GridBagConstraints;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

/**
 *
 * @author befast
 */
public abstract class AbstractInternalFrame {
    
    private final boolean RESIZABLE   = false;
    private final boolean CLOSABLE    = false;
    private final boolean MAXIMIZABLE = false;
    private final boolean ICONFIABLE  = false;

    protected final String FRAME_DESCRIPTION = "Описание";
    
    protected JInternalFrame frame = new JInternalFrame(this.FRAME_DESCRIPTION,
                                                        this.RESIZABLE,
                                                        this.CLOSABLE,
                                                        this.MAXIMIZABLE,
                                                        this.ICONFIABLE);

    protected final GridBagConstraints gbc = new GridBagConstraints();
    protected final JPanel ROOT_PANEL = new JPanel();
    
    protected int width  = 340;
    protected int height = 320;
    
    protected int maxChars = 13;
    
    protected final JToolBar TOOLBAR = new JToolBar(); 
    
    public JInternalFrame getInternalFrame() {
        return this.frame;    
    }
    
    protected void setToolbarSettings() {
        this.TOOLBAR.setFloatable(false);
        this.TOOLBAR.setVisible(true);
    }
    
    protected void addToolbarButton(JComponent toolbarButton, boolean addSeparator) {
        this.TOOLBAR.add(toolbarButton);
        
        if (addSeparator) {
            this.TOOLBAR.addSeparator();
        }
    }    
    
    public boolean isVisible() {
        return this.frame.isVisible();
    }
    
    public void setVisible(boolean visibility) {
        this.frame.setVisible(visibility);
    }
    
    public abstract void intializeFrame();
    public abstract void initializeRootPanel();
    public abstract void setAllSettings();
    public abstract void addAllActionListeners();

}