package capstone.is4103capstone.general.model;

import sun.java2d.loops.FillRect;

public class GeneralEntityRes extends GeneralRes{
    GeneralEntityModel entity;

    public GeneralEntityRes(String message, Boolean hasError) {
        super(message, hasError);
    }

    public GeneralEntityRes(String message, Boolean hasError, GeneralEntityModel entity) {
        super(message, hasError);
        this.entity = entity;
    }

    public GeneralEntityModel getEntity() {
        return entity;
    }

    public void setEntity(GeneralEntityModel entity) {
        this.entity = entity;
    }
}
