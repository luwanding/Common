package com.chyjr.platform.common.util;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.codehaus.jackson.type.TypeReference;

/**
 * 
 * @ProjectName:  [PlatformCommon]
 * @Package:      [com.chyjr.platform.common.util.JSONUtils.java] 
 * @ClassName:    [JSONUtils]  
 * @Description:  [json 工具类]  
 * @Author:       [cz]  
 * @CreateDate:   [Oct 14, 2014 5:03:22 PM]  
 * @UpdateUser:   [cz]  
 * @UpdateDate:   [Oct 14, 2014 5:03:22 PM]  
 * @UpdateRemark: [说明本次修改内容] 
 * @Version:      [v1.0]
 *
 */
public class JSONUtils {
	
	private static ObjectMapper mapper = null;
	static {
		
		if (mapper != null) {
			mapper.getSerializationConfig().set(Feature.WRITE_NULL_PROPERTIES,false);
			mapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
		} else {
			mapper = new ObjectMapper();
			mapper.getSerializationConfig().set(Feature.WRITE_NULL_PROPERTIES,false);
			mapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
		}

	}
	
	/**
	 * 解析json
	 * @param json
	 * @param valueType
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static <T> T parseJson(String json, Class<T> valueType)
			throws JsonParseException, JsonMappingException, IOException {

		T t = null;

		if (!StringUtils.isBlank(json) && valueType != null) {

			t = mapper.readValue(json, valueType);

		}

		return t;
	}
	
	/**
	 * 解析json
	 * @param json
	 * @param typeRef
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static <T> T parseJson(String json, TypeReference<T> typeRef)
			throws JsonParseException, JsonMappingException, IOException {

		T t = null;

		if (!StringUtils.isBlank(json) && typeRef != null) {

			t = (T) mapper.readValue(json, typeRef);

		}

		return t;
	}
	
	/**
	 * 生成json
	 * @param list
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static <T> String writeJson(List<T> list)
			throws JsonGenerationException, JsonMappingException, IOException {

		return mapper.writeValueAsString(list);

	}
	
	/**
	 * 生成json
	 * @param t
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static <T> String writeJson(T t) throws JsonGenerationException,
			JsonMappingException, IOException {

		return mapper.writeValueAsString(t);

	}
}
