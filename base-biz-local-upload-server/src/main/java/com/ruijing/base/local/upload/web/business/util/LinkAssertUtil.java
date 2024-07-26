package com.ruijing.base.local.upload.web.business.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @decription:
 * @create: 2022-10-26 14:38
 */
public final class LinkAssertUtil {
    
    public static class Link {
        
        private final List<String> msg = new ArrayList<>();
        
        public Link notNull(Object s, String msg) {
            if (s == null && msg != null) {
                this.msg.add(msg);
            }
            return this;
        }
        
        public Link notEmpty(Collection<?> s, String msg) {
            if (CollectionUtils.isEmpty(s)) {
                this.msg.add(msg);
            }
            return this;
        }
        
        public Link notBlank(String s, String msg) {
            if (StringUtils.isBlank(s)) {
                this.msg.add(msg);
            }
            return this;
        }
        
        public Link maxLength(String s, int max, String msg) {
            if (s != null && s.length() > max) {
                this.msg.add(msg);
            }
            return this;
        }
        
        public Link isTrue(boolean s, String msg) {
            if (!s && StringUtils.isNotBlank(msg)) {
                this.msg.add(msg);
            }
            return this;
        }
        
        public String getErrorMsg() {
            return StringUtils.join(this.msg, "、");
        }
        
        public boolean hasError() {
            return CollectionUtils.isNotEmpty(this.msg);
        }
        
    }
    
    public static Link linkValid() {
        return new Link();
    }
    
}
