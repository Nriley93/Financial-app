
package business;

import java.text.NumberFormat;

/**
 *
 * @author n.riley
 */
public class PV extends Financial {
    
    public static final String AMTDESC = "Future Amount";
    public static final String DISCOUNTDESC = "Discount";
    public static final String RESULTDESC = "Present Value";
    
    private double presVal;
    private String emsg;
    private boolean built;
    private double[] bbal, ichg, ebal;
    
    public PV(){
        super();
        this.presVal = 0;
        this.built = false;
    }
    
    public PV(double a, double r, int t){
        super(a,r,t);
        this.built = false;
        if(super.isValid()) {
            buildPV();
        }
    }
    
    private void buildPV() {
        double morate;
        try {
            morate = super.getRate() / 12.0;
            
            this.bbal = new double[super.getTerm()+1];
            this.ichg = new double[super.getTerm()+1];
            this.ebal = new double[super.getTerm()+1];
            
            for (int i=0; i < super.getTerm()+1; i++) {
                this.ebal[i] = super.getAmt() / (
                    Math.pow(1 + morate,super.getTerm()-i));
                this.presVal = ebal[0];
                this.bbal[i] = super.getAmt() - this.ebal[i];
            }
            this.built = true;
        } catch (Exception e) {
            super.setErrorMsg("Build fail on Present Value: " + e.getMessage());
            this.built = false;
        }
    }
    
    @Override
    public String getTitle(){
        NumberFormat curr = NumberFormat.getCurrencyInstance();
        NumberFormat pct = NumberFormat.getPercentInstance();
        pct.setMaximumFractionDigits(3);
        return "Present Value: " + 
                curr.format(super.getAmt()) + " per month @ " + 
                pct.format(super.getRate()) + " for " + 
                super.getTerm() + " Months";
    }

    @Override
    public double getBegBal(int mo) {
        if (!this.built) {
            buildPV();
            if (!this.built) {
                return -1;
            }
        }
        if (mo < 1 || mo > super.getTerm()+1) {
            return -1;
        }
        return this.bbal[mo-1];
    }

    @Override
    public double getInterest(int mo) {
        if (!this.built) {
            buildPV();
            if (!this.built) {
                return -1;
            }
        }
        if (mo < 1 || mo > super.getTerm()) {
            return -1;
        }
        return this.ichg[mo-1];
    }

    @Override
    public double getEndBal(int mo) {
        if (!this.built) {
            buildPV();
            if (!this.built) {
                return -1;
            }
        }
        if (mo < 1 || mo > super.getTerm()+1) {
            return -1;
        }
        return this.ebal[mo-1];
    }
    
    @Override
    public double getPrincipal(int mo){
         if (!this.built) {
            buildPV();
            if (!this.built) {
                return -1;
            }
        }
        return super.getAmt();
    }

    @Override
    public double getResult() {
        if (!this.built) {
            buildPV();
            if (!this.built) {
                return -1;
            }
        }
        return this.presVal;
    }
}
