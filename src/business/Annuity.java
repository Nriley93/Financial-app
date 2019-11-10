
package business;

import java.text.NumberFormat;

/**
 *
 * @author n.riley
 */
public class Annuity extends Financial {
    public static final String AMTDESC = "Monthly Deposit";
    public static final String RESULTDESC = "Final Value of Annuity";
    public static final String BEGBALDESC = "Beg. Annuity Value";
    public static final String INTERESTDESC = "Interest earned";
    public static final String ENDBALDESC = "End. Annuity Value";
    public static final String PRINDESC = "Deposit";

    private double[] bbal, iearn, ebal;
    private boolean built;
    
    public Annuity(){
        super();
        this.built = false;
    }

    public Annuity(double a, double r, int t) {
        super(a,r,t);
        this.built = false;

        if (super.isValid()) {
            buildAnnuity();
        }
    }

    private void buildAnnuity() {
        try {
            this.bbal = new double[super.getTerm()];
            this.iearn = new double[super.getTerm()];
            this.ebal = new double[super.getTerm()];

            this.bbal[0] = 0;
            for (int i=0; i < super.getTerm(); i++) {
               if (i > 0) {
                   this.bbal[i] = this.ebal[i-1];
               }
               this.iearn[i] = (this.bbal[i] + super.getAmt()) * (super.getRate() / 12.0);
               this.ebal[i] = this.bbal[i] + super.getAmt() + this.iearn[i];
            }
            this.built = true;
        } catch (Exception e) {
            super.setErrorMsg("Annuity build error.");
            this.built = false;
        }
    }

    @Override
    public String getTitle(){
       NumberFormat curr = NumberFormat.getCurrencyInstance();
        NumberFormat pct = NumberFormat.getPercentInstance();
        pct.setMaximumFractionDigits(3);
        return "Annuity schedule: " + 
                curr.format(super.getAmt()) + " per month @ " + 
                pct.format(super.getRate()) + " for " + 
                super.getTerm() + " Months";
    }
    
    @Override
    public double getBegBal(int mo) {
        if (!this.built) {
            buildAnnuity();
            if (!this.built) {
                return -1;
            }
        }
        if (mo < 1 || mo > super.getTerm()) {
            return -1;
        }
        return this.bbal[mo-1];
    }

    @Override
    public double getInterest(int mo) {
        if (!this.built) {
            buildAnnuity();
            if (!this.built) {
                return -1;
            }
        }
        if (mo < 1 || mo > super.getTerm()) {
            return -1;
        }
        return this.iearn[mo-1];
    }
    
    @Override
    public double getPrincipal(int mo){
         if (!this.built) {
            buildAnnuity();
            if (!this.built) {
                return -1;
            }
        }
        return super.getAmt();
    }

    @Override
    public double getEndBal(int mo){
        if (!this.built) {
            buildAnnuity();
            if (!this.built) {
                return -1;
            }
        }
        if (mo < 1 || mo > super.getTerm()) {
            return -1;
        }
        return this.ebal[mo-1];
    }

    @Override
    public double getResult() {
        if (!this.built) {
            buildAnnuity();
            if (!this.built) {
                return -1;
            }
        }
        return this.ebal[super.getTerm()-1];
    }
}
