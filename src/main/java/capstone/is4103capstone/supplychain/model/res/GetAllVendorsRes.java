package capstone.is4103capstone.supplychain.model.res;

import capstone.is4103capstone.entities.supplyChain.Vendor;
import capstone.is4103capstone.general.model.GeneralRes;

import java.io.Serializable;
import java.util.List;

public class GetAllVendorsRes extends GeneralRes implements Serializable{
    public List<Vendor> vendorList;

    public GetAllVendorsRes() {
    }

    public GetAllVendorsRes(String message, Boolean hasError, List<Vendor> vendorList) {
        super(message, hasError);
        this.vendorList = vendorList;
    }

    public List<Vendor> getVendorList() {
        return vendorList;
    }

    public void setVendorList(List<Vendor> vendorList) {
        this.vendorList = vendorList;
    }
}