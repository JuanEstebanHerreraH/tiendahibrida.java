
package com.mycompany.tiendaadmindesktop;

import com.mycompany.tiendaadmindesktop.UI.LoginFrame;
import com.mycompany.tiendaadmindesktop.util.ConexionDB;
import javax.swing.SwingUtilities;

public class TiendaAdminDesktop {

    public static void main(String[] args) {
       SwingUtilities.invokeLater(LoginFrame::new);
           System.out.println(ConexionDB.getConnection());

    }
}
