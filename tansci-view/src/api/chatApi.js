import axios from '../utils/axios'
import env from '../config/env'
/**
 * chatGPT
 * @param {*} params 
 * @returns 
 */
export function send(promptText='',speechText='') {
    const token = localStorage.getItem('token');
    if(!token){
        return Promise.reject()
    }
    const url = `${env.host.base}/tansci/chatGPT/send2OpenAi?prompt=${encodeURIComponent(promptText)}&speechText=${encodeURIComponent(speechText)}`
    return axios.post(url, {},{
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
            'Authorization':`Bearer ${token}`
        },
    });
}