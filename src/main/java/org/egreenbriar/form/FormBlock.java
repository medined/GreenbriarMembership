package org.egreenbriar.form;

public class FormBlock {
    
    private String pk = null;
    private String name = null;
    private String value = null;
    private String blockName = null;
    
    public void setBlockName(final String blockName) {
        this.blockName = blockName;
    }
    
    public String getBlockName() {
        return this.blockName;
    }
    
    public FormBlock() {
    }

    /**
     * @return the pk
     */
    public String getPk() {
        return pk;
    }

    /**
     * @param pk the pk to set
     */
    public void setPk(String pk) {
        this.pk = pk;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

}
