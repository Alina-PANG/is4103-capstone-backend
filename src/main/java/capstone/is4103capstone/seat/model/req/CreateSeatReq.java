package capstone.is4103capstone.seat.model.req;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//        {
//        “id”: String,
//        “x”: int,
//        “y”: int
//        }

//        {
//        “id”: “ORQ-23-01”,
//        “x”: 0,
//        “y”: 0
//        }

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateSeatReq {
    private String id;
    private Integer x;
    private Integer y;

    public CreateSeatReq() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }
}
