package capstone.is4103capstone.supplychain.model.res;

import capstone.is4103capstone.entities.supplyChain.Vendor;
import capstone.is4103capstone.general.model.GeneralRes;

import java.io.Serializable;

public class GetVendorRes extends GeneralRes implements Serializable {
    private Vendor vendor;

    public GetVendorRes() {
    }

    public GetVendorRes(String message, Boolean error, Vendor vendor) {
        super(message, error);
        this.vendor = vendor;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }
}