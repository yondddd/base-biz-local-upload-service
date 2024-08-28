package com.ruijing.base.local.upload.web.s3.server.resp;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "ListAllMyBucketsResult")
public class ListAllMyBucketsResult {
    
    private List<Bucket> buckets;
    private Owner owner;
    
    @XmlElementWrapper(name = "Buckets") // Wrap the list in a Buckets element
    @XmlElement(name = "Bucket")
    public List<Bucket> getBuckets() {
        return buckets;
    }
    
    public void setBuckets(List<Bucket> buckets) {
        this.buckets = buckets;
    }
    
    @XmlElement(name = "Owner")
    public Owner getOwner() {
        return owner;
    }
    
    public void setOwner(Owner owner) {
        this.owner = owner;
    }
    
    public static class Bucket {
        private String creationDate;
        private String name;
        
        @XmlElement(name = "CreationDate")
        public String getCreationDate() {
            return creationDate;
        }
        
        public void setCreationDate(String creationDate) {
            this.creationDate = creationDate;
        }
        
        @XmlElement(name = "Name")
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
    }
    
    public static class Owner {
        private String displayName;
        private String id;
        
        @XmlElement(name = "DisplayName")
        public String getDisplayName() {
            return displayName;
        }
        
        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }
        
        @XmlElement(name = "ID")
        public String getId() {
            return id;
        }
        
        public void setId(String id) {
            this.id = id;
        }
    }
    
}
