package com.pulse.desktop.view.frame.childframes.assignment;


import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import javax.swing.BoxLayout;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import com.pulse.desktop.view.frame.childframes.template.TemplateService;
import com.pulse.desktop.view.panel.AnalysSelectionPanel;

/**
 *
 * @author Администратор
 */
public final class AssignmentFrame {
    
    private final boolean RESIZABLE   = false;
    private final boolean CLOSABLE    = true;
    private final boolean MAXIMIZABLE = false;
    private final boolean ICONFIABLE  = false;
    
    protected JInternalFrame frame = new JInternalFrame("",
                                                        this.RESIZABLE,
                                                        this.CLOSABLE,
                                                        this.MAXIMIZABLE,
                                                        this.ICONFIABLE);

    protected final GridBagConstraints gbc = new GridBagConstraints();
    protected final JPanel ROOT_PANEL = new JPanel();
    
    protected int width  = 340;
    protected int height = 450;
    
    protected int maxChars = 13;
    
    protected final JToolBar TOOLBAR = new JToolBar(); 
    
    public JInternalFrame getInternalFrame() {
        return this.frame;    
    }
            
    private AnalysSelectionPanel analysSelectionPanel;
    
    public AssignmentFrame(TemplateService service, String title) {
        this.analysSelectionPanel = new AnalysSelectionPanel(service);
        
        this.frame.setMaximizable(true);
        this.frame.setTitle(title);
        setAllSettings();       
        addAllActionListeners();
        intializeFrame();
    }
    
    public AnalysSelectionPanel getAnalysSelectionPanel() {
        return this.analysSelectionPanel;
    }
    
    public void intializeFrame() {
        initializeRootPanel();
        
        this.width = 1100;
        this.height = 550;
        
        this.frame.setSize(this.width, this.height);
        this.frame.setLayout(new BorderLayout());
        this.frame.add(this.ROOT_PANEL);
        this.frame.setResizable(true);
    }
    
    public void initializeRootPanel() {
        this.ROOT_PANEL.setLayout(new BoxLayout(this.ROOT_PANEL, BoxLayout.Y_AXIS)); 
        this.ROOT_PANEL.add(this.analysSelectionPanel.getRootPanel());
    }
    
    public void setAllSettings() {        
        this.frame.setVisible(false);
        this.frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.frame.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                getInternalFrame().setVisible(false);
            }
        });
    }
    
    public void addAllActionListeners() {
        
    }

}

