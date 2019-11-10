
package business;

import java.text.NumberFormat;

/**
 *
 * @author n.riley
 */
public class Loan extends Financial {
    public static final String AMTDESC = "Loan Amount";
    public static final String RESULTDESC = "Monthly Payment";
    public static final String BEGBALDESC = "Beg Loan Bal.";
    public static final String INTERESTDESC = "Interest Charged";
    public static final String ENDBALDESC = "End Loan Bal.";
    public static final String PAYMENTDESC = "Payment";
    public static final String PRINDESC = "Prin. Paid";
    
    private double mopmt;
    private String emsg;
    private boolean built;
    private double[] bbal, ichg, ebal, prinPaid;

    public Loan(){
        super();
        this.mopmt = 0;
        this.built = false;
    }
    
    public Loan(double a, double r, int t) {
        super(a,r,t);
        this.mopmt = 0;
        this.built = false;
        if (super.isValid()) {
            buildLoan();
        }
    }

    private void buildLoan () {
        double denom, morate;
        try {
            morate = super.getRate() / 12.0;
            denom = Math.pow(1+morate, super.getTerm()) -1;
            this.mopmt = (morate + morate / denom) * super.getAmt();

            this.bbal = new double[super.getTerm()];
            this.ichg = new double[super.getTerm()];
            this.ebal = new double[super.getTerm()];
            this.prinPaid = new double[super.getTerm()];

            this.bbal[0] = super.getAmt();
            
            for (int i=0; i < super.getTerm(); i++) {
                if (i > 0) {
                    this.bbal[i] = this.ebal[i-1];
                }
                
                this.ichg[i] = this.bbal[i] * morate;
                this.ebal[i] = this.bbal[i] + this.ichg[i] - this.mopmt;
                this.prinPaid[i] = this.mopmt - ichg[i];
//                this.prinPaid[i] = this.ebal[i] - bbal[i]; 
            }

            this.built = true;
        } catch (Exception e) {
            super.setErrorMsg("Build fail on Loan: " + e.getMessage());
            this.built = false;
        }
    }
    
    @Override
    public String getTitle(){
          NumberFormat curr = NumberFormat.getCurrencyInstance();
        NumberFormat pct = NumberFormat.getPercentInstance();
        pct.setMaximumFractionDigits(3);
        return "Loan Schedule: " + 
                curr.format(super.getAmt()) + " per month @ " + 
                pct.format(super.getRate()) + " for " + 
                super.getTerm() + " Months";
    }

    @Override
    public double getBegBal(int mo) {
        if (!this.built) {
            buildLoan();
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
            buildLoan();
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
            buildLoan();
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
    public double getPrincipal(int mo){
         if (!this.built) {
            buildLoan();
            if (!this.built) {
                return -1;
            }
        }
        return this.mopmt;
    }

    @Override
    public double getResult() {
        if (!this.built) {
            buildLoan();
            if (!this.built) {
                return -1;
            }
        }
        return this.mopmt;
    }
    
    public double getPrinPaid(int mo){
        if (!this.built) {
                buildLoan();
                if (!this.built) {
                    return -1;
                }
        }
            if (mo < 1 || mo > super.getTerm()) {
                return -1;
            }
            return this.prinPaid[mo-1];
    }

}

