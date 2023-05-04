import axios from '../utils/axios'

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
    const url = `https://opencast.tsi.edu.sg/tansci/chatGPT/send2OpenAi?prompt=${encodeURIComponent(promptText)}&speechText=${encodeURIComponent(speechText)}`
    return axios.post(url, {},{
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
            'Authorization':`Bearer ${token}`
        },
    });
}