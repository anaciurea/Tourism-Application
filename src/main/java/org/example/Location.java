package org.example;

public class Location {
    private String county;
    private Integer sirutaCode;

    private String locality;
    private String adminUnit;
    private String address;
    private Integer latitude;
    private Integer longitude;

    public String getCounty() {
        return county;
    }

    public Integer getSirutaCode() {
        return sirutaCode;
    }

    public  String getLocality() {
        return locality;
    }

    public String getAdminUnit() {
        return adminUnit;
    }

    public  String getAddress() {
        return address;
    }

    public  Integer getLatitude() {
        return latitude;
    }

    public  Integer getLongitude() {
        return longitude;
    }

    private Location(Builder builder) {
        this.county = builder.county;
        this.sirutaCode = builder.sirutaCode;

        this.locality = builder.locality;
        this.adminUnit = builder.adminUnit;
        this.address = builder.address;
        this.latitude = builder.latitude;
        this.longitude = builder.longitude;
    }

    public static class Builder {
        private String county;
        private Integer sirutaCode;

        private String locality;
        private String adminUnit;
        private String address;
        private Integer latitude;
        private Integer longitude;

       public Builder(String county, Integer sirutaCode) {
            this.county = county;
            this.sirutaCode = sirutaCode;
        }

        public Builder setLocality(String locality) {
           this.locality = locality;
           return this;
        }

        public Builder setAdminUnit(String adminUnit) {
           this.adminUnit = adminUnit;
           return this;
        }

        public Builder setAddress(String address) {
           this.address= address;
           return this;
        }

        public Builder setLatitude(Integer latitude) {
           this.latitude = latitude;
           return this;
        }

        public Builder setLongitude(Integer longitude) {
           this.longitude = longitude;
           return this;
        }

        public Location build() {
           return new Location(this);
        }

    }
}
