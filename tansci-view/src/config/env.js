const host = {
    base:'',// //opencast.tsi.edu.sg
    asrBase:'//opencast.tsi.edu.sg'
}
console.log('env.MODE-',import.meta.env.MODE)
if(import.meta.env.MODE === "development"){
    host.base = ''
    host.asrBase = ''
}
export default {
    host
}