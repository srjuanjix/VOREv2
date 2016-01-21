/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventana;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.table.DefaultTableModel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang.ArrayUtils;

/**
 *
 * @author acondaqua
 */
public class miSimulacionFacturas {
    
    // ...........................................  DEFINICIONES PÚBLICAS DE LA CLASE
    
    public float    fTablaInventario[]          = new float[100] ;
    public float    fTablaPotenciasInst[][][][] = new float[3][100][48][7];
    public float    fTablaPonderaciones[][][]     = new float[3][100][7];
    public String   sTablaInventario[][]        = new String[100][6] ;
    public String   sTablaPotenciasInst[][][]     = new String[100][49][7];
    public String   sTablaLineas[]               = new String[10] ;
    public String   sTablaPeriodosP3[][]         = new String[14][24];
    public String   sTablaPeriodosP6[][]         = new String[15][24];
    public float    fTablaCoeficientesP6[]       = new float[6];
    public int      iTablaDiasFestivos[]         = new int[12];
    public int      nInventario                   = 0 ;
    public float    fCoefPs6                      = 1.4064f ;
    public int      diasMes[]                     = new int[12] ;
    public float    fTablaPreciosPotenciaP6[]    = new float[6] ;
    public float    fTablaPotenciasP6[]          = {1000f,1000f,1.1000f,1.1000f,1000f,1600f} ;
    public int      iTablaCalendario[][]         = new int[365][7] ;
    public int      iTablaTemporadas[][]         = new int[12][12];
    public double   pEnergiaSubtotalP6[]         = new double[6];
    public double   eEnergiaSubtotalP6[]         = new double[6];
    public double   eEnergiaTotal = 0 ;
    public double   pEnergiaSubtotalP3[]         = new double[3];
    public double   eEnergiaSubtotalP3[]         = new double[3];
    public double   pEnergiaMedioPeriodoP6[]    = {0,0,0,0,0,0} ;
    public double   pEnergiaMedioPeriodoP30[]   = {0,0,0} ;
    public double   pEnergiaMedioPeriodoP31[]   = {0,0,0} ;

    

    
    public String ocsEnergia,ocsE1,ocsE2,ocsE3,ocsE4,ocsE5,ocsE6;
    public String PC1,PC2,PC3,PC4,PC5,PC6 ;
    public double ocsP1,ocsP2,ocsP3,ocsP4,ocsP5,ocsP6;
    public String pP1,pP2,pP3,pP4,pP5,pP6 ;
    public double tpP1,tpP2,tpP3,tpP4,tpP5,tpP6 ;
    
    public String  pmaxP1="0",pmaxP2="0",pmaxP3="0" ;
    public double dPF1a=0,dPF2a=0,dPF3a=0 ;
     // ........................................... 
    
    
    public void miSimulacionFacturas() {
     // ............................... Damos de alta las tablas con los periodos tarifarios
         
        String sTablaPeriodosP3[][] = {
        {"P3","P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P2","P2","P2","P2","P2","P2","P1","P1","P1","P1","P1","P1","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P2","P2","P2","P2","P2","P2","P1","P1","P1","P1","P1","P1","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P2","P2","P2","P2","P2","P2","P1","P1","P1","P1","P1","P1","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P1","P1","P1","P1","P1","P2","P2","P2","P2","P2","P2","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P1","P1","P1","P1","P1","P2","P2","P2","P2","P2","P2","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P1","P1","P1","P1","P1","P2","P2","P2","P2","P2","P2","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P1","P1","P1","P1","P1","P2","P2","P2","P2","P2","P2","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P1","P1","P1","P1","P1","P2","P2","P2","P2","P2","P2","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P1","P1","P1","P1","P1","P2","P2","P2","P2","P2","P2","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P1","P1","P1","P1","P1","P2","P2","P2","P2","P2","P2","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P2","P2","P2","P2","P2","P2","P2","P1","P1","P1","P1","P1","P1"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P2","P2"},
        
        };
        
       
        
        double pEnergiaSubtotalP6[] = {0,0,0,0,0,0} ;
        double eEnergiaSubtotalP6[] = {0,0,0,0,0,0} ;
        
         // ............................... Coeficientes asociados a la potencia contratada
        
        float fTablaCoeficientesP6[]    = {1.0f, 0.5f, 0.37f, 0.37f, 0.37f, 0.37f, 0.17f};
        
         // ............................... Tabla dias festivos
        
        int iTablaDiasFestivos[]        = {1,6,78,107,108,111,121,285,305,340,342,359} ;
        
        // ............................... Tabla dias festivos  
        
        int diasMes[]                   = {31,28,31,30,31,15,16,30,31,30,31,30,31} ;
        
        // ............................... Tabla precios de termino potencia P6
        
        float fTablaPreciosPotenciaP6[]   = {3.175178f,1.588963f,1.162857f,1.162857f,1.162857f,0.530570f} ;
                
        // ............................... Tabla potencias contratadas P6
       
    }
    // ----------------------------------------------------------------------------------------------------------------------------
    public double calcularSubtotalEnergiaP6(DefaultTableModel tablaPrecios,DefaultTableModel tablaEnergias,int mes,int year) {
        double pEnergiaTotal=0, consumo=0,precio=0;
        int i,j,k,dias,diaSemana,diasFestNacional=0, cntDia=0, cntP6=0, cntP5=0, cntP4=0, cntP3=0, cntP2=0, cntP1=0;
        double ocE1,ocE2,ocE3,ocE4,ocE5,ocE6 ;
        int nP1=0,nP2=0,nP3=0,nP4=0,nP5=0,nP6=0;
        
        
        int diasMes[]                   = {31,28,31,30,31,15,31,31,30,31,30,31} ;
        int iTablaDiasFestivos[]        = {1,120,226,284,341,358} ;
        
               
        String periodo="",sfecha="",sfechaIni=year+"-01-01";
        
        this.pEnergiaMedioPeriodoP6[0] =  0;
        this.pEnergiaMedioPeriodoP6[1] =  0;
        this.pEnergiaMedioPeriodoP6[2] =  0;
        this.pEnergiaMedioPeriodoP6[3] =  0;
        this.pEnergiaMedioPeriodoP6[4] =  0;
        this.pEnergiaMedioPeriodoP6[5] =  0;
        
        String sTablaPeriodosP6[][] = {
        {"P6","P6","P6","P6","P6","P6","P6","P6","P2","P2","P1","P1","P1","P2","P2","P2","P2","P2","P1","P1","P1","P2","P2","P2"},
        {"P6","P6","P6","P6","P6","P6","P6","P6","P2","P2","P1","P1","P1","P2","P2","P2","P2","P2","P1","P1","P1","P2","P2","P2"},
        {"P6","P6","P6","P6","P6","P6","P6","P6","P4","P4","P4","P4","P4","P4","P4","P4","P3","P3","P3","P3","P3","P3","P4","P4"},
        {"P6","P6","P6","P6","P6","P6","P6","P6","P5","P5","P5","P5","P5","P5","P5","P5","P5","P5","P5","P5","P5","P5","P5","P5"},
        {"P6","P6","P6","P6","P6","P6","P6","P6","P5","P5","P5","P5","P5","P5","P5","P5","P5","P5","P5","P5","P5","P5","P5","P5"},
        {"P6","P6","P6","P6","P6","P6","P6","P6","P4","P3","P3","P3","P3","P3","P3","P4","P4","P4","P4","P4","P4","P4","P4","P4"},
        {"P6","P6","P6","P6","P6","P6","P6","P6","P2","P2","P2","P1","P1","P1","P1","P1","P1","P1","P1","P2","P2","P2","P2","P2"},
        {"P6","P6","P6","P6","P6","P6","P6","P6","P2","P2","P2","P1","P1","P1","P1","P1","P1","P1","P1","P2","P2","P2","P2","P2"},
        {"P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6"},
        {"P6","P6","P6","P6","P6","P6","P6","P6","P4","P3","P3","P3","P3","P3","P3","P4","P4","P4","P4","P4","P4","P4","P4","P4"},
        {"P6","P6","P6","P6","P6","P6","P6","P6","P5","P5","P5","P5","P5","P5","P5","P5","P5","P5","P5","P5","P5","P5","P5","P5"},
        {"P6","P6","P6","P6","P6","P6","P6","P6","P4","P4","P4","P4","P4","P4","P4","P4","P3","P3","P3","P3","P3","P3","P4","P4"},
        {"P6","P6","P6","P6","P6","P6","P6","P6","P2","P2","P1","P1","P1","P2","P2","P2","P2","P2","P1","P1","P1","P2","P2","P2"},
        {"P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6"},
        {"P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6"},        
        };
        // .....................................................................
        
        ocE1 = Double.valueOf(this.ocsE1) ;                     // Otros precios de la energia P1
        ocE2 = Double.valueOf(this.ocsE2) ;                     // Otros precios de la energia P2
        ocE3 = Double.valueOf(this.ocsE3) ;                     // Otros precios de la energia P3
        ocE4 = Double.valueOf(this.ocsE4) ;                     // Otros precios de la energia P4
        ocE5 = Double.valueOf(this.ocsE5) ;                     // Otros precios de la energia P5
        ocE6 = Double.valueOf(this.ocsE6) ;                     // Otros precios de la energia P6
        
        // .....................................................................
        
        diasFestNacional = iTablaDiasFestivos.length ;
        
        if (mes==6) dias = 30 ; else dias = diasMes[mes-1] ;
        
        System.out.println("INICIO CALCULO DE SUBTOTAL ENERGIA P6, mes:"+mes+" dias="+dias);
         
         // .....................................................................
        
        for (j=0; j<dias; j++) {
            
            for (i=0; i<24; i++){   
                
                
                if ( mes == 6 ) {
                    
                        if ( j <=14 ) periodo = sTablaPeriodosP6[mes-1][i]; periodo.trim() ;
                        if ( j >14 )  periodo = sTablaPeriodosP6[mes][i];   periodo.trim() ;
                    
                    
                } else {
                
                       if (mes < 6 ) periodo = sTablaPeriodosP6[mes-1][i]; periodo.trim() ; 
                       if (mes > 6 ) periodo = sTablaPeriodosP6[mes][i]; periodo.trim() ;
                
                }
                
                // ...............................................................              Control de dia de la semana
                
        
                if (mes<10)  sfecha = year+"-"+"0"+mes+"-"+(j+1) ;
                else 
                            sfecha  = year+"-"+mes+"-"+(j+1) ;
              
                      
                diaSemana = getDayOfTheWeek(sfecha) ;
                
                if ( diaSemana == 1 || diaSemana == 7) periodo = "P6" ;                         
                // ...............................................................              Control de fiestas nacionales
                
                cntDia = diferenciaFechas(sfechaIni, sfecha ,1);                          //      System.out.println(sfechaIni+" - "+sfecha+" => cntDia="+cntDia+"  ----- y dia de la semana ="+diaSemana);
                
                
                for (k=0 ; k < diasFestNacional; k++) {
                    if ( cntDia == iTablaDiasFestivos[k])  { periodo="P6";  System.out.println("TENEMOS UN FESTIVO NACIONAL"); }
                }
                
                 // ...............................................................  
                
                precio  = Double.valueOf(String.valueOf(tablaPrecios.getValueAt(j+1,i+1)))/ 1000 ;              // Precio por kWh
                consumo = Double.valueOf(String.valueOf(tablaEnergias.getValueAt(j,i+1))); 
                
              // System.out.println(sfecha+" ------>("+diaSemana+") -> Comprobando (dia,hora)=("+j+","+(i+1)+") Tenemos (precio,consumo)=("+precio+","+consumo+") y corresponde a periodo="+periodo);
                
                if (periodo.equals("P1")) this.pEnergiaSubtotalP6[0] = this.pEnergiaSubtotalP6[0]+(consumo*(precio+ocE1)) ; 
                if (periodo.equals("P2")) this.pEnergiaSubtotalP6[1] = this.pEnergiaSubtotalP6[1]+(consumo*(precio+ocE2)) ;
                if (periodo.equals("P3")) this.pEnergiaSubtotalP6[2] = this.pEnergiaSubtotalP6[2]+(consumo*(precio+ocE3)) ;
                if (periodo.equals("P4")) this.pEnergiaSubtotalP6[3] = this.pEnergiaSubtotalP6[3]+(consumo*(precio+ocE4)) ;
                if (periodo.equals("P5")) this.pEnergiaSubtotalP6[4] = this.pEnergiaSubtotalP6[4]+(consumo*(precio+ocE5)) ;
                if (periodo.equals("P6")) this.pEnergiaSubtotalP6[5] = this.pEnergiaSubtotalP6[5]+(consumo*(precio+ocE6)) ;
                
                if (periodo.equals("P1")) this.eEnergiaSubtotalP6[0] = this.eEnergiaSubtotalP6[0]+consumo ;
                if (periodo.equals("P2")) this.eEnergiaSubtotalP6[1] = this.eEnergiaSubtotalP6[1]+consumo ;
                if (periodo.equals("P3")) this.eEnergiaSubtotalP6[2] = this.eEnergiaSubtotalP6[2]+consumo ;
                if (periodo.equals("P4")) this.eEnergiaSubtotalP6[3] = this.eEnergiaSubtotalP6[3]+consumo ;
                if (periodo.equals("P5")) this.eEnergiaSubtotalP6[4] = this.eEnergiaSubtotalP6[4]+consumo ;
                if (periodo.equals("P6")) this.eEnergiaSubtotalP6[5] = this.eEnergiaSubtotalP6[5]+consumo ;
                
                /*
                if (periodo.equals("P1")) { if (this.pEnergiaMedioPeriodoP6[0]!=0 ) { this.pEnergiaMedioPeriodoP6[0] = (this.pEnergiaMedioPeriodoP6[0] + precio) / 2 ; } else {this.pEnergiaMedioPeriodoP6[0]=precio;}}
                if (periodo.equals("P2")) { if (this.pEnergiaMedioPeriodoP6[1]!=0 ) { this.pEnergiaMedioPeriodoP6[1] = (this.pEnergiaMedioPeriodoP6[1] + precio) / 2 ; } else {this.pEnergiaMedioPeriodoP6[1]=precio;}}
                if (periodo.equals("P3")) { if (this.pEnergiaMedioPeriodoP6[2]!=0 ) { this.pEnergiaMedioPeriodoP6[2] = (this.pEnergiaMedioPeriodoP6[2] + precio) / 2 ; } else {this.pEnergiaMedioPeriodoP6[2]=precio;}}
                if (periodo.equals("P4")) { if (this.pEnergiaMedioPeriodoP6[3]!=0 ) { this.pEnergiaMedioPeriodoP6[3] = (this.pEnergiaMedioPeriodoP6[3] + precio) / 2 ; } else {this.pEnergiaMedioPeriodoP6[3]=precio;}}
                if (periodo.equals("P5")) { cntP5 ++; if (this.pEnergiaMedioPeriodoP6[4]!=0 ) { this.pEnergiaMedioPeriodoP6[4] = (this.pEnergiaMedioPeriodoP6[4] + precio) / 2 ; } else {this.pEnergiaMedioPeriodoP6[4]=precio;} }
                if (periodo.equals("P6")) { cntP6 ++;if (this.pEnergiaMedioPeriodoP6[5]!=0 ) { this.pEnergiaMedioPeriodoP6[5] = (this.pEnergiaMedioPeriodoP6[5] + precio) / 2 ; } else {this.pEnergiaMedioPeriodoP6[5]=precio;} System.out.println("Media movil P6="+this.pEnergiaMedioPeriodoP6[5]+ " cntP6="+cntP6);}
               */
                if (periodo.equals("P1")) {  cntP1 ++; this.pEnergiaMedioPeriodoP6[0] = this.pEnergiaMedioPeriodoP6[0] + precio ; }
                if (periodo.equals("P2")) {  cntP2 ++; this.pEnergiaMedioPeriodoP6[1] = this.pEnergiaMedioPeriodoP6[1] + precio ; } 
                if (periodo.equals("P3")) {  cntP3 ++; this.pEnergiaMedioPeriodoP6[2] = this.pEnergiaMedioPeriodoP6[2] + precio ; } 
                if (periodo.equals("P4")) {  cntP4 ++; this.pEnergiaMedioPeriodoP6[3] = this.pEnergiaMedioPeriodoP6[3] + precio; }
                if (periodo.equals("P5")) {  cntP5 ++;  this.pEnergiaMedioPeriodoP6[4] = this.pEnergiaMedioPeriodoP6[4] + precio; }
                if (periodo.equals("P6")) {  cntP6 ++;  this.pEnergiaMedioPeriodoP6[5] = this.pEnergiaMedioPeriodoP6[5] + precio ; }  // System.out.println(sfecha+" ------>("+diaSemana+") -> Media movil P6="+this.pEnergiaMedioPeriodoP6[5]+ " cntP6="+cntP6);
                
                
                }
                
        }
        // ........................................................
        pEnergiaTotal = this.pEnergiaSubtotalP6[0] +
                        this.pEnergiaSubtotalP6[1] +
                        this.pEnergiaSubtotalP6[2] +
                        this.pEnergiaSubtotalP6[3] +
                        this.pEnergiaSubtotalP6[4] +
                        this.pEnergiaSubtotalP6[5] ;
        
        eEnergiaTotal = this.eEnergiaSubtotalP6[0] +
                        this.eEnergiaSubtotalP6[1] +
                        this.eEnergiaSubtotalP6[2] +
                        this.eEnergiaSubtotalP6[3] +
                        this.eEnergiaSubtotalP6[4] +
                        this.eEnergiaSubtotalP6[5] ;
         // ........................................................
        
        this.pEnergiaMedioPeriodoP6[0] = this.pEnergiaMedioPeriodoP6[0] / cntP1 ;
        this.pEnergiaMedioPeriodoP6[1] = this.pEnergiaMedioPeriodoP6[1] / cntP2 ;
        this.pEnergiaMedioPeriodoP6[2] = this.pEnergiaMedioPeriodoP6[2] / cntP3 ;
        this.pEnergiaMedioPeriodoP6[3] = this.pEnergiaMedioPeriodoP6[3] / cntP4 ;
        this.pEnergiaMedioPeriodoP6[4] = this.pEnergiaMedioPeriodoP6[4] / cntP5 ;
        this.pEnergiaMedioPeriodoP6[5] = this.pEnergiaMedioPeriodoP6[5] / cntP6 ;
        
     /* 
        System.out.println("Total  P1="+this.pEnergiaMedioPeriodoP6[0]+ " cntP1="+cntP1);
        System.out.println("Total  P2="+this.pEnergiaMedioPeriodoP6[1]+ " cntP2="+cntP2);
        System.out.println("Total  P3="+this.pEnergiaMedioPeriodoP6[2]+ " cntP3="+cntP3);
        System.out.println("Total  P4="+this.pEnergiaMedioPeriodoP6[3]+ " cntP4="+cntP4);
        System.out.println("Total  P5="+this.pEnergiaMedioPeriodoP6[4]+ " cntP5="+cntP5);
        System.out.println("Total  P6="+this.pEnergiaMedioPeriodoP6[5]+ " cntP6="+cntP6);
     */           
        // ........................................................ 
        return pEnergiaTotal;
    }
    // ----------------------------------------------------------------------------------------------------------------------------
    public double calcularSubtotalPotenciaP6(DefaultTableModel tablaPotencias,int mes,int year) {
        
        double pPotencia=0,PCP=0,PM=0,Psobrepasamiento=0;
        int i,j,k,dias,diaSemana,diasFestNacional=0, cnt=0;
        String sFecha = "", sHora="", sDia="", sFechaCal="", sCuarto="";
        int iHora,iDia,cntDia,iCuarto;
        String periodo="", sfechaIni=year+"-01-01";
        double Ae1=0,Ae2=0,Ae3=0,Ae4=0,Ae5=0,Ae6=0 ;
        float    fCoefPs6 = 1.4064f ;   
        
        int diasMes[]                   = {31,28,31,30,31,15,30,31,30,31,30,31} ;
        int iTablaDiasFestivos[]        = {1,120,226,284,341,358} ;
        float fTablaCoeficientesP6[]    = {1.0f, 0.5f, 0.37f, 0.37f, 0.37f, 0.37f, 0.17f};
         
         String sTablaPeriodosP6[][] = {
        {"P6","P6","P6","P6","P6","P6","P6","P6","P2","P2","P1","P1","P1","P2","P2","P2","P2","P2","P1","P1","P1","P2","P2","P2"},
        {"P6","P6","P6","P6","P6","P6","P6","P6","P2","P2","P1","P1","P1","P2","P2","P2","P2","P2","P1","P1","P1","P2","P2","P2"},
        {"P6","P6","P6","P6","P6","P6","P6","P6","P4","P4","P4","P4","P4","P4","P4","P4","P3","P3","P3","P3","P3","P3","P4","P4"},
        {"P6","P6","P6","P6","P6","P6","P6","P6","P5","P5","P5","P5","P5","P5","P5","P5","P5","P5","P5","P5","P5","P5","P5","P5"},
        {"P6","P6","P6","P6","P6","P6","P6","P6","P5","P5","P5","P5","P5","P5","P5","P5","P5","P5","P5","P5","P5","P5","P5","P5"},
        {"P6","P6","P6","P6","P6","P6","P6","P6","P4","P3","P3","P3","P3","P3","P3","P4","P4","P4","P4","P4","P4","P4","P4","P4"},
        {"P6","P6","P6","P6","P6","P6","P6","P6","P2","P2","P2","P1","P1","P1","P1","P1","P1","P1","P1","P2","P2","P2","P2","P2"},
        {"P6","P6","P6","P6","P6","P6","P6","P6","P2","P2","P2","P1","P1","P1","P1","P1","P1","P1","P1","P2","P2","P2","P2","P2"},
        {"P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6"},
        {"P6","P6","P6","P6","P6","P6","P6","P6","P4","P3","P3","P3","P3","P3","P3","P4","P4","P4","P4","P4","P4","P4","P4","P4"},
        {"P6","P6","P6","P6","P6","P6","P6","P6","P5","P5","P5","P5","P5","P5","P5","P5","P5","P5","P5","P5","P5","P5","P5","P5"},
        {"P6","P6","P6","P6","P6","P6","P6","P6","P4","P4","P4","P4","P4","P4","P4","P4","P3","P3","P3","P3","P3","P3","P4","P4"},
        {"P6","P6","P6","P6","P6","P6","P6","P6","P2","P2","P1","P1","P1","P2","P2","P2","P2","P2","P1","P1","P1","P2","P2","P2"},
        {"P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6"},
        {"P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6","P6"},        
        };
        
        // ...............................................
         
        cnt  = tablaPotencias.getRowCount();            System.out.println("Tenemos cnt="+cnt);
        
        // ...............................................
        
        for ( i=0 ; i<cnt; i++){
            
            sFecha = String.valueOf(tablaPotencias.getValueAt(i,0)) ;
            
            sHora   = sFecha.substring(11, 13); 
            sCuarto = sFecha.substring(14, 16); System.out.println("Cuarto ="+sCuarto+" y iHora="+sHora);
            sDia    = sFecha.substring(0, 2);   
            
            
            iHora   = Integer.valueOf(sHora);
            iCuarto = Integer.valueOf(sCuarto);
            iDia    = Integer.valueOf(sDia);
            
            // ............................................
            
             if ( mes == 6 ) {
                    
                        if ( iDia <=14 ){ 
                                periodo = sTablaPeriodosP6[mes-1][iHora]; periodo.trim() ;
                                if (iHora==0 && iCuarto==0) periodo = sTablaPeriodosP6[mes-1][23] ; else {
                                    if (iCuarto==0 && iHora !=0) periodo = sTablaPeriodosP6[mes-1][iHora-1] ; 
                                }
                        }
                        if ( iDia >14 ) { 
                                periodo = sTablaPeriodosP6[mes][iHora];   periodo.trim() ;
                                 if (iHora==0 && iCuarto==0) periodo = sTablaPeriodosP6[mes][23] ; else {
                                    if (iCuarto==0 && iHora !=0) periodo = sTablaPeriodosP6[mes][iHora-1] ; 
                                }
                        }
                    
                    
                } else {
                
                       if (mes < 6 ) {
                                periodo = sTablaPeriodosP6[mes-1][iHora];   System.out.println("sTablaPeriodosP6["+(mes-1)+"]["+iHora+"]="+sTablaPeriodosP6[mes-1][iHora]);
                                periodo.trim() ; 
                                if (iHora==0 && iCuarto==0) periodo = sTablaPeriodosP6[mes-1][23] ; else {
                                    if (iCuarto==0 && iHora !=0) periodo = sTablaPeriodosP6[mes-1][iHora-1] ; 
                                }
                        }
                       if (mes > 6 ) {
                                periodo = sTablaPeriodosP6[mes][iHora];   
                                periodo.trim() ;    
                                if (iHora==0 && iCuarto==0) periodo = sTablaPeriodosP6[mes][23] ; else {
                                    if (iCuarto==0 && iHora !=0) periodo = sTablaPeriodosP6[mes][iHora-1] ; 
                                }
                        } 
                }
                
           
             
                // ...............................................................              Control de dia de la semana
                
        
                if (mes<10)  sFechaCal = year+"-"+"0"+mes+"-"+sDia ;
                else 
                             sFechaCal  = year+"-"+mes+"-"+sDia ;
              
                      
                diaSemana = getDayOfTheWeek(sFechaCal) ;
                
                if ( diaSemana == 1 || diaSemana == 7) periodo = "P6" ;     
                
                  System.out.println("Periodo = "+periodo);
                // ...............................................................              Control de fiestas nacionales
                
                
             
                
                
                cntDia = diferenciaFechas(sfechaIni, sFechaCal ,1);                          //      System.out.println(sfechaIni+" - "+sfecha+" => cntDia="+cntDia+"  ----- y dia de la semana ="+diaSemana);
                
                
                for (k=0 ; k < diasFestNacional; k++) {
                    if ( cntDia == iTablaDiasFestivos[k])  { periodo="P6";  System.out.println("TENEMOS UN FESTIVO NACIONAL"); }
                }
                
                 // ...............................................................  
            
            
                tablaPotencias.setValueAt(periodo, i, 2);
            
                // ...............................................................  
            
                 
                if (periodo.equals("P1"))  PCP = Double.valueOf(this.PC1);
                if (periodo.equals("P2"))  PCP = Double.valueOf(this.PC2);
                if (periodo.equals("P3"))  PCP = Double.valueOf(this.PC3);
                if (periodo.equals("P4"))  PCP = Double.valueOf(this.PC4);
                if (periodo.equals("P5"))  PCP = Double.valueOf(this.PC5);
                if (periodo.equals("P6"))  PCP = Double.valueOf(this.PC6);
            
                // ...............................................................  
                
                PM = Double.valueOf(String.valueOf(tablaPotencias.getValueAt(i,1))) ;
                
                // ...............................................................  
                
                if ( PM > PCP)  {
                    
                    Psobrepasamiento = PM-PCP ; 
                    
                     if (periodo.equals("P1")) Ae1 = Ae1 + ((PM - PCP) * ( PM - PCP)) ; 
                     if (periodo.equals("P2")) Ae2 = Ae2 + ((PM - PCP) * ( PM - PCP)) ; 
                     if (periodo.equals("P3")) Ae3 = Ae3 + ((PM - PCP) * ( PM - PCP)) ; 
                     if (periodo.equals("P4")) Ae4 = Ae4 + ((PM - PCP) * ( PM - PCP)) ; 
                     if (periodo.equals("P5")) {Ae5 = Ae5 + ((PM - PCP) * ( PM - PCP)) ;   System.out.println("la hora de "+sFecha+" es ="+sHora+"El dia es  "+sFecha+" es ="+sDia+ " y el dia de la semana ="+diaSemana+"  Psobrepasamiento="+Psobrepasamiento+"  Ae5 = "+Ae5);}
                     if (periodo.equals("P6")) Ae6 = Ae6 + ((PM - PCP) * ( PM - PCP)) ; 
                
                
                }
                else {
                    Psobrepasamiento = 0 ;
                }
                
                // ............................................................... 
                
                tablaPotencias.setValueAt(Psobrepasamiento, i, 3);
                
                // ............................................................... 
            
        }
        
        // ............................................................... 
        
        Ae1 = Math.sqrt(Ae1);  Ae2 = Math.sqrt(Ae2); Ae3 = Math.sqrt(Ae3);  Ae4 = Math.sqrt(Ae4);  Ae5 = Math.sqrt(Ae5);  Ae6 = Math.sqrt(Ae6); 

        this.ocsP1 = (fTablaCoeficientesP6[0] * fCoefPs6 * Ae1) ;
        this.ocsP2 = (fTablaCoeficientesP6[1] * fCoefPs6 * Ae2) ;
        this.ocsP3 = (fTablaCoeficientesP6[2] * fCoefPs6 * Ae3) ;
        this.ocsP4 = (fTablaCoeficientesP6[3] * fCoefPs6 * Ae4) ;
        this.ocsP5 = (fTablaCoeficientesP6[4] * fCoefPs6 * Ae5) ;
        this.ocsP6 = (fTablaCoeficientesP6[5] * fCoefPs6 * Ae6) ;
        
        
        // ............................................................... 
        
        pPotencia = fTablaCoeficientesP6[0] * fCoefPs6 * Ae1 +
                    fTablaCoeficientesP6[1] * fCoefPs6 * Ae2 +
                    fTablaCoeficientesP6[2] * fCoefPs6 * Ae3 +
                    fTablaCoeficientesP6[3] * fCoefPs6 * Ae4 +
                    fTablaCoeficientesP6[4] * fCoefPs6 * Ae5 +
                    fTablaCoeficientesP6[5] * fCoefPs6 * Ae6 ;
        
        
        return pPotencia ;
    }
    
        // ----------------------------------------------------------------------------------------------------------------------------
    public double calcularSubtotalTarifaPotenciaP6(int dias) {
       double Potencia=0;
        
       Potencia = dias * (Double.valueOf(this.pP1)* Double.valueOf(this.PC1)) +
                  dias * (Double.valueOf(this.pP2)* Double.valueOf(this.PC2)) +
                  dias * (Double.valueOf(this.pP3)* Double.valueOf(this.PC3)) +
                  dias * (Double.valueOf(this.pP4)* Double.valueOf(this.PC4)) +
                  dias * (Double.valueOf(this.pP5)* Double.valueOf(this.PC5)) +
                  dias * (Double.valueOf(this.pP6)* Double.valueOf(this.PC6)) ;
                      
       
       this.tpP1 = dias * (Double.valueOf(this.pP1)* Double.valueOf(this.PC1)) ;
       this.tpP2 = dias * (Double.valueOf(this.pP2)* Double.valueOf(this.PC2)) ;
       this.tpP3 = dias * (Double.valueOf(this.pP3)* Double.valueOf(this.PC3)) ;
       this.tpP4 = dias * (Double.valueOf(this.pP4)* Double.valueOf(this.PC4)) ;
       this.tpP5 = dias * (Double.valueOf(this.pP5)* Double.valueOf(this.PC5)) ;
       this.tpP6 = dias * (Double.valueOf(this.pP6)* Double.valueOf(this.PC6)) ;
       
                              
       return Potencia; 
    }
    
    
    
    
    
    
    
    
    
    
    // ----------------------------------------------------------------------------------------------------------------------------
    public static int getDayOfTheWeek(String fecha){
        
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaActual = null;
        try {
            fechaActual = df.parse(fecha);
        } catch (ParseException e) {
            System.err.println("No se ha podido parsear la fecha.");
            e.printStackTrace();
        }
        
        
	GregorianCalendar cal = new GregorianCalendar();
	cal.setTime(fechaActual);
	return cal.get(Calendar.DAY_OF_WEEK);	
        
       
    }
     // ----------------------------------------------------------------------------------------------------------------------------
   
    public int diferenciaFechas(String fec1, String fec2,int valor){
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    int retorno=0;
    java.util.Date date1 = null;
    java.util.Date date2 = null;
    try
    {
        Calendar cal1 = null;
        date1=df.parse(fec1);
        cal1=Calendar.getInstance();

        Calendar cal2 = null;
        date2=df.parse(fec2);
        cal2=Calendar.getInstance();

        // different date might have different offset
        cal1.setTime(date1);
        long ldate1 = date1.getTime() + cal1.get(Calendar.ZONE_OFFSET) + cal1.get(Calendar.DST_OFFSET);

        cal2.setTime(date2);
        long ldate2 = date2.getTime() + cal2.get(Calendar.ZONE_OFFSET) + cal2.get(Calendar.DST_OFFSET);

        // Use integer calculation, truncate the decimals
        int hr1 = (int)(ldate1/3600000); //60*60*1000
        int hr2 = (int)(ldate2/3600000);

        int days1 = (int)hr1/24;
        int days2 = (int)hr2/24;

        int dateDiff = days2 - days1;
        int yearDiff = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);
        int monthDiff = yearDiff * 12 + cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH);

        if(valor==1) {
          //  if (dateDiff<0) dateDiff=dateDiff*(-1);
            retorno=dateDiff;
            }else if(valor==2){
            if (monthDiff<0) monthDiff=monthDiff*(-1);
            retorno=monthDiff;
        }else if(valor==3){
                if (yearDiff<0) yearDiff=yearDiff*(-1);
                retorno=yearDiff;
        }
    }
    catch (ParseException pe)
    {
        pe.printStackTrace();
    }
    return retorno;
    } 
    // ------------------------------------------------------------------------------------------------------------------------
    
    public double calcularSubtotalEnergiaP30(DefaultTableModel tablaPrecios,DefaultTableModel tablaEnergias,int mes,int year) {
        double pEnergiaTotal=0, consumo=0,precio=0;
        int i,j,k,dias,diaSemana,diasFestNacional=0, cntDia=0, cntP1=0,cntP2=0,cntP3=0;
        double ocE1,ocE2,ocE3 ;
        
        int diasMes[]                   = {31,28,31,30,31,15,30,31,30,31,30,31} ;
        int iTablaDiasFestivos[]        = {1,120,226,284,341,358} ;
        
               
        String periodo="",sfecha="",sfechaIni=year+"-01-01";
        
        String sTablaPeriodosP30[][] = {
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P2","P2","P2","P2","P2","P2","P2","P2","P1","P1","P1","P1","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P2","P2","P2","P2","P2","P2","P2","P2","P1","P1","P1","P1","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P2","P2","P2","P2","P2","P2","P2","P2","P1","P1","P1","P1","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P2","P1","P1","P1","P1","P2","P2","P2","P2","P2","P2","P2","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P2","P1","P1","P1","P1","P2","P2","P2","P2","P2","P2","P2","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P2","P1","P1","P1","P1","P2","P2","P2","P2","P2","P2","P2","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P2","P1","P1","P1","P1","P2","P2","P2","P2","P2","P2","P2","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P2","P1","P1","P1","P1","P2","P2","P2","P2","P2","P2","P2","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P2","P1","P1","P1","P1","P2","P2","P2","P2","P2","P2","P2","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P2","P1","P1","P1","P1","P2","P2","P2","P2","P2","P2","P2","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P2","P2","P2","P2","P2","P2","P2","P2","P1","P1","P1","P1","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P2","P2","P2","P2","P2","P2","P2","P2","P1","P1","P1","P1","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3"},
        
        };
        // .....................................................................
                    
        ocE1 = Double.valueOf(this.ocsE1) ;                     // Otros precios de la energia P1
        ocE2 = Double.valueOf(this.ocsE2) ;                     // Otros precios de la energia P2
        ocE3 = Double.valueOf(this.ocsE3) ;                     // Otros precios de la energia P3
                
        // .....................................................................
        
        diasFestNacional = iTablaDiasFestivos.length ;
        
        if (mes==6) dias = 30 ; else dias = diasMes[mes-1] ;
        
        System.out.println("INICIO CALCULO DE SUBTOTAL ENERGIA P3, mes:"+mes+" dias="+dias);
         
         // .....................................................................
        
        for (j=0; j<dias; j++) {
            
            for (i=0; i<24; i++){   
                
                    periodo = sTablaPeriodosP30[mes-1][i]; periodo.trim() ;
                    
                
                
                // ...............................................................              Control de dia de la semana
                
        
                if (mes<10)  sfecha = year+"-"+"0"+mes+"-"+(j+1) ;
                else 
                            sfecha  = year+"-"+mes+"-"+(j+1) ;
              
                      
                diaSemana = getDayOfTheWeek(sfecha) ;
                
               if ( diaSemana == 1 || diaSemana == 7) periodo = "P3" ;                         
                // ...............................................................              Control de fiestas nacionales
                
                cntDia = diferenciaFechas(sfechaIni, sfecha ,1);                          //      System.out.println(sfechaIni+" - "+sfecha+" => cntDia="+cntDia+"  ----- y dia de la semana ="+diaSemana);
                
             
                for (k=0 ; k < diasFestNacional; k++) {
                    if ( cntDia == iTablaDiasFestivos[k])  { periodo="P3";  System.out.println("TENEMOS UN FESTIVO NACIONAL"); }
                }
             
                 // ...............................................................  
                
                precio  = Double.valueOf(String.valueOf(tablaPrecios.getValueAt(j+1,i+1)))/ 1000 ;              // Precio por kWh
                consumo = Double.valueOf(String.valueOf(tablaEnergias.getValueAt(j,i+1))); 
                
        //        System.out.println(sfecha+" ------>("+diaSemana+") -> Comprobando (dia,hora)=("+j+","+i+1+") Tenemos (precio,consumo)=("+precio+","+consumo+") y corresponde a periodo="+periodo);
                
                if (periodo.equals("P1")) this.pEnergiaSubtotalP3[0] = this.pEnergiaSubtotalP3[0]+(consumo*(precio+ocE1)) ; 
                if (periodo.equals("P2")) this.pEnergiaSubtotalP3[1] = this.pEnergiaSubtotalP3[1]+(consumo*(precio+ocE2)) ;
                if (periodo.equals("P3")) this.pEnergiaSubtotalP3[2] = this.pEnergiaSubtotalP3[2]+(consumo*(precio+ocE3)) ;
               
                
                if (periodo.equals("P1")) this.eEnergiaSubtotalP3[0] = this.eEnergiaSubtotalP3[0]+consumo ;
                if (periodo.equals("P2")) this.eEnergiaSubtotalP3[1] = this.eEnergiaSubtotalP3[1]+consumo ;
                if (periodo.equals("P3")) this.eEnergiaSubtotalP3[2] = this.eEnergiaSubtotalP3[2]+consumo ;
             // ...............................  
                /*
                
              if ( i>0 ) {
                if (periodo.equals("P1")) {  this.pEnergiaMedioPeriodoP30[0] = (this.pEnergiaMedioPeriodoP30[0] + precio) / 2 ; System.out.println(" ---- "+precio+" --- >this.pEnergiaMedioPeriodoP6[0]="+this.pEnergiaMedioPeriodoP6[0]);}
                if (periodo.equals("P2")) {  this.pEnergiaMedioPeriodoP30[1] = (this.pEnergiaMedioPeriodoP30[1] + precio) / 2 ;}
                if (periodo.equals("P3")) {  this.pEnergiaMedioPeriodoP30[2] = (this.pEnergiaMedioPeriodoP30[2] + precio) / 2 ;}
              } else {
                if (periodo.equals("P1")) {  this.pEnergiaMedioPeriodoP30[0] =  precio ;}
                if (periodo.equals("P2")) {  this.pEnergiaMedioPeriodoP30[1] =  precio ;}
                if (periodo.equals("P3")) {  this.pEnergiaMedioPeriodoP30[2] =  precio ;} 
              } 
                */
                
                if (periodo.equals("P1")) {  this.pEnergiaMedioPeriodoP30[0] =  this.pEnergiaMedioPeriodoP30[0]+precio ; cntP1++;}
                if (periodo.equals("P2")) {  this.pEnergiaMedioPeriodoP30[1] =  this.pEnergiaMedioPeriodoP30[1]+precio ; cntP2++;}
                if (periodo.equals("P3")) {  this.pEnergiaMedioPeriodoP30[2] =  this.pEnergiaMedioPeriodoP30[2]+precio ; cntP3++;} 
                
              // ...............................
            }
        }   
        
        // ........................................................
        pEnergiaTotal = this.pEnergiaSubtotalP3[0] +
                        this.pEnergiaSubtotalP3[1] +
                        this.pEnergiaSubtotalP3[2] ;
                        
        
        eEnergiaTotal = this.eEnergiaSubtotalP3[0] +
                        this.eEnergiaSubtotalP3[1] +
                        this.eEnergiaSubtotalP3[2] ;
                       
         // ........................................................
        
        this.pEnergiaMedioPeriodoP30[0] = this.pEnergiaMedioPeriodoP30[0] / cntP1 ;
        this.pEnergiaMedioPeriodoP30[1] = this.pEnergiaMedioPeriodoP30[1] / cntP2 ;
        this.pEnergiaMedioPeriodoP30[2] = this.pEnergiaMedioPeriodoP30[2] / cntP3 ;
       
        System.out.println("Total  P1="+this.pEnergiaMedioPeriodoP30[0]+ " cntP1="+cntP1);
        System.out.println("Total  P2="+this.pEnergiaMedioPeriodoP30[1]+ " cntP2="+cntP2);
        System.out.println("Total  P3="+this.pEnergiaMedioPeriodoP30[2]+ " cntP3="+cntP3);
       
                
        // ........................................................ 
        
        return pEnergiaTotal;
    }
    // ----------------------------------------------------------------------------------------------------------------------------
    public double calcularSubtotalPotenciaP30(DefaultTableModel tablaPotencias,int mes,int year,int fReglaPotenciaActual,int fDatosMaxiBloqueo) {
        
        double pPotencia=0,PCP=0,PM=0,Psobrepasamiento=0;
        int i,j,k,dias,diaSemana,diasFestNacional=0, cnt=0;
        String sFecha = "", sHora="", sDia="", sFechaCal="";
        int iHora,iDia,cntDia ;
        String periodo="", sfechaIni=year+"-01-01";
        double Ae1=0,Ae2=0,Ae3=0 ;
        int ip1=0,ip2=0,ip3=0;
        double P1M,P2M,P3M ;
        double pPC1,pPC2,pPC3 ;
        double PF1a,PF2a,PF3a;
        
        int diasMes[]                   = {31,28,31,30,31,15,30,31,30,31,30,31} ;
        int iTablaDiasFestivos[]        = {1,120,226,284,341,358} ;
      
    if ( fDatosMaxiBloqueo == 0) {   
        String sTablaPeriodosP30[][] = {
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P2","P2","P2","P2","P2","P2","P2","P2","P1","P1","P1","P1","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P2","P2","P2","P2","P2","P2","P2","P2","P1","P1","P1","P1","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P2","P2","P2","P2","P2","P2","P2","P2","P1","P1","P1","P1","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P2","P1","P1","P1","P1","P2","P2","P2","P2","P2","P2","P2","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P2","P1","P1","P1","P1","P2","P2","P2","P2","P2","P2","P2","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P2","P1","P1","P1","P1","P2","P2","P2","P2","P2","P2","P2","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P2","P1","P1","P1","P1","P2","P2","P2","P2","P2","P2","P2","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P2","P1","P1","P1","P1","P2","P2","P2","P2","P2","P2","P2","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P2","P1","P1","P1","P1","P2","P2","P2","P2","P2","P2","P2","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P2","P1","P1","P1","P1","P2","P2","P2","P2","P2","P2","P2","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P2","P2","P2","P2","P2","P2","P2","P2","P1","P1","P1","P1","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P2","P2","P2","P2","P2","P2","P2","P2","P1","P1","P1","P1","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3"},
        
        };
        
        // ...............................................
          
         
        cnt  = tablaPotencias.getRowCount();            System.out.println("Tenemos cnt="+cnt);
        
        // ...............................................
        
         
          double potenciaP1[]          = new double[cnt];
          double potenciaP2[]          = new double[cnt];
          double potenciaP3[]          = new double[cnt];
   
         
         // ...............................................
         
        
        for ( i=0 ; i<cnt; i++){
            
            sFecha = String.valueOf(tablaPotencias.getValueAt(i,0)) ;
            
            sHora   = sFecha.substring(11, 13); 
            sDia    = sFecha.substring(0, 2);   
            
            
            iHora   = Integer.valueOf(sHora);
            iDia    = Integer.valueOf(sDia);
            
            // ............................................
            
         
                
             periodo = sTablaPeriodosP30[mes-1][iHora]; periodo.trim() ;
             
                
                
                
                // ...............................................................              Control de dia de la semana
                
        
                if (mes<10)  sFechaCal = year+"-"+"0"+mes+"-"+sDia ;
                else 
                             sFechaCal  = year+"-"+mes+"-"+sDia ;
              
                      
            //    diaSemana = getDayOfTheWeek(sFechaCal) ;
                
            //    if ( diaSemana == 1 || diaSemana == 7) periodo = "P6" ;                         
                // ...............................................................              Control de fiestas nacionales
                
                
         //      System.out.println("la hora de "+sFecha+" es ="+sHora+"El dia es  "+sFecha+" es ="+sDia+ " y el dia de la semana ="+diaSemana);
                
         /*       
                cntDia = diferenciaFechas(sfechaIni, sFechaCal ,1);                          //      System.out.println(sfechaIni+" - "+sfecha+" => cntDia="+cntDia+"  ----- y dia de la semana ="+diaSemana);
                
                
                for (k=0 ; k < diasFestNacional; k++) {
                    if ( cntDia == iTablaDiasFestivos[k])  { periodo="P6";  System.out.println("TENEMOS UN FESTIVO NACIONAL"); }
                }
         */       
                 // ...............................................................  
            
            
                tablaPotencias.setValueAt(periodo, i, 2);
            
                // ...............................................................  
            
                 
                if (periodo.equals("P1"))  PCP = Double.valueOf(this.PC1);
                if (periodo.equals("P2"))  PCP = Double.valueOf(this.PC2);
                if (periodo.equals("P3"))  PCP = Double.valueOf(this.PC3);
               
            
                // ...............................................................  
                
                PM = Double.valueOf(String.valueOf(tablaPotencias.getValueAt(i,1))) ;
                
                 // ............................................................... 
                
                 if (periodo.equals("P1")) { potenciaP1[ip1] = PM ;  ip1++; }
                 if (periodo.equals("P2")) { potenciaP2[ip2] = PM ;  ip2++; } 
                 if (periodo.equals("P3")) { potenciaP3[ip3] = PM ;  ip3++; }
                                         
                if ( PM > PCP)  {
                    
                    Psobrepasamiento = PM-PCP ; 
                   
                }
                else {
                Psobrepasamiento = 0 ;
                }
                
                // ............................................................... 
                
                tablaPotencias.setValueAt(Psobrepasamiento, i, 3);
                
                // ...............................................................               
                
        }
        
            List b = Arrays.asList(ArrayUtils.toObject(potenciaP1));
            List c = Arrays.asList(ArrayUtils.toObject(potenciaP2));
            List d = Arrays.asList(ArrayUtils.toObject(potenciaP3));

                        
            this.pmaxP1 = String.valueOf(Collections.max(b)) ;  P1M = Double.valueOf(this.pmaxP1 );
            this.pmaxP2 = String.valueOf(Collections.max(c)) ;  P2M = Double.valueOf(this.pmaxP2 );
            this.pmaxP3 = String.valueOf(Collections.max(d)) ;  P3M = Double.valueOf(this.pmaxP3 );
    }      else {
        
         P1M = Double.valueOf(this.pmaxP1 );
         P2M = Double.valueOf(this.pmaxP2 );
         P3M = Double.valueOf(this.pmaxP3 );
        
        
    }
            
            pPC1 = Double.valueOf(this.PC1);
            pPC2 = Double.valueOf(this.PC2);
            pPC3 = Double.valueOf(this.PC3);
           
             if (fReglaPotenciaActual == 1 ) {                                                                 // Si la compañia aplica regla de potencia
         
                if ((P1M/pPC1)>= 1.05 ) {   PF1a =  P1M + 2 * ( P1M - 1.05 * pPC1); } else {                   // Potencia a facturar P1 actual

                                            PF1a = 0.85 * pPC1 ;
                }   

                if ((P2M/pPC2)>= 1.05 ) {   PF2a =  P2M + 2 * ( P2M - 1.05 * pPC2); } else {                   // Potencia a facturar P2 actual

                                            PF2a = 0.85 * pPC2 ;
                }

                if ((P3M/pPC3)>= 1.05 ) {   PF3a =  P3M + 2 * ( P3M - 1.05 * pPC3); } else {                   // Potencia a facturar P3 actual

                                            PF3a = 0.85 * pPC3 ;
                }
            } else {
                                            PF1a = P1M ; PF2a = P2M ; PF3a = P3M ;
            }
                // ...................................................................
                   
            this.dPF1a = PF1a ;
            this.dPF2a = PF2a ;
            this.dPF3a = PF3a ;
          
            return pPotencia ;
    }
    // ----------------------------------------------------------------------------------------------------------------------------
    public double calcularSubtotalTarifaPotenciaP30(int dias) {
       double Potencia=0;
       
       System.out.println("dias, dPF1a, pP1 ="+dias+" , "+this.dPF1a+" , "+this.pP1);
       
       Potencia = dias * (this.dPF1a* Double.valueOf(this.pP1)) +
                  dias * (this.dPF2a* Double.valueOf(this.pP2)) +
                  dias * (this.dPF3a* Double.valueOf(this.pP3)) ;
                 
                      
       
       this.tpP1 = dias * (this.dPF1a* Double.valueOf(this.pP1)) ;
       this.tpP2 = dias * (this.dPF2a* Double.valueOf(this.pP2)) ;
       this.tpP3 = dias * (this.dPF3a* Double.valueOf(this.pP3)) ;
      
                              
       return Potencia; 
    }
     // ------------------------------------------------------------------------------------------------------------------------
    
    public double calcularSubtotalEnergiaP31(DefaultTableModel tablaPrecios,DefaultTableModel tablaEnergias,int mes,int year) {
        double pEnergiaTotal=0, consumo=0,precio=0;
        int i,j,k,dias,diaSemana,diasFestNacional=0, cntDia=0, cntP1=0, cntP2=0, cntP3=0;
        double ocE1,ocE2,ocE3 ;
        
        int diasMes[]                   = {31,28,31,30,31,15,30,31,30,31,30,31} ;
        int iTablaDiasFestivos[]        = {1,120,226,284,341,358} ;
        
               
        String periodo="",sfecha="",sfechaIni=year+"-01-01";
        
        String sTablaPeriodosP31[][] = {
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P2","P2","P2","P2","P2","P2","P2","P1","P1","P1","P1","P1","P1","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P2","P2","P2","P2","P2","P2","P2","P1","P1","P1","P1","P1","P1","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P2","P2","P2","P2","P2","P2","P2","P1","P1","P1","P1","P1","P1","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P1","P1","P1","P1","P1","P1","P2","P2","P2","P2","P2","P2","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P1","P1","P1","P1","P1","P1","P2","P2","P2","P2","P2","P2","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P1","P1","P1","P1","P1","P1","P2","P2","P2","P2","P2","P2","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P1","P1","P1","P1","P1","P1","P2","P2","P2","P2","P2","P2","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P1","P1","P1","P1","P1","P1","P2","P2","P2","P2","P2","P2","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P1","P1","P1","P1","P1","P1","P2","P2","P2","P2","P2","P2","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P1","P1","P1","P1","P1","P1","P2","P2","P2","P2","P2","P2","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P2","P2","P2","P2","P2","P2","P2","P1","P1","P1","P1","P1","P1","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P2","P2","P2","P2","P2","P2","P2","P1","P1","P1","P1","P1","P1","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P2","P2","P2","P2"},
        
        };
        // .....................................................................
                    
        ocE1 = Double.valueOf(this.ocsE1) ;                     // Otros precios de la energia P1
        ocE2 = Double.valueOf(this.ocsE2) ;                     // Otros precios de la energia P2
        ocE3 = Double.valueOf(this.ocsE3) ;                     // Otros precios de la energia P3
                
        // .....................................................................
        
        diasFestNacional = iTablaDiasFestivos.length ;
        
        if (mes==6) dias = 30 ; else dias = diasMes[mes-1] ;
        
        System.out.println("INICIO CALCULO DE SUBTOTAL ENERGIA P3, mes:"+mes+" dias="+dias);
         
         // .....................................................................
        
        for (j=0; j<dias; j++) {
            
            for (i=0; i<24; i++){   
                
                    periodo = sTablaPeriodosP31[mes-1][i]; periodo.trim() ;
                    
                
                
                // ...............................................................              Control de dia de la semana
                
        
                if (mes<10)  sfecha = year+"-"+"0"+mes+"-"+(j+1) ;
                else 
                            sfecha  = year+"-"+mes+"-"+(j+1) ;
              
                      
                diaSemana = getDayOfTheWeek(sfecha) ;
                
                if ( diaSemana == 1 || diaSemana == 7)  periodo = sTablaPeriodosP31[12][i]; periodo.trim() ;                        
                // ...............................................................              Control de fiestas nacionales
                
                cntDia = diferenciaFechas(sfechaIni, sfecha ,1);                          //      System.out.println(sfechaIni+" - "+sfecha+" => cntDia="+cntDia+"  ----- y dia de la semana ="+diaSemana);
                
              
                for (k=0 ; k < diasFestNacional; k++) {
                    if ( cntDia == iTablaDiasFestivos[k])  { 
                        System.out.println("TENEMOS UN FESTIVO NACIONAL"); 
                        periodo = sTablaPeriodosP31[12][i]; periodo.trim() ;
                    }
                }
              
                 // ...............................................................  
                
                precio  = Double.valueOf(String.valueOf(tablaPrecios.getValueAt(j+1,i+1)))/ 1000 ;              // Precio por kWh
                consumo = Double.valueOf(String.valueOf(tablaEnergias.getValueAt(j,i+1))); 
                
        //        System.out.println(sfecha+" ------>("+diaSemana+") -> Comprobando (dia,hora)=("+j+","+i+1+") Tenemos (precio,consumo)=("+precio+","+consumo+") y corresponde a periodo="+periodo);
                
                if (periodo.equals("P1")) this.pEnergiaSubtotalP3[0] = this.pEnergiaSubtotalP3[0]+(consumo*(precio+ocE1)) ; 
                if (periodo.equals("P2")) this.pEnergiaSubtotalP3[1] = this.pEnergiaSubtotalP3[1]+(consumo*(precio+ocE2)) ;
                if (periodo.equals("P3")) this.pEnergiaSubtotalP3[2] = this.pEnergiaSubtotalP3[2]+(consumo*(precio+ocE3)) ;
               
                
                if (periodo.equals("P1")) this.eEnergiaSubtotalP3[0] = this.eEnergiaSubtotalP3[0]+consumo ;
                if (periodo.equals("P2")) this.eEnergiaSubtotalP3[1] = this.eEnergiaSubtotalP3[1]+consumo ;
                if (periodo.equals("P3")) this.eEnergiaSubtotalP3[2] = this.eEnergiaSubtotalP3[2]+consumo ;
              
                /*
                
                if (periodo.equals("P1")) {  this.pEnergiaMedioPeriodoP31[0] = (this.pEnergiaMedioPeriodoP31[0] + precio) / 2 ; System.out.println(" ---- "+precio+" --- >this.pEnergiaMedioPeriodoP6[0]="+this.pEnergiaMedioPeriodoP6[0]);}
                if (periodo.equals("P2")) {  this.pEnergiaMedioPeriodoP31[1] = (this.pEnergiaMedioPeriodoP31[1] + precio) / 2 ;}
                if (periodo.equals("P3")) {  this.pEnergiaMedioPeriodoP31[2] = (this.pEnergiaMedioPeriodoP31[2] + precio) / 2 ;}
               */
                
                if (periodo.equals("P1")) {  this.pEnergiaMedioPeriodoP31[0] = (this.pEnergiaMedioPeriodoP31[0] + precio) ; cntP1++; System.out.println(" ---- "+precio+" --- >this.pEnergiaMedioPeriodoP31[0]="+this.pEnergiaMedioPeriodoP31[0]);}
                if (periodo.equals("P2")) {  this.pEnergiaMedioPeriodoP31[1] = (this.pEnergiaMedioPeriodoP31[1] + precio) ; cntP2++; }
                if (periodo.equals("P3")) {  this.pEnergiaMedioPeriodoP31[2] = (this.pEnergiaMedioPeriodoP31[2] + precio) ; cntP3++; }

               
            }
        }   
        
        // ........................................................
        pEnergiaTotal = this.pEnergiaSubtotalP3[0] +
                        this.pEnergiaSubtotalP3[1] +
                        this.pEnergiaSubtotalP3[2] ;
                        
        
        eEnergiaTotal = this.eEnergiaSubtotalP3[0] +
                        this.eEnergiaSubtotalP3[1] +
                        this.eEnergiaSubtotalP3[2] ;
                       
         // ........................................................
       
        this.pEnergiaMedioPeriodoP31[0] = this.pEnergiaMedioPeriodoP31[0] / cntP1 ;
        this.pEnergiaMedioPeriodoP31[1] = this.pEnergiaMedioPeriodoP31[1] / cntP2 ;
        this.pEnergiaMedioPeriodoP31[2] = this.pEnergiaMedioPeriodoP31[2] / cntP3 ;

                System.out.println("Total  P1="+this.pEnergiaMedioPeriodoP31[0]+ " cntP1="+cntP1);
                System.out.println("Total  P2="+this.pEnergiaMedioPeriodoP31[1]+ " cntP2="+cntP2);
                System.out.println("Total  P3="+this.pEnergiaMedioPeriodoP31[2]+ " cntP3="+cntP3);


                // ........................................................ 
        
        return pEnergiaTotal;
    }
    // ----------------------------------------------------------------------------------------------------------------------------
    public double calcularSubtotalPotenciaP31(DefaultTableModel tablaPotencias,int mes,int year,int fReglaPotenciaActual) {
        
        double pPotencia=0,PCP=0,PM=0,Psobrepasamiento=0;
        int i,j,k,dias,diaSemana,diasFestNacional=0, cnt=0;
        String sFecha = "", sHora="", sDia="", sFechaCal="";
        int iHora,iDia,cntDia ;
        String periodo="", sfechaIni=year+"-01-01";
        double Ae1=0,Ae2=0,Ae3=0 ;
        int ip1=0,ip2=0,ip3=0;
        double P1M,P2M,P3M ;
        double pPC1,pPC2,pPC3 ;
        double PF1a,PF2a,PF3a;
        
        int diasMes[]                   = {31,28,31,30,31,15,30,31,30,31,30,31} ;
        int iTablaDiasFestivos[]        = {1,120,226,284,341,358} ;
      
        String sTablaPeriodosP31[][] = {
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P2","P2","P2","P2","P2","P2","P2","P1","P1","P1","P1","P1","P1","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P2","P2","P2","P2","P2","P2","P2","P1","P1","P1","P1","P1","P1","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P2","P2","P2","P2","P2","P2","P2","P1","P1","P1","P1","P1","P1","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P1","P1","P1","P1","P1","P1","P2","P2","P2","P2","P2","P2","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P1","P1","P1","P1","P1","P1","P2","P2","P2","P2","P2","P2","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P1","P1","P1","P1","P1","P1","P2","P2","P2","P2","P2","P2","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P1","P1","P1","P1","P1","P1","P2","P2","P2","P2","P2","P2","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P1","P1","P1","P1","P1","P1","P2","P2","P2","P2","P2","P2","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P1","P1","P1","P1","P1","P1","P2","P2","P2","P2","P2","P2","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P1","P1","P1","P1","P1","P1","P2","P2","P2","P2","P2","P2","P2","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P2","P2","P2","P2","P2","P2","P2","P1","P1","P1","P1","P1","P1","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P2","P2","P2","P2","P2","P2","P2","P1","P1","P1","P1","P1","P1","P2"},
        {"P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P3","P2","P2","P2","P2","P2","P2"},
        
        };
        
        // ...............................................
          
         
        cnt  = tablaPotencias.getRowCount();            System.out.println("Tenemos cnt="+cnt);
        
        // ...............................................
        
         
          double potenciaP1[]          = new double[cnt];
          double potenciaP2[]          = new double[cnt];
          double potenciaP3[]          = new double[cnt];
   
         
         // ...............................................
         
        
        for ( i=0 ; i<cnt; i++){
            
            sFecha = String.valueOf(tablaPotencias.getValueAt(i,0)) ;
            
            sHora   = sFecha.substring(11, 13); 
            sDia    = sFecha.substring(0, 2);   
            
            
            iHora   = Integer.valueOf(sHora);
            iDia    = Integer.valueOf(sDia);
            
            // ............................................
            
         
                
             periodo = sTablaPeriodosP31[mes-1][iHora]; periodo.trim() ;
             
               
             // ...............................................................              Control de dia de la semana
                
        
                if (mes<10)  sFechaCal = year+"-"+"0"+mes+"-"+sDia ;
                else 
                             sFechaCal  = year+"-"+mes+"-"+sDia ;
              
                      
               diaSemana = getDayOfTheWeek(sFechaCal) ;
                
               if ( diaSemana == 1 || diaSemana == 7) { periodo = sTablaPeriodosP31[12][iHora]; periodo.trim() ; }                         
               
                // ...............................................................              Control de fiestas nacionales
                
                
         //      System.out.println("la hora de "+sFecha+" es ="+sHora+"El dia es  "+sFecha+" es ="+sDia+ " y el dia de la semana ="+diaSemana);
                
               
                cntDia = diferenciaFechas(sfechaIni, sFechaCal ,1);                          //      System.out.println(sfechaIni+" - "+sfecha+" => cntDia="+cntDia+"  ----- y dia de la semana ="+diaSemana);
                
                
                for (k=0 ; k < diasFestNacional; k++) {
                    if ( cntDia == iTablaDiasFestivos[k])  { 
                        periodo = sTablaPeriodosP31[12][iHora];  
                        System.out.println("TENEMOS UN FESTIVO NACIONAL"); 
                    }
                }
               
                 // ...............................................................  
            
            
                tablaPotencias.setValueAt(periodo, i, 2);
            
                // ...............................................................  
            
                 
                if (periodo.equals("P1"))  PCP = Double.valueOf(this.PC1);
                if (periodo.equals("P2"))  PCP = Double.valueOf(this.PC2);
                if (periodo.equals("P3"))  PCP = Double.valueOf(this.PC3);
               
            
                // ...............................................................  
                
                PM = Double.valueOf(String.valueOf(tablaPotencias.getValueAt(i,1))) ;
                
                 // ............................................................... 
                
                 if (periodo.equals("P1")) { potenciaP1[ip1] = PM ;  ip1++; }
                 if (periodo.equals("P2")) { potenciaP2[ip2] = PM ;  ip2++; } 
                 if (periodo.equals("P3")) { potenciaP3[ip3] = PM ;  ip3++; }
                                         
                if ( PM > PCP)  {
                    
                    Psobrepasamiento = PM-PCP ; 
                   
                }
                else {
                Psobrepasamiento = 0 ;
                }
                
                // ............................................................... 
                
                tablaPotencias.setValueAt(Psobrepasamiento, i, 3);
                
                // ...............................................................               
                
        }
        
            List b = Arrays.asList(ArrayUtils.toObject(potenciaP1));
            List c = Arrays.asList(ArrayUtils.toObject(potenciaP2));
            List d = Arrays.asList(ArrayUtils.toObject(potenciaP3));

                        
            this.pmaxP1 = String.valueOf(Collections.max(b)) ;  P1M = Double.valueOf(this.pmaxP1 );
            this.pmaxP2 = String.valueOf(Collections.max(c)) ;  P2M = Double.valueOf(this.pmaxP2 );
            this.pmaxP3 = String.valueOf(Collections.max(d)) ;  P3M = Double.valueOf(this.pmaxP3 );
            
            pPC1 = Double.valueOf(this.PC1);
            pPC2 = Double.valueOf(this.PC2);
            pPC3 = Double.valueOf(this.PC3);
           
             if (fReglaPotenciaActual == 1 ) {                                                                 // Si la compañia aplica regla de potencia
         
                if ((P1M/pPC1)>= 1.05 ) {   PF1a =  P1M + 2 * ( P1M - 1.05 * pPC1); } else {                   // Potencia a facturar P1 actual

                                            PF1a = 0.85 * pPC1 ;
                }   

                if ((P2M/pPC2)>= 1.05 ) {   PF2a =  P2M + 2 * ( P2M - 1.05 * pPC2); } else {                   // Potencia a facturar P2 actual

                                            PF2a = 0.85 * pPC2 ;
                }

                if ((P3M/pPC3)>= 1.05 ) {   PF3a =  P3M + 2 * ( P3M - 1.05 * pPC3); } else {                   // Potencia a facturar P3 actual

                                            PF3a = 0.85 * pPC3 ;
                }
            } else {
                                            PF1a = P1M ; PF2a = P2M ; PF3a = P3M ;
            }
                // ...................................................................
                   
            this.dPF1a = PF1a ;
            this.dPF2a = PF2a ;
            this.dPF3a = PF3a ;
          
            return pPotencia ;
    }
    // ----------------------------------------------------------------------------------------------------------------------------
    public double calcularSubtotalTarifaPotenciaP31(int dias) {
       double Potencia=0;
        
       Potencia = dias * (this.dPF1a* Double.valueOf(this.pP1)) +
                  dias * (this.dPF2a* Double.valueOf(this.pP2)) +
                  dias * (this.dPF3a* Double.valueOf(this.pP3)) ;
                 
                      
       
       this.tpP1 = dias * (this.dPF1a* Double.valueOf(this.pP1)) ;
       this.tpP2 = dias * (this.dPF2a* Double.valueOf(this.pP2)) ;
       this.tpP3 = dias * (this.dPF3a* Double.valueOf(this.pP3)) ;
      
                              
       return Potencia; 
    }
}
