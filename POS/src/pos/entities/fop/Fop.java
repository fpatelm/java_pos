/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos.entities.fop;

import java.util.logging.Logger;
import org.mongodb.morphia.annotations.Embedded;

/**
 *
 * @author fpatel
 */
@Embedded
public class Fop implements java.io.Serializable{
    private FopCode.Type fopcode;
    private Double value;

    public Fop(FopCode.Type  fopcode) {
        this.fopcode = fopcode;
    }

    public Fop(FopCode.Type  fopcode, Double value) {
        this.fopcode = fopcode;
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public FopCode.Type getFopcode() {
        return fopcode;
    }
    
}
