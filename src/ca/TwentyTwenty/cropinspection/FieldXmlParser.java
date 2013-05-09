package ca.TwentyTwenty.cropinspection;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

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
    
    // This class represents a single field in the XML feed.
    public static class Field {   			
        public final int id;
        public final String crop_name;
        public final String field_entrance_lng;
        public final String field_entrance_lat;
        public final String field_center_lng;
        public final String field_center_lat;

        public Field(Integer id, String crop, String field_entrance_lng, String field_entrance_lat, String field_center_lng, String field_center_lat) {
            this.id = id;
            this.crop_name = crop;
            this.field_entrance_lng = field_entrance_lng;
            this.field_entrance_lat = field_entrance_lat;
            this.field_center_lng = field_center_lng;
            this.field_center_lat = field_center_lat;
        }
    }
    
    // Parses the contents of a field
    private Field readField(XmlPullParser parser) throws XmlPullParserException, IOException {
        Integer id = null;
        String crop_name = null;
        String field_entrance_lng;
        String field_entrance_lat;
        String field_center_lng;
        String field_center_lat;
        		
		id = Integer.parseInt(parser.getAttributeValue(null, "id"));
		crop_name = parser.getAttributeValue(null, "crop");
		field_entrance_lng = parser.getAttributeValue(null, "fieldEntranceLng");
		field_entrance_lat = parser.getAttributeValue(null, "fieldEntranceLat");
		field_center_lng = parser.getAttributeValue(null, "fieldCenterLng");
		field_center_lat = parser.getAttributeValue(null, "fieldCenterLat");
		//Log.w("info", "name = " + field_entrance_lng + ", value = " + id);
            
//            else if(eventType == XmlPullParser.END_TAG) {
//                Log.d("debug", "In end tag = "+parser.getName());
//
//            }
        
        return new Field(id, crop_name, field_entrance_lng, field_entrance_lat, field_center_lng, field_center_lat);
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
}