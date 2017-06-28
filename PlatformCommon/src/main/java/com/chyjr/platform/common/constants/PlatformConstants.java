package com.chyjr.platform.common.constants;

/**
 * 
 * @ProjectName:  [PlatformCommon]
 * @Package:      [com.chyjr.platform.common.constants.PlatformConstants.java] 
 * @ClassName:    [PlatformConstants]  
 * @Description:  [平台常量定义类]  
 * @Author:       [jx_xudelin]  
 * @CreateDate:   [Oct 14, 2014 4:45:24 PM]  
 * @UpdateUser:   [jx_xudelin]  
 * @UpdateDate:   [Oct 14, 2014 4:45:24 PM]  
 * @UpdateRemark: [] 
 * @Version:      [v1.0]
 *
 */
public class PlatformConstants {
	/**
	 * jdk环境变量加载时需要配置该变量，该变量的值是平台配置文件的目录地址。
	 * 通过这种配置的好处是可以把代码和配置文件隔离。
	 */
    final public static String SYS_CFG_PRO_PATH = System.getProperty("resources.config.path");

    final public static String SYS_CFG_ENV_PATH = System.getenv("resources.config.path");

	/**
	 * 公司人事系统缓存KEY
	 */
	final public static String MEM_CLIENT_HR ="mem_client_hr";
	
	/**
	 * 猎头系统缓存KEY
	 */
	final public static String MEM_CLIENT_HUNTER ="mem_client_hunter";
	
	/**
	 * 系统通用缓存KEY
	 */
    final public static String MEM_CLIENT_COMM ="mem_client_comm";
    
    /**
	 * 个人系统缓存KEY
	 */
    final public static String VITA ="vita";
    final public static String VITA_CAPTCHA_CODE ="_VITA_CAPTCHA_CODE";
    final public static String HR_CAPTCHA_CODE ="_HR_CAPTCHA_CODE";
    /**
     * HR系统缓存KEY
     */
    final public static String HR = "hr";
    final public static String ACTIVITY = "activity";
    /**
     * Hunter系统缓存KEY
     */
    final public static String HUNTER = "hunter";
    /**
     * 表自增缓存KEY
     */
    final public static String TABLE_RESUME_KEY = "RESUME";
    final public static String TABLE_P_USERINFO_KEY = "P_USERINFO";

    /**
     * 验证来源
     */
    final public static String[] SOURCEFROM = {"", "51job", "zhilian", "zhuopin", "liepin"};
    /**
     * 加密key
     */
    final public static String SOURCEFROM_KEY = "%!##@$%|$#$%(^)$}$*{^*+%";
    /************   数据字典Code  *****************/
    
    /**
     * 公司所在地
     */
    final public static String DICT_COMP_CITY = "DICT_COMP_CITY";
    /**
     * 公司性质
     */
    final public static String DICT_COMP_NATURE = "DICT_COMP_NATURE";
    /**
     * 公司规模
     */
    final public static String DICT_COMP_SCOPE = "DICT_COMP_SCOPE";
    /**
     * 所属行业
     */
    final public static String DICT_COMP_INDUSTRY = "DICT_COMP_INDUSTRY";
    /**
     * 发布时间
     */
    final public static String DICT_JOB_PUB_DATE = "DICT_JOB_PUB_DATE";
    /**
     * 工作类型
     */
    final public static String DICT_JOB_JOBTYPE = "DICT_JOB_JOBTYPE";
    /**
     * 薪资
     */
    final public static String DICT_JOB_SALARY = "DICT_RESUME_SALARY";
    /**
     * 公司福利
     */
    final public static String DICT_JOB_WEAL = "DICT_JOB_WEAL";

    
    /**
     * 学历要求
     */
    final public static String DICT_JOB_EDU = "DICT_JOB_EDU";  
    
    /**
     * 简历公开程度
     */
    final public static String DICT_RESUME_ISOPEN = "DICT_RESUME_ISOPEN";
    
    /**
     * 婚姻状况
     */
    final public static String DICT_RESUME_WEDDING = "DICT_RESUME_WEDDING";
    
    
    /**
     * 证件类型
     */
    final public static String DICT_RESUME_DOCUMENT = "DICT_RESUME_DOCUMENT";
    
    /**
     * 求职状态
     */
    final public static String DICT_RESUME_JOBSTATU = "DICT_RESUME_JOBSTATU";
    
    /**
     * 薪资状况
     */
    final public static String DICT_RESUME_SALARY = "DICT_RESUME_SALARY";

    /**
     * 性别
     */
    final public static String DICT_BASE_SEX = "DICT_BASE_SEX";
    /**
     * 政治面貌
     */
    final public static String DICT_RESUME_POLITICS = "DICT_RESUME_POLITICS";

    /**
     * 工作年限
     */
    final public static String DICT_RESUME_WORKYEAR = "DICT_RESUME_WORKYEAR";

    /**
     * 期望薪水
     */
    final public static String DICT_RESUME_ESC = "DICT_RESUME_ESC";
    /**
     * 到岗时间
     */
    final public static String DICT_RESUME_POSTTIME = "DICT_RESUME_POSTTIME";
    /**
     * 海外学习经历
     */
    final public static String DICT_RESUME_STUDYH = "DICT_RESUME_STUDYH";
    
    /**
     * 语言类别
     */
    final public static String DICT_RESUME_LANTYPE = "DICT_RESUME_LANTYPE";
    /**
     * 掌握程度、读写、听说能力
     */
    final public static String DICT_RESUME_MASTERY = "DICT_RESUME_MASTERY";
    /**
     * 语言类别
     */
    final public static String DICT_RESUME_SUBJECT = "DICT_RESUME_SUBJECT";
    
    /**
     * 从业时间
     */
    final public static String DICT_WORK_YEARS = "DICT_WORK_YEARS";
    /**
     * 理财师证书
     */
    final public static String DICT_FX_CERT = "DICT_FX_CERT";
    /**
     * 理财师专业资格或其他认证
     */
    final public static String DICT_FX_CERTOTHER = "DICT_FX_CERTOTHER";
    /**
     * 理财工作侧重于以下哪些领域
     */
    final public static String DICT_FX_FOCUSAREA = "DICT_FX_FOCUSAREA";

    /**
     * 所有数据字典
     */
    final public static String[] DICT_ALL = new String[]{DICT_COMP_CITY,DICT_COMP_NATURE,DICT_COMP_SCOPE,DICT_COMP_INDUSTRY,DICT_JOB_PUB_DATE
    	,DICT_RESUME_ISOPEN,DICT_RESUME_WEDDING,DICT_RESUME_DOCUMENT,DICT_RESUME_JOBSTATU,DICT_RESUME_JOBSTATU,DICT_RESUME_SALARY,DICT_BASE_SEX
    ,DICT_RESUME_POLITICS,DICT_RESUME_WORKYEAR,DICT_RESUME_ESC,DICT_RESUME_POSTTIME,DICT_RESUME_STUDYH,DICT_RESUME_LANTYPE,DICT_RESUME_MASTERY
    ,DICT_RESUME_SUBJECT,DICT_WORK_YEARS,DICT_JOB_EDU,DICT_FX_CERT,DICT_FX_CERTOTHER,DICT_FX_FOCUSAREA,DICT_JOB_JOBTYPE};
}
