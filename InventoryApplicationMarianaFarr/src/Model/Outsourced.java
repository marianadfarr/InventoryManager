package Model;
/**
 * Models Outsourced Parts - a subclass of the abstract class Part
 * @author Mariana Farr

 */

    public class Outsourced extends Part {

        private String companyName;

        public Outsourced(int ID, String name, double price, int stock, int min, int max, String companyName) {
            super(ID, name, price, stock, min, max);
            this.companyName = companyName;
        }

        /**
         * @return the Company Name
         */
        public String getCompanyName() {
            return companyName;
        }

        /**
         * @param companyName the Company name to set
         */
        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }
    }


