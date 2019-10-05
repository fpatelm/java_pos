/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos.entities.fop;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.util.ArrayList;
import java.util.List;
import org.mongodb.morphia.annotations.Embedded;

/**
 *
 * @author fpatel
 */
@Embedded
public class FopTable implements java.io.Serializable{
    List<FopCode> ListFop = new ArrayList<>();

    public FopTable() {
        ListFop.add(new FopCode(FopCode.Type.CASH, true));
        ListFop.add(new FopCode(FopCode.Type.CC, true));
        ListFop.add(new FopCode(FopCode.Type.ACCOUNT, false));
        ListFop.add(new FopCode(FopCode.Type.CHECK, false));
    }

    public List<FopCode> getListFop() {
        return ListFop;
    } 
}
