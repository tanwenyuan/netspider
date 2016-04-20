package com.qind.common.util.properties;

import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * @describe：<p>读取properties文件信息</p>
 * @author QinDong
 * @date  2015-09-23 17:10:00
 */
public class ResourceBundleAdapter extends Properties {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public ResourceBundleAdapter(ResourceBundle rb) {
        assert (rb instanceof java.util.PropertyResourceBundle);
        Enumeration<String> e = rb.getKeys();
        while (e.hasMoreElements()) {
            Object o = e.nextElement();
            this.put(o, rb.getObject((String) o));
        }
    }

}