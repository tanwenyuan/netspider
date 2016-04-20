package com.qind.common.util.properties;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;


/**
 * @describe：<p>读取properties文件信息</p>
 * @author QinDong
 * @date  2015-09-23 17:10:00
 */
public class OperateProperties {
	
    public final static int BY_PROPERTIES             = 1;
    public final static int BY_RESOURCEBUNDLE         = 2;
    public final static int BY_PROPERTYRESOURCEBUNDLE = 3;
    public final static int BY_CLASS                  = 4;
    public final static int BY_CLASSLOADER            = 5;
    public final static int BY_SYSTEM_CLASSLOADER     = 6;

	/**
	 * 加载配置文件属性
	 * @param name 加载路径
	 * @param type 加载方式
	 * @return Properties 返回属性
	 * @since 2015-10-15 10:52:10
	 */
    public final static Properties loadProperties(final String name, final int type){
        Properties p = new Properties();
        InputStream in = null;
        
        try {
	        if (type == BY_PROPERTIES) {
				in = new BufferedInputStream(new FileInputStream(name));
	            assert (in != null);
	            p.load(in);
	        } else if (type == BY_RESOURCEBUNDLE) {
	            ResourceBundle rb = ResourceBundle.getBundle(name, Locale.CHINA);
	            assert (rb != null);
	            p = new ResourceBundleAdapter(rb);
	        } else if (type == BY_PROPERTYRESOURCEBUNDLE) {
	            in = new BufferedInputStream(new FileInputStream(name));
	            assert (in != null);
	            ResourceBundle rb = new PropertyResourceBundle(in);
	            p = new ResourceBundleAdapter(rb);
	        } else if (type == BY_CLASS) {
	            assert (OperateProperties.class.equals(new OperateProperties().getClass()));
	            in = OperateProperties.class.getResourceAsStream(name);
	            assert (in != null);
	            p.load(in);
	        } else if (type == BY_CLASSLOADER) {
	            assert (OperateProperties.class.getClassLoader().equals(new OperateProperties().getClass().getClassLoader()));
	            in = OperateProperties.class.getClassLoader().getResourceAsStream(name);
	            assert (in != null);
	            p.load(in);
	        } else if (type == BY_SYSTEM_CLASSLOADER) {
	            in = ClassLoader.getSystemResourceAsStream(name);
	            assert (in != null);
	            p.load(in);
	        }
	
	        if (in != null) {
	            in.close();
	        }
        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return p;
    }
    
}