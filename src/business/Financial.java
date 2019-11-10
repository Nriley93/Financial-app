
package business;

/**
 *
 * @author n.riley
 */
abstract public class Financial {
    private double amt, rate;
    private int term;
    private String emsg;
    
    public Financial(){
        this.amt = 0;
        this.emsg = "";
        this.rate = 0;
        this.term = 0;
    }
    
    public Financial(double a, double r, int t){
        this.amt = a;
        this.rate = r;
        this.term = t;
        this.emsg = "";
    }
    
    protected boolean isValid() {
        this.emsg = "";
        boolean valid = true;
        if (this.amt <= 0) {
            valid = false;
            this.emsg += "Ammount must be positive. ";
        }
        if (this.rate <= 0) {
            valid = false;
            this.emsg += "Rate must be positive. ";
        }
        if (this.term <= 0) {
            valid = false;
            this.emsg += "Term must be positive. ";
        }
        return valid;
    }

    public double getAmt() {
        return amt;
    }

    public void setAmt(double amt) {
        this.amt = amt;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public String getErrorMsg() {
        return emsg;
    }
    
    protected void setErrorMsg(String msg){
        this.emsg = msg;
    }
    
    abstract public String getTitle();
    abstract public double getResult();
    abstract public double getBegBal(int mo);
    abstract public double getInterest(int mo);
    abstract public double getPrincipal(int mo);
    abstract public double getEndBal(int mo);
    
}
