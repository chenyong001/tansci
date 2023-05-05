import axios from '../utils/axios'
import env from '../config/env'
// ==================登录=============================
/**
 * 登录
 * @param params
 * @returns {Promise<AxiosResponse<T>>}
 */
export function login(params) {
    return axios.post(env.host.base + '/tansci/user/login', params);
}

/**
 * 微信扫码登录获取二维码
 * @param params
 * @returns {Promise<AxiosResponse<T>>}
 */
export function wxLogin(params) {
    return axios.post(env.host.base + '/tansci/auth/wxLogin', params);
}

/**
 * 微信扫码登录获取token
 * @param params
 * @returns {Promise<AxiosResponse<T>>}
 */
export function wxCallback(params) {
    return axios.post(env.host.base + '/tansci/auth/wxCallback', params);
}

/**
 * 登出
 * @param params
 * @returns {Promise<AxiosResponse<T>>}
 */
export function logout(params) {
    return axios.post(env.host.base + '/tansci/user/logout', params);
}


// =================数据字典============================
/**
 * 分页列表
 * @param {*} params 
 * @returns 
 */
export function dicList(params) {
    return axios.get(env.host.base + '/tansci/dic/dicList', { params: params });
}

/**
 * 列表
 * @param {*} params 
 * @returns 
 */
export function getGroupNameByList(params) {
    return axios.get(env.host.base + '/tansci/dic/getGroupNameByList', { params: params });
}

/**
 * 添加字典
 * @param {*} params 
 * @returns 
 */
export function dicSave(params) {
    return axios.post(env.host.base + '/tansci/dic/save', params);
}

/**
 * 修改字典
 * @param {*} params 
 * @returns 
 */
export function dicUpdate(params) {
    return axios.post(env.host.base + '/tansci/dic/update', params);
}

/**
 * 删除字典
 * @param {*} params 
 * @returns 
 */
export function dicDel(params) {
    return axios.get(env.host.base + '/tansci/dic/del', { params: params });
}

// =================用户信息===========================
/**
 * 用户列表分页
 * @param {*} params 
 * @returns 
 */
export function userPage(params) {
    return axios.get(env.host.base + '/tansci/user/page', { params: params });
}

/**
 * 用户列表
 * @param {*} params 
 * @returns 
 */
export function userList(params) {
    return axios.get(env.host.base + '/tansci/user/list', { params: params });
}

/**
 * 添加用户
 * @param {*} params 
 * @returns 
 */
export function addUser(params) {
    return axios.post(env.host.base + '/tansci/user/save', params);
}

/**
 * 修改用户
 * @param {*} params 
 * @returns 
 */
export function updateUser(params) {
    return axios.post(env.host.base + '/tansci/user/update', params);
}

/**
 * 删除用户
 * @param {*} params 
 * @returns 
 */
export function dicUser(params) {
    return axios.get(env.host.base + '/tansci/user/del', { params: params });
}

/**
 * 修改密码
 * @param {*} params 
 * @returns 
 */
export function modifyPass(params) {
    return axios.post(env.host.base + '/tansci/user/modifyPass', params);
}

// =====================菜单管理==================================

/**
 * 列表
 * @param {*} params 
 * @returns 
 */
export function menuList(params) {
    return axios.get(`${env.host.base}/tansci/menu/list`, { params: params });
}

/**
 * 删除菜单
 * @param {*} params 
 * @returns 
 */
export function delMenu(params) {
    return axios.get(env.host.base + '/tansci/menu/del', { params: params });
}

/**
 * 添加菜单
 * @param {*} params 
 * @returns 
 */
export function saveMenu(params) {
    return axios.post(env.host.base + '/tansci/menu/save', params);
}

/**
 * 更新菜单
 * @param {*} params 
 * @returns 
 */
export function updateMenu(params) {
    return axios.post(env.host.base + '/tansci/menu/update', params);
}

// =================角色管理============================

/**
 * 分页列表
 * @param {*} params 
 * @returns 
 */
export function rolePage(params) {
    return axios.get(env.host.base + '/tansci/role/page', { params: params });
}

/**
 * 列表
 * @param {*} params 
 * @returns 
 */
export function roleList(params) {
    return axios.get(env.host.base + '/tansci/role/list', { params: params });
}

/**
 * 删除角色
 * @param {*} params 
 * @returns 
 */
export function delRole(params) {
    return axios.get(env.host.base + '/tansci/role/del', { params: params });
}

/**
 * 添加角色
 * @param {*} params 
 * @returns 
 */
export function saveRole(params) {
    return axios.post(env.host.base + '/tansci/role/save', params);
}

/**
 * 更新角色
 * @param {*} params 
 * @returns 
 */
export function updateRole(params) {
    return axios.post(env.host.base + '/tansci/role/update', params);
}

/**
 * 菜单角色列表
 * @param {*} params 
 * @returns 
 */
export function menuRoleList(params) {
    return axios.get(env.host.base + '/tansci/role/menuRoleList', { params: params });
}

/**
 * 分配菜单角色
 * @param {*} params 
 * @returns 
 */
export function menuRoleAdd(params) {
    return axios.post(env.host.base + '/tansci/role/menuRoleAdd', params);
}

/**
 * 用户角色详情
 * @param {*} params 
 * @returns 
 */
export function userRoleInfo(params) {
    return axios.get(env.host.base + '/tansci/role/userRoleInfo', { params: params });
}

/**
 * 分配用户角色
 * @param {*} params 
 * @returns 
 */
export function userRoleAdd(params) {
    return axios.post(env.host.base + '/tansci/role/userRoleAdd', params);
}

// ====================组织机构=========================
/**
 * 组织分页列表
 * @param {*} params 
 * @returns 
 */
export function orgList(params) {
    return axios.get(env.host.base + '/tansci/org/list', { params: params });
}

/**
 * 删除组织
 * @param {*} params 
 * @returns 
 */
export function delOrg(params) {
    return axios.get(env.host.base + '/tansci/org/del', { params: params });
}

/**
 * 添加组织
 * @param {*} params 
 * @returns 
 */
export function saveOrg(params) {
    return axios.post(env.host.base + '/tansci/org/save', params);
}

/**
 * 更新组织
 * @param {*} params 
 * @returns 
 */
export function updateOrg(params) {
    return axios.post(env.host.base + '/tansci/org/update', params);
}

/**
 * 组织角色详情
 * @param {*} params 
 * @returns 
 */
export function orgRoleInfo(params) {
    return axios.get(env.host.base + '/tansci/role/orgRoleInfo', { params: params });
}

/**
 * 分配组织角色
 * @param {*} params 
 * @returns 
 */
export function orgRoleAdd(params) {
    return axios.post(env.host.base + '/tansci/role/orgRoleAdd', params);
}

// ==============日志===============================================

/**
 * 操作日志分页
 * @param {*} params 
 * @returns 
 */
export function logInfoPage(params) {
    return axios.get(env.host.base + '/tansci/log/logInfoPage', { params: params });
}

/**
 * 异常日志分页
 * @param {*} params 
 * @returns 
 */
export function logErrorPage(params) {
    return axios.get(env.host.base + '/tansci/log/logErrorPage', { params: params });
}

// ====================任务管理=========================
/**
 * 任务分页
 * @param {*} params 
 * @returns 
 */
export function taskPage(params) {
    return axios.get(env.host.base + '/tansci/taskConfig/page', { params: params });
}

/**
 * 删除任务
 * @param {*} params 
 * @returns 
 */
export function delTask(params) {
    return axios.get(env.host.base + '/tansci/taskConfig/del', { params: params });
}

/**
 * 添加任务
 * @param {*} params 
 * @returns 
 */
export function saveTask(params) {
    return axios.post(env.host.base + '/tansci/taskConfig/save', params);
}

/**
 * 修改任务
 * @param {*} params 
 * @returns 
 */
export function updateTask(params) {
    return axios.post(env.host.base + '/tansci/taskConfig/update', params);
}

/**
 * 任务执行日志分页
 * @param {*} params 
 * @returns 
 */
export function taskLogPage(params) {
    return axios.get(env.host.base + '/tansci/taskLog/page', { params: params });
}

/**
 * 清空日志
 * @param {*} params 
 * @returns 
 */
export function taskLogClear(params) {
    return axios.get(env.host.base + '/tansci/taskLog/clear', { params: params });
}

// ====================采集管理=========================
/**
 * 采集任务分页
 * @param {*} params 
 * @returns 
 */
export function collectPage(params) {
    return axios.get(env.host.base + '/tansci/collect/page', { params: params });
}
/**
 * 采集数据分页
 * @param {*} params 
 * @returns 
 */
export function collectDataPage(params) {
    return axios.get(env.host.base + '/tansci/collect/dataPage', { params: params });
}
/**
 * 导出采集数据分页
 * @param {*} params 
 * @returns 
 */
export function exportTxt(params) {
    return axios({
        method: 'get',
        url: env.host.base + '/tansci/collect/exportTxt',
        responseType: 'blob',
        params: params
      });

}

export function exportSrt(params) {
    return axios({
        method: 'get',
        url: env.host.base + '/tansci/collect/exportSrt',
        responseType: 'blob',
        params: params
      });

}
export function exportWAV(params) {
    return axios({
        method: 'get',
        url: env.host.base + '/tansci/collect/exportWAV',
        responseType: 'blob',
        params: params
      });

}
/**
 * 采集管理
 * @param {*} params 
 * @returns 
 */
export function collect(params) {
    return axios.get(env.host.base + '/tansci/collect/record', { params: params });
}
/**
 * 采集管理
 * @param {*} params 
 * @returns 
 */
export function sendNote(params) {
    return axios.get(env.host.base + '/tansci/collect/sendNote', { params: params });

}

export function updateNote(params) {
    return axios.get(env.host.base + '/tansci/collect/updateNote', { params: params });
}



/**
 * 创建或更新
 * @param {*} params 
 * @returns 
 */
export function createNote(params) {
    return axios.get(env.host.base + '/tansci/collect/createNote', { params: params });
  
}

/**
 * 切割
 * @param {*} params 
 * @returns 
 */
export function cuttingRecord(params) {
    return axios.post(env.host.base + '/tansci/collect/cuttingRecord', params);
}
/**
 * 合并
 * @param {*} params 
 * @returns 
 */
export function mergeRecord(params) {
    return axios.post(env.host.base + '/tansci/collect/mergeRecord', params);
}
/**
 * 删除管理
 * @param {*} params 
 * @returns 
 */
export function deleteNote(params) {
    return axios.post(env.host.base + '/tansci/collect/deleteNote', params);
}

/**
 * 采集管理
 * @param {*} params 
 * @returns 
 */
export function getMyData(params) {
    // return axios.get(env.host.base + '/tansci/collect/record', params);
    // return axios.post(env.host.base + '/tansci/taskConfig/save', params);

    return axios.get(env.host.base + '/tansci/collect/getMyData', { params: params });
    // return axios({
    //     method: 'get',
    //     url: env.host.base + '/tansci/collect/record',
    //     params: params
    //   });
}

/**
 * 根据DOCID获取标记列表
 * @param {*} params 
 * @returns 
 */
export function getMarksByDocId(params) {
    return axios.get(env.host.base + '/tansci/collect/getMarksByDocId', { params: params });
}

/**
 * 参数数据分页
 * @param {*} params 
 * @returns 
 */
export function paramPage(params) {
    return axios.get(env.host.base + '/tansci/recordParam/page', { params: params });
}

export function updateParam(params) {
    return axios.get(env.host.base + '/tansci/recordParam/updateParam', { params: params });
}

/**
 * 创建
 * @param {*} params 
 * @returns 
 */
export function createParam(params) {
    return axios.get(env.host.base + '/tansci/recordParam/createParam', { params: params });
  
}

/**
 * 删除管理
 * @param {*} params 
 * @returns 
 */
export function deleteParam(params) {
    return axios.post(env.host.base + '/tansci/recordParam/deleteParam', params);
}

/** ===chatGPT====start */
/**
 * chatGPT page
 * @param {*} params 
 * @returns 
 */
export function chatGPTPage(params) {
    return axios.get(env.host.base + '/tansci/chatGPT/page', { params: params });
}
/**
 * 导出采集数据分页
 * @param {*} params 
 * @returns 
 */
export function exportChatGPTTxt(params) {
    return axios({
        method: 'get',
        url: env.host.base + '/tansci/chatGPT/exportChatGPTTxt',
        responseType: 'blob',
        params: params
      });

}

/**
 * 删除管理
 * @param {*} params 
 * @returns 
 */
export function deleteChatGPT(params) {
    return axios.post(env.host.base + '/tansci/chatGPT/deleteChatGPT', params);
}

/** ===chatGPT====end */
