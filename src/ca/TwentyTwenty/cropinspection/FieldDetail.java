package ca.TwentyTwenty.cropinspection;

public class FieldDetail {
    private String id = "";
    private String crop = "";
    private String field_center_lng = "";

    public void setId(String id) {
     this.id = id;
    }

    public String getId() {
     return id;
    }

    public void setCrop(String crop) {
     this.crop = crop;
    }

    public String getCrop() {
     return crop;
    }
    public void setFieldCenterLng(String field_center_lng) {
     this.field_center_lng = field_center_lng;
    }

    public String getFieldCenterLng() {
     return field_center_lng;
    }
}
