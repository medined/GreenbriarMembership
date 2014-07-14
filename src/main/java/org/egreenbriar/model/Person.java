package org.egreenbriar.model;

public class Person implements Comparable {

    public Person() {
    }
    
    private String pk;
    private String districtName = null;
    private String blockName = null;
    private String houseNumber = null;
    private String streetName = null;
    private String last;
    private String first;
    private String phone;
    private String email;
    private boolean noDirectory = false;
    private boolean unlisted = false;
    private String comment = null;

    public boolean isEmpty() {
        return getLast().isEmpty() && getFirst().isEmpty() && getPhone().isEmpty() & getEmail().isEmpty();
    }

    public boolean inHouse(final String houseNumber, final String streetName) {
        return this.houseNumber.equals(houseNumber) && this.streetName.equals(streetName);
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.getLast() != null ? this.getLast().hashCode() : 0);
        hash = 97 * hash + (this.getFirst() != null ? this.getFirst().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Person other = (Person) obj;
        if ((this.getLast() == null) ? (other.getLast() != null) : !this.last.equals(other.last)) {
            return false;
        }
        if ((this.getFirst() == null) ? (other.getFirst() != null) : !this.first.equals(other.first)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Person{" + "pk=" + getPk() + " last=" + getLast() + ", first=" + getFirst() + ", phone=" + getPhone() + ", email=" + getEmail() + ", noList=" + isNoDirectory() + ", unlisted=" + isUnlisted() + ", comment=" + getComment() + '}';
    }

    @Override
    public int compareTo(Object o) {
        int rv = -1;
        if (o != null) {
            Person that = (Person)o;
            if (this.equals(that)) {
                rv = 0;
            } else {
                rv = this.getLast().compareTo(that.getLast());
                if (rv == 0) {
                    rv = this.getFirst().compareTo(that.getFirst());
                }
            }
        }
        return rv;
    }

    boolean is(String last, String first) {
        return this.getLast().equals(last) && this.getFirst().equals(first);
    }

    public String getName() {
        return getFirst() + " " + getLast();
    }

    /**
     * @return the last
     */
    public String getLast() {
        return last;
    }

    /**
     * @param last the last to set
     */
    public void setLast(String last) {
        this.last = last;
    }

    /**
     * @return the first
     */
    public String getFirst() {
        return first;
    }

    /**
     * @param first the first to set
     */
    public void setFirst(String first) {
        this.first = first;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isNoDirectory() {
        return noDirectory;
    }

    public boolean inDirectory() {
        return !noDirectory;
    }

    public void setNoDirectory(boolean noList) {
        this.noDirectory = noList;
    }

    public boolean isUnlisted() {
        return unlisted;
    }

    public boolean isListed() {
        return !unlisted;
    }

    public String listed() {
        return unlisted ? "Unlisted" : "Listed";
    }

    public String listedStyle() {
        return unlisted ? "button negate" : "button";
    }

    public String directory() {
        return noDirectory ? "No Directory" : "Directory";
    }

    public String directoryStyle() {
        return noDirectory ? "button negate" : "button";
    }

    public void setUnlisted(boolean unlisted) {
        this.unlisted = unlisted;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public void toggleListed() {
        this.unlisted = !this.unlisted;
    }

    public void toggleDirectory() {
        this.noDirectory = !this.noDirectory;
    }

    /**
     * @return the districtName
     */
    public String getDistrictName() {
        return districtName;
    }

    /**
     * @param districtName the districtName to set
     */
    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    /**
     * @return the blockName
     */
    public String getBlockName() {
        return blockName;
    }

    /**
     * @param blockName the blockName to set
     */
    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    /**
     * @return the houseNumber
     */
    public String getHouseNumber() {
        return houseNumber;
    }

    /**
     * @param houseNumber the houseNumber to set
     */
    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    /**
     * @return the streetName
     */
    public String getStreetName() {
        return streetName;
    }

    /**
     * @param streetName the streetName to set
     */
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }
    
    public boolean hasBadEmail() {
        return this.email != null && !this.email.trim().isEmpty() && !this.email.contains("@");
    }
}