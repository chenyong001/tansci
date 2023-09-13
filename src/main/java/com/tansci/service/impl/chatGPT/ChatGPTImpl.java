package com.tansci.service.impl.chatGPT;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tansci.domain.system.ChatGPT;
import com.tansci.domain.system.SysDic;
import com.tansci.domain.system.SysUser;
import com.tansci.domain.system.dto.SysDicDto;
import com.tansci.enums.QuestionTypeEnum;
import com.tansci.mapper.chatGPT.ChatGPTMapper;
import com.tansci.service.chatGPT.ChatGPTService;
import com.tansci.service.system.SysDicService;
import com.tansci.service.system.SysUserService;
import com.tansci.utils.CollectionUtil;
import com.tansci.utils.DateUtil;
import com.tansci.utils.HttpClientUtil;
import com.tansci.utils.SecurityUserUtils;
import com.tansci.utils.SystemUtil;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author chenyong
 * @Date 2023/2/9 14:50
 * @Version 1.0
 */
@Slf4j
@Service
public class ChatGPTImpl extends ServiceImpl<ChatGPTMapper, ChatGPT> implements ChatGPTService {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysDicService sysDicService;
    String uri = "https://api.openai.com/v1/engines/text-davinci-003/completions";

    String apiKey;
    String azureApiKey;
    /**
     * chatGPT 用户消息集合
     */
    private Map<String, List<ChatGPTImpl.ChatGPTMessage>> messagesMap = new HashMap<>();
//  private Map<String, List<ChatGPTImpl.ChatGPTMessage>> messagesLamsMap = new HashMap<>();

    @Override
    public String send(String prompt, String speechText) {
        String result = "";
        try {
            if (StringUtils.isBlank(apiKey)) {
                SysDicDto sysDicDto = new SysDicDto();
                sysDicDto.setKeyword("chat_gpt_apikey");
                List<SysDic> sysDics = sysDicService.dicList(sysDicDto);
                if (CollectionUtil.isEmpty(sysDics)) {
                    log.warn("chatGPT apikey is not get!!!");
                    return result;
                }
                apiKey = sysDics.get(0).getRemarks();
            }

            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost(uri);
            request.addHeader("Content-Type", "application/json");
            request.addHeader("Authorization", "Bearer " + apiKey);
            JSONObject requestBody = new JSONObject();
            requestBody.put("prompt", prompt);
            requestBody.put("max_tokens", 1024);
            requestBody.put("temperature", 0.7);
            requestBody.put("n", 1);

            StringEntity requestEntity = new StringEntity(requestBody.toString(), "UTF-8");
            request.setEntity(requestEntity);

            HttpResponse response = httpClient.execute(request);
            String responseString = EntityUtils.toString(response.getEntity());
            JSONObject responseJson = new JSONObject(responseString);
            JSONArray choices = responseJson.getJSONArray("choices");
            result = choices.getJSONObject(0).getString("text");
            result = result.trim();
        } catch (Exception e) {
            log.error("Exception:", e);
        } finally {
            //      添加记录
            ChatGPT chatGPT = new ChatGPT();
            chatGPT.setUserId(SecurityUserUtils.getUser().getId());
            chatGPT.setPrompt(prompt);
            if (StringUtils.isNotBlank(speechText)) {
                chatGPT.setSpeechText(speechText);
            }
            chatGPT.setContent(result);
            chatGPT.setCreateTime(new Date());
            chatGPT.setUpdateTime(new Date());
            save(chatGPT);
            return result;
        }
    }

    @Override
    public String send2OpenAi(String prompt, String speechText, String system) {
        String result = "";
        String uri = "https://api.openai.com/v1/chat/completions";
        try {
            if (StringUtils.isBlank(apiKey)) {
                SysDicDto sysDicDto = new SysDicDto();
                sysDicDto.setKeyword("chat_gpt_apikey");
                List<SysDic> sysDics = sysDicService.dicList(sysDicDto);
                if (CollectionUtil.isEmpty(sysDics)) {
                    log.warn("chatGPT apikey is not get!!!");
                    return result;
                }
                apiKey = sysDics.get(0).getRemarks();
            }

            Map<String, String> headerParams = Maps.newHashMap();
            headerParams.put("Content-Type", "application/json");
            headerParams.put("Authorization", "Bearer " + apiKey);
            Map<String, Object> bodyParams = Maps.newHashMap();
            bodyParams.put("model", "gpt-3.5-turbo");
            List<ChatGPTImpl.ChatGPTMessage> messages = new ArrayList<>();
            if (StringUtils.isBlank(system)) {
                system = "You are a helpful assistant.";
            }
            messages.add(new ChatGPTImpl.ChatGPTMessage("system", system));
            messages.add(new ChatGPTImpl.ChatGPTMessage("user", prompt));
            bodyParams.put("messages", messages);
            bodyParams.put("max_tokens", 1024);
            bodyParams.put("n", 1);
            String responseString = HttpClientUtil.sendPostRequest2(uri, headerParams, bodyParams);

            JSONObject responseJson = new JSONObject(responseString);
            JSONArray choices = responseJson.getJSONArray("choices");
            JSONObject message = choices.getJSONObject(0).getJSONObject("message");
            result = message.getString("content");
            result = result.trim();

        } catch (Exception e) {
            log.error("Exception:", e);
        } finally {
            //      添加记录
            ChatGPT chatGPT = new ChatGPT();
            chatGPT.setUserId(SecurityUserUtils.getUser().getId());
            chatGPT.setPrompt(prompt);
            if (StringUtils.isNotBlank(speechText)) {
                chatGPT.setSpeechText(speechText);
            }
            chatGPT.setContent(result);
            chatGPT.setCreateTime(new Date());
            chatGPT.setUpdateTime(new Date());
            save(chatGPT);
            return result;
        }
    }

    @Override
    public String send2Azure(String prompt, String speechText, String system) {
        String result = "";
        String uri = "https://tsigpt.openai.azure.com/openai/deployments/tsigpt4/chat/completions?api-version=2023-03-15-preview";
        String userId = SecurityUserUtils.getUser().getId();
        try {
            if (StringUtils.isBlank(azureApiKey)) {
                SysDicDto sysDicDto = new SysDicDto();
                sysDicDto.setKeyword("chat_gpt_azure_apikey");
                List<SysDic> sysDics = sysDicService.dicList(sysDicDto);
                if (CollectionUtil.isEmpty(sysDics)) {
                    log.warn("chatGPT apikey is not get!!!");
                    return result;
                }
                azureApiKey = sysDics.get(0).getRemarks();
            }

            Map<String, String> headerParams = Maps.newHashMap();
            headerParams.put("Content-Type", "application/json");
            headerParams.put("api-key", azureApiKey);
            Map<String, Object> bodyParams = Maps.newHashMap();
            //      bodyParams.put("model", "gpt-3.5-turbo");
            List<ChatGPTImpl.ChatGPTMessage> messages = new ArrayList<>();
            if (StringUtils.isBlank(system)) {
                system = "You are a helpful assistant.";
            }
            if (messagesMap.containsKey(userId)) {
                messages = messagesMap.get(userId);
            } else {
                messages.add(new ChatGPTImpl.ChatGPTMessage("system", system));
            }
            messages.add(new ChatGPTImpl.ChatGPTMessage("user", prompt));
            bodyParams.put("messages", messages);
            bodyParams.put("max_tokens", 1024);
            bodyParams.put("temperature", 0);
            String responseString = HttpClientUtil.sendPostRequest2(uri, headerParams, bodyParams);

            JSONObject responseJson = new JSONObject(responseString);
            JSONArray choices = responseJson.getJSONArray("choices");
            JSONObject message = choices.getJSONObject(0).getJSONObject("message");
            result = message.getString("content");
            result = result.trim();

            messagesMap.put(userId, messages);

        } catch (Exception e) {
            log.error("Exception:", e);
        } finally {
            //      添加记录
            ChatGPT chatGPT = new ChatGPT();
            chatGPT.setUserId(userId);
            chatGPT.setPrompt(prompt);
            if (StringUtils.isNotBlank(speechText)) {
                chatGPT.setSpeechText(speechText);
            }
            chatGPT.setContent(result);
            chatGPT.setCreateTime(new Date());
            chatGPT.setUpdateTime(new Date());
            save(chatGPT);
            return result;
        }
    }

    /**
     * 题型：单选、多选、判断题、简答题
     * 题数：10
     * 参考答案：A
     * 反馈：答案解释
     * 语言：回答的语言 questionType questionNum questionLanguage
     *
     * @param prompt
     * @param speechText
     * @param system
     * @return
     */
    @Override
    public String send2AzureLams(String prompt, String speechText, String system, String questionType, String questionNum,
                                 String questionLanguage) {

        String lamsFileName = "出题失败，请重试！！！";
        String result = "";
        String uri = "https://tsigpt.openai.azure.com/openai/deployments/tsigpt4/chat/completions?api-version=2023-03-15-preview";
        String userId = SecurityUserUtils.getUser().getId();
        try {
            if (StringUtils.isBlank(azureApiKey)) {
                SysDicDto sysDicDto = new SysDicDto();
                sysDicDto.setKeyword("chat_gpt_azure_apikey");
                List<SysDic> sysDics = sysDicService.dicList(sysDicDto);
                if (CollectionUtil.isEmpty(sysDics)) {
                    log.warn("chatGPT apikey is not get!!!");
                    return result;
                }
                azureApiKey = sysDics.get(0).getRemarks();
            }

            Map<String, String> headerParams = Maps.newHashMap();
            headerParams.put("Content-Type", "application/json");
            headerParams.put("api-key", azureApiKey);
            Map<String, Object> bodyParams = Maps.newHashMap();
            //      bodyParams.put("model", "gpt-3.5-turbo");
//      List<ChatGPTImpl.ChatGPTMessage> messages = new ArrayList<>();
//      if (StringUtils.isBlank(system)) {
//        system = "你是个擅长根据文章提出选择题和答案的专家.";
//      }
//      if (messagesLamsMap.containsKey(userId)) {
//        messages = messagesLamsMap.get(userId);
//        messages.add(new ChatGPTImpl.ChatGPTMessage("user", prompt));
//      } else {
            List<ChatGPTImpl.ChatGPTMessage> messages = getConfig(questionType, questionNum, questionLanguage, prompt);
            //
            //        messages.add(new ChatGPTImpl.ChatGPTMessage("system", "根据文章提出选择题和答案"));
            //        messages.add(new ChatGPTImpl.ChatGPTMessage("user", "根据文章提出选择题和答案"));
            //        messages.add(new ChatGPTImpl.ChatGPTMessage("assistant",
            //            "以JSON格式返回,每题4个选项A、B、C、D,JSON包含一个questions数组，每个json对象包含title,options,answer,feedback."));
//      }
//      messages.add(new ChatGPTImpl.ChatGPTMessage("user", prompt));
            bodyParams.put("messages", messages);
            bodyParams.put("max_tokens", 1024);
            bodyParams.put("temperature", 0);
            String responseString = HttpClientUtil.sendPostRequest2(uri, headerParams, bodyParams);
            System.out.println("==========responseString=" + responseString);

            StringBuilder sb = analysisResult(questionType, responseString);
            result = sb.toString();
            log.info(result);
            //      将结果转为lams能接受的docx文档
            lamsFileName = convert2lams(result, userId);

            //      messagesLamsMap.put(userId, messages);
            //      private String feedback;
            //      private String feedbackOnCorrect;
            //      private String feedbackOnPartiallyCorrect;
            //      private String feedbackOnIncorrect;

        } catch (Exception e) {
            log.error("Exception:", e);
        } finally {
            //      添加记录
            ChatGPT chatGPT = new ChatGPT();
            chatGPT.setUserId(userId);
            chatGPT.setPrompt(prompt);
            if (StringUtils.isNotBlank(speechText)) {
                chatGPT.setSpeechText(speechText);
            }
            chatGPT.setContent(result);
            chatGPT.setCreateTime(new Date());
            chatGPT.setUpdateTime(new Date());
            save(chatGPT);
            return lamsFileName;
        }
    }

    private static StringBuilder analysisResult(String questionType, String responseString) throws JSONException {
        JSONObject responseJson = new JSONObject(responseString);
        StringBuilder sb = new StringBuilder();
        JSONArray choices = responseJson.getJSONArray("choices");
        JSONObject message = choices.getJSONObject(0).getJSONObject("message");
        String contentStr = message.getString("content");
        JSONObject contentJson = new JSONObject(contentStr);
        JSONArray questions = contentJson.getJSONArray("questions");


        String optionA = "";
        String optionB = "";
        String optionC = "";
        String optionD = "";
        for (int i = 0; i < questions.length(); i++) {
            JSONObject jsonObject = questions.getJSONObject(i);
            String title = jsonObject.getString("title");
            sb.append(title).append("\n");
            if (!questionType.equalsIgnoreCase(QuestionTypeEnum.SHORT_ANSWER_CHOICE.getType())) {
                //      简答题
                JSONObject optionObject = jsonObject.getJSONObject("options");
                optionA = optionObject.getString("A");
                optionB = optionObject.getString("B");
                try {
                    optionC = optionObject.getString("C");
                    optionD = optionObject.getString("D");
                } catch (Exception e) {

                }
//      optionC = optionObject.getString("C");
//      optionD = optionObject.getString("D");
                String answer = jsonObject.getString("answer");
                if (answer.length() > 1) {
//        如果超过一个答案，则处理为多项选择题
                    answer = Arrays.stream(answer.split("")).distinct().collect(Collectors.joining(","));
                }
                sb.append("A).").append(optionA).append("\n");
                sb.append("B).").append(optionB).append("\n");
                if (StringUtils.isNotBlank(optionC)) {
                    sb.append("C).").append(optionC).append("\n");
                }
                if (StringUtils.isNotBlank(optionD)) {
                    sb.append("D).").append(optionD).append("\n");
                }
                sb.append("Answer: ").append(answer).append("\n");

            }
            String feedback = jsonObject.getString("feedback");
            sb.append("Feedback: ").append(feedback).append("\n");
            String lo = jsonObject.getString("lo");
            sb.append("LO: ").append(lo).append("\n");

            //只有题型为TF（判断题），才有正确和错误反馈
        }
        return sb;
    }

    private List<ChatGPTMessage> getConfig(String questionType, String questionNum,
                                           String questionLanguage, String prompt) {
        List<ChatGPTMessage> messages = new ArrayList<>();
        if (questionType.equalsIgnoreCase(QuestionTypeEnum.SINGLE_CHOICE.getType())) {
//      单选题
            messages.add(new ChatGPTMessage("system", "你是个擅长根据文章提出选择题和答案的专家"));
            messages.add(new ChatGPTMessage("user", "请用" + questionLanguage + ",根据文章提出" + questionNum + "道单项选择题、答案和考点(lo)"));
            messages.add(new ChatGPTMessage("assistant",
                    "以JSON格式返回,每题4个选项A、B、C、D,JSON包含一个questions数组，每个json对象包含title、options、answer、feedback、lo."));
            messages.add(new ChatGPTMessage("user", prompt));

        } else if (questionType.equalsIgnoreCase(QuestionTypeEnum.MULTIPLE_CHOICE.getType())) {
//      多选题
            messages.add(new ChatGPTMessage("system", "你是一名助手，旨在根据文章提出多项选择题、答案和考点(lo)"));
            messages.add(new ChatGPTMessage("user", "请帮我出一道多项选择题"));
            messages.add(new ChatGPTMessage("assistant",
                    "题目：以下哪些国家是世界上面积前三的国家？ A. 中国 B. 美国 C. 加拿大 D. 俄罗斯 答案：ABC"));
            String suffix = "---请用" + questionLanguage + ",我需要的是多项选择题，以JSON格式返回,JSON包含一个questions数组，每个json对象包含title、options、answer、feedback、lo,答案必须在2个选项以上，每题4个选项A、B、C、D。请重新出" + questionNum + "道多项选择题";
            messages.add(new ChatGPTMessage("user", prompt + suffix));

        } else if (questionType.equalsIgnoreCase(QuestionTypeEnum.TF_CHOICE.getType())) {
//      判断题
            messages.add(new ChatGPTMessage("system", "你是个擅长根据文章提出问题和答案的专家"));
            messages.add(new ChatGPTMessage("user", "请用" + questionLanguage + ",根据文章提出" + questionNum + "道判断题、答案和考点(lo)"));
            messages.add(new ChatGPTMessage("assistant",
                    "以JSON格式返回,JSON包含一个questions数组，每个json对象包含title、options、answer、feedback、lo,选项为‘true’和‘false’"));
            messages.add(new ChatGPTMessage("user", prompt));
        } else if (questionType.equalsIgnoreCase(QuestionTypeEnum.SHORT_ANSWER_CHOICE.getType())) {
//      简答题
            messages.add(new ChatGPTMessage("system", "你是个擅长根据文章提出问题和答案的专家"));
            messages.add(new ChatGPTMessage("user", "请用" + questionLanguage + ",根据文章提出" + questionNum + "道简答题和考点(lo)"));
            messages.add(new ChatGPTMessage("assistant",
                    "以JSON格式返回,JSON包含一个questions数组，每个json对象包含title,lo,feedback."));
            messages.add(new ChatGPTMessage("user", prompt));
        }
        return messages;
    }

    private String convert2lams(String result, String userId) throws IOException {
        //      1、保存文件为txt
        //      2、调用python脚本转换为docx，返回下载链接

        File txtFile = null;
        String resultName = null;
        try {
            // 准备文件夹,获取项目中upload文件夹的路径
            File directory = new File("");//设定为当前文件夹
            String parentDir =
                    directory.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources"
                            + File.separator + "static" + File.separator + "uploadFile";

            System.out.println("\tpath=" + parentDir);
            File parent = new File(parentDir);
            if (!parent.exists()) {
                parent.mkdirs();
            }
            String txtFileName = userId + "_" + DateUtil.date2Str(new Date(), DateUtil.FORMAT_YYYYMMDDHHMMSS) + ".txt";
            String txtFileNamePath = parent + File.separator + txtFileName;
            //      1、保存文件为txt
            txtFile = new File(txtFileNamePath);
            FileUtils.writeByteArrayToFile(txtFile, result.getBytes());
            resultName = userId + "_" + DateUtil.date2Str(new Date(), DateUtil.FORMAT_YYYYMMDDHHMMSS) + ".docx";
            String resultFilePath = parent + File.separator + resultName;
            //2、调用python脚本转换为docx，返回下载链接
            if (!convert2py(txtFile.getAbsolutePath(), resultFilePath)) {
                //        txtFile.delete();
                resultName = null;
                //不成功
                return resultName;
            }
        } catch (Exception e) {
            log.error("Exception:", e);
        } finally {
            if (Objects.nonNull(txtFile)) {
                txtFile.delete();
            }
        }

        return resultName;
    }

    boolean convert2py(String fileName, String targetName) {
        Process proc;
        String filePath = null;
        boolean result = true;
        try {

            //    String fileName = docId + "_" + System.currentTimeMillis() + "_" + (int) (Math.random() * 100) + ".txt";

            //      jsonStr写入文件，由py文件读取
            //    String[] args1;
            StringBuffer args2 = new StringBuffer();
            //    String keywordsList = getListByType(docId, 1);
            //    String stopwordsList = getListByType(docId, 0);
            //    String num = getConfigNum(docId);
            if (SystemUtil.isWindows()) {
                //      filePath = "E:\\tansci\\py/" + fileName;
                //      FileUtils.write(new File(filePath), jsonStr, "utf-8");
                //        args1 = new String[] { "python", "E:\\tansci\\py/analyseRecord_windows.py", "--filePath=" + filePath,
                //            "--num=" + num, "--keywordsList" + keywordsList, "--stopwordsList" + stopwordsList };
                args2.append("python  E://work//tansci//py//convert2.0.py  --fileName ").append(fileName)
                        //            .append("\"")
                        .append(" --targetName ").append(targetName)
                //            .append("\"")
                ;
                //      if (org.apache.commons.lang3.StringUtils.isNotBlank(keywordsList)) {
                //        args2.append(" --keywordsList ").append(keywordsList);
                //      }
                //      if (org.apache.commons.lang3.StringUtils.isNotBlank(stopwordsList)) {
                //        args2.append(" --stopwordsList").append(stopwordsList);
                //      }

                //        args2="python E://tansci//py//analyseRecord_windows.py  --filePath=E:\\tansci\\py\\4658E7EC4A364AA6BF394F64E7716917_1670309430243_65.txt "
                //                  + " --num=20 --keywordsList 开始 --stopwordsList 腾讯 生活";
            } else {
                //      filePath = "/temp/" + fileName;
                //      FileUtils.write(new File(filePath), jsonStr, "utf-8");
                //      //        args1 = new String[] { "python", "/analyseRecord_linux.py", "--filePath=" + filePath };
                args2.append("python  /convert2.0.py  --fileName  ").append(fileName)
                        //            .append("\'")
                        .append(" --targetName ").append(targetName)
                //            .append("\'")
                ;
                //      if (org.apache.commons.lang3.StringUtils.isNotBlank(keywordsList)) {
                //        args2.append(" --keywordsList ").append(keywordsList);
                //      }
                //      if (org.apache.commons.lang3.StringUtils.isNotBlank(stopwordsList)) {
                //        args2.append(" --stopwordsList").append(stopwordsList);
                //      }
            }

            long startTime = System.currentTimeMillis();
            System.out.println("exec cmd:" + args2.toString());
            proc = Runtime.getRuntime().exec(args2.toString());
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }

            BufferedReader error = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
            String lineerror;
            while ((lineerror = error.readLine()) != null) {
                System.out.println(lineerror);
            }
            error.close();

            in.close();
            int code = proc.waitFor();
            long endTime = System.currentTimeMillis();
            if (code == 0) {
                log.info("执行脚本成功,times={}s", (endTime - startTime) / 1000f);
            } else {
                log.info("执行脚本失败,code={code},times={}s", (endTime - startTime) / 1000f);
            }
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public IPage<ChatGPT> page(Page page, ChatGPT chatGPT) {

        SysUser user = SecurityUserUtils.getUser();
        if (!Objects.equals(0, user.getType())) {
            chatGPT.setOrgIds(user.getOrgIds());
        }
        if (Objects.equals(user.getType(), 2)) {
            //      如果类型是普通用户，才通过账号ID过滤
            chatGPT.setUserId(user.getId());
        }

        IPage<ChatGPT> iPage = this.baseMapper.page(page, chatGPT);
        //    IPage<ChatGPT> iPage = this.baseMapper.selectPage(page,
        //        Wrappers.<ChatGPT>lambdaQuery().eq(ChatGPT::getUserId, SecurityUserUtils.getUser().getId())
        //            .eq(ChatGPT::getStatus, 1)
        //            .orderByDesc(ChatGPT::getCreateTime));

        iPage.getRecords().forEach(item -> {
            //      遍历查询每个会话的用户信息
            SysUser sysUser = sysUserService.selectByUserId(item.getUserId());
            item.setUserName(sysUser == null ? "" : sysUser.getUsername());
        });
        return iPage;
    }

    @Override
    public boolean save(ChatGPT chatGPT) {
        this.baseMapper.insert(chatGPT);
        return true;
    }

    @Override
    public boolean del(ChatGPT chatGPT) {
        int row = this.baseMapper.deleteById(chatGPT.getId());
        if (row > 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<ChatGPT> selectList(ChatGPT chatGPT) {
        LambdaQueryWrapper<ChatGPT> eq = Wrappers.<ChatGPT>lambdaQuery()
                .eq(ChatGPT::getUserId, SecurityUserUtils.getUser().getId()).eq(ChatGPT::getStatus, 1)
                .orderByDesc(ChatGPT::getCreateTime);
        return this.baseMapper.selectList(eq);
    }

    @Data
    static class ChatGPTMessage implements Serializable {
        private static final long serialVersionUID = -8593133888176767391L;
        String role;
        String content;

        public ChatGPTMessage() {
        }

        public ChatGPTMessage(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }

}
