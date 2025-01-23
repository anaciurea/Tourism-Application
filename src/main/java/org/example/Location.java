package org.example;

public class Location {
    private String county;
    private Integer sirutaCode;

    private String locality;
    private String adminUnit;
    private String address;
    private Integer latitude;
    private Integer longitude;
    private String phone;
    private String fax;
    private Integer foundingYear;
    private String url;
    private String email;

    public String getCounty() {
        return county;
    }

    public Integer getSirutaCode() {
        return sirutaCode;
    }

    public String getLocality() {
        return locality;
    }

    public String getAdminUnit() {
        return adminUnit;
    }

    public String getAddress() {
        return address;
    }

    public Integer getLatitude() {
        return latitude;
    }

    public Integer getLongitude() {
        return longitude;
    }

    public String getPhone() {
        return phone;
    }

    public String getFax() {
        return fax;
    }

    public Integer getFoundingYear() {
        return foundingYear;
    }

    public String getUrl() {
        return url;
    }

    public String getEmail() {
        return email;
    }

    private Location(Builder builder) {
        this.county = builder.county;
        this.sirutaCode = builder.sirutaCode;

        this.locality = builder.locality;
        this.adminUnit = builder.adminUnit;
        this.address = builder.address;
        this.latitude = builder.latitude;
        this.longitude = builder.longitude;
        this.phone = builder.phone;
        this.fax = builder.fax;
        this.foundingYear = builder.foundingYear;
        this.url = builder.url;
        this.email = builder.email; // Adăugat
    }

    public static class Builder {
        private String county;
        private Integer sirutaCode;

        private String locality;
        private String adminUnit;
        private String address;
        private Integer latitude;
        private Integer longitude;
        private String phone;
        private String fax;
        private Integer foundingYear;
        private String url;
        private String email;

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
            this.address = address;
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

        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder setFax(String fax) {
            this.fax = fax;
            return this;
        }

        public Builder setFoundingYear(Integer foundingYear) {
            this.foundingYear = foundingYear;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Location build() {
            return new Location(this);
        }
    }
}
