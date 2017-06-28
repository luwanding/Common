package com.platform.common.model;

import java.io.Serializable;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author vincent
 * @version $ BaseResultInfo.java, v 0.1 2011-8-3 上午11:56:03 vincent Exp $
 * @since   JDK1.6
 */
public class BaseResult implements Serializable {

    private static final long serialVersionUID = -2024277758564966880L;

    /**
     * Object Id
     */
    private Long              objectId;

    /**
     * 返回结果代码
     */
    private String            resultCode;

    /**
     * 返回结果信息
     */
    private String            resultMsg;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    @Override
    public String toString() {
        return "BaseResultInfo [objectId=" + objectId + ", resultCode=" + resultCode
               + ", resultMsg=" + resultMsg + "]";
    }

}