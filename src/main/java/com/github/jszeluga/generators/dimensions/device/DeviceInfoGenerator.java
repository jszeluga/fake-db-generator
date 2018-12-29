package com.github.jszeluga.generators.dimensions.device;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.github.jszeluga.entity.dimension.DeviceDimension;
import com.github.jszeluga.generators.AbstractGenerator;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

public class DeviceInfoGenerator extends AbstractGenerator<DeviceDimension> {

    private static final XmlMapper xmlMapper = new XmlMapper();
    private List<Device> deviceList = new ArrayList<>();

    @Override
    public void initialize() throws Exception {
        InputStream xmlStream = this.getClass().getClassLoader().getResourceAsStream("device_data_source.xml.gz");
        Objects.requireNonNull(xmlStream);

        try (GZIPInputStream xmlGzip = new GZIPInputStream(xmlStream)){
            XMLStreamReader xmlStreamReader = XMLInputFactory.newFactory().createXMLStreamReader(xmlGzip);
            parseXml(xmlStreamReader);
            xmlStreamReader.close();

            deviceList.forEach(rec->{
                //turn list of Property into Map for easier processing
                Map<String, String> propertyMap = rec.getProperties().stream()
                        .distinct()
                        .collect(Collectors.toMap(Property::getName, Property::getValue));

                rec.setPropertyMap(propertyMap);

                //Create vendor name from the parentId
                String parentId = rec.getParentId();
                if(parentId != null && parentId.startsWith("generic")){
                    String vendor = parentId.replace("generic", "");
                    rec.setVendor(vendor);
                }
            });
        }
    }

    private void parseXml(XMLStreamReader sr) throws Exception {
        //advance to list of devices
        String currentElem = "";
        while(sr.hasNext() && !"Devices".equals(currentElem)){
            int next = sr.next();
            if(next == XMLStreamReader.START_ELEMENT){
                currentElem = sr.getLocalName();
            }
        }

        sr.next(); //advance to <device> tag
        while(sr.hasNext()){
            Device device = xmlMapper.readValue(sr, Device.class);
            deviceList.add(device);

            //at end element
            //go to next tag
            int next = sr.nextTag();
            if(next == XMLStreamReader.END_ELEMENT){
                break;
            }
        }


    }

    @Override
    public void accept(DeviceDimension deviceDimension) {
        if(deviceDimension != null){
            if(!deviceList.isEmpty()) {

                int index = ThreadLocalRandom.current().nextInt(0, deviceList.size());
                Device device = deviceList.get(index);
                deviceList.remove(index); //unique devices

                Map<String, String> propertyMap = device.getPropertyMap();

                //If there is a vendor property then that overrides the calculated vendor
                if (propertyMap.containsKey("vendor")) {
                    deviceDimension.setVendor(propertyMap.get("vendor"));
                } else {
                    deviceDimension.setVendor(device.getVendor());
                }

                if (propertyMap.containsKey("model")) {
                    deviceDimension.setModel(propertyMap.get("model"));
                }
                if (propertyMap.containsKey("marketing_name")) {
                    deviceDimension.setMarketingName(propertyMap.get("marketing_name"));
                } else if (deviceDimension.getModel() != null) {
                    deviceDimension.setMarketingName(deviceDimension.getModel());
                }
                if (propertyMap.containsKey("device_os")) {
                    deviceDimension.setDeviceOs(propertyMap.get("device_os"));
                }
                if (propertyMap.containsKey("device_os_version")) {
                    deviceDimension.setDeviceOsVersion(propertyMap.get("device_os_version"));
                }

                boolean volte = ThreadLocalRandom.current().nextBoolean();
                deviceDimension.setVolte(volte);
            } else {
                throw new RuntimeException("DEVICE_DIM too large. No more data to add");
            }
        }

    }

    @JacksonXmlRootElement(localName = "device")
    private static class Device {

        @JacksonXmlProperty(localName = "id", isAttribute = true)
        private String id;

        @JacksonXmlProperty(localName = "parentId", isAttribute = true)
        private String parentId;


        @JacksonXmlProperty(localName = "property")
        @JacksonXmlElementWrapper(useWrapping = false)
        private List<Property> properties;

        @JsonIgnore
        private Map<String, String> propertyMap;

        @JsonIgnore
        private String vendor;

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<Property> getProperties() {
            return properties;
        }

        public void setProperties(List<Property> properties) {
            this.properties = properties;
        }

        public Map<String, String> getPropertyMap() {
            return propertyMap;
        }

        public void setPropertyMap(Map<String, String> propertyMap) {
            this.propertyMap = propertyMap;
        }

        public String getVendor() {
            return vendor;
        }

        public void setVendor(String vendor) {
            this.vendor = vendor;
        }
    }

    private static class Property {

        @JacksonXmlProperty(localName = "name", isAttribute = true)
        private String name;

        @JacksonXmlProperty(localName = "value", isAttribute = true)
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Property property = (Property) o;
            return Objects.equals(name, property.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }
}
