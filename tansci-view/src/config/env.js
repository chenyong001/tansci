const host = {
    base:'',// //opencast.tet-ai.com.sg
    asrBase:''
}
console.log('env.MODE-',import.meta.env.MODE)
if(import.meta.env.MODE === "development"){
    host.base = ''
    host.asrBase = ''
}
export default {
    host
}