/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import javax.swing.JInternalFrame;

class MeuFrameInterno extends JInternalFrame{

    private MeuJPanel panel;

    public MeuFrameInterno(){
        super("Imagem",true,true,true);
    }

    public MeuFrameInterno(String imagem){
        super(imagem,true,true,true,true);
        setPanel(new MeuJPanel(imagem));
        this.add(panel);
    }
    /**
     * @return the panel
     */
    public MeuJPanel getPanel() {
        return panel;
    }

    /**
     * @param panel the panel to set
     */
    private void setPanel(MeuJPanel panel) {
        this.panel = panel;
    }
}
