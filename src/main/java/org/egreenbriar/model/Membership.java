package org.egreenbriar.model;

public class Membership implements Comparable {
    
    private String year = null;
    
    public Membership(final String year) {
        this.year = year;
    }

    public int compareTo(Object o) {
        int rv = -1;
        if (o != null) {
            Membership that = (Membership)o;
            if (this.equals(that)) {
                rv = 0;
            } else {
                rv = this.year.compareTo(that.year);
            }
        }
        return rv;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.year != null ? this.year.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Membership{" + "year=" + year + '}';
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
        if ((this.year == null) ? (other.year != null) : !this.year.equals(other.year)) {
            return false;
        }
        return true;
    }
}
