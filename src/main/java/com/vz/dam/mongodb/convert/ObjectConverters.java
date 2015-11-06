/*package com.vz.dam.mongodb.convert;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import com.mongodb.DBObject;
import com.vz.dam.domain.DAMAssetDetails;

public class ObjectConverters {

  private ObjectConverters() {

  }

  *//**
   * <p>
   * getConvertersToRegister.
   * </p>
   *
   * @return a {@link java.util.List} object.
   *//*
  public static List<Converter<?, ?>> getConvertersToRegister() {
    List<Converter<?, ?>> list = new ArrayList<Converter<?, ?>>();
    list.add(DbObjectToDAMAssetDetailsConverter.INSTANCE);
    return list;
  }

  @ReadingConverter
  public static enum DbObjectToDAMAssetDetailsConverter implements Converter<DBObject, DAMAssetDetails> {
    INSTANCE;

    @Override
    public DAMAssetDetails convert(DBObject source) {

      if (source == null) {
        return null;
      }
      DBObject db0 = (DBObject) source.get("dateCreated");
      DatatypeFactory dtf;
      XMLGregorianCalendar cal = null;
      try {
        dtf = DatatypeFactory.newInstance();
        String fraction = db0.get("fractionalSecond").toString();
        cal = dtf.newXMLGregorianCalendar(Integer.valueOf(db0.get("year").toString()),
            Integer.valueOf(db0.get("month").toString()), Integer.valueOf(db0.get("day").toString()),
            Integer.valueOf(db0.get("hour").toString()), Integer.valueOf(db0.get("minute").toString()),
            Integer.valueOf(db0.get("second").toString()),
            Integer.valueOf(fraction.substring(fraction.indexOf('.') + 1)),
            Integer.valueOf(db0.get("timezone").toString()));

      } catch (DatatypeConfigurationException e) {
      }
      DAMAssetDetails assetDetails = new DAMAssetDetails();
      assetDetails.setAssetId(String.valueOf(source.get("_id")));
      assetDetails.setAssetName(String.valueOf(source.get("assetName")));
      assetDetails.setAssetType(String.valueOf(source.get("assetType")));
      assetDetails.setStoragePath(String.valueOf(source.get("storagePath")));
      assetDetails.setCheckSum(String.valueOf(source.get("checkSum")));
      assetDetails.setTranscoderTool(String.valueOf(source.get("transcoderTool")));
      assetDetails.setDateCreated(cal);
      assetDetails.setParentId(String.valueOf(source.get("parentId")));

      return assetDetails;
    }
  }


}
*/