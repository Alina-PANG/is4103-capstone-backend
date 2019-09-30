package capstone.is4103capstone.supplychain.model.res;

import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.supplychain.model.VendorModel;

import java.io.Serializable;

public class GetVendorRes extends GeneralRes implements Serializable {
    VendorModel vendorModel;

    public GetVendorRes() {
    }

    public GetVendorRes(String message, Boolean hasError, VendorModel vendorModel) {
        super(message, hasError);
        this.vendorModel = vendorModel;
    }

    public GetVendorRes(String message, Boolean hasError) {
        super(message, hasError);
    }

    public VendorModel getVendorModel() {
        return vendorModel;
    }

    public void setVendorModel(VendorModel vendorModel) {
        this.vendorModel = vendorModel;
    }
}