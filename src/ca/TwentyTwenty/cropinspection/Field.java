package ca.TwentyTwenty.cropinspection;

import android.database.Cursor;
import android.os.Bundle;

public class Field {
	public String acres;
	public String agronomist;
	public String area;
	public String comments;
	public String contract_grower;
	public String crop;
	public String crop_condition_appearance;
	public String crop_condition_uniformity;
	public String crop_condition_weed;
	public int customer_id;
	public String date_inspected;
	public String date_ready;
	public String date_ready_to;
	public String east_isolation_condition;
	public String east_isolation_crop;
	public String east_isolation_size;
	public String east_isolation_type;
	public String female_crop_certificate_no;
	public String female_seed_sealing_no;
	public String female_tags;
	public String female_year;
	public Double field_center_lat;
	public Double field_center_lng;
	public Double field_entrance_lat;
	public Double field_entrance_lng;
	public String field_location;
	public String field_no;
	public String flowering_female;
	public String flowering_male;
	public Double gps_max_lat;
	public Double gps_max_lng;
	public Double gps_min_lat;
	public Double gps_min_lng;
	public String grower_no;
	public int id;
	public int inspector_1_id;
	public int inspector_2_id;
	public String internal_client_comments;
	public String male_crop_certificate_no;
	public String male_seed_sealing_no;
	public String male_tags;
	public String male_year;
	public String north_isolation_condition;
	public String north_isolation_crop;
	public String north_isolation_size;
	public String north_isolation_type;
	public String objectionable_weeds;
	public String open_pollinated_crops;
	public int other_crop1_count1;
	public int other_crop1_count2;
	public int other_crop1_count3;
	public int other_crop1_count4;
	public int other_crop1_count5;
	public int other_crop1_count6;
	public String other_crop1_name;
	public int other_crop2_count1;
	public int other_crop2_count2;
	public int other_crop2_count3;
	public int other_crop2_count4;
	public int other_crop2_count5;
	public int other_crop2_count6;
	public String other_crop2_name;
	public int other_crop3_count1;
	public int other_crop3_count2;
	public int other_crop3_count3;
	public int other_crop3_count4;
	public int other_crop3_count5;
	public int other_crop3_count6;
	public String other_crop3_name;
	public String plants_per_m2;
	public String previous_crop1;
	public String previous_crop2;
	public String previous_crop3;
	public String previous_crop4;
	public String previous_crop5;
	public String qa;
	public int reinspection_of_id;
	public String seq_no;
	public String south_isolation_condition;
	public String south_isolation_crop;
	public String south_isolation_size;
	public String south_isolation_type;
	public int weed1_count1;
	public int weed1_count2;
	public int weed1_count3;
	public int weed1_count4;
	public int weed1_count5;
	public int weed1_count6;
	public String weed1_name;
	public int weed2_count1;
	public int weed2_count2;
	public int weed2_count3;
	public int weed2_count4;
	public int weed2_count5;
	public int weed2_count6;
	public String weed2_name;
	public int weed3_count1;
	public int weed3_count2;
	public int weed3_count3;
	public int weed3_count4;
	public int weed3_count5;
	public int weed3_count6;
	public String weed3_name;
	public String west_isolation_condition;
	public String west_isolation_crop;
	public String west_isolation_size;
	public String west_isolation_type;
	public String year;
	public String status;
	
	// limited field details for map activity
	public Field(int id, String field_no, String field_location,  String crop, String agronomist, String acres,
		String contract_grower, Double field_center_lat, Double field_center_lng, Double field_entrance_lat, Double field_entrance_lng,
		String status){
		
		this.id = id;
		this.field_no = field_no;
		this.field_location = field_location;
		this.crop = crop;
		this.agronomist = agronomist;
		this.acres = acres;
		this.contract_grower = contract_grower;
		this.field_center_lat = field_center_lat;
		this.field_center_lng = field_center_lng;
		this.field_entrance_lat = field_entrance_lat;
		this.field_entrance_lng = field_entrance_lng;
		this.status = status;
	}
	
	// build new object from database cursor
	public Field(Cursor c){
		this.acres = c.getString(c.getColumnIndex("acres"));
		this.agronomist = c.getString(c.getColumnIndex("agronomist"));
		this.area = c.getString(c.getColumnIndex("area"));
		this.comments = c.getString(c.getColumnIndex("comments"));
		this.contract_grower = c.getString(c.getColumnIndex("contract_grower"));
		this.crop = c.getString(c.getColumnIndex("crop"));
		this.crop_condition_appearance = c.getString(c.getColumnIndex("crop_condition_appearance"));
		this.crop_condition_uniformity = c.getString(c.getColumnIndex("crop_condition_uniformity"));
		this.crop_condition_weed = c.getString(c.getColumnIndex("crop_condition_weed"));
		this.customer_id = c.getInt(c.getColumnIndex("customer_id"));
		this.date_inspected = c.getString(c.getColumnIndex("date_inspected"));
		this.date_ready = c.getString(c.getColumnIndex("date_ready"));
		this.date_ready_to = c.getString(c.getColumnIndex("date_ready_to"));
		this.east_isolation_condition = c.getString(c.getColumnIndex("east_isolation_condition"));
		this.east_isolation_crop = c.getString(c.getColumnIndex("east_isolation_crop"));
		this.east_isolation_size = c.getString(c.getColumnIndex("east_isolation_size"));
		this.east_isolation_type = c.getString(c.getColumnIndex("east_isolation_type"));
		this.female_crop_certificate_no = c.getString(c.getColumnIndex("female_crop_certificate_no"));
		this.female_seed_sealing_no = c.getString(c.getColumnIndex("female_seed_sealing_no"));
		this.female_tags = c.getString(c.getColumnIndex("female_tags"));
		this.female_year = c.getString(c.getColumnIndex("female_year"));
		this.field_center_lat = c.getDouble(c.getColumnIndex("field_center_lat"));
		this.field_center_lng = c.getDouble(c.getColumnIndex("field_center_lng"));
		this.field_entrance_lat = c.getDouble(c.getColumnIndex("field_entrance_lat"));
		this.field_entrance_lng = c.getDouble(c.getColumnIndex("field_entrance_lng"));
		this.field_location = c.getString(c.getColumnIndex("field_location"));
		this.field_no = c.getString(c.getColumnIndex("field_no"));
		this.flowering_female = c.getString(c.getColumnIndex("flowering_female"));
		this.flowering_male = c.getString(c.getColumnIndex("flowering_male"));
		this.gps_max_lat = c.getDouble(c.getColumnIndex("gps_max_lat"));
		this.gps_max_lng = c.getDouble(c.getColumnIndex("gps_max_lng"));
		this.gps_min_lat = c.getDouble(c.getColumnIndex("gps_min_lat"));
		this.gps_min_lng = c.getDouble(c.getColumnIndex("gps_min_lng"));
		this.grower_no = c.getString(c.getColumnIndex("grower_no"));
		this.id = c.getInt(c.getColumnIndex("id"));
		this.inspector_1_id = c.getInt(c.getColumnIndex("inspector_1_id"));
		this.inspector_2_id = c.getInt(c.getColumnIndex("inspector_2_id"));
		this.internal_client_comments = c.getString(c.getColumnIndex("internal_client_comments"));
		this.male_crop_certificate_no = c.getString(c.getColumnIndex("male_crop_certificate_no"));
		this.male_seed_sealing_no = c.getString(c.getColumnIndex("male_seed_sealing_no"));
		this.male_tags = c.getString(c.getColumnIndex("male_tags"));
		this.male_year = c.getString(c.getColumnIndex("male_year"));
		this.north_isolation_condition = c.getString(c.getColumnIndex("north_isolation_condition"));
		this.north_isolation_crop = c.getString(c.getColumnIndex("north_isolation_crop"));
		this.north_isolation_size = c.getString(c.getColumnIndex("north_isolation_size"));
		this.north_isolation_type = c.getString(c.getColumnIndex("north_isolation_type"));
		this.objectionable_weeds = c.getString(c.getColumnIndex("objectionable_weeds"));
		this.open_pollinated_crops = c.getString(c.getColumnIndex("open_pollinated_crops"));
		this.other_crop1_count1 = c.getInt(c.getColumnIndex("other_crop1_count1"));
		this.other_crop1_count2 = c.getInt(c.getColumnIndex("other_crop1_count2"));
		this.other_crop1_count3 = c.getInt(c.getColumnIndex("other_crop1_count3"));
		this.other_crop1_count4 = c.getInt(c.getColumnIndex("other_crop1_count4"));
		this.other_crop1_count5 = c.getInt(c.getColumnIndex("other_crop1_count5"));
		this.other_crop1_count6 = c.getInt(c.getColumnIndex("other_crop1_count6"));
		this.other_crop1_name = c.getString(c.getColumnIndex("other_crop1_name"));
		this.other_crop2_count1 = c.getInt(c.getColumnIndex("other_crop2_count1"));
		this.other_crop2_count2 = c.getInt(c.getColumnIndex("other_crop2_count2"));
		this.other_crop2_count3 = c.getInt(c.getColumnIndex("other_crop2_count3"));
		this.other_crop2_count4 = c.getInt(c.getColumnIndex("other_crop2_count4"));
		this.other_crop2_count5 = c.getInt(c.getColumnIndex("other_crop2_count5"));
		this.other_crop2_count6 = c.getInt(c.getColumnIndex("other_crop2_count6"));
		this.other_crop2_name = c.getString(c.getColumnIndex("other_crop2_name"));
		this.other_crop3_count1 = c.getInt(c.getColumnIndex("other_crop3_count1"));
		this.other_crop3_count2 = c.getInt(c.getColumnIndex("other_crop3_count2"));
		this.other_crop3_count3 = c.getInt(c.getColumnIndex("other_crop3_count3"));
		this.other_crop3_count4 = c.getInt(c.getColumnIndex("other_crop3_count4"));
		this.other_crop3_count5 = c.getInt(c.getColumnIndex("other_crop3_count5"));
		this.other_crop3_count6 = c.getInt(c.getColumnIndex("other_crop3_count6"));
		this.other_crop3_name = c.getString(c.getColumnIndex("other_crop3_name"));
		this.plants_per_m2 = c.getString(c.getColumnIndex("plants_per_m2"));
		this.previous_crop1 = c.getString(c.getColumnIndex("previous_crop1"));
		this.previous_crop2 = c.getString(c.getColumnIndex("previous_crop2"));
		this.previous_crop3 = c.getString(c.getColumnIndex("previous_crop3"));
		this.previous_crop4 = c.getString(c.getColumnIndex("previous_crop4"));
		this.previous_crop5 = c.getString(c.getColumnIndex("previous_crop5"));
		this.qa = c.getString(c.getColumnIndex("qa"));
		this.reinspection_of_id = c.getInt(c.getColumnIndex("reinspection_of_id"));
		this.seq_no = c.getString(c.getColumnIndex("seq_no"));
		this.south_isolation_condition = c.getString(c.getColumnIndex("south_isolation_condition"));
		this.south_isolation_crop = c.getString(c.getColumnIndex("south_isolation_crop"));
		this.south_isolation_size = c.getString(c.getColumnIndex("south_isolation_size"));
		this.south_isolation_type = c.getString(c.getColumnIndex("south_isolation_type"));
		this.weed1_count1 = c.getInt(c.getColumnIndex("weed1_count1"));
		this.weed1_count2 = c.getInt(c.getColumnIndex("weed1_count2"));
		this.weed1_count3 = c.getInt(c.getColumnIndex("weed1_count3"));
		this.weed1_count4 = c.getInt(c.getColumnIndex("weed1_count4"));
		this.weed1_count5 = c.getInt(c.getColumnIndex("weed1_count5"));
		this.weed1_count6 = c.getInt(c.getColumnIndex("weed1_count6"));
		this.weed1_name = c.getString(c.getColumnIndex("weed1_name"));
		this.weed2_count1 = c.getInt(c.getColumnIndex("weed2_count1"));
		this.weed2_count2 = c.getInt(c.getColumnIndex("weed2_count2"));
		this.weed2_count3 = c.getInt(c.getColumnIndex("weed2_count3"));
		this.weed2_count4 = c.getInt(c.getColumnIndex("weed2_count4"));
		this.weed2_count5 = c.getInt(c.getColumnIndex("weed2_count5"));
		this.weed2_count6 = c.getInt(c.getColumnIndex("weed2_count6"));
		this.weed2_name = c.getString(c.getColumnIndex("weed2_name"));
		this.weed3_count1 = c.getInt(c.getColumnIndex("weed3_count1"));
		this.weed3_count2 = c.getInt(c.getColumnIndex("weed3_count2"));
		this.weed3_count3 = c.getInt(c.getColumnIndex("weed3_count3"));
		this.weed3_count4 = c.getInt(c.getColumnIndex("weed3_count4"));
		this.weed3_count5 = c.getInt(c.getColumnIndex("weed3_count5"));
		this.weed3_count6 = c.getInt(c.getColumnIndex("weed3_count6"));
		this.weed3_name = c.getString(c.getColumnIndex("weed3_name"));
		this.west_isolation_condition = c.getString(c.getColumnIndex("west_isolation_condition"));
		this.west_isolation_crop = c.getString(c.getColumnIndex("west_isolation_crop"));
		this.west_isolation_size = c.getString(c.getColumnIndex("west_isolation_size"));
		this.west_isolation_type = c.getString(c.getColumnIndex("west_isolation_type"));
		this.year = c.getString(c.getColumnIndex("year"));
		this.status = c.getString(c.getColumnIndex("status"));
	}
	
	// full field details for detail view
	public Field(Bundle b){
		this.acres = b.getString("acres");
		this.agronomist = b.getString("agronomist");
		this.area = b.getString("area");
		this.comments = b.getString("comments");
		this.contract_grower = b.getString("contract_grower");
		this.crop = b.getString("crop");
		this.crop_condition_appearance = b.getString("crop_condition_appearance");
		this.crop_condition_uniformity = b.getString("crop_condition_uniformity");
		this.crop_condition_weed = b.getString("crop_condition_weed");
		this.customer_id = b.getInt("customer_id");
		this.date_inspected = b.getString("date_inspected");
		this.date_ready = b.getString("date_ready");
		this.date_ready_to = b.getString("date_ready_to");
		this.east_isolation_condition = b.getString("east_isolation_condition");
		this.east_isolation_crop = b.getString("east_isolation_crop");
		this.east_isolation_size = b.getString("east_isolation_size");
		this.east_isolation_type = b.getString("east_isolation_type");
		this.female_crop_certificate_no = b.getString("female_crop_certificate_no");
		this.female_seed_sealing_no = b.getString("female_seed_sealing_no");
		this.female_tags = b.getString("female_tags");
		this.female_year = b.getString("female_year");
		this.field_center_lat = b.getDouble("field_center_lat");
		this.field_center_lng = b.getDouble("field_center_lng");
		this.field_entrance_lat = b.getDouble("field_entrance_lat");
		this.field_entrance_lng = b.getDouble("field_entrance_lng");
		this.field_location = b.getString("field_location");
		this.field_no = b.getString("field_no");
		this.flowering_female = b.getString("flowering_female");
		this.flowering_male = b.getString("flowering_male");
		this.gps_max_lat = b.getDouble("gps_max_lat");
		this.gps_max_lng = b.getDouble("gps_max_lng");
		this.gps_min_lat = b.getDouble("gps_min_lat");
		this.gps_min_lng = b.getDouble("gps_min_lng");
		this.grower_no = b.getString("grower_no");
		this.id = b.getInt("id");
		this.inspector_1_id = b.getInt("inspector_1_id");
		this.inspector_2_id = b.getInt("inspector_2_id");
		this.internal_client_comments = b.getString("internal_client_comments");
		this.male_crop_certificate_no = b.getString("male_crop_certificate_no");
		this.male_seed_sealing_no = b.getString("male_seed_sealing_no");
		this.male_tags = b.getString("male_tags");
		this.male_year = b.getString("male_year");
		this.north_isolation_condition = b.getString("north_isolation_condition");
		this.north_isolation_crop = b.getString("north_isolation_crop");
		this.north_isolation_size = b.getString("north_isolation_size");
		this.north_isolation_type = b.getString("north_isolation_type");
		this.objectionable_weeds = b.getString("objectionable_weeds");
		this.open_pollinated_crops = b.getString("open_pollinated_crops");
		this.other_crop1_count1 = b.getInt("other_crop1_count1");
		this.other_crop1_count2 = b.getInt("other_crop1_count2");
		this.other_crop1_count3 = b.getInt("other_crop1_count3");
		this.other_crop1_count4 = b.getInt("other_crop1_count4");
		this.other_crop1_count5 = b.getInt("other_crop1_count5");
		this.other_crop1_count6 = b.getInt("other_crop1_count6");
		this.other_crop1_name = b.getString("other_crop1_name");
		this.other_crop2_count1 = b.getInt("other_crop2_count1");
		this.other_crop2_count2 = b.getInt("other_crop2_count2");
		this.other_crop2_count3 = b.getInt("other_crop2_count3");
		this.other_crop2_count4 = b.getInt("other_crop2_count4");
		this.other_crop2_count5 = b.getInt("other_crop2_count5");
		this.other_crop2_count6 = b.getInt("other_crop2_count6");
		this.other_crop2_name = b.getString("other_crop2_name");
		this.other_crop3_count1 = b.getInt("other_crop3_count1");
		this.other_crop3_count2 = b.getInt("other_crop3_count2");
		this.other_crop3_count3 = b.getInt("other_crop3_count3");
		this.other_crop3_count4 = b.getInt("other_crop3_count4");
		this.other_crop3_count5 = b.getInt("other_crop3_count5");
		this.other_crop3_count6 = b.getInt("other_crop3_count6");
		this.other_crop3_name = b.getString("other_crop3_name");
		this.plants_per_m2 = b.getString("plants_per_m2");
		this.previous_crop1 = b.getString("previous_crop1");
		this.previous_crop2 = b.getString("previous_crop2");
		this.previous_crop3 = b.getString("previous_crop3");
		this.previous_crop4 = b.getString("previous_crop4");
		this.previous_crop5 = b.getString("previous_crop5");
		this.qa = b.getString("qa");
		this.reinspection_of_id = b.getInt("reinspection_of_id");
		this.seq_no = b.getString("seq_no");
		this.south_isolation_condition = b.getString("south_isolation_condition");
		this.south_isolation_crop = b.getString("south_isolation_crop");
		this.south_isolation_size = b.getString("south_isolation_size");
		this.south_isolation_type = b.getString("south_isolation_type");
		this.weed1_count1 = b.getInt("weed1_count1");
		this.weed1_count2 = b.getInt("weed1_count2");
		this.weed1_count3 = b.getInt("weed1_count3");
		this.weed1_count4 = b.getInt("weed1_count4");
		this.weed1_count5 = b.getInt("weed1_count5");
		this.weed1_count6 = b.getInt("weed1_count6");
		this.weed1_name = b.getString("weed1_name");
		this.weed2_count1 = b.getInt("weed2_count1");
		this.weed2_count2 = b.getInt("weed2_count2");
		this.weed2_count3 = b.getInt("weed2_count3");
		this.weed2_count4 = b.getInt("weed2_count4");
		this.weed2_count5 = b.getInt("weed2_count5");
		this.weed2_count6 = b.getInt("weed2_count6");
		this.weed2_name = b.getString("weed2_name");
		this.weed3_count1 = b.getInt("weed3_count1");
		this.weed3_count2 = b.getInt("weed3_count2");
		this.weed3_count3 = b.getInt("weed3_count3");
		this.weed3_count4 = b.getInt("weed3_count4");
		this.weed3_count5 = b.getInt("weed3_count5");
		this.weed3_count6 = b.getInt("weed3_count6");
		this.weed3_name = b.getString("weed3_name");
		this.west_isolation_condition = b.getString("west_isolation_condition");
		this.west_isolation_crop = b.getString("west_isolation_crop");
		this.west_isolation_size = b.getString("west_isolation_size");
		this.west_isolation_type = b.getString("west_isolation_type");
		this.year = b.getString("year");
		this.status = b.getString("status");
	}

    public Bundle getField() {
    	Bundle b = new Bundle();
    	
    	b.putString("acres", acres);
    	b.putString("agronomist", agronomist);
    	b.putString("area", area);
    	b.putString("comments", comments);
    	b.putString("contract_grower", contract_grower);
    	b.putString("crop", crop);
    	b.putString("crop_condition_appearance", crop_condition_appearance);
    	b.putString("crop_condition_uniformity", crop_condition_uniformity);
    	b.putString("crop_condition_weed", crop_condition_weed);
    	b.putInt("customer_id", customer_id);
    	b.putString("date_inspected", date_inspected);
    	b.putString("date_ready", date_ready);
    	b.putString("date_ready_to", date_ready_to);
    	b.putString("east_isolation_condition", east_isolation_condition);
    	b.putString("east_isolation_crop", east_isolation_crop);
    	b.putString("east_isolation_size", east_isolation_size);
    	b.putString("east_isolation_type", east_isolation_type);
    	b.putString("female_crop_certificate_no", female_crop_certificate_no);
    	b.putString("female_seed_sealing_no", female_seed_sealing_no);
    	b.putString("female_tags", female_tags);
    	b.putString("female_year", female_year);
    	b.putDouble("field_center_lat", field_center_lat);
    	b.putDouble("field_center_lng", field_center_lng);
    	b.putDouble("field_entrance_lat", field_entrance_lat);
    	b.putDouble("field_entrance_lng", field_entrance_lng);
    	b.putString("field_location", field_location);
    	b.putString("field_no", field_no);
    	b.putString("flowering_female", flowering_female);
    	b.putString("flowering_male", flowering_male);
    	b.putDouble("gps_max_lat", gps_max_lat);
    	b.putDouble("gps_max_lng", gps_max_lng);
    	b.putDouble("gps_min_lat", gps_min_lat);
    	b.putDouble("gps_min_lng", gps_min_lng);
    	b.putString("grower_no", grower_no);
    	b.putInt("id", id);
    	b.putInt("inspector_1_id", inspector_1_id);
    	b.putInt("inspector_2_id", inspector_2_id);
    	b.putString("internal_client_comments", internal_client_comments);
    	b.putString("male_crop_certificate_no", male_crop_certificate_no);
    	b.putString("male_seed_sealing_no", male_seed_sealing_no);
    	b.putString("male_tags", male_tags);
    	b.putString("male_year", male_year);
    	b.putString("north_isolation_condition", north_isolation_condition);
    	b.putString("north_isolation_crop", north_isolation_crop);
    	b.putString("north_isolation_size", north_isolation_size);
    	b.putString("north_isolation_type", north_isolation_type);
    	b.putString("objectionable_weeds", objectionable_weeds);
    	b.putString("open_pollinated_crops", open_pollinated_crops);
    	b.putInt("other_crop1_count1", other_crop1_count1);
    	b.putInt("other_crop1_count2", other_crop1_count2);
    	b.putInt("other_crop1_count3", other_crop1_count3);
    	b.putInt("other_crop1_count4", other_crop1_count4);
    	b.putInt("other_crop1_count5", other_crop1_count5);
    	b.putInt("other_crop1_count6", other_crop1_count6);
    	b.putString("other_crop1_name", other_crop1_name);
    	b.putInt("other_crop2_count1", other_crop2_count1);
    	b.putInt("other_crop2_count2", other_crop2_count2);
    	b.putInt("other_crop2_count3", other_crop2_count3);
    	b.putInt("other_crop2_count4", other_crop2_count4);
    	b.putInt("other_crop2_count5", other_crop2_count5);
    	b.putInt("other_crop2_count6", other_crop2_count6);
    	b.putString("other_crop2_name", other_crop2_name);
    	b.putInt("other_crop3_count1", other_crop3_count1);
    	b.putInt("other_crop3_count2", other_crop3_count2);
    	b.putInt("other_crop3_count3", other_crop3_count3);
    	b.putInt("other_crop3_count4", other_crop3_count4);
    	b.putInt("other_crop3_count5", other_crop3_count5);
    	b.putInt("other_crop3_count6", other_crop3_count6);
    	b.putString("other_crop3_name", other_crop3_name);
    	b.putString("plants_per_m2", plants_per_m2);
    	b.putString("previous_crop1", previous_crop1);
    	b.putString("previous_crop2", previous_crop2);
    	b.putString("previous_crop3", previous_crop3);
    	b.putString("previous_crop4", previous_crop4);
    	b.putString("previous_crop5", previous_crop5);
    	b.putString("qa", qa);
    	b.putInt("reinspection_of_id", reinspection_of_id);
    	b.putString("seq_no", seq_no);
    	b.putString("south_isolation_condition", south_isolation_condition);
    	b.putString("south_isolation_crop", south_isolation_crop);
    	b.putString("south_isolation_size", south_isolation_size);
    	b.putString("south_isolation_type", south_isolation_type);
    	b.putInt("weed1_count1", weed1_count1);
    	b.putInt("weed1_count2", weed1_count2);
    	b.putInt("weed1_count3", weed1_count3);
    	b.putInt("weed1_count4", weed1_count4);
    	b.putInt("weed1_count5", weed1_count5);
    	b.putInt("weed1_count6", weed1_count6);
    	b.putString("weed1_name", weed1_name);
    	b.putInt("weed2_count1", weed2_count1);
    	b.putInt("weed2_count2", weed2_count2);
    	b.putInt("weed2_count3", weed2_count3);
    	b.putInt("weed2_count4", weed2_count4);
    	b.putInt("weed2_count5", weed2_count5);
    	b.putInt("weed2_count6", weed2_count6);
    	b.putString("weed2_name", weed2_name);
    	b.putInt("weed3_count1", weed3_count1);
    	b.putInt("weed3_count2", weed3_count2);
    	b.putInt("weed3_count3", weed3_count3);
    	b.putInt("weed3_count4", weed3_count4);
    	b.putInt("weed3_count5", weed3_count5);
    	b.putInt("weed3_count6", weed3_count6);
    	b.putString("weed3_name", weed3_name);
    	b.putString("west_isolation_condition", west_isolation_condition);
    	b.putString("west_isolation_crop", west_isolation_crop);
    	b.putString("west_isolation_size", west_isolation_size);
    	b.putString("west_isolation_type", west_isolation_type);
    	b.putString("year", year);
    	b.putString("status", status);
    	return b;
    }
    
    public int getIcon(){
    	int customer_id = this.customer_id;
    	Status status = Status.valueOf(this.status.toUpperCase());
    	int res_id = 0;
    	
    	switch (status) {
	    	case FIELD_ASSIGNED:
	    		switch (customer_id) {
	    			case 1296: res_id = R.drawable.field_assigned_1296; break;
	    			case 1638: res_id = R.drawable.field_assigned_1638; break;
	    			case 1968: res_id = R.drawable.field_assigned_1638; break;
	    			case 2167: res_id = R.drawable.field_assigned_2167; break;
	    			case 7151: res_id = R.drawable.field_assigned_7151; break;
	    			default:
	    				res_id = R.drawable.field_assigned;
	    				break;
	    		}
	    		break;
	    	case INSPECTION_COMPLETED:
	    		switch (customer_id) {
	    			case 1296: res_id = R.drawable.field_complete_1296; break;
	    			case 1638: res_id = R.drawable.field_complete_1638; break;                    
	    			case 1968: res_id = R.drawable.field_complete_1638; break;                    
	    			case 2167: res_id = R.drawable.field_complete_2167; break;                    
	    			case 7151: res_id = R.drawable.field_complete_7151; break;                    
	    			default:
	    				res_id = R.drawable.field_complete;
	    				break;
	    		}
	    		break;
	    	case FIELD_NOT_READY:
	    		switch (customer_id) {
	    			case 1296: res_id = R.drawable.field_notready_1296; break;                    
	    			case 1638: res_id = R.drawable.field_notready_1638; break;                    
	    			case 1968: res_id = R.drawable.field_notready_1638; break;                    
	    			case 2167: res_id = R.drawable.field_notready_2167; break;                    
	    			case 7151: res_id = R.drawable.field_notready_7151; break;                    
	    			default:                      
	    				res_id = R.drawable.field_notready;
	    				break;
	    		}
	    		break;
	    	case FIELD_READY_EXPIRED:
	    		switch (customer_id) {
	    			case 1296: res_id = R.drawable.field_notready_1296; break;                    
	    			case 1638: res_id = R.drawable.field_notready_1638; break;                    
	    			case 1968: res_id = R.drawable.field_notready_1638; break;                    
	    			case 2167: res_id = R.drawable.field_notready_2167; break;                    
	    			case 7151: res_id = R.drawable.field_notready_7151; break;                    
	    			default:
	    				res_id = R.drawable.field_notready;
	    				break;
	    		}
	    		break;
	    	case FIELD_REINSPECTION:
	    		switch (customer_id) {
	    			case 1296: res_id = R.drawable.field_reinspect_1296; break;
	    			case 1638: res_id = R.drawable.field_reinspect_1638; break;                    
	    			case 1968: res_id = R.drawable.field_reinspect_1638; break;                    
	    			case 2167: res_id = R.drawable.field_reinspect_2167; break;                    
	    			case 7151: res_id = R.drawable.field_reinspect_7151; break;                    
	    			default:
	    				res_id = R.drawable.field_reinspect;
	    				break;
	    		}
	    		break;
	    	case FIELD_READY:
	    		switch (customer_id) {
	    			case 1296: res_id = R.drawable.field_ready_1296; break;                    
	    			case 1638: res_id = R.drawable.field_ready_1638; break;                    
	    			case 1968: res_id = R.drawable.field_ready_1638; break;                    
	    			case 2167: res_id = R.drawable.field_ready_2167; break;                    
	    			case 7151: res_id = R.drawable.field_ready_7151; break;                    
	    			default:
	    				res_id = R.drawable.field_ready;
	    				break;
	    		}
	    		break;
	    	case INSPECTION_PENDING_REVIEW:
	    		switch (customer_id) {
	    			case 1296: res_id = R.drawable.field_pendingreview_1296; break;                    
	    			case 1638: res_id = R.drawable.field_pendingreview_1638; break;                    
	    			case 1968: res_id = R.drawable.field_pendingreview_1638; break;                    
	    			case 2167: res_id = R.drawable.field_pendingreview_2167; break;                    
	    			case 7151: res_id = R.drawable.field_pendingreview_7151; break;                    
	    			default:                      
	    				res_id = R.drawable.field_pendingreview;
	    				break;
	    		}
	    		break;
	    	default:
	    		res_id = R.drawable.unknown;
	    		break;
    	}
    	
    	return res_id;
    }
    
    // status of field.
    public enum Status {
    	FIELD_ASSIGNED,
    	INSPECTION_COMPLETED,
    	FIELD_NOT_READY,
    	FIELD_READY_EXPIRED,
    	FIELD_REINSPECTION,
        FIELD_READY,
        INSPECTION_PENDING_REVIEW;
    }    
    
}
