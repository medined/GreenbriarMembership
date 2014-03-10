package org.egreenbriar.model;

public class Membership implements Comparable {
    
    public static final Membership YEAR_2012 = new Membership("2012");
    public static final Membership YEAR_2013 = new Membership("2013");
    public static final Membership YEAR_2014 = new Membership("2014");

    private String year = null;
    
    public Membership(final String year) {
        this.year = year;
    }

    @Override
    public int compareTo(Object o) {
        int rv = -1;
        if (o != null) {
            Membership that = (Membership)o;
            if (this.equals(that)) {
                rv = 0;
            } else {
                rv = this.getYear().compareTo(that.getYear());
            }
        }
        return rv;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.getYear() != null ? this.getYear().hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Membership{" + "year=" + getYear() + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Membership other = (Membership) obj;
        if ((this.getYear() == null) ? (other.getYear() != null) : !this.year.equals(other.year)) {
            return false;
        }
        return true;
    }

    /**
     * @return the year
     */
    public String getYear() {
        return year;
    }
}
