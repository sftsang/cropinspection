package ca.TwentyTwenty.cropinspection;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.os.Bundle;
import android.util.Log;
import android.util.Xml;

public class FieldXmlParser {
	  private static final String ns = null;
	  
	  public List parse(InputStream in) throws XmlPullParserException, IOException {
		  try {
			  XmlPullParser parser = Xml.newPullParser();
			  parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			  parser.setInput(in, null);
			  parser.nextTag();
			  return readFieldList(parser);
		  } finally {
			  in.close();
		  }
	  }
	  
	  private List readFieldList(XmlPullParser parser) throws XmlPullParserException, IOException {
		  List<Customer> customers = new ArrayList();
		  List<Field> fields = new ArrayList();
		  List<List> responseObject = new ArrayList();
		  int eventType = parser.getEventType();
		  
		  parser.require(XmlPullParser.START_TAG, ns, "data");
		  
		  while(eventType != XmlPullParser.END_DOCUMENT) {
	            if(eventType == XmlPullParser.START_TAG) {
	            	String xname = parser.getName();
	            	if (xname.equals("customer")) {
	            		customers.add(readCustomer(parser));
	            	} else if (xname.equals("cropInspection")) {
	            		fields.add(readField(parser));
	            	}
	            }
	            
	            eventType = parser.next();
		  }
		  
//		  while (parser.next() != XmlPullParser.END_TAG) {
//			  if (parser.getEventType() != XmlPullParser.START_TAG) {
//				  continue;
//			  }
//			        
//			  String name = parser.getName();
//			  // Starts by looking for the customer tag		        
//			  if (name.equals("customers")) {
//				  customers.add(readCustomer(parser));
//			  } else {
//			      skip(parser);
//			  }
//		  }
		  
		  responseObject.add(customers);
		  responseObject.add(fields);
		  
		  return responseObject;
	  }
	  
    // This class represents a single customer in the XML feed.
    public static class Customer {
        public final int id;
        public final String name;

        Customer(Integer id, String name) {
            this.id = id;
            this.name = name;
        }
    }
    
    // Parses the contents of a field
    private Field readField(XmlPullParser parser) throws XmlPullParserException, IOException {
		Bundle b = new Bundle();
		
	    b.putString("acres", parser.getAttributeValue(null, "acres"));
	    b.putString("agronomist", parser.getAttributeValue(null, "agronomist"));
	    b.putString("area", parser.getAttributeValue(null, "area"));
	    b.putString("comments", parser.getAttributeValue(null, "comments"));
	    b.putString("contract_grower", parser.getAttributeValue(null, "contract_grower"));
	    b.putString("crop", parser.getAttributeValue(null, "crop"));
	    b.putString("crop_condition_appearance", parser.getAttributeValue(null, "crop_condition_appearance"));
	    b.putString("crop_condition_uniformity", parser.getAttributeValue(null, "crop_condition_uniformity"));
	    b.putString("crop_condition_weed", parser.getAttributeValue(null, "crop_condition_weed"));
	    b.putInt("customer_id", xmlIntValue(parser, "customer_id"));
	    b.putString("date_inspected", parser.getAttributeValue(null, "date_inspected"));
	    b.putString("date_ready", parser.getAttributeValue(null, "date_ready"));
	    b.putString("date_ready_to", parser.getAttributeValue(null, "date_ready_to"));
	    b.putString("east_isolation_condition", parser.getAttributeValue(null, "east_isolation_condition"));
	    b.putString("east_isolation_crop", parser.getAttributeValue(null, "east_isolation_crop"));
	    b.putString("east_isolation_size", parser.getAttributeValue(null, "east_isolation_size"));
	    b.putString("east_isolation_type", parser.getAttributeValue(null, "east_isolation_type"));
	    b.putString("female_crop_certificate_no", parser.getAttributeValue(null, "female_crop_certificate_no"));
	    b.putString("female_seed_sealing_no", parser.getAttributeValue(null, "female_seed_sealing_no"));
	    b.putString("female_tags", parser.getAttributeValue(null, "female_tags"));
	    b.putString("female_year", parser.getAttributeValue(null, "female_year"));
	    b.putDouble("field_center_lat", xmlDoubleValue(parser, "field_center_lat"));
	    b.putDouble("field_center_lng", xmlDoubleValue(parser, "field_center_lng"));
	    b.putDouble("field_entrance_lat", xmlDoubleValue(parser, "field_entrance_lat"));
	    b.putDouble("field_entrance_lng", xmlDoubleValue(parser, "field_entrance_lng"));
	    b.putString("field_location", parser.getAttributeValue(null, "field_location"));
	    b.putString("field_no", parser.getAttributeValue(null, "field_no"));
	    b.putString("flowering_female", parser.getAttributeValue(null, "flowering_female"));
	    b.putString("flowering_male", parser.getAttributeValue(null, "flowering_male"));
	    b.putDouble("gps_max_lat", xmlDoubleValue(parser, "gps_max_lat"));
	    b.putDouble("gps_max_lng", xmlDoubleValue(parser, "gps_max_lng"));
	    b.putDouble("gps_min_lat", xmlDoubleValue(parser, "gps_min_lat"));
	    b.putDouble("gps_min_lng", xmlDoubleValue(parser, "gps_min_lng"));
	    b.putString("grower_no", parser.getAttributeValue(null, "grower_no"));
	    b.putInt("id", xmlIntValue(parser, "id"));
	    b.putInt("inspector_1_id", xmlIntValue(parser, "inspector_1_id"));
	    b.putInt("inspector_2_id", xmlIntValue(parser, "inspector_2_id"));
	    b.putString("internal_client_comments", parser.getAttributeValue(null, "internal_client_comments"));
	    b.putString("male_crop_certificate_no", parser.getAttributeValue(null, "male_crop_certificate_no"));
	    b.putString("male_seed_sealing_no", parser.getAttributeValue(null, "male_seed_sealing_no"));
	    b.putString("male_tags", parser.getAttributeValue(null, "male_tags"));
	    b.putString("male_year", parser.getAttributeValue(null, "male_year"));
	    b.putString("north_isolation_condition", parser.getAttributeValue(null, "north_isolation_condition"));
	    b.putString("north_isolation_crop", parser.getAttributeValue(null, "north_isolation_crop"));
	    b.putString("north_isolation_size", parser.getAttributeValue(null, "north_isolation_size"));
	    b.putString("north_isolation_type", parser.getAttributeValue(null, "north_isolation_type"));
	    b.putString("objectionable_weeds", parser.getAttributeValue(null, "objectionable_weeds"));
	    b.putString("open_pollinated_crops", parser.getAttributeValue(null, "open_pollinated_crops"));
	    b.putInt("other_crop1_count1", xmlIntValue(parser, "other_crop1_count1"));
	    b.putInt("other_crop1_count2", xmlIntValue(parser, "other_crop1_count2"));
	    b.putInt("other_crop1_count3", xmlIntValue(parser, "other_crop1_count3"));
	    b.putInt("other_crop1_count4", xmlIntValue(parser, "other_crop1_count4"));
	    b.putInt("other_crop1_count5", xmlIntValue(parser, "other_crop1_count5"));
	    b.putInt("other_crop1_count6", xmlIntValue(parser, "other_crop1_count6"));
	    b.putString("other_crop1_name", parser.getAttributeValue(null, "other_cronullname"));
	    b.putInt("other_crop1_count1", xmlIntValue(parser, "other_crop1_count1"));
	    b.putInt("other_crop1_count2", xmlIntValue(parser, "other_crop1_count2"));
	    b.putInt("other_crop1_count3", xmlIntValue(parser, "other_crop1_count3"));
	    b.putInt("other_crop1_count4", xmlIntValue(parser, "other_crop1_count4"));
	    b.putInt("other_crop1_count5", xmlIntValue(parser, "other_crop1_count5"));
	    b.putInt("other_crop1_count6", xmlIntValue(parser, "other_crop1_count6"));
	    b.putString("other_crop1_name", parser.getAttributeValue(null, "other_crop1_name"));
	    b.putInt("other_crop2_count1", xmlIntValue(parser, "other_crop2_count1"));
	    b.putInt("other_crop2_count2", xmlIntValue(parser, "other_crop2_count2"));
	    b.putInt("other_crop2_count3", xmlIntValue(parser, "other_crop2_count3"));
	    b.putInt("other_crop2_count4", xmlIntValue(parser, "other_crop2_count4"));
	    b.putInt("other_crop2_count5", xmlIntValue(parser, "other_crop2_count5"));
	    b.putInt("other_crop2_count6", xmlIntValue(parser, "other_crop2_count6"));
	    b.putString("other_crop2_name", parser.getAttributeValue(null, "other_crop2_name"));
	    b.putInt("other_crop3_count1", xmlIntValue(parser, "other_crop3_count1"));
	    b.putInt("other_crop3_count2", xmlIntValue(parser, "other_crop3_count2"));
	    b.putInt("other_crop3_count3", xmlIntValue(parser, "other_crop3_count3"));
	    b.putInt("other_crop3_count4", xmlIntValue(parser, "other_crop3_count4"));
	    b.putInt("other_crop3_count5", xmlIntValue(parser, "other_crop3_count5"));
	    b.putInt("other_crop3_count6", xmlIntValue(parser, "other_crop3_count6"));
	    b.putString("other_crop3_name", parser.getAttributeValue(null, "other_crop3_name"));
	    b.putString("plants_per_m2", parser.getAttributeValue(null, "plants_per_m2"));
	    b.putString("previous_crop1", parser.getAttributeValue(null, "previous_crop1"));
	    b.putString("previous_crop2", parser.getAttributeValue(null, "previous_crop2"));
	    b.putString("previous_crop3", parser.getAttributeValue(null, "previous_crop3"));
	    b.putString("previous_crop4", parser.getAttributeValue(null, "previous_crop4"));
	    b.putString("previous_crop5", parser.getAttributeValue(null, "previous_crop5"));
	    b.putString("qa", parser.getAttributeValue(null, "qa"));
	    b.putInt("reinspection_of_id", xmlIntValue(parser, "reinspection_of_id"));
	    b.putString("seq_no", parser.getAttributeValue(null, "seq_no"));
	    b.putString("south_isolation_condition", parser.getAttributeValue(null, "south_isolation_condition"));
	    b.putString("south_isolation_crop", parser.getAttributeValue(null, "south_isolation_crop"));
	    b.putString("south_isolation_size", parser.getAttributeValue(null, "south_isolation_size"));
	    b.putString("south_isolation_type", parser.getAttributeValue(null, "south_isolation_type"));
	    b.putInt("weed1_count1", xmlIntValue(parser, "weed1_count1"));
	    b.putInt("weed1_count2", xmlIntValue(parser, "weed1_count2"));
	    b.putInt("weed1_count3", xmlIntValue(parser, "weed1_count3"));
	    b.putInt("weed1_count4", xmlIntValue(parser, "weed1_count4"));
	    b.putInt("weed1_count5", xmlIntValue(parser, "weed1_count5"));
	    b.putInt("weed1_count6", xmlIntValue(parser, "weed1_count6"));
	    b.putString("weed1_name", parser.getAttributeValue(null, "weed1_name"));
	    b.putInt("weed2_count1", xmlIntValue(parser, "weed2_count1"));
	    b.putInt("weed2_count2", xmlIntValue(parser, "weed2_count2"));
	    b.putInt("weed2_count3", xmlIntValue(parser, "weed2_count3"));
	    b.putInt("weed2_count4", xmlIntValue(parser, "weed2_count4"));
	    b.putInt("weed2_count5", xmlIntValue(parser, "weed2_count5"));
	    b.putInt("weed2_count6", xmlIntValue(parser, "weed2_count6"));
	    b.putString("weed2_name", parser.getAttributeValue(null, "weed2_name"));
	    b.putInt("weed3_count1", xmlIntValue(parser, "weed3_count1"));
	    b.putInt("weed3_count2", xmlIntValue(parser, "weed3_count2"));
	    b.putInt("weed3_count3", xmlIntValue(parser, "weed3_count3"));
	    b.putInt("weed3_count4", xmlIntValue(parser, "weed3_count4"));
	    b.putInt("weed3_count5", xmlIntValue(parser, "weed3_count5"));
	    b.putInt("weed3_count6", xmlIntValue(parser, "weed3_count6"));
	    b.putString("weed3_name", parser.getAttributeValue(null, "weed3_name"));
	    b.putString("west_isolation_condition", parser.getAttributeValue(null, "west_isolation_condition"));
	    b.putString("west_isolation_crop", parser.getAttributeValue(null, "west_isolation_crop"));
	    b.putString("west_isolation_size", parser.getAttributeValue(null, "west_isolation_size"));
	    b.putString("west_isolation_type", parser.getAttributeValue(null, "west_isolation_type"));
	    b.putString("year", parser.getAttributeValue(null, "year"));
	    b.putString("status", parser.getAttributeValue(null, "status"));
		
        return new Field(b);
    }

    // Parses the contents of a customer
    private Customer readCustomer(XmlPullParser parser) throws XmlPullParserException, IOException {
        //parser.require(XmlPullParser.START_TAG, ns, "customers");
        Integer id = null;
        String cname = null;
        
		id = Integer.parseInt(parser.getAttributeValue(null, "id"));
		cname = readText(parser);
		// Log.w("info", "name = " + cname + ", value = " + id);
            
//            else if(eventType == XmlPullParser.END_TAG) {
//                Log.d("debug", "In end tag = "+parser.getName());
//
//            }
        
        return new Customer(id, cname);
    }
    
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
  	  String result = "";
  	  if (parser.next() == XmlPullParser.TEXT) {
  	     result = parser.getText();
  	     parser.nextTag();
  	  }
  	  return result;
  	}


    // Skips tags the parser isn't interested in. Uses depth to handle nested tags. i.e.,
    // if the next tag after a START_TAG isn't a matching END_TAG, it keeps going until it
    // finds the matching END_TAG (as indicated by the value of "depth" being 0).
    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
            case XmlPullParser.END_TAG:
                    depth--;
                    break;
            case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
    
    // detect if the xml has a value and parse it otherwise return a 0
    private int xmlIntValue(XmlPullParser parser, String parseString) {
    	String parsedAttr = parser.getAttributeValue(null, parseString);
    	if (parsedAttr != null && !parsedAttr.isEmpty()) { 
    		return Integer.parseInt(parsedAttr);
    	} else {
    		return 0;
    	}		    	
    }
    
    // detect if the xml has a value and parse it otherwise return a 0
    private Double xmlDoubleValue(XmlPullParser parser, String parseString) {
    	String parsedAttr = parser.getAttributeValue(null, parseString);
    	if (parsedAttr != null && !parsedAttr.isEmpty()) { 
    		return Double.parseDouble(parsedAttr);
    	} else {
    		return 0.0;
    	}		    	
    }
}