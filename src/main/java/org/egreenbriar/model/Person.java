package org.egreenbriar.model;

public class Person implements Comparable {

    public static final Person EMPTY = new Person("", "", "", "", "");
    
    private String last;
    private String first;
    private String phone;
    private String email;
    private boolean noList = false;
    private boolean unlisted = false;
    private String comment = null;

    public Person(final String last, final String first, final String phone, final String email, String comment) {
        this.last = last;
        this.first = first;
        this.phone = phone;
        this.email = email;
        if ("Unlisted".equals(comment)) {
            this.unlisted = true;
        } else if ("No List".equals(comment)) {
            this.noList = true;
        } else if (comment != null && !comment.isEmpty()) {
            this.comment = comment;
        }
    }
    
    public boolean isEmpty() {
        return getLast().isEmpty() && getFirst().isEmpty() && getPhone().isEmpty() & getEmail().isEmpty();
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
        return "Person{" + "last=" + getLast() + ", first=" + getFirst() + ", phone=" + getPhone() + ", email=" + getEmail() + ", noList=" + isNoList() + ", unlisted=" + isUnlisted() + ", comment=" + getComment() + '}';
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

    /**
     * @return the noList
     */
    public boolean isNoList() {
        return noList;
    }

    /**
     * @param noList the noList to set
     */
    public void setNoList(boolean noList) {
        this.noList = noList;
    }

    /**
     * @return the unlisted
     */
    public boolean isUnlisted() {
        return unlisted;
    }

    /**
     * @param unlisted the unlisted to set
     */
    public void setUnlisted(boolean unlisted) {
        this.unlisted = unlisted;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

}
