package com.platform.core.constant;

/**
 * @author: Advance
 * @create: 2022-03-08 17:14
 * @since V1.0.0
 */
public interface GlobalConstant {
    String SYS = "SYS";
    /**
     * 数据权限用户机构代码
     */
    String USER_ORG_CODE = "institution";

    /**
     * 分页参数：当前页码
     */
    String PARAM_PAGE = "page";

    /**
     * 分页参数: 每页显示条数
     */
    String PARAM_LENGTH = "length";
//
//    /**
//     * 画面对应的所有元素被保存在session中的属性
//     */
//    String PAGE_ELE_SESSION_ARRT = "page.all.elements";

    /**
     * 画面对应的所有元素被保存在session中的属性
     */
    String PAGE_ELE_CACHE_KEY = "sysPageElement";

    /**
     * 超级管理员的角色名
     */
    String SUPER_ADMIN_ROLE_NAME = "joyin.admin";

    /**
     * 超级管理员的用户名
     */
    String SUPER_ADMIN_USER_NAME = "admin";

    /**
     * 模糊查询字符串
     */
    String LIKE_STRING = "search";

    /**
     * 总页数
     */
    String PAGE_TOTAL_FIFLD = "totalPage";

    /**
     * 下拉框与查询按钮区分
     */
    String SEARCH_FOR_SELECT = "isSelectData";

    /**
     * 排序名称
     */
    String SORT_NAME = "key";

    /**
     * 排序方式名称 asc:正序 | desc:倒序
     */
    String SORT_ORDER = "order";

    /**
     * 画面ID key
     */
    String PAGE_ID = "pageId";

    /**
     * 画面元素ID key
     */
    String ELEMENT_ID = "elementId";

    /**
     * 画面信息缓存
     */
    String SYS_PAGE_CACHE_KEY = "sysPage";

    /**
     * 当前平台日期
     */
    String NOW_SYS_DATE = "nowSysDate";

    /**
     * 当前用户信息
     */
    String CURRENT_USER = "curUser";

    /**
     * 工作流ID
     */
    String WORKFLOW_ID = "workflowId";

    /**
     * 履历No
     */
    String HIS_NO = "hisNo";

    /**
     * 数据状态
     */
    String STATUS = "status";

    /**
     * entity
     */
    String ENTITY = "entity";

    /**
     * excel文件xls后缀
     */
    String FILE_POSTFIX_XLS = ".xls";

    /**
     * excel文件xlsx后缀
     */
    String FILE_POSTFIX_XLSX = ".xlsx";

    /**
     * 左括号
     */
    String OPEN_PARENTHESIS = "(";

    /**
     * 右括号
     */
    String CLOSE_PARENTHESIS = ")";

    /**
     * 左中括号[
     */
    String OPEN_MIDDLE = "[";

    /**
     * 右中括号]
     */
    String CLOSE_MIDDLE = "]";

    /**
     * 杠
     */
    String SPLIT = "-";

    /**
     * 下划线
     */
    String UNDER_LINE = "_";

    /**
     * 点
     */
    String POINT = ".";

    /**
     * 逗号
     */
    String COMMA = ",";

    /**
     * 分号
     */
    String SEMICOLON = ";";

    /**
     * 字符串 双引号
     */
    String QUOTATION_MARK = "\"";

    /**
     * 冒号
     */
    String COLON = ":";

    /**
     *
     */
    String AT = "@";

    /**
     * 用户所属机构代码
     */
    String SESSION_INSTITUTION_CODE = "_sk_institutioncode_";

    /**
     * 换行符
     */
    String LINE_SEPARATOR = System.getProperty("line.separator");

    /**
     * |
     */
    String VERTICAL_LINE = "|";

    /**
     * 批处理时的登录用户
     */
    String SYSTEM_USER = "system";

    /**
     * 分页查询最大记录数
     */
    int MAX_RECORDS = 100000;

    /**
     * 默认分页大小
     */
    int DEFAULT_PAGE_SIZE = 30;

    /**
     * 系统参数缓存
     */
    String SYS_PARA_KEY = "sysParaKey";

    /**
     * 用户缓存（用户名和真实姓名）
     */
    String SYS_USER_KEY = "sysUserKey";

    /**
     * 用户缓存（用户名和邮件地址）
     */
    String SYS_USER_MAIL_KEY = "sysUserMailKey";

    /**
     * 机构缓存
     */
    String SYS_INSTITUTION_KEY = "sysInstitutionKey";

    /**
     * 机构代码与机构名称对应的缓存
     */
    String SYS_INSTITUTION_CODE_NAME_KEY = "sysInstitutionCodeNameKey";

    /**
     * 过滤缓存
     */
    String FILTER_MAPPER_CONFIG = "filterMapperConfig";

    /**
     * 不选右上角机构时  默认的是 当前用户所属机构
     * 选了右上角时 就是选择的机构
     *
     */
    String SELECT_INSTITUTION_CODE = "selectInstitutionCode";

    /**
     * 不选右上角机构时  默认的是本机及 当前用户 有权限的子机构
     * 选了右上角时 就是选择的机构
     */
    String SELECT_TREE_INSTITUTION_CODE = "selectTreeInstitutionCode";

    /**
     * 部门缓存
     */
    String SYS_DEPARTMENT_KEY = "sysDepartmentKey";

    /**
     *
     */
    String LOGIN_FAIL_LOCK_TIME = "login_fail_lock_time";

    /**
     * 共通Property属性--导出临时文件存放路径
     */
    String COM_PROP_ACCESS_EXPORTPATH = "access_exportfilepath";

    /**
     * 共通Property属性--缓存刷新时间
     */
    String DATADICT_REFRESH_PERIOD = "datadict_refresh_period";

    /**
     * 共通Propert属性--上传文件路劲
     */
    String COM_PROP_UPLOAD_PATH = "upload_file_path";

    /**
     * 共通Propert属性--输出文件路径
     */
    String COM_PROP_OUTPUT_PATH = "output_file_path";

    /**
     * 工作流提交审批是否需要发邮件
     */
    String IS_MAIL_SEND_WORKFLOW = "is_mail_send_workflow";

    /**
     * 导出excel的最大行数
     */
    String EXPORT_EXCEL_ROW_COUNT = "export_excel_row_count";

    /**
     * 是否允许导出显示列
     */
    String EXPORT_BY_COLUMN = "export_by_column";

    /**
     * 数据权限
     */
    String DATA_AUTHORITY = "data_authority";

    /**
     * URL权限拦截开关
     */
    String URL_AUTHORITY = "url_authority";

    /**
     * 邮件服务器IP
     */
    String MAIL_SERVER = "mail_server";

    /**
     * 邮件服务器PORT
     */
    String MAIL_POST = "mail_post";

    /**
     * 邮件服务器PROTOCOL
     */
    String MAIL_PROTOCOL = "mail_protocol";

    /**
     * 工作流提交审批的发件人是否使用默认账户
     */
    String IS_USE_DEFAULT_FROM_WORKFLOW = "is_use_default_from_workflow";

    /**
     * 工作流提交审批的默认发件人
     */
    String DEFAULT_FROM_WORKFLOW = "default_from_workflow";

    /**
     * 工作流提交审批的默认发件人密码
     */
    String DEFAULT_FROM_PWD_WORKFLOW = "default_from_pwd_workflow";

    /**
     * 是否允许自定义机构代码
     */
    String IS_EDIT_ORG_CODE = "is_edit_org_code";

    /**
     * 是否允许自定义部门代码
     */
    String IS_EDIT_DEPT_CODE = "is_edit_dept_code";

    /**
     * 是否允许展示批量审批按钮
     */
    String ON_SUBMIT_ALL_SHOW ="on_submit_all_show";

    /**
     * 批量审批按钮白名单
     */
    String MULTI_APPROVAL_PAGE ="multi_approval_page";

    /**
     * 批量审批在最后一个流程节点时是否允许夸流程模板审批
     */
    String MULTI_APPROVAL_LAST_TASK = "multi_approval_last_task";

    /**
     * 批量审批按钮黑名单
     */
    String MULTI_APPROVAL_BLACKPAGE = "multi_approval_blackpage";
    /**
     * 用户名验证规则
     */
    String USERNAME_RULE = "username_rule";

    /**
     * 机构代码验证规则
     */
    String ORG_CODE_RULE = "org_code_rule";

    /**
     * 部门代码验证规则
     */
    String DEPT_CODE_RULE = "dept_code_rule";

    /**
     * 重复登录处理模式
     */
    String DUPLICATE_LOGON_TYPE = "duplicate_logon_type";

    /**
     * 明细画面模式 - 新建模式
     */
    String DETAIL_PAGE_MODEL_NEW = "0";

    /**
     * 明细画面模式 - 修改模式
     */
    String DETAIL_PAGE_MODEL_EDIT = "1";

    /**
     * 明细画面模式 - 参照模式
     */
    String DETAIL_PAGE_MODEL_REFERENCE = "2";

    /**
     * 明细画面模式键
     */
    String DETAIL_PAGE_MODEL_KEY = "pageModel";

    /**
     * 按组执行
     */
    String BATCH_EXECUTE_TYPE_GROUP = "0";

    /**
     * 按单个
     */
    String BATCH_EXECUTE_TYPE_SINGLE = "1";

    /**
     * 用户菜单模式session key
     */
    String SESSION_MY_MENU = "SESSION_MY_MENU";

    /**
     * 用户肤色设置session key
     */
    String SESSION_MY_SKIN = "SESSION_MY_SKIN";
//
//    /**
//     * 平台日期设置session key
//     */
//    String SESSION_SYS_DATE = "SESSION_SYS_DATE";

    /**
     * 主动提醒是否显示session key
     */
    String SESSION_REMIND = "SESSION_REMIND";
//
//    /**
//     * 所有的权限控制对象
//     */
//    String SESSION_ALL_MODULES = "SESSION_ALL_MODULES";

    /**
     * 所有的权限控制对象
     */
    String ALL_AUTH_MODULES = "ALL_AUTH_MODULES";

    /**
     * 批处理监控消息ID
     */
    String MESSAGE_ID = "MESSAGE_ID";

    /**
     * 批处理监控消息目标
     */
    String PAGE = "PAGE";

    /**
     * 时间控件确认按钮显示开关
     */
    String DATE_SELECT_BTN_CTR ="date_select_btn_ctr";
    /**
     * 日期控件是否默认支持选取单边
     */
    String DATE_SELECT_ALLOW_SINGLE = "date_select_allow_single";
    /**
     * 筛选区域默认展示开关
     */
    String FILTER_AREA_SHOW = "filter_area_show";
    /**
     * 查询与重置按钮是否显示图标
     */
    String IS_SHOW_BTN_ICON = "is_show_btn_icon";
    /**
     * 主动提醒展示开关
     */
    String IS_SHOW_REMIND = "is_show_remind";

    /**
     * 是否退回到经办(01:是,02:否)
     */
    String IS_BACK_TO_FIRST = "is_back_to_first";

    /**
     * SSO认证成功后，SSO用户名在Session或cookie中存储的关键字
     */
    String SSOUSERNAME_KEY = "ssoCookieOrSessionName";

    /**
     * 用户新建是否要配置产品组合
     */
    String IS_NEED_PTL_CREATEUSER = "is_need_ptl_createUser";

    /**
     * 用户新建是否显示管理员标志
     */
    String IS_SHOW_ADMIN_FLAG = "is_show_admin_flag";

    /**
     * 用户新建是否显示SSO用户名
     */
    String IS_NEED_SSO_USER = "is_need_sso_user";

    /**
     * 是否允许修改修改用户名
     */
    String IS_MODIFY_USERNAME = "is_modify_username";
    /**
     * html换行符
     */
    String HTML_NEWLINE = "<br/>";

    /**
     * excel数据导入文件信息
     */
    String EXCEL_IMP_HANDLE_FILE_INFO = "EXCEL_IMP_HANDLE_FILE_INFO";
    /**
     * 首页是否显示我的主页
     */
    String IS_SHOW_MYHOME = "is_show_myhome";

    /**
     * 登陆成功的首页中导航栏中的主体中心是否展示
     */
    String IS_SHOW_THEME="is_show_theme";
    /**
     * 菜单与菜单之间的间距
     */
    String MENU_MENU_DISTANCE="menu_menu_distance";
    /**
     * 菜单文字字体大小设置
     */
    String MENU_FONT_SIZE = "menu_font_size";
    /**
     * 菜单名称和图标是一行展示，还是分行展示
     */
    String MENUNAME_ICON_ONELINE = "menuname_icon_oneline";
    /**
     * 系统名称前是否增加银行的log
     */
    String SYSNAME_PRE_ICON = "sysname_pre_icon";

    /**
     * 用户密码（正则，校验的规则）
     */
    String  PW_RULE = "pw_rule";

    /**
     * 密码与前n次密码相同 （设置的值为校验的层级数）
     */
    String PW_PRESAME="pw_presame";
    /**
     *  是否校验与用户名相同
     */
    String PW_NAMESAME="pw_nameSame";
    /**
     * 密码修改周期
     */
    String PW_CYCLE= "pw_cycle";
    /**
     *  用户初次登录是否强制要求修改密码
     */
    String PW_INITEDIT ="pw_initEdit";
    /**
     *  初始密码
     */
    String INIT_PASSWORD ="init_password";
    /**
     *  忽略用户名大小写
     */
    String IGNORE_USERNAME_CASE = "ignore_username_case";

    /**
     *  日期控件的选中的焦点类型
     */
    String DATE_TEXT_TYPE = "dateTextType";

    /**
     *  导出时模板类型选择
     */
    String EXPORT_EXCEL_TYPE = "export_excel_type";

    /**
     *  导出模板
     */
    String EXPORT_EXCEL_TEMPLATE = "export_excel_template";

    /**
     *  导出时模板类型选择
     */
    String DEFAULT_SUBPAGE_ID = "999999";
    /**
     *  联想框与下拉框输入值自动转换大小写模式（01:转换大写,02:转换小写，03:不处理）
     */
    String INPUT_SELECT_IGNORECASE = "input_select_ignorecase";

    /**
     * 数据权限模式：01、银行模式，02：联社模式
     */
    String AUTHORITY_TYPE = "AUTHORITY_TYPE";

    /**
     * 是否开启默认处理人选择（01：开启，02：不开起）
     */
    String IS_SET_DEFAULT_USER = "IS_SET_DEFAULT_USER";

    /**
     * 用户所属机构上级机构代码session key
     */
    String SESSION_UPPER_ORG_CODE = "__sk_upper_orgcode_";
    /**
     * 用户所属机构类型
     */
    String SESSION_USER_ORGTYPE = "__sk_user_orgType_";

    //START 系统参数中五条可修改配置的参数
    String PARA_AUTHORIZE_LIMIT = "AUTHORIZE_LIMIT";

    String PARA_PRICE_CAL_TYPE = "PRICE_CAL_TYPE";

    String PARA_PORTFOLIO_HANDLE_USERID = "portfolio_handle_userid";

    String PARA_SETTLE_ACCT_NAME = "settle_acct_name";

    String PARA_SETTLE_ACCT_NO = "settle_acct_no";

    String PARA_PROD_TRU_ACCT_NO ="prod_tru_acct_no";

    String PARA_PROD_TRU_ACCT_NAME ="prod_tru_acct_name";

    String PARA_LENDING_ACCT_NO = "lending_acct_no";

    String PARA_LENDING_ACCT_NAME = "lending_acct_name";
    //END 系统参数中五条可修改配置的参数

    /**
     * 左侧菜单展示模式 01:现有的一级一级展示、02:光大的子菜单合并展示）
     */
    String LEFT_MENU_DISPLAY_TYPE = "LEFT_MENU_DISPLAY_TYPE";

    /**
     * 菜单展示级数
     */
    String MENU_DISPLAY_LEVEL = "MENU_DISPLAY_LEVEL";

    /**
     * 菜单展示级数_4级
     */
    String MENU_DISPLAY_LEVEL_FOUR = "4";

    /**
     * 特殊字符是否过滤
     */
    String IS_NEED_SPECSTR_GL = "IS_NEED_SPECSTR_GL";

    /**
     * 特殊字符是否过滤为01：是的时候，不能做特殊字符过滤的画面ID
     */
    String NOT_SPECSTR_GL_PAGE = "NOT_SPECSTR_GL_PAGE";

    /**
     * 系统参数：批处理定时任务是否支持跳过节假日（01:支持 02：不支持）
     */
    public static final String BATCH_QUARTZ_SKIP_HOLIDAY = "BATCH_QUARTZ_SKIP_HOLIDAY";
    // etliu 批处理定时任务支持跳过节假日 20200116 modify end


    /**
     * 必选项下拉框只有一个选项时是否默认显示
     */
    String SELECT_ONLY_ONE_DISPLAY = "SELECT_ONLY_ONE_DISPLAY";

    /**
     * 用户新建是否显示银行间交易员ID
     */
    String IS_SHOW_IBU_TRADER = "IS_SHOW_IBU_TRADER";

    /**
     * 在导出显示列打开的时候，不能显示导出显示列的画面一览（逗号分隔）
     */
    String NOT_SHOW_EXPORT_BY_COLUMN_PAGE = "not_show_export_by_column_page";

    /**
     * 是否允许导出选中行
     */
    String EXPORT_SELECTED_ROW = "export_selected_row";

    /**
     * 数据库类型缓存
     */
    String DATABASE_URL_KEY = "databaseUrlKey";

    /**
     * 允许导出选中行为01的时候，对象外画面一览
     */
    String EXPORT_SELECTED_ROW_NOT_TARGET_PAGE = "export_selected_row_not_target_page";


    /**
     * 用户得到初始密码(用户创建)或重置密码后首次登录间隔天数超过既定值是否报错
     */
    String IS_NEED_CHECK_REGIST_LIMIT_DAYS = "IS_NEED_CHECK_REGIST_LIMIT_DAYS";

    /**
     * 用户得到初始密码(用户创建)或重置密码后首次登录间隔天数
     */
    String REGIST_LIMIT_DAYS = "REGIST_LIMIT_DAYS";

    /**
     * 待办事项是否按照最大件数显示
     */
    String IS_SHOW_MAX_TODOLIST = "IS_SHOW_MAX_TODOLIST";

    /**
     * 待办事项最大显示件数
     */
    String MAX_TODOLIST_COUNT = "MAX_TODOLIST_COUNT";

    /**
     * 是否展示批量审批分类汇总选人界面
     */
    String IS_SHOW_MULTI_APPROVAL_DIALOG = "IS_SHOW_MULTI_APPROVAL_DIALOG";

    /**
     * 经办节点是否更新所有字段
     */
    String IS_UPDATE_ALLFIELD_JINGBAN = "IS_UPDATE_ALLFIELD_JINGBAN";

    /**
     * 用户新建是否显示身份证号系统参数
     */
    public static final String IS_SHOW_SFZH = "IS_SHOW_SFZH";

    /**
     * 身份证号是否必输
     */
    public static final String IS_REQUIRED_SFZH = "IS_REQUIRED_SFZH";
}
