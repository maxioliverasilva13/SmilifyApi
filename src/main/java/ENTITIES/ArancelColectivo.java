/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ENTITIES;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author rodrigo
 */
@Entity
@DiscriminatorValue("ArancelColectivo")
public class ArancelColectivo extends Arancel {
    
      @Column
      @Basic
      private int cantOrdenes; 
      
      public int getCantOrdenes(){
         return this.cantOrdenes;
      }
      
      public void setCantOrdenes(int cantOrdenes){
          this.cantOrdenes = cantOrdenes;
      }
 }
