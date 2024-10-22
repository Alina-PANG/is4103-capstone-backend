package capstone.is4103capstone.supplychain.model.res;

import capstone.is4103capstone.entities.supplyChain.Vendor;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.supplychain.model.VendorModel;

import java.io.Serializable;
import java.util.List;

public class GetVendorsRes extends GeneralRes implements Serializable{
    List<VendorModel> vendorModelList;

    public GetVendorsRes() {
    }

    public GetVendorsRes(String message, Boolean hasError, List<VendorModel> vendorModelList) {
        super(message, hasError);
        this.vendorModelList = vendorModelList;
    }

    public GetVendorsRes(String message, Boolean hasError) {
        super(message, hasError);
    }

    public List<VendorModel> getVendorModelList() {
        return vendorModelList;
    }

    public void setVendorModelList(List<VendorModel> vendorModelList) {
        this.vendorModelList = vendorModelList;
    }
}