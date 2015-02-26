package com.pulse.desktop.view.frame.childframes;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import com.pulse.desktop.controller.table.TableService.TableHolder;
import com.pulse.desktop.controller.builder.ToolbarBuilder;
import com.pulse.model.constant.Privelegy;


/**
 * 
 * @author Vladimir Shin <vladimir.shin@gmail.com>
 */
public class AbstractTabledChildFrame {
    
    private final boolean RESIZABLE   = true;
    private final boolean MAXIMIZABLE = true;
    private final boolean CLOSABLE    = false;    
    private final boolean ICONFIABLE  = false;

    private List<JComponent> toolbarBtnList;    
    private TableHolder tableHolder;
    private Privelegy privelegy;
    
    protected final JInternalFrame CHILD_FRAME = new JInternalFrame();
    protected final GridBagConstraints gbc = new GridBagConstraints();
    protected final JPanel ROOT_PANEL = new JPanel();    
    protected final int maxChars = 13;
    protected int width;
    protected int height;  
    
    protected final JToolBar TOOLBAR = ToolbarBuilder.build();
            
    // =================================================================================== Private functions
    private void initializeToolbar() {        
        if (this.toolbarBtnList != null && !this.toolbarBtnList.isEmpty()) {
            for (JComponent component : this.toolbarBtnList) {
                addToolbarComponent(component, true);
            }
        }        
    }
    
    private void addToolbarComponent(JComponent toolbarButton, boolean addSeparator) {
        this.TOOLBAR.add(toolbarButton);
        
        if (addSeparator) {
            this.TOOLBAR.addSeparator();
        }
    }
        
    private void initializeRootPanel() {
        this.ROOT_PANEL.setVisible(true);
        this.ROOT_PANEL.setLayout(new BorderLayout());
        this.ROOT_PANEL.add(this.TOOLBAR, BorderLayout.NORTH);
        
        if (this.tableHolder != null) {
            this.ROOT_PANEL.add(this.tableHolder.getScrollPane(), 
                    BorderLayout.CENTER);
        }
    } 
    // =================================================================================== Private functions
    
    public AbstractTabledChildFrame() {        
    }
    
    public void setPrivelegy(Privelegy privelegy) {
        this.privelegy = privelegy;    
    }
    
    public void setTableHolder(TableHolder tableHolder) {
        this.tableHolder = tableHolder;
    }
            
    public void setToolbarBtnList(List<JComponent> toolbarBtnList) {
        this.toolbarBtnList = toolbarBtnList;
    }
    
    public boolean isVisible() {
        return this.CHILD_FRAME.isVisible();
    }
    
    public void setVisible(boolean visibility) {
        this.CHILD_FRAME.setVisible(visibility);
    }
        
    public void initializeFrame() {
        initializeToolbar();  
        initializeRootPanel();
        
        this.width = 969;
        this.height = 400;
                
        this.CHILD_FRAME.setResizable(this.RESIZABLE);
        this.CHILD_FRAME.setClosable(this.CLOSABLE);
        this.CHILD_FRAME.setMaximizable(this.MAXIMIZABLE);
        this.CHILD_FRAME.setIconifiable(this.ICONFIABLE);
        
        this.CHILD_FRAME.setSize(this.width, this.height);
        this.CHILD_FRAME.add(this.ROOT_PANEL); 
        
        this.CHILD_FRAME.setTitle(this.privelegy.getName());
    }
    
    public void initFont() {
        if (this.toolbarBtnList != null && ! this.toolbarBtnList.isEmpty()) {
            for (JComponent component : toolbarBtnList) {
                
            }
        }
    }
    
    public Privelegy getPrivelegy() {
        return this.privelegy;
    }
    
    public TableHolder getTableHolder() {
        return this.tableHolder;
    }
    
    public JInternalFrame getInternalFrame() {        
        return this.CHILD_FRAME;    
    }   
       
}