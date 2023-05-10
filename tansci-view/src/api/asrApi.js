import axios from '../utils/axios'
import env from '../config/env'

/**
 * 获取Azure Token
 * @param {*} params 
 * @returns 
 */
export function getAzureToken() {
    const token = localStorage.getItem('token');
    if(!token){
        return Promise.reject()
    }
    const url = `${env.host.base}/tansci/collect/getAzureToken`
    // return axios.post(url, {},{
    //     headers: {
    //         'Content-Type': 'application/x-www-form-urlencoded;',
    //         'Authorization':`Bearer ${token}`
    //     },
    // });
    return new Promise((resolve,reject)=>{
        const a = new XMLHttpRequest();
        a.open("POST", url);
        a.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        a.setRequestHeader("Authorization", 'Bearer '+token);
        a.send();
        a.onload = function () {
            resolve(this.responseText)
        }
    })
    
}


/**
 * ASR 创建Note
 * @param {*} params 
 * @returns 
 */
export function createNote({sessionId,remark}) {
    const token = localStorage.getItem('token');
    if(!token){
        return Promise.reject()
    }
    const url = `${env.host.base}/tansci/collect/createNote?docId=${sessionId}&remark=${remark}`
    return axios.get(url, {},{
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded;',
            'Authorization':`Bearer ${token}`
        },
    });
}

/**
 * ASR 发送Note
 * @param {*} params 
 * @returns 
 */
export function sendNote({resultText,language,sessionId,offset,duration}) {
    const token = localStorage.getItem('token');
    if(!token){
        return Promise.reject()
    }
    const url = `${env.host.base}/tansci/collect/sendNote?resultText=${resultText}&language=${language}&docId=${sessionId}&offset=${offset}&duration=${duration}`
    return axios.get(url, {},{
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded;',
            'Authorization':`Bearer ${token}`
        },
    });
}

/**
 * 上传录音
 * @param {*} params 
 * @returns 
 */
export function uploadFile({fileName,mp3Blob,sessionId}) {
    const token = localStorage.getItem('token');
    if(!token){
        return Promise.reject()
    }
    const url = `${env.host.base}/tansci/collect/upload`
    const data = new FormData()
    const mp3Name = encodeURIComponent(fileName + '.mp3');
    data.append('fileName', mp3Name);
    data.append('file', mp3Blob);
    data.append('docId', sessionId);
    // return axios.post(url, data,{
    //     headers: {
    //         'Authorization':`Bearer ${token}`
    //     },
    // });
    return new Promise((resolve,reject)=>{
        const xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4 && xhr.status == 200) {
                console.log('上传响应：'+xhr.responseText);
                resolve(xhr.responseText)
            }
        };
        xhr.open('POST', url);
        xhr.setRequestHeader("Authorization", 'Bearer ' + token);
        xhr.send(data);
    })
    
}