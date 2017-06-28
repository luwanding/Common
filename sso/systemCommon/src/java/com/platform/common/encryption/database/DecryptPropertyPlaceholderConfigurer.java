package com.platform.common.encryption.database;

import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.platform.common.encryption.Encrypter;

/**
 * <pre>
 *
 * </pre>
 *
 * @author twotoo
 * @version $ DecryptPropertyPlaceholderConfigurer.java, v 0.1 May 3, 2011 9:51:18 AM twotoo Exp $
 * @since   JDK1.6
 */
public class DecryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    protected String resolvePlaceholder(String placeholder, Properties props) {

        String s = props.getProperty(placeholder);

        if (placeholder != null && s != null) {

            if (placeholder.startsWith("jdbc")) {

                if(placeholder.endsWith(".username")){

                    s = Encrypter.decrypt(s);

                }

                if(placeholder.endsWith(".password")){

                    s = Encrypter.decrypt(s);

                }

            }
        }

        return s;
    }
}
